# Employee-Salary-Tracker (Payroll Management System)
A console-based application developed in **Java**, designed to manage employee data and payroll information. This project demonstrates core **Java concepts**, **database integration**, **object-oriented programming principles**, and the use of data structures like **HashMap** for efficient in-memory operations.

---

## Approach: HashMap + Database
The application employs a hybrid approach that leverages both **HashMap** and **database storage** for efficient and reliable data management:  
1. **On Program Startup**:  
   - Load data from the database into the HashMap for fast in-memory operations.  
2. **During Execution**:  
   - Perform CRUD operations on the HashMap to ensure high-speed data manipulation.  
3. **On Program Termination**:  
   - Synchronize the HashMap with the database to persist any new records or updates made during execution.  

---

## Features
- Add, view, update, and delete employee records.  
- Generate and view payslips for employees.  
- Store and retrieve data using **MySQL**.  
- Modular design with multiple classes and services for better maintainability.  
- Dynamic payroll calculations, including HRA, DA, and deductions.  

---

## Technologies Used
- **Java**: Core programming language.  
- **MySQL**: Database for storing employee and payslip details.  
- **JDBC**: For database connectivity.  

---

# Setup Instructions
Clone the repository.
## Database Setup
1. Import the database schema:
   - Open MySQL Workbench or your preferred MySQL client.
   - Run the commands in the `database_schema.sql` file to set up the database.
2. Configure the database connection in the `DBConnection.java` file:
   
   private static final String URL = "jdbc:mysql://localhost:3306/payroll";
   private static final String USER = "your_username";
   private static final String PASSWORD = "your_password";

# Java Code Setup and Execution
## Prerequisites
Install JDK: Ensure that Java Development Kit (JDK) is installed on your system (version 8 or later).
Install an IDE: Any Java IDE, such as Eclipse, IntelliJ IDEA, NetBeans or VS Code.
Install MySQL Connector: Download the MySQL JDBC driver from MySQL Downloads.

# Commands to compile and run the application:
javac -d build -cp lib/mysql-connector-j-9.1.0.jar src/payroll/db/DBConnection.java src/payroll/model/Employee.java src/payroll/model/Payslip.java src/payroll/services/EmployeeService.java src/payroll/services/PayslipService.java src/payroll/Main.java

java -cp "build;lib/mysql-connector-j-9.1.0.jar" payroll.Main


# How It Works
The system provides a menu-driven interface to perform the following operations:

Manage employee records (add, view, delete).
Generate payslips for employees based on salary and allowances.
View all payslips stored in the database.

![image](https://github.com/user-attachments/assets/49e092c4-cf56-4844-9578-15fbf8e22214)
![image](https://github.com/user-attachments/assets/80f2b4e9-b52c-4dd7-9276-af94f730f173)
![image](https://github.com/user-attachments/assets/db253e5c-66ae-4fd1-a1b4-15e71f40eee3)


