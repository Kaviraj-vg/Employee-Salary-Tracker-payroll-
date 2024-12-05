package payroll;

import payroll.model.Employee;
import payroll.model.Payslip;
import payroll.services.EmployeeService;
import payroll.services.PayslipService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EmployeeService employeeService = new EmployeeService();
        PayslipService payslipService = new PayslipService();  
        Scanner scanner = new Scanner(System.in);

        // Load data from database into HashMap
        employeeService.loadEmployeesFromDB();

        // Main Menu
        while (true) {
            System.out.println("\nPayroll Management System");
            System.out.println("1. Add Employee");
            System.out.println("2. View All Employees");
            System.out.println("3. Generate Payslip");
            System.out.println("4. View Payslips");
            System.out.println("5. Delete Employee");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                   
                    System.out.print("Enter Employee ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); 
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Position: ");
                    String position = scanner.nextLine();
                    System.out.print("Enter Salary: ");
                    double salary = scanner.nextDouble();
                    scanner.nextLine(); 
                    System.out.print("Enter Department: ");
                    String department = scanner.nextLine();

                    Employee emp = new Employee(id, name, position, salary, department);
                    employeeService.addOrUpdateEmployee(emp);
                    System.out.println("Employee added/updated successfully.");
                    employeeService.saveEmployeesToDB();

                    break;

                case 2:
                    
                    System.out.println("\nAll Employees:");
                    for (Employee e : employeeService.getAllEmployees().values()) {
                        System.out.println(e);
                    }
                    break;

                case 3:

                    System.out.print("Enter Employee ID: ");
                    int empId = scanner.nextInt();
                    System.out.print("Enter Month: ");
                    String month = scanner.next();
                    System.out.print("Enter Basic Pay: ");
                    double basicPay = scanner.nextDouble();

                    payslipService.generatePayslip(empId, month, basicPay);
                    employeeService.saveEmployeesToDB();

                    break;

                case 4:
                   
                    System.out.print("Enter Employee ID to view payslips: ");
                    int empIdToView = scanner.nextInt();
                    payslipService.viewPayslips(empIdToView);
                    break;

                case 5:
                    System.out.print("Enter Employee ID to delete: ");
                    int deleteEmpId = scanner.nextInt();
                    boolean isDeleted = employeeService.deleteEmployeeById(deleteEmpId);
                    if (isDeleted) {
                        System.out.println("Employee deleted successfully.");
                    } else {
                        System.out.println("Employee not found or could not be deleted.");
                    }
                    employeeService.saveEmployeesToDB();
                    break;

                case 6:
                  
                    employeeService.saveEmployeesToDB();
                    System.out.println("Data saved to database. Exiting...");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
