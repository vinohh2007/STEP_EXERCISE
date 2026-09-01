public class EmployeeStatic {

    private String empName;
    private double salary;

    private static String companyName = "Bright Horizon Technologies";
    private static int employeeCount = 0;

    public EmployeeStatic(String empName, double salary) {
        this.empName = empName;
        this.salary = salary;
        employeeCount++;
    }

    public static void printCompanyInfo() {
        System.out.println(companyName);
        System.out.println("Employees on record: " + employeeCount);
    }

    public static void main(String[] args) {
        EmployeeStatic e1 = new EmployeeStatic("Rahul", 45000);
        EmployeeStatic e2 = new EmployeeStatic("Sneha", 52000);
        EmployeeStatic e3 = new EmployeeStatic("Kabir", 48000);

        EmployeeStatic.printCompanyInfo();
    }
}
