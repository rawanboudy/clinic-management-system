package com.example.demo.GUI.Assistant;

import javax.sql.DataSource;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.springframework.stereotype.Component;
import com.example.demo.Config.Admin.DBimpAdmin;
import com.example.demo.Config.Assistant.DBimpAssistant;
import com.example.demo.Config.Appointment.DBimpAppointment;
import com.example.demo.Config.Doctor.DBimpDoctor;
import com.example.demo.Config.DoctorFunctions.DBimpDoctorFunctions;
import com.example.demo.Config.Patient.DBimpPatient;
import com.example.demo.GUI.LoginWindow;
import com.example.demo.GUI.Appointment.AppointmentManagementWindow;

import org.springframework.context.ApplicationContext;

@Component
public class AssistantManagementWindow extends JFrame implements ActionListener {
    private final DBimpAssistant dbimpAssistant;
    private final DBimpAppointment dbimpAppointment;
    private final DBimpPatient dbimpPatient;
    private final DataSource dataSource;
    private final DBimpAdmin dbimpAdmin;
    private final DBimpDoctor dbimpDoctor;
    private final DBimpDoctorFunctions doctorFunctions;
    private final ApplicationContext context;

    private final JButton manageAppointmentsButton;
    private final JButton updatePatientEmailButton;
    private final JButton logoutButton;

    public AssistantManagementWindow(DBimpAssistant dbimpAssistant, DBimpAppointment dbimpAppointment, DBimpPatient dbimpPatient,
                                     DataSource dataSource, DBimpAdmin dbimpAdmin, DBimpDoctor dbimpDoctor,
                                     DBimpDoctorFunctions doctorFunctions, ApplicationContext context) {
        this.dbimpAssistant = dbimpAssistant;
        this.dbimpAppointment = dbimpAppointment;
        this.dbimpPatient = dbimpPatient;
        this.dataSource = dataSource;
        this.dbimpAdmin = dbimpAdmin;
        this.dbimpDoctor = dbimpDoctor;
        this.doctorFunctions = doctorFunctions;
        this.context = context;

        setTitle("Assistant Management");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Initialize buttons
        manageAppointmentsButton = new JButton("Manage Appointments");
        manageAppointmentsButton.addActionListener(this);

        updatePatientEmailButton = new JButton("Update Patient Email");
        updatePatientEmailButton.addActionListener(this);

        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(this);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(manageAppointmentsButton);
        buttonPanel.add(updatePatientEmailButton);
        buttonPanel.add(logoutButton);

        add(buttonPanel);

        pack();
        setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == manageAppointmentsButton) {
            this.dispose();
            SwingUtilities.invokeLater(() -> {
                AppointmentManagementWindow appointmentManagementWindow = context.getBean(AppointmentManagementWindow.class);
                appointmentManagementWindow.setVisible(true);
            });
        } else if (e.getSource() == updatePatientEmailButton) {
            this.dispose();
            SwingUtilities.invokeLater(() -> {
                AssistantUpdateWindow assistantUpdateWindow = context.getBean(AssistantUpdateWindow.class);
                assistantUpdateWindow.setVisible(true);
            });
        } else if (e.getSource() == logoutButton) {
            this.dispose();
            SwingUtilities.invokeLater(() -> {
                LoginWindow loginWindow = context.getBean(LoginWindow.class);
                loginWindow.setVisible(true);
            });
        }
    }
}
