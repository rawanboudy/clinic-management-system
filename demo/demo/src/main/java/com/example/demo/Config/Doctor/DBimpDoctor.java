package com.example.demo.Config.Doctor;

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
public class DBimpDoctor implements IDBDoctor {
    private final DataSource dataSource;

    @Autowired
    public DBimpDoctor(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Doctor getByname(String name) throws SQLException {
        String query = "SELECT * FROM doctors WHERE name = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, name);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                return resultSetToDoctor(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error getting doctor by name", e);
        }
        return null;
    }

    @Override
    public Doctor getById(int id) throws SQLException {
        String query = "SELECT * FROM doctors WHERE D_ID = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                return resultSetToDoctor(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error getting doctor by ID", e);
        }
        return null;
    }

    @Override
    public List<Doctor> getAll() throws SQLException {
        List<Doctor> doctorList = new ArrayList<>();
        String query = "SELECT * FROM doctors";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                doctorList.add(resultSetToDoctor(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error getting all doctors", e);
        }
        return doctorList;
    }

    @Override
    public void insert(Doctor doctor) throws SQLException {
        String query = "INSERT INTO doctors (Email, Name, Phone, Password, Specialty) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, doctor.getEmail());
            preparedStatement.setString(2, doctor.getName());
            preparedStatement.setString(3, doctor.getPhone());
            preparedStatement.setString(4, doctor.getPassword());
            preparedStatement.setString(5, doctor.getSpecialty());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error inserting doctor: " + e.getMessage(), e.getSQLState(), e.getErrorCode());
        }
    }

    @Override
    public void update(Doctor doctor) throws SQLException {
        String query = "UPDATE doctors SET Email = ?, Name = ?, Phone = ?, Password = ?, Specialty = ? WHERE D_ID = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, doctor.getEmail());
            preparedStatement.setString(2, doctor.getName());
            preparedStatement.setString(3, doctor.getPhone());
            preparedStatement.setString(4, doctor.getPassword());
            preparedStatement.setString(5, doctor.getSpecialty());
            preparedStatement.setInt(6, doctor.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error updating doctor: " + e.getMessage(), e.getSQLState(), e.getErrorCode());
        }
    }

    @Override
    public int delete(int doctorId) throws SQLException {
        String query = "DELETE FROM doctors WHERE D_ID = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, doctorId);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error deleting doctor with ID: " + doctorId, e);
        }
    }

    private Doctor resultSetToDoctor(ResultSet rs) throws SQLException {
        Doctor doctor = new Doctor();
        doctor.setId(rs.getInt("D_ID"));
        doctor.setName(rs.getString("name"));
        doctor.setEmail(rs.getString("email"));
        doctor.setPhone(rs.getString("Phone"));
        doctor.setPassword(rs.getString("Password"));
        doctor.setSpecialty(rs.getString("Specialty"));
        return doctor;
    }

    public boolean authenticate(String username, String password) {
        String query = "SELECT * FROM doctors WHERE name = ? AND password = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet resultSet = ps.executeQuery();

            return resultSet.next(); // If there is a matching record, authentication is successful
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
        return false;
    }
}
