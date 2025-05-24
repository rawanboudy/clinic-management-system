package com.example.demo.Config.Appointment;

import java.sql.SQLException;
import java.util.List;

public interface IDBAppointment {
    Appointment getByname(String name) throws SQLException;
    Appointment getById(int id) throws SQLException;
    List<Appointment> getAll() throws SQLException;
    void insert(Appointment object) throws SQLException;
    void update(Appointment object) throws SQLException;
    int delete(int id) throws SQLException;
    
    // New methods
    void addAppointmentToPatient(String patientName, Appointment appointment) throws SQLException;
    void updateAppointmentByPatientAndDoctorName(String patientName, String doctorName, Appointment appointment) throws SQLException;
    int deleteAppointmentByPatientAndDoctorName(String patientName, String doctorName) throws SQLException;
}
