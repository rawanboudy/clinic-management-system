package com.example.demo.Config.Patient;

import java.sql.SQLException;
import java.util.List;

public interface IDBPatient {
    Patient getByname(String name) throws SQLException;
    Patient getById(int id) throws SQLException;
    List<Patient> getAll() throws SQLException;
    void insert(Patient object) throws SQLException;
    void update(Patient object) throws SQLException;
    int delete(int id) throws SQLException;
    void updatePrescriptions(int patientId, String prescriptions) throws SQLException; // Added method
}
