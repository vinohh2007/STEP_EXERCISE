import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

abstract class Room {
    private final int number;
    private final List<Reservation> reservations = new ArrayList<>();
    protected Room(int n) { number = n; }
    public int getNumber() { return number; }
    public abstract String category();
    public abstract double calculatePrice(long nights);

    public boolean isAvailable(LocalDate in, LocalDate out) {
        for (Reservation r : reservations)
            if (r.isActive() && r.overlaps(in, out)) return false;   // check-out day is free
        return true;
    }
    void add(Reservation r) { reservations.add(r); }
    public String label() { return category() + " Room " + number; }
}
class StandardRoom extends Room {
    StandardRoom(int n) { super(n); }
    public String category() { return "Standard"; }
    public double calculatePrice(long nights) { return 100.0 * nights; }
}
class DeluxeRoom extends Room {
    DeluxeRoom(int n) { super(n); }
    public String category() { return "Deluxe"; }
    public double calculatePrice(long nights) { return 180.0 * nights + 25; } // amenity fee
}
class Suite extends Room {
    Suite(int n) { super(n); }
    public String category() { return "Suite"; }
    public double calculatePrice(long nights) { return 350.0 * nights + 60; } // size/amenity fee
}

class Customer {
    private final String name;
    Customer(String n) { name = n; }
    public String getName() { return name; }
}

class Reservation {
    private static final int CANCEL_DEADLINE_DAYS = 2;   // must cancel >= 2 days before check-in
    private final Customer customer; private final Room room;
    private final LocalDate checkIn, checkOut;
    private boolean active = true;
    Reservation(Customer c, Room r, LocalDate in, LocalDate out) { customer = c; room = r; checkIn = in; checkOut = out; }
    public boolean isActive() { return active; }
    boolean overlaps(LocalDate in, LocalDate out) { return checkIn.isBefore(out) && in.isBefore(checkOut); }
    public double price() { return room.calculatePrice(ChronoUnit.DAYS.between(checkIn, checkOut)); }
    public LocalDate cancelDeadline() { return checkIn.minusDays(CANCEL_DEADLINE_DAYS); }
    public Customer getCustomer() { return customer; }
    public Room getRoom() { return room; }
    public String period() { return fmt(checkIn) + "-" + checkOut.getDayOfMonth(); }
    static String fmt(LocalDate d) { String m = d.getMonth().toString(); return m.charAt(0) + m.substring(1,3).toLowerCase() + " " + d.getDayOfMonth(); }
    void cancel() { active = false; }
}

class BookingService {
    public boolean checkAvailability(Room room, LocalDate in, LocalDate out) {
        boolean ok = room.isAvailable(in, out);
        System.out.println(room.label() + (ok ? " is available" : " is not available")
                + " from " + Reservation.fmt(in) + " to " + Reservation.fmt(out) + ".");
        return ok;
    }
    public Reservation reserve(Customer c, Room room, LocalDate in, LocalDate out) {
        if (!out.isAfter(in) || !room.isAvailable(in, out)) {
            System.out.println(room.label() + " is not available from " + Reservation.fmt(in) + " to " + Reservation.fmt(out) + ".");
            return null;
        }
        Reservation r = new Reservation(c, room, in, out);
        room.add(r);
        System.out.println("Reservation confirmed for " + c.getName() + ", " + room.label()
                + " (" + r.period() + "). Price: $" + String.format("%.2f", r.price()));
        return r;
    }
    public boolean cancel(Reservation r, LocalDate today) {
        if (today.isAfter(r.cancelDeadline())) {
            System.out.println("Cancellation deadline (" + r.cancelDeadline() + ") has passed.");
            return false;
        }
        r.cancel();
        System.out.println("Reservation for " + r.getCustomer().getName() + ", " + r.getRoom().label()
                + " (" + r.period() + ") cancelled successfully.");
        return true;
    }
}

public class HotelBookingSystem {
    public static void main(String[] args) {
        BookingService svc = new BookingService();
        Room r101 = new StandardRoom(101), r201 = new DeluxeRoom(201);
        Customer a = new Customer("Customer A"), b = new Customer("Customer B"), c = new Customer("Customer C");
        LocalDate j1 = LocalDate.of(2026,1,1), j5 = LocalDate.of(2026,1,5), j3 = LocalDate.of(2026,1,3), j7 = LocalDate.of(2026,1,7);

        svc.checkAvailability(r101, j1, j5);
        Reservation res = svc.reserve(a, r101, j1, j5);
        svc.reserve(b, r101, j3, j7);
        svc.cancel(res, LocalDate.of(2025,12,20));   // before deadline
        svc.reserve(c, r201, LocalDate.of(2026,2,10), LocalDate.of(2026,2,12));
    }
}
