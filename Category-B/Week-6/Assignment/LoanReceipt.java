package pageturner;

import java.util.Arrays;

// Note: kept as a non-final class (fields are final, no mutators, defensive
// copies in/out) rather than a literally `final` class, because
// ReferenceOnlyLoanReceipt must extend it — Java does not allow subclassing a
// class declared final.
public class LoanReceipt {
    private final String memberId;
    private final String[] bookIds;

    public LoanReceipt(String memberId, String[] bookIds) {
        this.memberId = memberId;
        this.bookIds = Arrays.copyOf(bookIds, bookIds.length);
    }

    public String getMemberId() {
        return memberId;
    }

    public String[] getBookIds() {
        return Arrays.copyOf(bookIds, bookIds.length);
    }

    public LoanReceipt withCorrectedBookId(int index, String newId) {
        String[] updated = Arrays.copyOf(bookIds, bookIds.length);
        updated[index] = newId;
        return new LoanReceipt(memberId, updated);
    }

    public static String processNightlyCirculation(LoanReceipt[] receipts) {
        int processed = 0;
        int nullSkipped = 0;
        int referenceOnly = 0;
        int regular = 0;
        for (LoanReceipt r : receipts) {
            if (r == null) {
                nullSkipped++;
                continue;
            }
            processed++;
            if (r instanceof ReferenceOnlyLoanReceipt) {
                referenceOnly++;
            } else {
                regular++;
            }
        }
        return processed + " processed | " + nullSkipped + " null skipped | "
                + referenceOnly + " reference-only | " + regular + " regular";
    }
}
