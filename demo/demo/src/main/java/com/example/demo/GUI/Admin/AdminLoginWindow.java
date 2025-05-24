package com.example.demo.GUI.Admin;

import com.example.demo.Config.Admin.DBimpAdmin;
import com.example.demo.Config.Doctor.DBimpDoctor;
import com.example.demo.Config.Patient.DBimpPatient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import javax.sql.DataSource;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AdminLoginWindow extends JFrame implements ActionListener {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JButton loginButton;
    private final DBimpAdmin dbimpAdmin;
    private final DataSource dataSource;
    private final ApplicationContext context; // Add ApplicationContext

    @Autowired
    public AdminLoginWindow(DBimpAdmin dbimpAdmin, DataSource dataSource, ApplicationContext context) {
        this.dbimpAdmin = dbimpAdmin;
        this.dataSource = dataSource; // Initialize dataSource here
        this.context = context; // Initialize context here

        setTitle("Admin Login");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3, 1));

        // Create labels and text fields for username and password
        JPanel usernamePanel = new JPanel(new FlowLayout());
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(20);
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);

        JPanel passwordPanel = new JPanel(new FlowLayout());
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);

        // Create login button
        JPanel buttonPanel = new JPanel(new FlowLayout());
        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        buttonPanel.add(loginButton);

        // Add components to the frame
        add(usernamePanel);
        add(passwordPanel);
        add(buttonPanel);

        pack();
        setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = usernameField.getText();
            String password = String.valueOf(passwordField.getPassword());

            try {
                if (dbimpAdmin.authenticate(username, password)) {
                    JOptionPane.showMessageDialog(this, "Admin login successful", "Admin Login", JOptionPane.INFORMATION_MESSAGE);
                    // Open AdminManagementWindow after successful login
                    EventQueue.invokeLater(() -> {
                        // Create instances of DBimpPatient and DBimpDoctor passing the injected DataSource
                        DBimpPatient dbimpPatient = new DBimpPatient(dataSource);
                        DBimpDoctor dbimpDoctor = new DBimpDoctor(dataSource);

                        AdminManagementWindow adminManagementWindow = new AdminManagementWindow(dbimpAdmin, dbimpPatient, dbimpDoctor, context);
                        adminManagementWindow.setVisible(true);
                    });
                    // Optionally, dispose the current login window
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid username or password", "Admin Login - Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error authenticating admin: " + ex.getMessage(), "Admin Login - Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
