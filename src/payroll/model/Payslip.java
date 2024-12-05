package payroll.model;

public class Payslip {
    private int employeeId;
    private String month;
    private double basicPay;
    private double hra;
    private double da;
    private double otherAllowances;
    private double deductions;
    private double grossSalary;
    private double netSalary;

   
    public Payslip(int employeeId, String month, double basicPay, double hra, double da, 
                   double otherAllowances, double deductions, double grossSalary, double netSalary) {
        this.employeeId = employeeId;
        this.month = month;
        this.basicPay = basicPay;
        this.hra = hra;
        this.da = da;
        this.otherAllowances = otherAllowances;
        this.deductions = deductions;
        this.grossSalary = grossSalary;
        this.netSalary = netSalary;
    }


    public int getEmployeeId() {
        return employeeId;
    }

    public String getMonth() {
        return month;
    }

    public double getBasicPay() {
        return basicPay;
    }

    public double getHra() {
        return hra;
    }

    public double getDa() {
        return da;
    }

    public double getOtherAllowances() {
        return otherAllowances;
    }

    public double getDeductions() {
        return deductions;
    }

    public double getGrossSalary() {
        return grossSalary;
    }

    public double getNetSalary() {
        return netSalary;
    }
}
