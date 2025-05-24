package com.example.demo.GUI.Patient;

import javax.swing.*;
import com.example.demo.Config.Patient.DBimpPatient;
import com.example.demo.Config.Patient.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class patientUpdateWindow extends JFrame implements ActionListener {
    private JTextField nameField, emailField, ageField;
    private JComboBox<String> genderComboBox; // Changed to JComboBox
    private JButton updateButton;
    private int patientId;
    private final DBimpPatient dbimpPatient;

    @Autowired
    public patientUpdateWindow(int patientId, DBimpPatient dbimpPatient) {
        this.patientId = patientId;
        this.dbimpPatient = dbimpPatient;

        setTitle("Update User");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create a panel for the logo
        JPanel logoPanel = new JPanel();
        ImageIcon logoIcon = resizeIcon("C:\\Users\\Lenovo\\Desktop\\dscs\\component project last\\demo\\demo\\src\\main\\java\\com\\example\\demo\\pictures\\update.png", 100, 100);
        JLabel logoLabel = new JLabel(logoIcon);
        logoPanel.add(logoLabel);

        // Create a panel for the form fields
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10)); // Updated grid layout for better spacing

        // Create labels and text fields for user details
        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();
        JLabel genderLabel = new JLabel("Gender:");
        // Create a combo box with the gender options
        genderComboBox = new JComboBox<>(new String[]{"Male", "Female"});
        JLabel ageLabel = new JLabel("Age:");
        ageField = new JTextField();

        // Create update button
        updateButton = new JButton("Update");
        updateButton.addActionListener(this);

        // Add components to the form panel
        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(emailLabel);
        formPanel.add(emailField);
        formPanel.add(genderLabel);
        formPanel.add(genderComboBox);
        formPanel.add(ageLabel);
        formPanel.add(ageField);
        formPanel.add(new JLabel()); // Empty label for alignment
        formPanel.add(updateButton);

        // Add panels to the frame
        add(logoPanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);

        // Fetch patient details to populate fields
        fetchPatientDetails();

        setSize(400, 400); // Set the size of the window
        setLocationRelativeTo(null); // Center the window

        setVisible(true);
    }

    private ImageIcon resizeIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }

    private void fetchPatientDetails() {
        try {
            Patient patient = dbimpPatient.getById(patientId);
            if (patient != null) {
                nameField.setText(patient.getName());
                emailField.setText(patient.getEmail());
                // Select the appropriate gender in the combo box
                genderComboBox.setSelectedItem(patient.getGender());
                ageField.setText(String.valueOf(patient.getAge()));
            } else {
                JOptionPane.showMessageDialog(this, "Patient not found", "Update User", JOptionPane.ERROR_MESSAGE);
                dispose();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error fetching patient details: " + ex.getMessage(), "Update User", JOptionPane.ERROR_MESSAGE);
            dispose();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == updateButton) {
            try {
                // Retrieve user details from text fields
                String name = nameField.getText();
                String email = emailField.getText();
                String gender = (String) genderComboBox.getSelectedItem(); // Get selected gender
                String ageStr = ageField.getText();
                int age = Integer.parseInt(ageStr);

                // Create a new Patient object with the retrieved details
                Patient updatedPatient = new Patient();
                updatedPatient.setId(patientId);
                updatedPatient.setName(name);
                updatedPatient.setEmail(email);
                updatedPatient.setGender(gender);
                updatedPatient.setAge(age);

                // Call the update method of your PatientDAO implementation to update the patient
                dbimpPatient.update(updatedPatient);

                // Show a success message
                JOptionPane.showMessageDialog(this, "Patient updated successfully", "Update User", JOptionPane.INFORMATION_MESSAGE);

                this.dispose();

                // Show the UserManagementWindow
                SwingUtilities.invokeLater(() -> new patientManagementWindow(dbimpPatient));

            } catch (SQLException ex) {
                // Show an error message with detailed SQL exception information
                String errorMessage = "Error updating patient:\n" +
                        "SQL State: " + ex.getSQLState() + "\n" +
                        "Error Code: " + ex.getErrorCode() + "\n" +
                        "Message: " + ex.getMessage();
                JOptionPane.showMessageDialog(this, errorMessage, "Update User - Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                // Show an error message if age cannot be parsed as an integer
                JOptionPane.showMessageDialog(this, "Invalid age: " + ageField.getText(), "Update User - Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
