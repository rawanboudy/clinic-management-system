package com.example.demo.GUI;

import javax.sql.DataSource;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.example.demo.Config.Admin.DBimpAdmin;
import com.example.demo.Config.Assistant.DBimpAssistant;
import com.example.demo.Config.Appointment.DBimpAppointment;
import com.example.demo.Config.Doctor.DBimpDoctor;
import com.example.demo.Config.DoctorFunctions.DBimpDoctorFunctions;
import com.example.demo.Config.DoctorFunctions.IDBDoctorFunctions;
import com.example.demo.Config.Patient.DBimpPatient;
import com.example.demo.GUI.Admin.AdminLoginWindow;
import com.example.demo.GUI.Assistant.AssistantLoginWindow;
import com.example.demo.GUI.DoctorFunc.DoctorLoginWindow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.context.ApplicationContext;

@Component
public class LoginWindow extends JFrame implements ActionListener {
    private final JButton adminButton;
    private final JButton doctorButton;
    private final JButton assistantButton;
    private final DBimpAdmin dbimpAdmin;
    private final DBimpDoctor dbimpDoctor;
    private final DBimpPatient dbimpPatient;
    private final DataSource dataSource;
    private final IDBDoctorFunctions doctorFunctions;
    private final DBimpAssistant dbimpAssistant;
    private final DBimpAppointment dbimpAppointment;
    private final ApplicationContext context;

    @Autowired
   
   
public LoginWindow(DBimpAdmin dbimpAdmin, DBimpDoctor dbimpDoctor, DBimpPatient dbimpPatient,
                   DataSource dataSource, IDBDoctorFunctions doctorFunctions,
                   DBimpAssistant dbimpAssistant, DBimpAppointment dbimpAppointment,
                   ApplicationContext context) {
    this.dbimpAdmin = dbimpAdmin;
    this.dbimpDoctor = dbimpDoctor;
    this.dbimpPatient = dbimpPatient;
    this.dataSource = dataSource;
    this.doctorFunctions = doctorFunctions;
    this.dbimpAssistant = dbimpAssistant;
    this.dbimpAppointment = dbimpAppointment;
    this.context = context;



        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));

        // Create buttons
        adminButton = new JButton("Login as Admin");
        adminButton.addActionListener(this);
        buttonPanel.add(adminButton);

        doctorButton = new JButton("Login as Doctor");
        doctorButton.addActionListener(this);
        buttonPanel.add(doctorButton);

        assistantButton = new JButton("Login as Assistant");
        assistantButton.addActionListener(this);
        buttonPanel.add(assistantButton);

        // Add button panel to the frame
        add(buttonPanel, BorderLayout.CENTER);

        setSize(300, 250);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // ActionListener implementation
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == adminButton) {
            AdminLoginWindow adminLoginWindow = new AdminLoginWindow(dbimpAdmin, dataSource,context);
            adminLoginWindow.setVisible(true);
            dispose();
        } else if (e.getSource() == doctorButton) {
            DoctorLoginWindow doctorLoginWindow = new DoctorLoginWindow( context, doctorFunctions);
            doctorLoginWindow.setVisible(true);
            dispose();
        } else if (e.getSource() == assistantButton) {
            AssistantLoginWindow assistantLoginWindow = new AssistantLoginWindow(dbimpAssistant, context);
            assistantLoginWindow.setVisible(true);
            dispose();
        }
    }
}
