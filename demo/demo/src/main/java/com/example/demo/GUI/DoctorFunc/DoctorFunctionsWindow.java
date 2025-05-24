package com.example.demo.GUI.DoctorFunc;

import com.example.demo.Config.DoctorFunctions.IDBDoctorFunctions;
import com.example.demo.Config.Patient.Patient;
import com.example.demo.GUI.LoginWindow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class DoctorFunctionsWindow extends JFrame implements ActionListener {
    private JButton addPrescriptionButton;
    private JButton viewPatientButton;
    private JButton editPrescriptionButton;
    private JButton deletePrescriptionButton;
    private JButton deletePatientButton;
    private JButton logoutButton;
    private final IDBDoctorFunctions doctorFunctions;
    private final ApplicationContext context;
    private static final Logger logger = Logger.getLogger(DoctorFunctionsWindow.class.getName());

    @Autowired
    public DoctorFunctionsWindow(IDBDoctorFunctions doctorFunctions, ApplicationContext context) {
        this.doctorFunctions = doctorFunctions;
        this.context = context;

        setTitle("Doctor Management");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(6, 1)); // Updated to 6 rows to accommodate the logout button

        addPrescriptionButton = new JButton("Add Prescription");
        addPrescriptionButton.addActionListener(this);
        panel.add(addPrescriptionButton);

        viewPatientButton = new JButton("View Patient");
        viewPatientButton.addActionListener(this);
        panel.add(viewPatientButton);

        editPrescriptionButton = new JButton("Edit Prescription");
        editPrescriptionButton.addActionListener(this);
        panel.add(editPrescriptionButton);

        deletePrescriptionButton = new JButton("Delete Prescription");
        deletePrescriptionButton.addActionListener(this);
        panel.add(deletePrescriptionButton);

        deletePatientButton = new JButton("Delete Patient");
        deletePatientButton.addActionListener(this);
        panel.add(deletePatientButton);

        // Create logout button
        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(this);
        panel.add(logoutButton);

        add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addPrescriptionButton) {
            String name = JOptionPane.showInputDialog("Enter patient's name:").trim();
            String prescription = JOptionPane.showInputDialog("Enter prescription:").trim();
            try {
                doctorFunctions.addPrescription(name, prescription);
                logger.log(Level.INFO, "Prescription added for patient: {0}", name);
                JOptionPane.showMessageDialog(this, "Prescription added successfully!");
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, "Error adding prescription for patient: " + name, ex);
                JOptionPane.showMessageDialog(this, "Error adding prescription: " + ex.getMessage());
            }
        } else if (e.getSource() == viewPatientButton) {
            String name = JOptionPane.showInputDialog("Enter patient's name:").trim();
            try {
                Patient patient = doctorFunctions.viewPatientByName(name);
                if (patient != null) {
                    String patientInfo = "Name: " + patient.getName() + "\n"
                            + "Age: " + patient.getAge() + "\n"
                            + "Gender: " + patient.getGender() + "\n"
                            + "Email: " + patient.getEmail() + "\n"
                            + "Prescriptions: " + patient.getMedicine();
                    logger.log(Level.INFO, "Viewing patient: {0}", name);
                    JOptionPane.showMessageDialog(this, patientInfo);
                } else {
                    logger.log(Level.WARNING, "Patient not found: {0}", name);
                    JOptionPane.showMessageDialog(this, "Patient not found.");
                }
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, "Error viewing patient: " + name, ex);
                JOptionPane.showMessageDialog(this, "Error viewing patient: " + ex.getMessage());
            }
        } else if (e.getSource() == editPrescriptionButton) {
            String name = JOptionPane.showInputDialog("Enter patient's name:").trim();
            String prescription = JOptionPane.showInputDialog("Enter new prescription:").trim();
            try {
                doctorFunctions.editPrescription(name, prescription);
                logger.log(Level.INFO, "Prescription updated for patient: {0}", name);
                JOptionPane.showMessageDialog(this, "Prescription updated successfully!");
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, "Error updating prescription for patient: " + name, ex);
                JOptionPane.showMessageDialog(this, "Error updating prescription: " + ex.getMessage());
            }
        } else if (e.getSource() == deletePrescriptionButton) {
            String name = JOptionPane.showInputDialog("Enter patient's name:").trim();
            try {
                doctorFunctions.deletePrescription(name);
                logger.log(Level.INFO, "Prescription deleted for patient: {0}", name);
                JOptionPane.showMessageDialog(this, "Prescription deleted successfully!");
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, "Error deleting prescription for patient: " + name, ex);
                JOptionPane.showMessageDialog(this, "Error deleting prescription: " + ex.getMessage());
            }
        } else if (e.getSource() == deletePatientButton) {
            String name = JOptionPane.showInputDialog("Enter patient's name:").trim();
            try {
                doctorFunctions.deletePatientByName(name);
                logger.log(Level.INFO, "Patient deleted: {0}", name);
                JOptionPane.showMessageDialog(this, "Patient deleted successfully!");
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, "Error deleting patient: " + name, ex);
                JOptionPane.showMessageDialog(this, "Error deleting patient: " + ex.getMessage());
            }
        } else if (e.getSource() == logoutButton) {
            // Handle logout action
            logout();
        }
    }

    private void logout() {
        SwingUtilities.invokeLater(() -> {
            // Create a new instance of LoginWindow
            LoginWindow loginWindow = context.getBean(LoginWindow.class); // Use context to get a bean instance
            loginWindow.setVisible(true);
            dispose();
        });
    }
}
