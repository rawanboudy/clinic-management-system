package com.example.demo.Config.Patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DBimpPatient implements IDBPatient {
    private final DataSource dataSource;

    @Autowired
    public DBimpPatient(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
public Patient getByname(String name) throws SQLException {
    String query = "SELECT * FROM patients WHERE Name = ?";
    try (Connection connection = dataSource.getConnection();
         PreparedStatement ps = connection.prepareStatement(query)) {
        ps.setString(1, name);
        ResultSet resultSet = ps.executeQuery();

        if (resultSet.next()) {
            return resultSetToPatient(resultSet);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw new SQLException("Error getting patient by name", e);
    }
    return null;
}


    @Override
    public Patient getById(int id) throws SQLException {
        String query = "SELECT * FROM patients WHERE P_ID = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                return resultSetToPatient(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error getting patient by ID", e);
        }
        return null;
    }

    @Override
    public List<Patient> getAll() throws SQLException {
        List<Patient> patientList = new ArrayList<>();
        String query = "SELECT * FROM patients";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                patientList.add(resultSetToPatient(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error getting all patients", e);
        }
        return patientList;
    }

    @Override
    public void insert(Patient patient) throws SQLException {
        String query = "INSERT INTO patients (Name, Email, Medicine, Age, Gender, appointment) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, patient.getName());
            preparedStatement.setString(2, patient.getEmail());
            preparedStatement.setString(3, patient.getMedicine());
            preparedStatement.setInt(4, patient.getAge());
            preparedStatement.setString(5, patient.getGender());
            preparedStatement.setString(6, patient.getAppointment());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error inserting patient", e);
        }
    }

    @Override
    public void update(Patient patient) throws SQLException {
        String query = "UPDATE patients SET Name = ?, Email = ?, Medicine = ?, Age = ?, Gender = ?, appointment = ? WHERE P_ID = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, patient.getName());
            preparedStatement.setString(2, patient.getEmail());
            preparedStatement.setString(3, patient.getMedicine());
            preparedStatement.setInt(4, patient.getAge());
            preparedStatement.setString(5, patient.getGender());
            preparedStatement.setString(6, patient.getAppointment());
            preparedStatement.setInt(7, patient.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error updating patient", e);
        }
    }

    @Override
    public int delete(int patientId) throws SQLException {
        String query = "DELETE FROM patients WHERE P_ID = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, patientId);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error deleting patient with ID: " + patientId, e);
        }
    }

    public void updatePrescriptions(int patientId, String prescriptions) throws SQLException {
        String query = "UPDATE patients SET Medicine = ? WHERE P_ID = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, prescriptions);
            preparedStatement.setInt(2, patientId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error updating prescriptions", e);
        }
    }

    private Patient resultSetToPatient(ResultSet rs) throws SQLException {
        Patient patient = new Patient();
        patient.setId(rs.getInt("P_ID"));
        patient.setName(rs.getString("Name"));
        patient.setEmail(rs.getString("Email"));
        patient.setMedicine(rs.getString("Medicine"));
        patient.setAge(rs.getInt("Age"));
        patient.setGender(rs.getString("Gender"));
        patient.setAppointment(rs.getString("appointment"));
        return patient;
    }
}
