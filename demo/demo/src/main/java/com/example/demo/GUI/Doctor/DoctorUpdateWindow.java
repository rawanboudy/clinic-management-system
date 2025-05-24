package com.example.demo.GUI.Doctor;

import javax.swing.*;
import com.example.demo.Config.Doctor.DBimpDoctor;
import com.example.demo.Config.Doctor.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class DoctorUpdateWindow extends JFrame implements ActionListener {
    private JTextField nameField, emailField, phoneField, passwordField, specialtyField;
    private JButton updateButton;
    private final int doctorId;
    private final DBimpDoctor dbimpDoctor;
    private final ApplicationContext context;

    @Autowired
    public DoctorUpdateWindow(int doctorId, DBimpDoctor dbimpDoctor, ApplicationContext context) {
        this.doctorId = doctorId;
        this.dbimpDoctor = dbimpDoctor;
        this.context = context;
        setTitle("Update Doctor");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create a panel for the form fields
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));

        // Create labels and text fields for doctor details
        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();
        JLabel phoneLabel = new JLabel("Phone:");
        phoneField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        JLabel specialtyLabel = new JLabel("Specialty:");
        specialtyField = new JTextField();

        // Create update button
        updateButton = new JButton("Update");
        updateButton.addActionListener(this);

        // Add components to the form panel
        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(emailLabel);
        formPanel.add(emailField);
        formPanel.add(phoneLabel);
        formPanel.add(phoneField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);
        formPanel.add(specialtyLabel);
        formPanel.add(specialtyField);
        formPanel.add(new JLabel());
        formPanel.add(updateButton);

        // Add form panel to the frame
        add(formPanel, BorderLayout.CENTER);

        // Set logo at the top
        ImageIcon logoIcon = resizeIcon("C:\\Users\\Lenovo\\Desktop\\dscs\\component project last\\demo\\demo\\src\\main\\java\\com\\example\\demo\\pictures\\update.png", 100, 100);
        JLabel logoLabel = new JLabel(logoIcon);
        add(logoLabel, BorderLayout.NORTH);

        setSize(500, 300);
        setLocationRelativeTo(null);
        setVisible(true);

        // Fetch doctor details
        fetchDoctorDetails();
    }

    private void fetchDoctorDetails() {
        try {
            // Retrieve doctor details from the database
            Doctor doctor = dbimpDoctor.getById(doctorId);
            if (doctor != null) {
                nameField.setText(doctor.getName());
                emailField.setText(doctor.getEmail());
                phoneField.setText(doctor.getPhone());
                passwordField.setText(doctor.getPassword());
                specialtyField.setText(doctor.getSpecialty());
            }
        } catch (SQLException ex) {
            showErrorDialog("Error fetching doctor details:", ex);
        }
    }

    private ImageIcon resizeIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == updateButton) {
            try {
                // Retrieve updated doctor details from text fields
                String name = nameField.getText();
                String email = emailField.getText();
                String phone = phoneField.getText();
                String password = passwordField.getText();
                String specialty = specialtyField.getText();

                // Create a new Doctor object with the updated details
                Doctor updatedDoctor = new Doctor();
                updatedDoctor.setId(doctorId);
                updatedDoctor.setName(name);
                updatedDoctor.setEmail(email);
                updatedDoctor.setPhone(phone);
                updatedDoctor.setPassword(password);
                updatedDoctor.setSpecialty(specialty);

                // Call the update method to update the doctor
                dbimpDoctor.update(updatedDoctor);

                // Show a success message
                JOptionPane.showMessageDialog(this, "Doctor updated successfully", "Update Doctor", JOptionPane.INFORMATION_MESSAGE);

                this.dispose();

                // Show the DoctorManagementWindow
                SwingUtilities.invokeLater(() -> new DoctorManagementWindow(dbimpDoctor,context));
            } catch (SQLException ex) {
                showErrorDialog("Error updating doctor:", ex);
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
