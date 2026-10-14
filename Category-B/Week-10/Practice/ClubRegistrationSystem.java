import java.util.*;

public class ClubRegistrationSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Set<Student> members = new HashSet<>();
        List<String> containsResults = new ArrayList<>();
        boolean addsDone = false;

        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (line.isEmpty()) continue;
            String[] p = line.split("\\s+", 3);
            String name = p.length > 2 ? p[2] : "";
            Student s = new Student(p[1], name);
            if (p[0].equalsIgnoreCase("ADD")) {
                System.out.println(members.add(s) ? "Added" : "duplicate rejected");
            } else if (p[0].equalsIgnoreCase("CONTAINS")) {
                containsResults.add("contains: " + members.contains(s));
            }
        }
        System.out.println("member count " + members.size());
        for (String r : containsResults) System.out.println(r);
    }
}

class Student {
    private final String rollNumber;
    private final String name;

    Student(String rollNumber, String name) { this.rollNumber = rollNumber; this.name = name; }

    // Only roll number decides uniqueness
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        return rollNumber.equals(((Student) o).rollNumber);
    }

    @Override public int hashCode() { return rollNumber.hashCode(); }

    @Override public String toString() { return rollNumber + " " + name; }
}
