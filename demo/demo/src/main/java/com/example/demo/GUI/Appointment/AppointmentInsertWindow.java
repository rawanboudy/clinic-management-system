package com.example.demo.GUI.Appointment;

import javax.sql.DataSource;
import javax.swing.*;
import com.example.demo.Config.Appointment.DBimpAppointment;
import com.example.demo.Config.Appointment.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import java.awt.*;
import org.springframework.stereotype.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

@Component
public class AppointmentInsertWindow extends JFrame implements ActionListener {
    private static final String IMAGE_PATH_ADD = "C:\\Users\\Lenovo\\Desktop\\dscs\\component project last\\demo\\demo\\src\\main\\java\\com\\example\\demo\\pictures\\add.png";
    private JTextField patientNameField, doctorNameField, descriptionField;
    private JFormattedTextField appointmentDateField;
    private JComboBox<String> appointmentStatusComboBox;
    private JButton insertButton, backButton;
    private final DBimpAppointment dbimpAppointment;

    @Autowired
    public AppointmentInsertWindow(DBimpAppointment dbimpAppointment) {
        this.dbimpAppointment = dbimpAppointment;

        setTitle("Insert Appointment");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel appointmentDateLabel = new JLabel("Appointment Date:");
        appointmentDateField = new JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd"));
        JLabel appointmentDateNoteLabel = new JLabel("(yyyy-MM-dd format)");
        JLabel patientNameLabel = new JLabel("Patient Name:");
        patientNameField = new JTextField();
        JLabel doctorNameLabel = new JLabel("Doctor Name:");
        doctorNameField = new JTextField();
        JLabel appointmentStatusLabel = new JLabel("Appointment Status:");
        String[] appointmentStatusOptions = {"Follow Up", "Procedure", "Checkup", "First-Time Patient"};
        appointmentStatusComboBox = new JComboBox<>(appointmentStatusOptions);
        JLabel descriptionLabel = new JLabel("Description:");
        descriptionField = new JTextField();

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(appointmentDateLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(appointmentDateField, gbc);
        gbc.gridx = 2;
        formPanel.add(appointmentDateNoteLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(patientNameLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(patientNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(doctorNameLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(doctorNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(appointmentStatusLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(appointmentStatusComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(descriptionLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(descriptionField, gbc);

        insertButton = new JButton("Insert");
        insertButton.addActionListener(this);

        backButton = new JButton("Back");
        backButton.addActionListener(this);

        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(backButton, gbc);
        gbc.gridx = 1;
        formPanel.add(insertButton, gbc);

        add(formPanel, BorderLayout.CENTER);

        ImageIcon logoIcon = resizeIcon(IMAGE_PATH_ADD, 100, 100);
        JLabel logoLabel = new JLabel(logoIcon);
        add(logoLabel, BorderLayout.NORTH);

        setSize(600, 600);
        setLocationRelativeTo(null);
        // setVisible(true); // Remove this line
    }

    private ImageIcon resizeIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == insertButton) {
            try {
                java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(appointmentDateField.getText());

                Appointment newAppointment = new Appointment();
                newAppointment.setAppointmentDate(new java.sql.Date(utilDate.getTime()));
                newAppointment.setPatientName(patientNameField.getText());
                newAppointment.setDoctorName(doctorNameField.getText());
                newAppointment.setAppointmentStatus((String) appointmentStatusComboBox.getSelectedItem());
                newAppointment.setDescription(descriptionField.getText());

                dbimpAppointment.add(newAppointment);

                JOptionPane.showMessageDialog(this, "Appointment added successfully", "Insert Appointment", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();

                // Return to the AppointmentManagementWindow

            } catch (SQLException ex) {
                showErrorDialog("Error adding appointment:", ex);
            } catch (java.text.ParseException | NullPointerException ex) {
                JOptionPane.showMessageDialog(this, "Invalid date format. Please use yyyy-MM-dd format.", "Insert Appointment - Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == backButton) {
            this.dispose(); // Simply dispose if the Back button is clicked
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
