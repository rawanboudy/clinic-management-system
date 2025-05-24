package com.example.demo.Config.DoctorFunctions;

import com.example.demo.Config.Patient.Patient;

import java.sql.SQLException;

public interface IDBDoctorFunctions {
    void addPrescription(String patientName, String prescription) throws SQLException;

    Patient viewPatientByName(String patientName) throws SQLException;

    void editPrescription(String patientName, String prescription) throws SQLException;

    void deletePrescription(String patientName) throws SQLException;

    void deletePatientByName(String patientName) throws SQLException;

    boolean authenticate(String username, String password) throws SQLException;
}
