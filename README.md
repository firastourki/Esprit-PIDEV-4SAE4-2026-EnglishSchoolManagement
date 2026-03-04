# English School Management - Task & Attendance Platform

## Overview
This project was developed as part of the **PIDEV - 4th Year Engineering Program** at **Esprit School of Engineering** (Academic Year 2025-2026).
It is a full-stack microservices application for managing an English language school, featuring schedule management and student attendance tracking.

## Features
- **Schedule Management:** Full CRUD operations for class schedules (day, time, room).
- **Attendance Tracking:** Mark and monitor student attendance (Present/Absent/Late).
- **Microservice Architecture:** Independent services for Schedules and Attendance.
- **Service Discovery:** Automatic service registration and discovery via Netflix Eureka.

## Tech Stack
- **Frontend:** Angular 17, TypeScript, Tailwind CSS
- **Backend:** Java 17, Spring Boot 3.2, Spring Cloud
- **Service Discovery:** Netflix Eureka Server
- **API Gateway:** Runs on port `8080`, registers with Eureka and routes requests to services
- **Database:** PostgreSQL / H2 (for development)
- **Others:** Maven, Git, Docker

## Architecture
The application is built using a microservices architecture:
1.  **Eureka Server:** Runs on port `8761`, acts as the service registry.
2.  **Attendance Service:** Manages attendance records, runs on port `8081`, registers with Eureka.
3.  **Schedule Service:** Manages class schedules, runs on port `8082`, registers with Eureka.
4.  **Frontend (Angular):** Consumes the APIs from both services.

## Academic Context
Developed at **Esprit School of Engineering - Tunisia**
- **Program:** PI-DEV (Integrated Project Development)
- **Class:** 4SAE4
- **Academic Year:** 2025-2026

## Contributors
- **Rayen Karouch** - `rayen` branch - Attendance & Schedule CRUD, Frontend Integration.

## Acknowledgments

Special thanks to our project supervisors and teammates for their guidance and collaboration.
