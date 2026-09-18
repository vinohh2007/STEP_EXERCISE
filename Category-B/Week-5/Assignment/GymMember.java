package gym;

import java.util.Arrays;

public class GymMember {
    private static int counter = 2000;
    private static int membersEnrolled = 0;

    protected String memberId;
    protected int monthlyFee;
    private int sessionsAttended;
    private int[] lateFeeHistory = new int[0];
    private int feesPaid;
    public final String membershipNumber;

    public GymMember(String memberId, int monthlyFee) {
        if (memberId == null || memberId.trim().isEmpty() || memberId.length() < 4) {
            throw new IllegalArgumentException("Invalid memberId: " + memberId);
        }
        this.memberId = memberId;
        this.monthlyFee = monthlyFee;
        this.membershipNumber = generateMembershipNumber();
    }

    public GymMember(int monthlyFee) {
        this.memberId = "AUTO";
        this.monthlyFee = monthlyFee;
        this.membershipNumber = generateMembershipNumber();
    }

    private static String generateMembershipNumber() {
        counter++;
        membersEnrolled++;
        return "GYM-" + counter;
    }

    public static int getMembersEnrolled() {
        return membersEnrolled;
    }

    public void attendSession() {
        sessionsAttended++;
    }

    public int getSessionsAttended() {
        return sessionsAttended;
    }

    public String displayInfo() {
        return "Standard Member | Sessions: " + sessionsAttended;
    }

    protected void chargeLateFee(int amount) {
        int[] updated = Arrays.copyOf(lateFeeHistory, lateFeeHistory.length + 1);
        updated[lateFeeHistory.length] = amount;
        lateFeeHistory = updated;
    }

    public int[] getLateFeeHistory() {
        return Arrays.copyOf(lateFeeHistory, lateFeeHistory.length);
    }

    public int getTotalLateFees() {
        int total = 0;
        for (int f : lateFeeHistory) {
            total += f;
        }
        return total;
    }

    public void payFee(int amount) {
        feesPaid += amount;
    }

    public void payFee(int amount, String mode) {
        // mode is recorded conceptually; the total is delegated to the flat-amount
        // version so both overloads share one source of truth.
        payFee(amount);
    }

    public int getFeesPaid() {
        return feesPaid;
    }

    public static String signUpBatch(String[] memberIds, int monthlyFee) {
        int signedUp = 0;
        int rejected = 0;
        for (String id : memberIds) {
            try {
                new GymMember(id, monthlyFee);
                signedUp++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }
        return "Signed Up: " + signedUp + " | Rejected: " + rejected;
    }

    public static String classifyGeneration(GymMember member) {
        if (member instanceof EliteMember) {
            return "Multilevel descendant (3 generations deep)";
        }
        if (member instanceof GroupClassMember) {
            return "Hierarchical sibling (independent branch)";
        }
        if (member instanceof PremiumMember) {
            return "Direct subclass";
        }
        return "Base member";
    }

    public static int getTotalSessionsAttended(GymMember[] members) {
        int total = 0;
        for (GymMember m : members) {
            total += m.getSessionsAttended();
        }
        return total;
    }

    public static String batchPrint(GymMember[] members) {
        StringBuilder sb = new StringBuilder();
        for (GymMember m : members) {
            sb.append(m.displayInfo());
            if (m instanceof PremiumMember) {
                PremiumMember p = (PremiumMember) m;
                sb.append(" [Trainer via downcast: ").append(p.getTrainerName()).append("]");
            }
            sb.append(" | ");
        }
        return sb.toString();
    }

    public static boolean isValidReferralCode(String code) {
        if (code == null || code.length() != 4) {
            return false;
        }
        if (code.charAt(0) != 'G') {
            return false;
        }
        if (!Character.isDigit(code.charAt(1)) || !Character.isDigit(code.charAt(2))) {
            return false;
        }
        return Character.isUpperCase(code.charAt(3));
    }

    public static String processWeeklyCheckIn(GymMember[] members) {
        int processed = 0;
        int nullSkipped = 0;
        int group = 0;
        int individual = 0;
        for (GymMember m : members) {
            if (m == null) {
                nullSkipped++;
                continue;
            }
            processed++;
            if (m instanceof GroupClassMember) {
                group++;
            } else {
                individual++;
            }
        }
        return processed + " processed | " + nullSkipped + " null skipped | "
                + group + " group | " + individual + " individual";
    }
}
