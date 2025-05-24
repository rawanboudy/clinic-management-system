# 🏥 Clinic Management System

A modular and extensible system developed using the **Spring Framework**, designed to streamline and manage core clinical operations such as appointments, patient and doctor records, prescriptions, and user authentication for different roles (admin, doctor, assistant).

---

## 📌 Project Overview

The Clinic Management System (CMS) simplifies and automates clinical workflows for administrators, medical staff, and assistants. This project demonstrates the practical application of **Spring's Dependency Injection (DI)** to ensure modularity, testability, and maintainability.

---

## ⚙️ Key Features

### 👤 Authentication
- Admin, Doctor, and Assistant login with role-based access control

### 👨‍⚕️ Patient Management
- Add, update, delete, and view patients (by Admin/Doctor)

### 🧑‍⚕️ Doctor Management
- Admin can perform CRUD operations on doctor records

### 💊 Prescription Management
- Doctors can create, update, and delete patient prescriptions

### 📅 Appointment Scheduling
- Assistants can manage appointments including adding, editing, and deleting entries

---

## 🧱 System Components

- **Authentication Component** – Role-based login (Admin, Doctor, Assistant)
- **Patient Management** – CRUD operations for patient records
- **Doctor Management** – Full control over doctor profiles
- **Prescription Management** – Manage patient prescriptions by doctor
- **Appointment Management** – Schedule and control appointments by assistant

---

## 🧩 Architecture & Design

- **Framework:** Spring (Core, Beans)
- **Design Pattern:** Dependency Injection (IoC)
- **Structure:** Component-based modular architecture
- **Data Access:** Interfaces with DAO/Repository abstraction

---

## 🚀 Operational Scenarios

- Admin adds new patients and doctors
- Doctor logs in and edits prescriptions
- Assistant schedules patient appointments
- Admin views, filters, and deletes records

---

## ✅ Functional Roles

| Role      | Capabilities                                               |
|-----------|------------------------------------------------------------|
| Admin     | Manage patients and doctors, oversee data integrity        |
| Doctor    | View patients, manage prescriptions                        |
| Assistant | Schedule, edit, and delete appointments                    |

---

## 📈 Non-Functional Goals

- **Performance**: <2s response time under load
- **Security**: Role-based access, encrypted data
- **Scalability**: Component-level extensibility
- **Usability**: Simple, intuitive GUI for staff

---

## 🧪 Technologies Used

- **Java** with Spring Framework
- **Spring Core** & Dependency Injection
- (No frontend framework used – simple GUI for demonstration)

---

## 🧠 Benefits of Using Spring & DI

- Increased modularity and maintainability
- Improved unit testing with mockable dependencies
- Clear separation of concerns using interfaces

---

## 👥 Contributors

- Farah Tarek  
- Farah Waleed  
- Mariam Kandeel  
- Rawan Waleed  
- Omar Hany

Supervised by: Dr. Hisham Mansour & Dr. Moamen Zaher – MSA University

---

## 📂 Note

This repository does not include a sophisticated frontend interface. The focus of the project was backend system architecture, modular components, and dependency management using Spring DI.

