package com.example.demo.GUI.Patient;

import javax.swing.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.Config.AppConfig;
import com.example.demo.Config.Patient.DBimpPatient;
import com.example.demo.Config.Patient.Patient;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class patientManagementWindow extends JFrame implements ActionListener {
    private JButton insertButton;
    private JButton viewButton;
    private JButton updateButton;
    private JButton deleteButton;
    private final DBimpPatient dbimpPatient;

    @Autowired
    public patientManagementWindow(DBimpPatient dbimpPatient) {
        this.dbimpPatient = dbimpPatient;

        setTitle("User Management");
        setSize(600, 300); // Larger window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(6, 2));

        // Load and resize icons
        ImageIcon addIcon = resizeIcon("C:\\Users\\Lenovo\\Desktop\\dscs\\component project last\\demo\\demo\\src\\main\\java\\com\\example\\demo\\pictures\\add.png", 50, 50);
        ImageIcon viewIcon = resizeIcon("C:\\Users\\Lenovo\\Desktop\\dscs\\component project last\\demo\\demo\\src\\main\\java\\com\\example\\demo\\pictures\\view.png", 50, 50);
        ImageIcon updateIcon = resizeIcon("C:\\Users\\Lenovo\\Desktop\\dscs\\component project last\\demo\\demo\\src\\main\\java\\com\\example\\demo\\pictures\\update.png", 50, 50);
        ImageIcon deleteIcon = resizeIcon("C:\\Users\\Lenovo\\Desktop\\dscs\\component project last\\demo\\demo\\src\\main\\java\\com\\example\\demo\\pictures\\delete.png", 50, 50);

        // Create buttons with resized icons
        insertButton = new JButton("Insert User", addIcon);
        insertButton.addActionListener(this);
        panel.add(insertButton);

        viewButton = new JButton("View Users", viewIcon);
        viewButton.addActionListener(this);
        panel.add(viewButton);

        updateButton = new JButton("Update User", updateIcon);
        updateButton.addActionListener(this);
        panel.add(updateButton);

        deleteButton = new JButton("Delete User", deleteIcon);
        deleteButton.addActionListener(this);
        panel.add(deleteButton);

        add(panel);

        // Center the window on the screen
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == insertButton) {
            this.dispose();
            SwingUtilities.invokeLater(() -> new patientInsertWindow(dbimpPatient));
        } else if (e.getSource() == viewButton) {
            SwingUtilities.invokeLater(() -> createViewPatientsWindow());
        } else if (e.getSource() == updateButton) {
            String patientIdStr = JOptionPane.showInputDialog(this, "Enter Patient ID to Update:", "Update User", JOptionPane.QUESTION_MESSAGE);
            if (patientIdStr != null && !patientIdStr.trim().isEmpty()) {
                try {
                    int patientId = Integer.parseInt(patientIdStr);
                    SwingUtilities.invokeLater(() -> new patientUpdateWindow(patientId, dbimpPatient));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid Patient ID", "Update User - Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (e.getSource() == deleteButton) {
            String patientIdString = JOptionPane.showInputDialog(this, "Enter Patient ID to delete:");
            if (patientIdString != null) {
                try {
                    int patientId = Integer.parseInt(patientIdString);
                    dbimpPatient.delete(patientId);
                    JOptionPane.showMessageDialog(this, "Patient deleted successfully.");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid patient ID format. Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error while deleting patient: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void createViewPatientsWindow() {
        JFrame frame = new JFrame("View Patients");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // Add logo
        JPanel logoPanel = new JPanel();
        ImageIcon logoIcon = resizeIcon("src/main/java/com/example/demo/pictures/patients.png", 100, 100);
        JLabel logoLabel = new JLabel(logoIcon);
        logoPanel.add(logoLabel);
        frame.add(logoPanel, BorderLayout.NORTH);

        // Table data
        String[] columnNames = {"ID", "Name", "Email", "Age", "Gender"};
        Object[][] data = {};

        try {
            List<Patient> patients = dbimpPatient.getAll();
            data = new Object[patients.size()][5];
            for (int i = 0; i < patients.size(); i++) {
                Patient patient = patients.get(i);
                data[i][0] = patient.getId();
                data[i][1] = patient.getName();
                data[i][2] = patient.getEmail();
                data[i][3] = patient.getAge();
                data[i][4] = patient.getGender();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error while fetching patients: " + ex.getMessage(), "View Users", JOptionPane.ERROR_MESSAGE);
        }

        // Create table
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private ImageIcon resizeIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }

    public static void main(String[] args) {
        // Create the Spring application context
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        // Get an instance of DBimpPatient from the application context
        DBimpPatient dbimpPatient = context.getBean(DBimpPatient.class);

        // Pass dbimpPatient to the UserManagementWindow constructor
        SwingUtilities.invokeLater(() -> new patientManagementWindow(dbimpPatient));
    }
}
