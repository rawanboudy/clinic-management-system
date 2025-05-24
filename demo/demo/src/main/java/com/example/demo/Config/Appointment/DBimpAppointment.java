package com.example.demo.Config.Appointment;

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
public class DBimpAppointment implements IDBAppointment {
    private final DataSource dataSource;

    @Autowired
    public DBimpAppointment(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Appointment getByname(String name) throws SQLException {
        String query = "SELECT * FROM appointments WHERE patientName = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, name);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return resultSetToAppointment(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error getting appointment by name", e);
        }
        return null; // Return null if appointment not found
    }

    @Override
    public Appointment getById(int id) throws SQLException {
        String query = "SELECT * FROM appointments WHERE appointmentId = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return resultSetToAppointment(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error getting appointment by ID", e);
        }
        return null; // Return null if appointment not found
    }

    @Override
    public List<Appointment> getAll() throws SQLException {
        List<Appointment> appointmentList = new ArrayList<>();
        String query = "SELECT * FROM appointments";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                appointmentList.add(resultSetToAppointment(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error getting all appointments", e);
        }
        return appointmentList;
    }
    

    @Override
    public void insert(Appointment appointment) throws SQLException {
        String query = "INSERT INTO appointments (appointmentDate, patientName, doctorName, appointmentStatus, description) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setDate(1, appointment.getAppointmentDate());
            ps.setString(2, appointment.getPatientName());
            ps.setString(3, appointment.getDoctorName());
            ps.setString(4, appointment.getAppointmentStatus());
            ps.setString(5, appointment.getDescription());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error inserting appointment", e);
        }
    }

    @Override
    public void update(Appointment appointment) throws SQLException {
        String query = "UPDATE appointments SET appointmentDate = ?, patientName = ?, doctorName = ?, appointmentStatus = ?, description = ? WHERE appointmentId = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setDate(1, appointment.getAppointmentDate());
            ps.setString(2, appointment.getPatientName());
            ps.setString(3, appointment.getDoctorName());
            ps.setString(4, appointment.getAppointmentStatus());
            ps.setString(5, appointment.getDescription());
            ps.setInt(6, appointment.getAppointmentId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error updating appointment", e);
        }
    }

    @Override
    public int delete(int id) throws SQLException {
        String query = "DELETE FROM appointments WHERE appointmentId = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error deleting appointment with ID: " + id, e);
        }
    }

    @Override
    public void addAppointmentToPatient(String patientName, Appointment appointment) throws SQLException {
        // Get the patient ID using the patient name
        int patientId = getPatientIdByName(patientName);
        if (patientId == -1) {
            throw new SQLException("Patient not found");
        }

        // Insert the appointment into the appointments table
        insert(appointment);

        // Optionally update other tables as needed, based on your application's logic
    }

    @Override
    public int deleteAppointmentByPatientAndDoctorName(String patientName, String doctorName) throws SQLException {
        String query = "DELETE FROM appointments WHERE patientName = ? AND doctorName = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, patientName);
            ps.setString(2, doctorName);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error deleting appointment for patient: " + patientName + " and doctor: " + doctorName, e);
        }
    }
    public void add(Appointment appointment) throws SQLException {
        String query = "INSERT INTO appointments (appointmentDate, patientName, doctorName, appointmentStatus, description) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setDate(1, appointment.getAppointmentDate());
            ps.setString(2, appointment.getPatientName());
            ps.setString(3, appointment.getDoctorName());
            ps.setString(4, appointment.getAppointmentStatus());
            ps.setString(5, appointment.getDescription());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error inserting appointment", e);
        }
    }
    @Override
    public void updateAppointmentByPatientAndDoctorName(String patientName, String doctorName, Appointment appointment) throws SQLException {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            // Get patient and doctor IDs
            int patientId = getPatientIdByName(patientName);
            int doctorId = getDoctorIdByName(doctorName);

            if (patientId == -1 || doctorId == -1) {
                throw new SQLException("Patient or Doctor not found");
            }

            // Update the appointment date in the patient table
            String updatePatientQuery = "UPDATE patients SET appointmentDate = ? WHERE patientId = ?";
            try (PreparedStatement ps = connection.prepareStatement(updatePatientQuery)) {
                ps.setDate(1, appointment.getAppointmentDate());
                ps.setInt(2, patientId);
                ps.executeUpdate();
            }

            // Update the appointment date in the doctor table
            String updateDoctorQuery = "UPDATE doctors SET appointmentDate = ? WHERE doctorId = ?";
            try (PreparedStatement ps = connection.prepareStatement(updateDoctorQuery)) {
                ps.setDate(1, appointment.getAppointmentDate());
                ps.setInt(2, doctorId);
                ps.executeUpdate();
            }

            // Update the appointment record
            String query = "UPDATE appointments SET appointmentDate = ?, appointmentStatus = ?, description = ? WHERE patientName = ? AND doctorName = ?";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setDate(1, appointment.getAppointmentDate());
                ps.setString(2, appointment.getAppointmentStatus());
                ps.setString(3, appointment.getDescription());
                ps.setString(4, patientName);
                ps.setString(5, doctorName);
                ps.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();
            }
            e.printStackTrace();
            throw new SQLException("Error updating appointment for patient: " + patientName + " and doctor: " + doctorName, e);
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
    }

    private int getPatientIdByName(String patientName) throws SQLException {
        String query = "SELECT patientId FROM patients WHERE patientName = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, patientName);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("patientId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error getting patient ID by name", e);
        }
        return -1; // Return -1 if patient not found
    }

    private int getDoctorIdByName(String doctorName) throws SQLException {
        String query = "SELECT doctorId FROM doctors WHERE doctorName = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, doctorName);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("doctorId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error getting doctor ID by name", e);
        }
        return -1; // Return -1 if doctor not found
    }

    private Appointment resultSetToAppointment(ResultSet rs) throws SQLException {
        Appointment appointment = new Appointment();
        appointment.setAppointmentId(rs.getInt("appointmentId"));
        appointment.setAppointmentDate(rs.getDate("appointmentDate"));
        appointment.setPatientName(rs.getString("patientName"));
        appointment.setDoctorName(rs.getString("doctorName"));
        appointment.setAppointmentStatus(rs.getString("appointmentStatus"));
        appointment.setDescription(rs.getString("description"));
        return appointment;
    }
}
