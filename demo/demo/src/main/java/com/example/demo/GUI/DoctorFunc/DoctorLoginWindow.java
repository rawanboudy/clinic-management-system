package com.example.demo.GUI.DoctorFunc;

import com.example.demo.Config.DoctorFunctions.IDBDoctorFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class DoctorLoginWindow extends JFrame implements ActionListener {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JButton loginButton;
    private final IDBDoctorFunctions doctorFunctions; // Ensure correct import and declaration
    private final ApplicationContext context;
    private static final Logger logger = Logger.getLogger(DoctorLoginWindow.class.getName());

    @Autowired
    public DoctorLoginWindow(ApplicationContext context, IDBDoctorFunctions doctorFunctions) { // Correct constructor parameters
        this.context = context;
        this.doctorFunctions = doctorFunctions;

        setTitle("Doctor Login");
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
                if (doctorFunctions.authenticate(username, password)) { // Corrected usage of doctorFunctions
                    JOptionPane.showMessageDialog(this, "Doctor login successful", "Doctor Login", JOptionPane.INFORMATION_MESSAGE);
                    // Open DoctorFunctionsWindow after successful login
                    DoctorFunctionsWindow doctorFunctionsWindow = context.getBean(DoctorFunctionsWindow.class);
                    doctorFunctionsWindow.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid username or password", "Doctor Login - Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "Error authenticating doctor: " + username, ex);
                JOptionPane.showMessageDialog(this, "Error authenticating doctor: " + ex.getMessage(), "Doctor Login - Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
