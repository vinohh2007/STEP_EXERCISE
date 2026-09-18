package cinehub;

import java.util.Arrays;


public class BookingReceipt {
    private final String bookingId;
    private final String[] seatNumbers;

    public BookingReceipt(String bookingId, String[] seatNumbers) {
        this.bookingId = bookingId;
        this.seatNumbers = Arrays.copyOf(seatNumbers, seatNumbers.length);
    }

    public String getBookingId() {
        return bookingId;
    }

    public String[] getSeatNumbers() {
        return Arrays.copyOf(seatNumbers, seatNumbers.length);
    }

    public BookingReceipt withUpdatedSeat(int index, String newSeat) {
        String[] updated = Arrays.copyOf(seatNumbers, seatNumbers.length);
        updated[index] = newSeat;
        return new BookingReceipt(bookingId, updated);
    }

    public static String processNightlySettlement(BookingReceipt[] receipts) {
        int processed = 0;
        int nullSkipped = 0;
        int group = 0;
        int individual = 0;
        for (BookingReceipt r : receipts) {
            if (r == null) {
                nullSkipped++;
                continue;
            }
            processed++;
            if (r instanceof GroupBookingReceipt) {
                group++;
            } else {
                individual++;
            }
        }
        return processed + " processed | " + nullSkipped + " null skipped | "
                + group + " group | " + individual + " individual";
    }
}
