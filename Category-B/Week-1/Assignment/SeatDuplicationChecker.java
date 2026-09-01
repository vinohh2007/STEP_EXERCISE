public class SeatDuplicationChecker {

    void checkDuplicateSeats(int[] seatNumbers) {
        boolean foundDuplicate = false;

        for (int i = 0; i < seatNumbers.length; i++) {
            for (int j = i + 1; j < seatNumbers.length; j++) {
                if (seatNumbers[i] == seatNumbers[j]) {
                    System.out.println("Duplicate Seat Number Found: " + seatNumbers[i]);
                    foundDuplicate = true;
                }
            }
        }

        if (!foundDuplicate) {
            System.out.println("No Duplicate Seats Found");
        }
    }

    public static void main(String[] args) {
        SeatDuplicationChecker checker = new SeatDuplicationChecker();
        checker.checkDuplicateSeats(new int[]{101, 102, 103, 102, 105});
        checker.checkDuplicateSeats(new int[]{101, 102, 103, 104, 105});
    }
}
