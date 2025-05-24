package com.example.demo.Config.Doctor;
import java.sql.SQLException;
import java.util.List;

public interface IDBDoctor {
    Doctor getByname(String name) throws SQLException;
    Doctor getById(int id) throws SQLException;
    List<Doctor> getAll() throws SQLException;
    void insert(Doctor object) throws SQLException;
    void update(Doctor object) throws SQLException;
    int delete(int id) throws SQLException;
}