public class Employee {
    String empId;
    double salary;

    public Employee(String empId, double salary) {
        this.empId = empId;
        this.salary = salary;
    }

    void raiseSalary(double salary) {
        this.salary += salary;
    }

    public static void main(String[] args) {
        Employee[] employees = new Employee[4];
        employees[0] = new Employee("E-101", 40000);
        employees[1] = new Employee("E-102", 55000);
        employees[2] = new Employee("E-103", 62000);
        employees[3] = new Employee("E-104", 48000);

        for (Employee emp : employees) {
            emp.raiseSalary(5000);
            System.out.println(emp.empId + " | Final Salary: Rs " + emp.salary);
        }
    }
}
