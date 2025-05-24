package com.example.demo.Config.Appointment;

import java.sql.Date;

public class Appointment {
    private int appointmentId;
    private Date appointmentDate;
    private String patientName;
    private String doctorName;
    private String appointmentStatus;
    private String description;

    // Constructor
    public Appointment() {
        this.appointmentId = 0;
        this.appointmentDate = new Date(System.currentTimeMillis());
        this.patientName = "";
        this.doctorName = "";
        this.appointmentStatus = "";
        this.description = "";
    }

    // Getters and setters
    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(String appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
