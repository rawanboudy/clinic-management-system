package com.example.demo.Config.DoctorFunctions;

import com.example.demo.Config.Patient.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class DBimpDoctorFunctions implements IDBDoctorFunctions {
    private final DataSource dataSource;
    private static final Logger logger = Logger.getLogger(DBimpDoctorFunctions.class.getName());

    @Autowired
    public DBimpDoctorFunctions(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void addPrescription(String patientName, String prescription) throws SQLException {
        String query = "UPDATE patients SET Medicine = CONCAT(medicine, ?) WHERE Name = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, ", " + prescription);
            ps.setString(2, patientName);
            int updatedRows = ps.executeUpdate();
            if (updatedRows > 0) {
                logger.log(Level.INFO, "Prescription added for patient: {0}", patientName);
            } else {
                logger.log(Level.WARNING, "No patient found with name: {0}", patientName);
            }
        }
    }
    
    @Override
    public Patient viewPatientByName(String patientName) throws SQLException {
        String query = "SELECT * FROM patients WHERE Name = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, patientName);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return resultSetToPatient(resultSet);
            }
        }
        return null; // Return null if patient not found
    }
    
    @Override
public void editPrescription(String patientName, String prescription) throws SQLException {
    String query = "UPDATE patients SET Medicine = ? WHERE Name = ?";
    try (Connection connection = dataSource.getConnection();
         PreparedStatement ps = connection.prepareStatement(query)) {
        ps.setString(1, prescription);
        ps.setString(2, patientName);
        int updatedRows = ps.executeUpdate();
        if (updatedRows > 0) {
            logger.log(Level.INFO, "Prescription updated for patient: {0}", patientName);
        } else {
            logger.log(Level.WARNING, "No patient found with name: {0}", patientName);
        }
    }
}


@Override
public void deletePrescription(String patientName) throws SQLException {
    String query = "UPDATE patients SET medicine = '' WHERE Name = ?";
    try (Connection connection = dataSource.getConnection();
         PreparedStatement ps = connection.prepareStatement(query)) {
        ps.setString(1, patientName);
        int updatedRows = ps.executeUpdate();
        if (updatedRows > 0) {
            logger.log(Level.INFO, "Prescription deleted for patient: {0}", patientName);
        } else {
            logger.log(Level.WARNING, "No patient found with name: {0}", patientName);
        }
    }
}

@Override
public void deletePatientByName(String patientName) throws SQLException {
    String query = "DELETE FROM patients WHERE Name = ?";
    try (Connection connection = dataSource.getConnection();
         PreparedStatement ps = connection.prepareStatement(query)) {
        ps.setString(1, patientName);
        int deletedRows = ps.executeUpdate();
        if (deletedRows > 0) {
            logger.log(Level.INFO, "Patient deleted: {0}", patientName);
        } else {
            logger.log(Level.WARNING, "No patient found with name: {0}", patientName);
        }
    }
}

@Override
public boolean authenticate(String username, String password) throws SQLException {
    // Use the actual column names from your 'doctors' table schema
    String query = "SELECT COUNT(*) FROM doctors WHERE Name = ? AND password = ?";
    try (Connection connection = dataSource.getConnection();
         PreparedStatement ps = connection.prepareStatement(query)) {
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet resultSet = ps.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt(1) > 0;
        }
    }
    return false; // Return false if authentication fails
}

    

    private Patient resultSetToPatient(ResultSet rs) throws SQLException {
        Patient patient = new Patient();
        patient.setName(rs.getString("Name"));
        patient.setAge(rs.getInt("Age"));
        patient.setGender(rs.getString("Gender"));
        patient.setEmail(rs.getString("Email"));
        patient.setMedicine(rs.getString("Medicine"));
        return patient;
    }
}
