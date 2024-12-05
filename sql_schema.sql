CREATE DATABASE payroll;
USE payroll;

CREATE TABLE Employee (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    position VARCHAR(50),
    salary DOUBLE,
    department VARCHAR(50)
);

CREATE TABLE payslips (
    payslip_id INT AUTO_INCREMENT PRIMARY KEY,
    employee_id INT NOT NULL,
    month VARCHAR(20) NOT NULL,
    basic_pay DOUBLE NOT NULL,
    hra DOUBLE NOT NULL,
    da DOUBLE NOT NULL,
    other_allowances DOUBLE NOT NULL,
    deductions DOUBLE NOT NULL,
    gross_salary DOUBLE NOT NULL,
    net_salary DOUBLE NOT NULL,
    FOREIGN KEY (employee_id) REFERENCES Employee(id) ON DELETE CASCADE
);


select * from Employee;
select * from payslips;
