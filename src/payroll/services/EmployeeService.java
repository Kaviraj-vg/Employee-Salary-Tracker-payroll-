package payroll.services;

import payroll.db.DBConnection;
import payroll.model.Employee;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class EmployeeService {
    private Map<Integer, Employee> employeeMap = new HashMap<>();

    // Load data from DB into HashMap
    public void loadEmployeesFromDB() {
        String query = "SELECT * FROM Employee";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Employee emp = new Employee(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("position"),
                        rs.getDouble("salary"),
                        rs.getString("department")
                );
                employeeMap.put(emp.getId(), emp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add or update employee in HashMap
    public void addOrUpdateEmployee(Employee employee) {
        employeeMap.put(employee.getId(), employee);
    }

    public boolean deleteEmployeeById(int employeeId) {
        String deletePayslipsQuery = "DELETE FROM payslips WHERE employee_id = ?";
        String deleteEmployeeQuery = "DELETE FROM Employee WHERE id = ?";
        try (Connection conn = DBConnection.getConnection()) {
            // Delete associated payslips
            try (PreparedStatement payslipsStmt = conn.prepareStatement(deletePayslipsQuery)) {
                payslipsStmt.setInt(1, employeeId);
                payslipsStmt.executeUpdate();
            }
    
            // Delete employee
            try (PreparedStatement employeeStmt = conn.prepareStatement(deleteEmployeeQuery)) {
                employeeStmt.setInt(1, employeeId);
                int rowsAffected = employeeStmt.executeUpdate();
                if (rowsAffected > 0) {
                    employeeMap.remove(employeeId); // Update the in-memory map
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    

    // Save HashMap data to DB
    public void saveEmployeesToDB() {
        String query = "INSERT INTO Employee (id, name, position, salary, department) " +
                       "VALUES (?, ?, ?, ?, ?) " +
                       "ON DUPLICATE KEY UPDATE name = ?, position = ?, salary = ?, department = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            for (Employee emp : employeeMap.values()) {
                stmt.setInt(1, emp.getId());
                stmt.setString(2, emp.getName());
                stmt.setString(3, emp.getPosition());
                stmt.setDouble(4, emp.getSalary());
                stmt.setString(5, emp.getDepartment());
    
                // Set for update part in case of duplicate key
                stmt.setString(6, emp.getName());
                stmt.setString(7, emp.getPosition());
                stmt.setDouble(8, emp.getSalary());
                stmt.setString(9, emp.getDepartment());
    
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    // Get all employees
    public Map<Integer, Employee> getAllEmployees() {
        return employeeMap;
    }
}
