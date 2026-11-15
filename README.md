# Employee Management System

A Spring Boot application for adding, viewing, updating and deleting
employees using MySQL, Thymeleaf and Lombok.

## Features

- Employee registration and employee list
- Edit and delete employees
- Input validation and duplicate email checking
- Styled web interface

## Requirements

- JDK 23 (matching the current project configuration)
- MySQL or XAMPP
- Internet connection for the first Maven build

## Setup

1. Start MySQL.
2. Create a database named `employee_db`.
3. Check the MySQL port in `src/main/resources/application.properties`.
   This project currently uses port `3307`.
4. Copy `secrets.properties.example` to `secrets.properties`
   in the project root.
5. Enter your MySQL username and password in `secrets.properties`.
   For a local account with no password, leave the password value empty.

## Run on Windows

run the EmployeeManagerApplication.java

Open http://localhost:8081/employees in your browser.

## Configuration

`secrets.properties` contains local database credentials and is ignored
by Git. `secrets.properties.example` provides sample settings.

## Testing

CRUD operations can be tested in the browser and Postman.
For Postman POST requests, use Body → x-www-form-urlencoded.
Database changes can be checked using MySQL Workbench.