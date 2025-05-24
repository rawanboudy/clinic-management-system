package com.example.demo.Config.Doctor;

import java.util.ArrayList;
import java.util.List;

public class Doctor {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String password; // Add password field
    private String specialty; // Corrected spelling of specialty field
    private List<Integer> appointmentIds;

    // Constructor
    public Doctor() {
        appointmentIds = new ArrayList<>();
    }

    // Getters and setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPassword() { // Add getter for password
        return password;
    }
    public void setPassword(String password) { // Add setter for password
        this.password = password;
    }
    public String getSpecialty() { // Corrected spelling of getter for specialty
        return specialty;
    }
    public void setSpecialty(String specialty) { // Corrected spelling of setter for specialty
        this.specialty = specialty;
    }
    public List<Integer> getAppointmentIds() {
        return appointmentIds;
    }
    public void setAppointmentIds(List<Integer> appointmentIds) {
        this.appointmentIds = appointmentIds;
    }
}
