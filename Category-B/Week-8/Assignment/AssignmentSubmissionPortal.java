import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

// ---------- Each assignment type owns its late-penalty rule ----------
abstract class Assignment {
    private final String title; private final int maxMarks; private final LocalDate dueDate;
    protected Assignment(String title, int maxMarks, LocalDate dueDate) { this.title = title; this.maxMarks = maxMarks; this.dueDate = dueDate; }
    public String getTitle() { return title; }
    public int getMaxMarks() { return maxMarks; }
    protected abstract double penaltyPerDay();                 // fraction, e.g. 0.10

    // late days are an assignment concern: it knows its own due date
    public long daysLate(LocalDate submittedOn) { return Math.max(0, ChronoUnit.DAYS.between(dueDate, submittedOn)); }
    public double penaltyFraction(long daysLate) { return Math.min(1.0, penaltyPerDay() * daysLate); }
    public double applyPenalty(double awarded, long daysLate) { return awarded * (1 - penaltyFraction(daysLate)); }
}
class CodingAssignment extends Assignment {
    CodingAssignment(String t, int m, LocalDate d) { super(t, m, d); }
    protected double penaltyPerDay() { return 0.10; }
}
class WrittenAssignment extends Assignment {
    WrittenAssignment(String t, int m, LocalDate d) { super(t, m, d); }
    protected double penaltyPerDay() { return 0.20; }
}

class Student {
    private final String name;
    Student(String n) { name = n; }
    public String getName() { return name; }
}

class Submission {
    enum Status { SUBMITTED, GRADED }
    private final Student student; private final Assignment assignment;
    private LocalDate submittedOn; private Status status = Status.SUBMITTED; private double finalMarks;
    Submission(Student s, Assignment a, LocalDate on) { student = s; assignment = a; submittedOn = on; }
    public Student getStudent() { return student; }
    public Assignment getAssignment() { return assignment; }
    public Status getStatus() { return status; }
    public long daysLate() { return assignment.daysLate(submittedOn); }
    public double getFinalMarks() { return finalMarks; }

    // status can only change through these guarded methods
    boolean resubmit(LocalDate on) {
        if (status == Status.GRADED) return false;
        submittedOn = on; return true;
    }
    boolean grade(double awarded) {
        if (status == Status.GRADED) return false;
        finalMarks = assignment.applyPenalty(awarded, daysLate());   // calculated at grading time
        status = Status.GRADED;
        return true;
    }
}

class SubmissionPortal {
    private final Map<String, Submission> submissions = new HashMap<>();
    private static String key(Student s, Assignment a) { return s.getName() + "|" + a.getTitle(); }
    static String num(double d) { return d == Math.rint(d) ? String.valueOf((long) d) : String.format("%.1f", d); }

    public void submit(Student s, Assignment a, LocalDate on) {
        Submission existing = submissions.get(key(s, a));
        if (existing != null && !existing.resubmit(on)) {
            System.out.println("Cannot resubmit: '" + a.getTitle() + "' has already been graded.");
            return;
        }
        Submission sub = existing != null ? existing : new Submission(s, a, on);
        submissions.put(key(s, a), sub);
        long late = sub.daysLate();
        String when = late == 0 ? "on time" : late + (late == 1 ? " day late" : " days late");
        System.out.println(s.getName() + "'s submission for '" + a.getTitle() + "' received (" + when + "). Status: Submitted.");
    }

    public void grade(Student s, Assignment a, double awarded) {
        Submission sub = submissions.get(key(s, a));
        if (sub == null) { System.out.println(s.getName() + " has not submitted '" + a.getTitle() + "'. Cannot grade."); return; }
        if (awarded < 0 || awarded > a.getMaxMarks()) { System.out.println("Invalid marks."); return; }
        if (!sub.grade(awarded)) { System.out.println("Already graded."); return; }
        long late = sub.daysLate();
        String penalty = late == 0 ? "" : " after " + num(a.penaltyFraction(late) * 100) + "% late penalty";
        System.out.println(s.getName() + " graded: " + num(sub.getFinalMarks()) + "/" + a.getMaxMarks() + penalty + ". Status: Graded.");
    }
}

public class AssignmentSubmissionPortal {
    public static void main(String[] args) {
        Assignment lab = new CodingAssignment("Linked List Lab", 50, LocalDate.of(2026, 3, 10));
        Assignment essay = new WrittenAssignment("Design Essay", 50, LocalDate.of(2026, 3, 12));
        Student asha = new Student("Asha"), ravi = new Student("Ravi");
        SubmissionPortal portal = new SubmissionPortal();

        portal.submit(asha, lab, LocalDate.of(2026, 3, 10));
        portal.submit(ravi, essay, LocalDate.of(2026, 3, 14));
        portal.grade(asha, lab, 45);
        portal.grade(ravi, essay, 40);
        portal.submit(asha, lab, LocalDate.of(2026, 3, 11));
    }
}
