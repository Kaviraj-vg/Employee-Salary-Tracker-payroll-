package payroll.services;

import payroll.model.Payslip;
import payroll.db.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PayslipService {

    public void generatePayslip(int employeeId, String month, double basicPay) {

        if (!employeeExists(employeeId)) {
            System.out.println("Employee with ID " + employeeId + " does not exist.");
            return;  // Stop further processing if employee doesn't exist
        }


        double hra = basicPay * 0.20; // Example: 20% HRA
        double da = basicPay * 0.10;  // Example: 10% DA
        double otherAllowances = 3000; // Fixed allowance
        double deductions = (basicPay + hra + da + otherAllowances) * 0.15; // 15% deductions
        double grossSalary = basicPay + hra + da + otherAllowances;
        double netSalary = grossSalary - deductions;

        Payslip payslip = new Payslip(employeeId, month, basicPay, hra, da, otherAllowances, deductions, grossSalary, netSalary);

        savePayslipToDB(payslip);
    }

    private void savePayslipToDB(Payslip payslip) {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "INSERT INTO payslips (employee_id, month, basic_pay, hra, da, other_allowances, deductions, gross_salary, net_salary) " +
                           "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, payslip.getEmployeeId());
            ps.setString(2, payslip.getMonth());
            ps.setDouble(3, payslip.getBasicPay());
            ps.setDouble(4, payslip.getHra());
            ps.setDouble(5, payslip.getDa());
            ps.setDouble(6, payslip.getOtherAllowances());
            ps.setDouble(7, payslip.getDeductions());
            ps.setDouble(8, payslip.getGrossSalary());
            ps.setDouble(9, payslip.getNetSalary());

            ps.executeUpdate();
            System.out.println("Payslip saved successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewPayslips(int employeeId) {
        List<Payslip> payslips = getPayslipsFromDB(employeeId);

        if (payslips.isEmpty()) {
            System.out.println("No payslips found for this employee.");
        } else {
            System.out.println("Payslips for Employee ID: " + employeeId);
            for (Payslip payslip : payslips) {
                System.out.println("\n----------------------------");
                System.out.println("Month: " + payslip.getMonth());
                System.out.println("Basic Pay: " + payslip.getBasicPay());
                System.out.println("HRA: " + payslip.getHra());
                System.out.println("DA: " + payslip.getDa());
                System.out.println("Other Allowances: " + payslip.getOtherAllowances());
                System.out.println("Deductions: " + payslip.getDeductions());
                System.out.println("Gross Salary: " + payslip.getGrossSalary());
                System.out.println("Net Salary: " + payslip.getNetSalary());
                System.out.println("----------------------------");
            }
        }
    }

    private List<Payslip> getPayslipsFromDB(int employeeId) {
        List<Payslip> payslips = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM payslips WHERE employee_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, employeeId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("employee_id");
                String month = rs.getString("month");
                double basicPay = rs.getDouble("basic_pay");
                double hra = rs.getDouble("hra");
                double da = rs.getDouble("da");
                double otherAllowances = rs.getDouble("other_allowances");
                double deductions = rs.getDouble("deductions");
                double grossSalary = rs.getDouble("gross_salary");
                double netSalary = rs.getDouble("net_salary");

                Payslip payslip = new Payslip(id, month, basicPay, hra, da, otherAllowances, deductions, grossSalary, netSalary);
                payslips.add(payslip);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payslips;
    }
    private boolean employeeExists(int employeeId) {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT COUNT(*) FROM employee WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, employeeId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0; // If count is greater than 0, employee exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Employee does not exist
    }

}
