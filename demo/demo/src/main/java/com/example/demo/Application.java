package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import com.example.demo.Config.Appointment.DBimpAppointment;

@SpringBootApplication(scanBasePackages = "com.example.demo")
public class Application {
    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false");
        // Run the Spring Boot application and get the application context
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

        // Optionally, you can initialize and display other windows here
        // For example:
        // new AppointmentManagementWindow(dbimpAppointment);

        // You can call the AdminLoginWindow when needed
        // new AdminLoginWindow(dbimpAdmin);

        // The AssistantManagementWindow will only be opened when explicitly called
    }
}
