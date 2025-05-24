package com.example.demo.GUI.Admin;

import com.example.demo.Config.Admin.DBimpAdmin;
import com.example.demo.Config.Doctor.DBimpDoctor;
import com.example.demo.Config.Patient.DBimpPatient;
import com.example.demo.GUI.Doctor.DoctorManagementWindow;
import com.example.demo.GUI.Patient.patientManagementWindow;
import com.example.demo.GUI.LoginWindow;

import javax.swing.*;
import org.springframework.context.ApplicationContext;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminManagementWindow extends JFrame implements ActionListener {
    private JButton manageUsersButton;
    private JButton manageDoctorsButton;
    private JButton logoutButton; // Added logout button
    private JButton backButton; // Added back button
    private DBimpPatient dbimpPatient;
    private DBimpDoctor dbimpDoctor;
    private DBimpAdmin dbimpAdmin;
    private final ApplicationContext context;

    public AdminManagementWindow(DBimpAdmin dbimpAdmin, DBimpPatient dbimpPatient, DBimpDoctor dbimpDoctor, ApplicationContext context) {
        this.dbimpAdmin = dbimpAdmin;
        this.dbimpPatient = dbimpPatient;
        this.dbimpDoctor = dbimpDoctor;
        this.context = context;

        setTitle("Admin Management");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1)); // Updated to 4 rows for back button

        manageUsersButton = new JButton("Manage Users");
        manageUsersButton.addActionListener(this);
        add(manageUsersButton);

        manageDoctorsButton = new JButton("Manage Doctors");
        manageDoctorsButton.addActionListener(this);
        add(manageDoctorsButton);

        logoutButton = new JButton("Logout"); // Create logout button
        logoutButton.addActionListener(this);
        add(logoutButton);

        backButton = new JButton("Back"); // Create back button
        backButton.addActionListener(e -> {
            dispose(); // Close the current window
            SwingUtilities.invokeLater(() -> {
                // Open LoginWindow
                LoginWindow loginWindow = context.getBean(LoginWindow.class);
                loginWindow.setVisible(true);
            });
        });
        add(backButton);

        setLocationRelativeTo(null); // Center the window
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == manageUsersButton) {
            patientManagementWindow userManagementWindow = new patientManagementWindow(dbimpPatient);
            userManagementWindow.setVisible(true);
        } else if (e.getSource() == manageDoctorsButton) {
            DoctorManagementWindow doctorManagementWindow = new DoctorManagementWindow(dbimpDoctor, context);
            doctorManagementWindow.setVisible(true);
        } else if (e.getSource() == logoutButton) {
            this.dispose();
            SwingUtilities.invokeLater(() -> {
                // Create a new instance of LoginWindow
                LoginWindow loginWindow = context.getBean(LoginWindow.class); // Use context to get a bean instance
                loginWindow.setVisible(true);
            });
        }
    }
}