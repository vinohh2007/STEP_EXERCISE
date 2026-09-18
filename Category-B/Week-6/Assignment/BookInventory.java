package pageturner;

public class BookInventory {
    private final int copiesTotal;
    private int copiesAvailable;

    public BookInventory(int copiesTotal) {
        this.copiesTotal = copiesTotal;
        this.copiesAvailable = copiesTotal;
    }

    public void checkOut() {
        if (copiesAvailable > 0) {
            copiesAvailable--;
        }
       
    }

    public void checkIn() {
        if (copiesAvailable < copiesTotal) {
            copiesAvailable++;
        }
       
    }

    public int getCopiesAvailable() {
        return copiesAvailable;
    }
}
