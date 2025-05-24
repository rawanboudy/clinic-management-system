package com.example.demo.GUI.Doctor;

import javax.swing.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.Config.AppConfig;
import com.example.demo.Config.Doctor.DBimpDoctor;
import com.example.demo.Config.Doctor.Doctor;
import com.example.demo.GUI.LoginWindow;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DoctorManagementWindow extends JFrame implements ActionListener {
    private JButton insertButton;
    private JButton viewButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton backButton;
    private final DBimpDoctor dbimpDoctor;
    private final ApplicationContext context;

    @Autowired
    public DoctorManagementWindow(DBimpDoctor dbimpDoctor, ApplicationContext context) {
        this.dbimpDoctor = dbimpDoctor;
        this.context = context;

        setTitle("Doctor Management");
        setSize(400, 250); // Set window size
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

        // Create buttons with icons
        insertButton = new JButton("Insert Doctor", new ImageIcon(insertImg));
        insertButton.addActionListener(this);
        panel.add(insertButton);

        viewButton = new JButton("View Doctors", new ImageIcon(viewImg));
        viewButton.addActionListener(this);
        panel.add(viewButton);

        updateButton = new JButton("Update Doctor", new ImageIcon(updateImg));
        updateButton.addActionListener(this);
        panel.add(updateButton);

        deleteButton = new JButton("Delete Doctor", new ImageIcon(deleteImg));
        deleteButton.addActionListener(this);
        panel.add(deleteButton);

        // Create back button
        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            dispose(); // Close the current window
            SwingUtilities.invokeLater(() -> {
                // Open LoginWindow
                LoginWindow loginWindow = context.getBean(LoginWindow.class);
                loginWindow.setVisible(true);
            });
        });
        panel.add(backButton);

        add(panel);
        setLocationRelativeTo(null); // Center the window
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == insertButton) {
            this.dispose();
            SwingUtilities.invokeLater(() -> new DoctorInsertWindow(dbimpDoctor,context));
        } else if (e.getSource() == viewButton) {
            try {
                List<Doctor> doctors = dbimpDoctor.getAll();
                if (doctors.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No doctors found", "View Doctors", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    displayDoctorsTable(doctors);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error while fetching doctors: " + ex.getMessage(), "View Doctors", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == updateButton) {
            String doctorIdStr = JOptionPane.showInputDialog(this, "Enter Doctor ID to Update:", "Update Doctor", JOptionPane.QUESTION_MESSAGE);
            if (doctorIdStr != null && !doctorIdStr.trim().isEmpty()) {
                try {
                    int doctorId = Integer.parseInt(doctorIdStr);
                    SwingUtilities.invokeLater(() -> new DoctorUpdateWindow(doctorId, dbimpDoctor,context));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid Doctor ID", "Update Doctor - Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (e.getSource() == deleteButton) {
            String doctorIdString = JOptionPane.showInputDialog(this, "Enter Doctor ID to delete:");
            if (doctorIdString != null) {
                try {
                    int doctorId = Integer.parseInt(doctorIdString);
                    dbimpDoctor.delete(doctorId);
                    JOptionPane.showMessageDialog(this, "Doctor deleted successfully.");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid doctor ID format. Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error while deleting doctor: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void displayDoctorsTable(List<Doctor> doctors) {
        String[] columnNames = {"ID", "Name", "Email", "Phone", "Password", "Speciality"};
        Object[][] data = new Object[doctors.size()][6];

        for (int i = 0; i < doctors.size(); i++) {
            Doctor doctor = doctors.get(i);
            data[i][0] = doctor.getId();
            data[i][1] = doctor.getName();
            data[i][2] = doctor.getEmail();
            data[i][3] = doctor.getPhone();
            data[i][4] = doctor.getPassword();
            data[i][5] = doctor.getSpecialty();
        }

        JTable table = new JTable(new DefaultTableModel(data, columnNames));
        JScrollPane scrollPane = new JScrollPane(table);

        JFrame tableFrame = new JFrame("Doctors List");
        tableFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        tableFrame.add(scrollPane);
        tableFrame.setSize(800, 400);
        tableFrame.setLocationRelativeTo(null);
        tableFrame.setVisible(true);
    }

    public static void main(String[] args) {
        // Create the Spring application context
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        // Get an instance of DBimpDoctor from the application context
        DBimpDoctor dbimpDoctor = context.getBean(DBimpDoctor.class);

        // Pass dbimpDoctor and context to the DoctorManagementWindow constructor
        SwingUtilities.invokeLater(() -> new DoctorManagementWindow(dbimpDoctor, context));
    }
}