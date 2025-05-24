package com.example.demo.Config.Assistant;

import java.sql.SQLException;
import java.util.List;

public interface IDBAssistant {
    Assistant getByname(String name) throws SQLException;
    Assistant getById(int id) throws SQLException;
    List<Assistant> getAll() throws SQLException;
    boolean authenticate(String username, String password) throws SQLException;
}