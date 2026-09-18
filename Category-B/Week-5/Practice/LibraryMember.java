package library;

import java.util.Arrays;

public class LibraryMember {
    private static int counter = 100;
    private static int membersEnrolled = 0;

    protected String memberId;
    protected int borrowLimit;
    private int booksBorrowed;
    private int[] fineHistory = new int[0];
    public final String memberNumber;

    public LibraryMember(String memberId, int borrowLimit) {
        if (memberId == null || memberId.trim().isEmpty() || memberId.length() < 4) {
            throw new IllegalArgumentException("Invalid memberId: " + memberId);
        }
        this.memberId = memberId;
        this.borrowLimit = borrowLimit;
        this.memberNumber = generateMemberNumber();
    }

    public LibraryMember(int borrowLimit) {
        this.memberId = "AUTO";
        this.borrowLimit = borrowLimit;
        this.memberNumber = generateMemberNumber();
    }

    private static String generateMemberNumber() {
        counter++;
        membersEnrolled++;
        return "LIB-" + counter;
    }

    public static int getMembersEnrolled() {
        return membersEnrolled;
    }

    public void borrowBook() {
        booksBorrowed++;
    }

    public void borrowBook(String genre) {
        // genre is recorded conceptually but the count is delegated to the
        // no-argument version so both overloads share one source of truth.
        borrowBook();
    }

    public int getBooksBorrowed() {
        return booksBorrowed;
    }

    public String displayInfo() {
        return "General Member | Books Borrowed: " + booksBorrowed;
    }

    protected void chargeFine(int amount) {
        int[] updated = Arrays.copyOf(fineHistory, fineHistory.length + 1);
        updated[fineHistory.length] = amount;
        fineHistory = updated;
    }

    public int[] getFineHistory() {
        return Arrays.copyOf(fineHistory, fineHistory.length);
    }

    public int getTotalFine() {
        int total = 0;
        for (int f : fineHistory) {
            total += f;
        }
        return total;
    }

    public static String enrollBatch(String[] memberIds, int borrowLimit) {
        int enrolled = 0;
        int rejected = 0;
        for (String id : memberIds) {
            try {
                new LibraryMember(id, borrowLimit);
                enrolled++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }
        return "Enrolled: " + enrolled + " | Rejected: " + rejected;
    }

    public static String classifyGeneration(LibraryMember member) {
        if (member instanceof HonorsStudentMember) {
            return "Multilevel descendant (3 generations deep)";
        }
        if (member instanceof FacultyMember) {
            return "Hierarchical sibling (independent branch)";
        }
        if (member instanceof StudentMember) {
            return "Direct subclass";
        }
        return "Base member";
    }

    public static int getTotalBooksBorrowed(LibraryMember[] members) {
        int total = 0;
        for (LibraryMember m : members) {
            total += m.getBooksBorrowed();
        }
        return total;
    }

    public static String batchPrint(LibraryMember[] members) {
        StringBuilder sb = new StringBuilder();
        for (LibraryMember m : members) {
            sb.append(m.displayInfo());
            if (m instanceof StudentMember) {
                StudentMember s = (StudentMember) m;
                sb.append(" [Course via downcast: ").append(s.getCourse()).append("]");
            }
            sb.append(" | ");
        }
        return sb.toString();
    }

    public static boolean isValidRenewalCode(String code) {
        if (code == null || code.length() != 4) {
            return false;
        }
        if (code.charAt(0) != 'R') {
            return false;
        }
        if (!Character.isDigit(code.charAt(1)) || !Character.isDigit(code.charAt(2))) {
            return false;
        }
        return Character.isUpperCase(code.charAt(3));
    }

    public static String processNightlyAudit(LibraryMember[] members) {
        int processed = 0;
        int nullSkipped = 0;
        int faculty = 0;
        int regular = 0;
        for (LibraryMember m : members) {
            if (m == null) {
                nullSkipped++;
                continue;
            }
            processed++;
            if (m instanceof FacultyMember) {
                faculty++;
            } else {
                regular++;
            }
        }
        return processed + " processed | " + nullSkipped + " null skipped | "
                + faculty + " faculty | " + regular + " regular";
    }
}
