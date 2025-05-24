package com.example.demo.Config.Assistant;

import com.example.demo.Config.Appointment.DBimpAppointment;
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
public class DBimpAssistant implements IDBAssistant {
    private final DataSource dataSource;
    private final DBimpAppointment dbimpAppointment;

    @Autowired
    public DBimpAssistant(DataSource dataSource, DBimpAppointment dbimpAppointment) {
        this.dataSource = dataSource;
        this.dbimpAppointment = dbimpAppointment;
    }

    @Override
    public Assistant getByname(String name) throws SQLException {
        String query = "SELECT id, name, email, phone, password FROM assistant WHERE name = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, name);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                return resultSetToAssistant(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error getting assistant by name", e);
        }
        return null;
    }

    @Override
    public Assistant getById(int id) throws SQLException {
        String query = "SELECT id, name, email, phone, password FROM assistant WHERE assistantID = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                return resultSetToAssistant(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error getting assistant by ID", e);
        }
        return null;
    }

    @Override
    public List<Assistant> getAll() throws SQLException {
        List<Assistant> assistantList = new ArrayList<>();
        String query = "SELECT id, name, email, phone, password FROM assistant";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                assistantList.add(resultSetToAssistant(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error getting all assistant", e);
        }
        return assistantList;
    }



    @Override
    public boolean authenticate(String username, String password) throws SQLException {
        String query = "SELECT id FROM assistant WHERE name = ? AND password = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet resultSet = ps.executeQuery();
            return resultSet.next(); // Returns true if a matching assistant is found
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error authenticating assistant: " + e.getMessage());
        }
    }

    public boolean updatePatientEmail(int patientId, String newEmail) throws SQLException {
        String query = "UPDATE patients SET Email = ? WHERE P_ID = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, newEmail);
            ps.setInt(2, patientId);
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0; // Returns true if the update was successful
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error updating patient email", e);
        }
    }
    private Assistant resultSetToAssistant(ResultSet rs) throws SQLException {
        Assistant assistant = new Assistant();
        assistant.setId(rs.getInt("id"));
        assistant.setName(rs.getString("name"));
        assistant.setEmail(rs.getString("email"));
        assistant.setPhone(rs.getString("phone"));
        assistant.setPassword(rs.getString("password"));

        List<Integer> appointmentIds = getAppointmentIdsByAssistantId(assistant.getId());
        assistant.setAppointmentIds(appointmentIds);

        return assistant;
    }

    private List<Integer> getAppointmentIdsByAssistantId(int assistantId) throws SQLException {
        String query = "SELECT appointmentId FROM assistant_appointments WHERE assistantId = ?";
        List<Integer> appointmentIds = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, assistantId);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                appointmentIds.add(resultSet.getInt("appointmentId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error getting appointment IDs for assistant", e);
        }
        return appointmentIds;
    }
}