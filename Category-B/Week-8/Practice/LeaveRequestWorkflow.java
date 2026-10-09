import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

enum Status { PENDING, APPROVED, REJECTED }

// ---------- Policy per employee type (Strategy / polymorphism) ----------
interface LeavePolicy {
    boolean isAllowed(long days);
    String describe();
}
class FullTimePolicy implements LeavePolicy {
    public boolean isAllowed(long d) { return d <= 20; }
    public String describe() { return "max 20 days"; }
}
class PartTimePolicy implements LeavePolicy {
    public boolean isAllowed(long d) { return d <= 10; }
    public String describe() { return "max 10 days"; }
}
class ContractorPolicy implements LeavePolicy {
    public boolean isAllowed(long d) { return d <= 5; }
    public String describe() { return "max 5 days"; }
}

abstract class Employee {
    private final String name;
    protected Employee(String name) { this.name = name; }
    public String getName() { return name; }
    protected abstract LeavePolicy policy();

    public LeaveRequest submitLeave(LocalDate s, LocalDate e) {
        long days = ChronoUnit.DAYS.between(s, e) + 1;
        if (e.isBefore(s) || !policy().isAllowed(days)) {
            System.out.println("Leave request for " + name + " invalid (" + policy().describe() + ").");
            return null;
        }
        LeaveRequest r = new LeaveRequest(this, s, e);
        System.out.println("Leave request submitted for " + name + " (" + r.range() + "). Status: PENDING.");
        return r;
    }
}
class FullTimeEmployee extends Employee { FullTimeEmployee(String n){super(n);} protected LeavePolicy policy(){return new FullTimePolicy();} }
class PartTimeEmployee extends Employee { PartTimeEmployee(String n){super(n);} protected LeavePolicy policy(){return new PartTimePolicy();} }
class Contractor extends Employee { Contractor(String n){super(n);} protected LeavePolicy policy(){return new ContractorPolicy();} }

class LeaveRequest {
    private final Employee employee; private final LocalDate start, end;
    private Status status = Status.PENDING;
    private Reviewer reviewedBy;
    LeaveRequest(Employee e, LocalDate s, LocalDate d) { employee = e; start = s; end = d; }
    public Status getStatus() { return status; }
    public Employee getEmployee() { return employee; }
    public String range() { return start.getMonth().toString().substring(0,1) + start.getMonth().toString().substring(1,3).toLowerCase()
            + " " + start.getDayOfMonth() + "-" + end.getDayOfMonth(); }

    // package-private: only a Reviewer in this package may decide
    void decide(Reviewer r, Status target) {
        reviewedBy = r;
        changeStatus(target);
    }
    // single guarded entry point for all transitions
    public boolean changeStatus(Status target) {
        if (status != Status.PENDING) {
            System.out.println("Cannot change leave request status from " + cap(status) + " to " + cap(target) + ".");
            return false;
        }
        if (target == Status.PENDING) return true; // no-op
        status = target;
        return true;
    }
    static String cap(Status s) { return s.name().charAt(0) + s.name().substring(1).toLowerCase(); }
}

class Reviewer {
    private final String name;
    Reviewer(String n) { name = n; }
    public void approve(LeaveRequest r) {
        if (r.changeStatus(Status.APPROVED))
            System.out.println(r.getEmployee().getName() + "'s leave request (" + r.range() + ") approved. Status: Approved.");
    }
    public void reject(LeaveRequest r) {
        if (r.changeStatus(Status.REJECTED))
            System.out.println(r.getEmployee().getName() + "'s leave request (" + r.range() + ") rejected. Status: Rejected.");
    }
}

public class LeaveRequestWorkflow {
    public static void main(String[] a) {
        Employee john = new FullTimeEmployee("John"), jane = new PartTimeEmployee("Jane");
        Reviewer alice = new Reviewer("Alice"), bob = new Reviewer("Bob");
        LeaveRequest r1 = john.submitLeave(LocalDate.of(2026,1,1), LocalDate.of(2026,1,5));
        alice.approve(r1);
        LeaveRequest r2 = jane.submitLeave(LocalDate.of(2026,2,10), LocalDate.of(2026,2,11));
        bob.reject(r2);
        r1.changeStatus(Status.PENDING);   // illegal transition
    }
}
