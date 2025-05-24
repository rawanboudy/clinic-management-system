package com.example.demo.GUI.Assistant;

import javax.sql.DataSource;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import org.springframework.stereotype.Component;
import com.example.demo.Config.Admin.DBimpAdmin;
import com.example.demo.Config.Appointment.DBimpAppointment;
import com.example.demo.Config.Assistant.DBimpAssistant;
import com.example.demo.Config.Assistant.Assistant; // Import statement for Assistant
import com.example.demo.Config.Doctor.DBimpDoctor;
import com.example.demo.Config.DoctorFunctions.DBimpDoctorFunctions;
import com.example.demo.Config.Patient.DBimpPatient;
import org.springframework.context.ApplicationContext;

@Component
public class AssistantUpdateWindow extends JFrame implements ActionListener {
    private final DBimpPatient dbimpPatient;
    private final JTextField patientIdField;
    private final JTextField emailField;
    private final DBimpAssistant dbimpAssistant;
    private final DBimpAppointment dbimpAppointment;
    private final DBimpAdmin dbimpAdmin;
    private final DBimpDoctor dbimpDoctor;
    private final DBimpDoctorFunctions doctorFunctions;
    private final DataSource dataSource;
    private final ApplicationContext context;

    public AssistantUpdateWindow(DBimpPatient dbimpPatient, DBimpAssistant dbimpAssistant, DBimpAppointment dbimpAppointment,
                                 DBimpAdmin dbimpAdmin, DBimpDoctor dbimpDoctor, DBimpDoctorFunctions doctorFunctions,
                                 DataSource dataSource, ApplicationContext context) {
        this.dbimpPatient = dbimpPatient;
        this.dbimpAssistant = dbimpAssistant;
        this.dbimpAppointment = dbimpAppointment;
        this.dbimpAdmin = dbimpAdmin;
        this.dbimpDoctor = dbimpDoctor;
        this.doctorFunctions = doctorFunctions;
        this.dataSource = dataSource;
        this.context = context;

        setTitle("Update Assistant Details");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        panel.add(new JLabel("patient ID:"));
        patientIdField = new JTextField();
        panel.add(patientIdField);

        panel.add(new JLabel("New email:"));
        emailField = new JTextField();
        panel.add(emailField);

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(this);
        panel.add(updateButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            dispose(); // Close the current window
            SwingUtilities.invokeLater(() -> new AssistantManagementWindow(dbimpAssistant, dbimpAppointment, dbimpPatient, dataSource, dbimpAdmin, dbimpDoctor, doctorFunctions, context).setVisible(true));
        });
        panel.add(backButton);

        add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String patientIdStr = patientIdField.getText();
        String newEmail = emailField.getText();
        if (!patientIdStr.isEmpty() && !newEmail.isEmpty()) {
            try {
                int patientId = Integer.parseInt(patientIdStr);
                boolean updated = dbimpAssistant.updatePatientEmail(patientId, newEmail);
                if (updated) {
                    JOptionPane.showMessageDialog(this, "Patient email updated successfully", "Update Patient Email", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Patient not found", "Update Patient Email", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error while updating patient email: " + ex.getMessage(), "Update Patient Email", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please enter all fields", "Update Patient Email", JOptionPane.WARNING_MESSAGE);
        }
    }
}