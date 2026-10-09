// ---------- Fee rules live in the plans, status rules live in Membership ----------
abstract class MembershipPlan {
    static final double BASE_RATE = 1000;
    private final String name; private final int months;
    protected MembershipPlan(String name, int months) { this.name = name; this.months = months; }
    public String getName() { return name; }
    public int getMonths() { return months; }
    protected abstract double discount();                      // fraction off
    public double calculateFee() { return BASE_RATE * months * (1 - discount()); }
}
class MonthlyPlan extends MembershipPlan { MonthlyPlan() { super("Monthly", 1); } protected double discount() { return 0; } }
class QuarterlyPlan extends MembershipPlan { QuarterlyPlan() { super("Quarterly", 3); } protected double discount() { return 0.10; } }
class AnnualPlan extends MembershipPlan { AnnualPlan() { super("Annual", 12); } protected double discount() { return 0.25; } }

class Membership {
    enum Status { ACTIVE, FROZEN, EXPIRED }
    private final MembershipPlan plan;
    private Status status = Status.ACTIVE;                     // private: changed only via the methods below
    Membership(MembershipPlan p) { plan = p; }
    public MembershipPlan getPlan() { return plan; }
    public Status getStatus() { return status; }
    public double fee() { return plan.calculateFee(); }

    boolean freeze()   { if (status != Status.ACTIVE) return false; status = Status.FROZEN; return true; }
    boolean unfreeze() { if (status != Status.FROZEN) return false; status = Status.ACTIVE; return true; }
    boolean expire()   { if (status == Status.EXPIRED) return false; status = Status.EXPIRED; return true; }
    boolean canCheckIn() { return status == Status.ACTIVE; }
}

class Member {
    private final String name; private Membership membership;
    Member(String n) { name = n; }
    public String getName() { return name; }
    public Membership getMembership() { return membership; }
    private static String cap(Membership.Status s) { return s.name().charAt(0) + s.name().substring(1).toLowerCase(); }

    private static String article(Membership.Status s) { return s == Membership.Status.FROZEN ? "a" : "an"; }

    public void buy(MembershipPlan plan) {
        membership = new Membership(plan);
        System.out.println(plan.getName() + " membership created for " + name + ". Fee: ₹"
                + String.format("%,.2f", membership.fee()) + ". Status: " + cap(membership.getStatus()) + ".");
    }
    public void checkIn() {
        if (membership != null && membership.canCheckIn()) System.out.println(name + " checked in successfully.");
        else System.out.println("Check-in denied: " + name + "'s membership is "
                + (membership == null ? "missing" : cap(membership.getStatus())) + ".");
    }
    public void freeze() {
        if (membership.freeze()) System.out.println(name + "'s membership frozen. Status: Frozen.");
        else System.out.println("Cannot freeze " + article(membership.getStatus()) + " " + cap(membership.getStatus()) + " membership.");
    }
    public void unfreeze() {
        if (membership.unfreeze()) System.out.println(name + "'s membership unfrozen. Status: Active.");
        else System.out.println("Cannot unfreeze " + article(membership.getStatus()) + " " + cap(membership.getStatus()) + " membership.");
    }
    public void expire() {
        if (membership.expire()) System.out.println(name + "'s membership expired. Status: Expired.");
        else System.out.println(name + "'s membership is already Expired.");
    }
}

public class MembershipDeskSystem {
    public static void main(String[] args) {
        Member asha = new Member("Asha"), ravi = new Member("Ravi");
        asha.buy(new QuarterlyPlan());
        ravi.buy(new MonthlyPlan());
        asha.checkIn();
        asha.freeze();
        asha.checkIn();
        ravi.expire();
        ravi.freeze();
    }
}
