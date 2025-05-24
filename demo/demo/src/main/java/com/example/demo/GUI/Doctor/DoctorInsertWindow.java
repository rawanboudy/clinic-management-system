package com.example.demo.GUI.Doctor;

import javax.swing.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.example.demo.Config.Doctor.DBimpDoctor;
import com.example.demo.Config.Doctor.Doctor;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class DoctorInsertWindow extends JFrame implements ActionListener {
    private JTextField nameField, emailField, phoneField;
    private JButton insertButton;
    private final DBimpDoctor dbimpDoctor;
     private final ApplicationContext context;

    @Autowired
    public DoctorInsertWindow(DBimpDoctor dbimpDoctor, ApplicationContext context) {
        this.dbimpDoctor = dbimpDoctor;
        this.context = context;
        setTitle("Insert Doctor");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create a panel for the form fields
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        // Create labels and text fields for doctor details
        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();
        JLabel phoneLabel = new JLabel("Phone:");
        phoneField = new JTextField();

        // Create insert button without icon
        insertButton = new JButton("Insert");
        insertButton.addActionListener(this);

        // Add components to the form panel
        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(emailLabel);
        formPanel.add(emailField);
        formPanel.add(phoneLabel);
        formPanel.add(phoneField);
        formPanel.add(new JLabel()); // Empty label for alignment
        formPanel.add(insertButton);

        // Add form panel to the frame
        add(formPanel, BorderLayout.CENTER);

        // Set logo at the top
        ImageIcon logoIcon = resizeIcon("C:\\Users\\Lenovo\\Desktop\\dscs\\component project last\\demo\\demo\\src\\main\\java\\com\\example\\demo\\pictures\\add.png", 100, 100);
        JLabel logoLabel = new JLabel(logoIcon);
        add(logoLabel, BorderLayout.NORTH);

        setSize(500, 250); // Set the size of the window
        setLocationRelativeTo(null); // Center the window

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
                // Retrieve doctor details from text fields
                String name = nameField.getText();
                String email = emailField.getText();
                String phone = phoneField.getText();

                // Create a new Doctor object with the retrieved details
                Doctor newDoctor = new Doctor();
                newDoctor.setName(name);
                newDoctor.setEmail(email);
                newDoctor.setPhone(phone);

                // Call the insert method of your DoctorDAO implementation to insert the new doctor
                dbimpDoctor.insert(newDoctor);

                // Show a success message
                JOptionPane.showMessageDialog(this, "Doctor inserted successfully", "Insert Doctor", JOptionPane.INFORMATION_MESSAGE);

                this.dispose();

                // Show the DoctorManagementWindow
                SwingUtilities.invokeLater(() -> new DoctorManagementWindow(dbimpDoctor,context));

            } catch (SQLException ex) {
                // Show an error message with detailed SQL exception information
                showErrorDialog("Error inserting doctor:", ex);
            }
        }
    }

    private void showErrorDialog(String message, SQLException ex) {
        String errorMessage = message + "\n" +
                "SQL State: " + ex.getSQLState() + "\n" +
                "Error Code: " + ex.getErrorCode() + "\n" +
                "Message: " + ex.getMessage();
        JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
