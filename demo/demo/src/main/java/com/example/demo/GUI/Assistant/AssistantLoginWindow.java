package com.example.demo.GUI.Assistant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.demo.Config.Assistant.DBimpAssistant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.springframework.context.ApplicationContext;

@Component
public class AssistantLoginWindow extends JFrame implements ActionListener {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JButton loginButton;
    private final DBimpAssistant dbimpAssistant;
    private final ApplicationContext context;

    @Autowired
    public AssistantLoginWindow(DBimpAssistant dbimpAssistant, ApplicationContext context) {
        this.dbimpAssistant = dbimpAssistant;
        this.context = context;

        setTitle("Assistant Login");
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
                if (dbimpAssistant.authenticate(username, password)) {
                    JOptionPane.showMessageDialog(this, "Assistant login successful", "Assistant Login", JOptionPane.INFORMATION_MESSAGE);
                    // Open AssistantManagementWindow after successful login
                    AssistantManagementWindow assistantManagementWindow = context.getBean(AssistantManagementWindow.class);
                    assistantManagementWindow.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid username or password", "Assistant Login - Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error authenticating assistant: " + ex.getMessage(), "Assistant Login - Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
