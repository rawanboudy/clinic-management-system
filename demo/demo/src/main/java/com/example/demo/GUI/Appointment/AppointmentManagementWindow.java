package com.example.demo.GUI.Appointment;

import javax.sql.DataSource;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import org.springframework.stereotype.Component;
import com.example.demo.Config.Admin.DBimpAdmin;
import com.example.demo.Config.Assistant.DBimpAssistant;
import com.example.demo.Config.Doctor.DBimpDoctor;
import com.example.demo.Config.Patient.DBimpPatient;
import com.example.demo.Config.Appointment.DBimpAppointment;
import com.example.demo.Config.Appointment.Appointment;
import com.example.demo.GUI.Assistant.AssistantManagementWindow;
import org.springframework.context.ApplicationContext;
import com.example.demo.Config.DoctorFunctions.DBimpDoctorFunctions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Component
public class AppointmentManagementWindow extends JFrame implements ActionListener {
    private static final Logger logger = LoggerFactory.getLogger(AppointmentManagementWindow.class);

    private final JButton insertButton;
    private final JButton viewButton;
    private final JButton updateButton;
    private final JButton deleteButton;
    private final JButton backButton; // Add a back button
    private final DBimpAppointment dbimpAppointment;
    private final DBimpAssistant dbimpAssistant;
    private final DBimpPatient dbimpPatient;
    private final DataSource dataSource;
    private final DBimpAdmin dbimpAdmin;
    private final DBimpDoctor dbimpDoctor;
    private final ApplicationContext context;

    public AppointmentManagementWindow(DBimpAssistant dbimpAssistant, DBimpAppointment dbimpAppointment, DBimpPatient dbimpPatient,
                                       DataSource dataSource, DBimpAdmin dbimpAdmin, DBimpDoctor dbimpDoctor,
                                       DBimpDoctorFunctions doctorFunctions, ApplicationContext context) {
        this.dbimpAppointment = dbimpAppointment;
        this.dbimpAssistant = dbimpAssistant;
        this.dbimpPatient = dbimpPatient;
        this.dataSource = dataSource;
        this.dbimpAdmin = dbimpAdmin;
        this.dbimpDoctor = dbimpDoctor;
        this.context = context;

        setTitle("Appointment Management");
        setSize(400, 200); // Set window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1)); // Updated to 5 rows to accommodate the back button

        // Load icons
        ImageIcon insertIcon = new ImageIcon("C:\\Users\\Lenovo\\Desktop\\dscs\\component project last\\demo\\demo\\src\\main\\java\\com\\example\\demo\\pictures\\add.png");
        ImageIcon viewIcon = new ImageIcon("C:\\Users\\Lenovo\\Desktop\\dscs\\component project last\\demo\\demo\\src\\main\\java\\com\\example\\demo\\pictures\\view.png");
        ImageIcon updateIcon = new ImageIcon("C:\\Users\\Lenovo\\Desktop\\dscs\\component project last\\demo\\demo\\src\\main\\java\\com\\example\\demo\\pictures\\update.png");
        ImageIcon deleteIcon = new ImageIcon("C:\\Users\\Lenovo\\Desktop\\dscs\\component project last\\demo\\demo\\src\\main\\java\\com\\example\\demo\\pictures\\delete.png");

        // Resize icons to fit buttons
        Image insertImg = insertIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        Image viewImg = viewIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        Image updateImg = updateIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        Image deleteImg = deleteIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);

        // Create and add buttons
        insertButton = new JButton("Insert Appointment", new ImageIcon(insertImg));
        insertButton.addActionListener(this);
        panel.add(insertButton);

        viewButton = new JButton("View Appointments", new ImageIcon(viewImg));
        viewButton.addActionListener(this);
        panel.add(viewButton);

        updateButton = new JButton("Update Appointment", new ImageIcon(updateImg));
        updateButton.addActionListener(this);
        panel.add(updateButton);

        deleteButton = new JButton("Delete Appointment", new ImageIcon(deleteImg));
        deleteButton.addActionListener(this);
        panel.add(deleteButton);

        backButton = new JButton("Back"); // Initialize and configure the back button
        backButton.addActionListener(this);
        panel.add(backButton);

        add(panel);
        System.out.println("AppointmentManagementWindow bean initialized");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == insertButton) {
                SwingUtilities.invokeLater(() -> {
                    AppointmentInsertWindow insertWindow = context.getBean(AppointmentInsertWindow.class);
                    insertWindow.setVisible(true);
                });
            } else if (e.getSource() == viewButton) {
                try {
                    List<Appointment> appointments = dbimpAppointment.getAll();
                    if (appointments.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "No appointments found.", "View Appointments", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        displayAppointments(appointments);
                    }
                } catch (SQLException ex) {
                    showErrorDialog("Error viewing appointments:", ex);
                }
            } else if (e.getSource() == updateButton) {
                String appointmentIdStr = JOptionPane.showInputDialog(this, "Enter the ID of the appointment to update:", "Update Appointment", JOptionPane.PLAIN_MESSAGE);
                if (appointmentIdStr != null && !appointmentIdStr.isEmpty()) {
                    try {
                        int appointmentId = Integer.parseInt(appointmentIdStr);
                        SwingUtilities.invokeLater(() -> {
                            AppointmentUpdateWindow updateWindow = context.getBean(AppointmentUpdateWindow.class);
                            updateWindow.setAppointmentId(appointmentId);  // Set the appointment ID
                            updateWindow.setVisible(true);
                        });
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Invalid appointment ID.", "Update Appointment", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else if (e.getSource() == deleteButton) {
                String appointmentIdStr = JOptionPane.showInputDialog(this, "Enter the ID of the appointment to delete:", "Delete Appointment", JOptionPane.PLAIN_MESSAGE);
                if (appointmentIdStr != null && !appointmentIdStr.isEmpty()) {
                    try {
                        int appointmentId = Integer.parseInt(appointmentIdStr);
                        dbimpAppointment.delete(appointmentId);
                        JOptionPane.showMessageDialog(this, "Appointment deleted successfully.", "Delete Appointment", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException ex) {
                        showErrorDialog("Error deleting appointment:", ex);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Invalid appointment ID.", "Delete Appointment", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else if (e.getSource() == backButton) {
                this.dispose(); // Close the window and return to the previous window
                AssistantManagementWindow assistantManagementWindow = context.getBean(AssistantManagementWindow.class);
                assistantManagementWindow.setVisible(true);
            }
        } catch (Exception ex) {
            logger.error("An error occurred:", ex);
            JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayAppointments(List<Appointment> appointments) {
        JFrame frame = new JFrame("Appointments");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columnNames = {"ID", "Date", "Patient Name", "Doctor Name", "Status", "Description"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (Appointment appointment : appointments) {
            Object[] rowData = {appointment.getAppointmentId(), appointment.getAppointmentDate(), appointment.getPatientName(),
                    appointment.getDoctorName(), appointment.getAppointmentStatus(), appointment.getDescription()};
            model.addRow(rowData);
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void showErrorDialog(String message, SQLException ex) {
        String errorMessage = message + "\n" +
                "SQL State: " + ex.getSQLState() + "\n" +
                "Error Code: " + ex.getErrorCode() + "\n" +
                "Message: " + ex.getMessage();
        JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
