import java.util.*;

// ---------- Each wash type defines its own duration and charge ----------
abstract class WashType {
    private final String name;
    protected WashType(String name) { this.name = name; }
    public String getName() { return name; }
    public abstract int durationMinutes();
    public abstract double charge();
}
class QuickWash extends WashType {
    QuickWash() { super("Quick"); }
    public int durationMinutes() { return 30; }
    public double charge() { return 20; }
}
class NormalWash extends WashType {
    NormalWash() { super("Normal"); }
    public int durationMinutes() { return 45; }
    public double charge() { return 30; }
}
class HeavyWash extends WashType {
    HeavyWash() { super("Heavy"); }
    public int durationMinutes() { return 60; }
    public double charge() { return 45; }
}

class Student {
    private final String name;
    Student(String name) { this.name = name; }
    public String getName() { return name; }
}

class WashCycle {
    private final Student student; private final WashingMachine machine; private final WashType type;
    WashCycle(Student s, WashingMachine m, WashType t) { student = s; machine = m; type = t; }
    public Student getStudent() { return student; }
    public WashingMachine getMachine() { return machine; }
    public WashType getType() { return type; }
    public double charge() { return type.charge(); }
}

class WashingMachine {
    private final String id;
    private WashCycle current;                 // null = free; private so no outside code can change it
    WashingMachine(String id) { this.id = id; }
    public String getId() { return id; }
    public boolean isFree() { return current == null; }
    void begin(WashCycle c) { current = c; }   // package-private: only LaundryService calls these
    void finish() { current = null; }
    boolean isRunning() { return current != null; }
}

// Common booking logic: never changes when a new WashType (e.g. Delicate) is added
class LaundryService {
    public WashCycle startWash(Student s, WashingMachine m, WashType t) {
        if (!m.isFree()) {
            System.out.println("Machine " + m.getId() + " is currently busy.");
            return null;
        }
        WashCycle c = new WashCycle(s, m, t);
        m.begin(c);
        System.out.println(t.getName() + " wash started on " + m.getId() + " for " + s.getName()
                + " (" + t.durationMinutes() + " min). Charge: ₹" + String.format("%.2f", c.charge()) + ".");
        return c;
    }
    public void completeCycle(WashingMachine m) {
        if (!m.isRunning()) { System.out.println(m.getId() + " has no wash in progress."); return; }
        m.finish();
        System.out.println(m.getId() + " cycle completed. " + m.getId() + " is now free.");
    }
}

public class LaundryQueueSystem {
    public static void main(String[] args) {
        LaundryService svc = new LaundryService();
        WashingMachine m1 = new WashingMachine("M1"), m2 = new WashingMachine("M2");
        Student asha = new Student("Asha"), ravi = new Student("Ravi"), neha = new Student("Neha");

        svc.startWash(asha, m1, new QuickWash());
        svc.startWash(ravi, m1, new HeavyWash());
        svc.startWash(ravi, m2, new HeavyWash());
        svc.completeCycle(m1);
        svc.startWash(neha, m1, new NormalWash());
    }
}
