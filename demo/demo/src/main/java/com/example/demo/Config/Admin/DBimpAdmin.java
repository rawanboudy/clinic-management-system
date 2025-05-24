package com.example.demo.Config.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class DBimpAdmin implements IDBAdmin {
    private final DataSource dataSource;

    @Autowired
    public DBimpAdmin(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Transactional
    @Override
    public boolean authenticate(String username, String password) throws SQLException {
        String query = "SELECT * FROM admin WHERE username = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, username);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");
                return storedPassword.equals(password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error authenticating admin", e);
        }
        return false;
    }
}