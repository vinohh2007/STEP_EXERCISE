import java.util.*;

// ---------- Channel abstraction: NoticeBoard never sees concrete channels ----------
interface NotificationChannel {
    String name();
    void send(Student student, String message);
}
class EmailChannel implements NotificationChannel {
    public String name() { return "Email"; }
    public void send(Student s, String m) { System.out.println("[Email → " + s.getName() + "] " + m); }
}
class SmsChannel implements NotificationChannel {
    public String name() { return "SMS"; }
    public void send(Student s, String m) { System.out.println("[SMS → " + s.getName() + "] " + m); }
}
class AppChannel implements NotificationChannel {
    public String name() { return "App"; }
    public void send(Student s, String m) { System.out.println("[App → " + s.getName() + "] " + m); }
}

class Student {
    private final String name, department;
    private final Set<NotificationChannel> channels = new LinkedHashSet<>();
    Student(String name, String department, NotificationChannel... preferred) {
        this.name = name; this.department = department; channels.addAll(Arrays.asList(preferred));
    }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public void addChannel(NotificationChannel c) { channels.add(c); }
    public Set<NotificationChannel> getChannels() { return Collections.unmodifiableSet(channels); }
}

// Validation belongs to the Notice itself: an invalid Notice can never exist
class Notice {
    private final String title; private final List<String> departments;
    Notice(String title, String... departments) {
        if (title == null || title.trim().isEmpty()) throw new IllegalArgumentException("A title is required.");
        if (departments == null || departments.length == 0) throw new IllegalArgumentException("At least one target department is required.");
        this.title = title.trim();
        this.departments = List.of(departments);
    }
    public String getTitle() { return title; }
    public List<String> getDepartments() { return departments; }
}

class NoticeBoard {
    private final List<Student> students = new ArrayList<>();
    public void register(Student s) { students.add(s); }

    public void post(Notice n) {
        System.out.println("Notice '" + n.getTitle() + "' posted to " + String.join(", ", n.getDepartments()) + ".");
        for (Student s : students)
            if (n.getDepartments().contains(s.getDepartment()))
                for (NotificationChannel c : s.getChannels())
                    c.send(s, n.getTitle());                 // polymorphic delivery
    }
}

public class NoticeBroadcaster {
    public static void main(String[] args) {
        NotificationChannel email = new EmailChannel(), sms = new SmsChannel(), app = new AppChannel();
        NoticeBoard board = new NoticeBoard();
        board.register(new Student("Asha", "CSE", email, app));
        board.register(new Student("Ravi", "ECE", sms));

        try {
            board.post(new Notice("Lab Closed Tomorrow", "CSE"));
            board.post(new Notice("Fee Deadline Extended", "CSE", "ECE"));
            board.post(new Notice("Sports Day"));
        } catch (IllegalArgumentException e) {
            System.out.println("Cannot post notice: " + e.getMessage());
        }
    }
}
