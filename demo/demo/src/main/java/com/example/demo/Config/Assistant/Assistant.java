package com.example.demo.Config.Assistant;

import java.util.ArrayList;
import java.util.List;

public class Assistant {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String password;
    private List<Integer> appointmentIds;

    // Constructor
    public Assistant() {
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
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public List<Integer> getAppointmentIds() {
        return appointmentIds;
    }
    public void setAppointmentIds(List<Integer> appointmentIds) {
        this.appointmentIds = appointmentIds;
    }
}
