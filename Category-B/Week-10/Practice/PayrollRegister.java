import java.util.*;

public class PayrollRegister {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Staff> list = new ArrayList<>();
        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (line.isEmpty()) continue;
            String[] p = line.split("\\s+");
            switch (p[0].toLowerCase()) {
                case "fulltime": list.add(new FullTime(p[1], Double.parseDouble(p[2]))); break;
                case "parttime": list.add(new PartTime(p[1], Double.parseDouble(p[2]), Double.parseDouble(p[3]))); break;
                case "intern":   list.add(new Intern(p[1], Double.parseDouble(p[2]))); break;
            }
        }
        Staff[] register = list.toArray(new Staff[0]);   // array of payslips
        double total = 0;
        Staff top = null;
        for (Staff s : register) {
            System.out.println(s);
            total += s.pay();
            if (top == null || s.pay() > top.pay()) top = s;
        }
        System.out.println("Total " + Staff.fmt(total));
        if (top != null) System.out.println("top earner " + top.name);
    }
}

abstract class Staff {
    protected final String name;
    Staff(String name) { this.name = name; }
    abstract String type();
    abstract double pay();

    static String fmt(double d) {
        return d == Math.rint(d) ? String.valueOf((long) d) : String.valueOf(d);
    }

    @Override public String toString() {
        return "Payslip[name=" + name + ", type=" + type() + ", pay=" + fmt(pay()) + "]";
    }
}

class FullTime extends Staff {
    private final double salary;
    FullTime(String n, double salary) { super(n); this.salary = salary; }
    String type() { return "FullTime"; }
    double pay() { return salary; }
}

class PartTime extends Staff {
    private final double hours, rate;
    PartTime(String n, double hours, double rate) { super(n); this.hours = hours; this.rate = rate; }
    String type() { return "PartTime"; }
    double pay() { return hours * rate; }
}

class Intern extends Staff {
    private final double stipend;
    Intern(String n, double stipend) { super(n); this.stipend = stipend; }
    String type() { return "Intern"; }
    double pay() { return stipend; }
}
