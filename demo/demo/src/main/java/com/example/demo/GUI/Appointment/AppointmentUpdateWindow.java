package com.example.demo.GUI.Appointment;

import javax.swing.*;
import com.example.demo.Config.Appointment.DBimpAppointment;
import com.example.demo.Config.Appointment.Appointment;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

@Component
public class AppointmentUpdateWindow extends JFrame implements ActionListener {
    private final DBimpAppointment dbimpAppointment;
    private int appointmentId;  // Removed final keyword to set it later
    private JTextField dateField, patientField, doctorField, statusField, descriptionField;
    private JButton updateButton;

    public AppointmentUpdateWindow(DBimpAppointment dbimpAppointment) {
        this.dbimpAppointment = dbimpAppointment;

        setTitle("Update Appointment");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        panel.add(new JLabel("Appointment Date:"));
        dateField = new JTextField();
        panel.add(dateField);

        panel.add(new JLabel("Patient Name:"));
        patientField = new JTextField();
        panel.add(patientField);

        panel.add(new JLabel("Doctor Name:"));
        doctorField = new JTextField();
        panel.add(doctorField);

        panel.add(new JLabel("Appointment Status:"));
        statusField = new JTextField();
        panel.add(statusField);

        panel.add(new JLabel("Description:"));
        descriptionField = new JTextField();
        panel.add(descriptionField);

        updateButton = new JButton("Update");
        updateButton.addActionListener(this);
        panel.add(updateButton);

        add(panel);
        // Removed setVisible(true);, we'll call it manually after setting appointmentId
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
        loadAppointmentData();  // Load data after setting the appointment ID
    }

    private void loadAppointmentData() {
        try {
            Appointment appointment = dbimpAppointment.getById(appointmentId);
            if (appointment != null) {
                dateField.setText(appointment.getAppointmentDate().toString());
                patientField.setText(appointment.getPatientName());
                doctorField.setText(appointment.getDoctorName());
                statusField.setText(appointment.getAppointmentStatus());
                descriptionField.setText(appointment.getDescription());
            } else {
                JOptionPane.showMessageDialog(this, "Appointment not found", "Error", JOptionPane.ERROR_MESSAGE);
                this.dispose();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading appointment: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            this.dispose();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == updateButton) {
            try {
                Appointment appointment = new Appointment();
                appointment.setAppointmentId(appointmentId);
                appointment.setAppointmentDate(java.sql.Date.valueOf(dateField.getText()));
                appointment.setPatientName(patientField.getText());
                appointment.setDoctorName(doctorField.getText());
                appointment.setAppointmentStatus(statusField.getText());
                appointment.setDescription(descriptionField.getText());

                dbimpAppointment.update(appointment);
                JOptionPane.showMessageDialog(this, "Appointment updated successfully", "Update Appointment", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error updating appointment: " + ex.getMessage(), "Update Appointment", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
