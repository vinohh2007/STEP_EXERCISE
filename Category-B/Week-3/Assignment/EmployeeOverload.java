public class EmployeeOverload {

    private String empId;
    private String empName;
    private double salary;
    private boolean isIntern;

    public EmployeeOverload(String empId, String empName, double salary) {
        this.empId = empId;
        this.empName = empName;
        this.salary = salary;
        this.isIntern = false;
    }

    public EmployeeOverload(String empId, String empName) {
        this(empId, empName, 0);
        this.isIntern = true;
    }

    public void printProfile() {
        System.out.println(empId + " | " + empName + " | Rs " + salary + " | Intern: " + isIntern);
    }

    public static void main(String[] args) {
        EmployeeOverload permanent = new EmployeeOverload("E-101", "Divya", 65000);
        EmployeeOverload intern = new EmployeeOverload("E-102", "Arjun");

        permanent.printProfile();
        intern.printProfile();
    }
}
