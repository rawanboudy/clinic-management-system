package com.example.demo.GUI.Patient;

import javax.swing.*;
import com.example.demo.Config.Patient.DBimpPatient;
import com.example.demo.Config.Patient.Patient;
import com.example.demo.GUI.Patient.patientManagementWindow;
import org.springframework.beans.factory.annotation.Autowired;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class patientInsertWindow extends JFrame implements ActionListener {
    private JTextField nameField, emailField, ageField;
    private JComboBox<String> genderComboBox; // Changed to JComboBox
    private JButton insertButton;
    private final DBimpPatient dbimpPatient;

    @Autowired
    public patientInsertWindow(DBimpPatient dbimpPatient) {
        this.dbimpPatient = dbimpPatient;

        setTitle("Insert Patient");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel logoPanel = new JPanel();
        ImageIcon logoIcon = resizeIcon("C:\\Users\\Lenovo\\Desktop\\dscs\\component project last\\demo\\demo\\src\\main\\java\\com\\example\\demo\\pictures\\add.png", 100, 100);
        JLabel logoLabel = new JLabel(logoIcon);
        logoPanel.add(logoLabel);

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();
        JLabel genderLabel = new JLabel("Gender:");
        // Create a combo box with the gender options
        genderComboBox = new JComboBox<>(new String[]{"Male", "Female"});
        JLabel ageLabel = new JLabel("Age:");
        ageField = new JTextField();

        insertButton = new JButton("Insert");
        insertButton.addActionListener(this);

        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(emailLabel);
        formPanel.add(emailField);
        formPanel.add(genderLabel);
        formPanel.add(genderComboBox);
        formPanel.add(ageLabel);
        formPanel.add(ageField);
        formPanel.add(new JLabel());
        formPanel.add(insertButton);

        add(logoPanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);

        setSize(400, 400);
        setLocationRelativeTo(null);

        setVisible(true);
    }

    private ImageIcon resizeIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == insertButton) {
            try {
                String name = nameField.getText();
                String email = emailField.getText();
                String gender = (String) genderComboBox.getSelectedItem();
                String ageStr = ageField.getText().trim(); // Trim whitespace
                if (ageStr.isEmpty()) {
                    throw new NumberFormatException("Age is required");
                }
                int age = Integer.parseInt(ageStr);

                if (age < 0) {
                    throw new NumberFormatException("Age cannot be negative");
                }

                Patient newPatient = new Patient();
                newPatient.setName(name);
                newPatient.setEmail(email);
                newPatient.setGender(gender);
                // Set age here
                newPatient.setAge(age);

                dbimpPatient.insert(newPatient);

                JOptionPane.showMessageDialog(this, "Patient inserted successfully", "Insert Patient", JOptionPane.INFORMATION_MESSAGE);

                this.dispose();

                SwingUtilities.invokeLater(() -> new patientManagementWindow(dbimpPatient));

            } catch (SQLException ex) {
                String errorMessage = "Error inserting patient:\n" +
                        "SQL State: " + ex.getSQLState() + "\n" +
                        "Error Code: " + ex.getErrorCode() + "\n" +
                        "Message: " + ex.getMessage();
                JOptionPane.showMessageDialog(this, errorMessage, "Insert Patient - Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid age: " + ageField.getText(), "Insert Patient - Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
