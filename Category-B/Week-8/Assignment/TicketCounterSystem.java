import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

// ---------- Each seat category defines its own price ----------
abstract class Seat {
    private final String id;
    protected Seat(String id) { this.id = id; }
    public String getId() { return id; }
    public abstract double getPrice();
}
class RegularSeat extends Seat { RegularSeat(String id) { super(id); } public double getPrice() { return 150; } }
class PremiumSeat extends Seat { PremiumSeat(String id) { super(id); } public double getPrice() { return 250; } }
class ReclinerSeat extends Seat { ReclinerSeat(String id) { super(id); } public double getPrice() { return 400; } }

class Customer {
    private final String name;
    Customer(String n) { name = n; }
    public String getName() { return name; }
}

class Show {
    private final String title; private final LocalDateTime start;
    private final Map<String, Seat> seats = new LinkedHashMap<>();
    private final Set<String> booked = new HashSet<>();      // seats already booked for THIS show
    Show(String title, LocalDateTime start) { this.title = title; this.start = start; }
    public String getTitle() { return title; }
    public LocalDateTime getStart() { return start; }
    public void addSeat(Seat s) { seats.put(s.getId(), s); }
    public Seat getSeat(String id) { return seats.get(id); }
    public boolean isAvailable(String id) { return seats.containsKey(id) && !booked.contains(id); }
    void reserve(Collection<Seat> s) { s.forEach(x -> booked.add(x.getId())); }
    void release(Collection<Seat> s) { s.forEach(x -> booked.remove(x.getId())); }
}

class Booking {
    private final Customer customer; private final Show show; private final List<Seat> seats; private boolean cancelled;
    Booking(Customer c, Show sh, List<Seat> s) { customer = c; show = sh; seats = List.copyOf(s); }
    public Customer getCustomer() { return customer; }
    public Show getShow() { return show; }
    public List<Seat> getSeats() { return seats; }
    public boolean isCancelled() { return cancelled; }
    void markCancelled() { cancelled = true; }
    public double total() { return seats.stream().mapToDouble(Seat::getPrice).sum(); }
    public String seatList() { return seats.stream().map(Seat::getId).collect(Collectors.joining(", ")); }
}

class BookingService {
    private static final int MAX_SEATS = 6;

    public boolean checkAvailability(Show show, String seatId) { return show.isAvailable(seatId); }

    public Booking book(Customer c, Show show, String... seatIds) {
        if (seatIds.length == 0 || seatIds.length > MAX_SEATS) {
            System.out.println("A booking must have 1 to " + MAX_SEATS + " seats."); return null;
        }
        List<Seat> chosen = new ArrayList<>();
        Set<String> seen = new HashSet<>();
        for (String id : seatIds) {                          // validate everything first: all-or-nothing
            if (!seen.add(id)) { System.out.println("Seat " + id + " selected twice."); return null; }
            if (show.getSeat(id) == null) { System.out.println("Seat " + id + " does not exist."); return null; }
            if (!show.isAvailable(id)) { System.out.println("Seat " + id + " is already booked for this show."); return null; }
            chosen.add(show.getSeat(id));
        }
        show.reserve(chosen);
        Booking b = new Booking(c, show, chosen);
        System.out.println("Booking confirmed for " + c.getName() + ": " + b.seatList()
                + ". Total: ₹" + String.format("%.2f", b.total()) + ".");
        return b;
    }

    public boolean cancel(Booking b, LocalDateTime now) {
        if (b.isCancelled()) { System.out.println("Booking already cancelled."); return false; }
        if (!now.isBefore(b.getShow().getStart())) {
            System.out.println("Cannot cancel: the show has already started."); return false;
        }
        b.markCancelled();
        b.getShow().release(b.getSeats());                   // cancelling frees every seat
        System.out.println(b.getCustomer().getName() + "'s booking cancelled. Seats " + b.seatList() + " released.");
        return true;
    }
}

public class TicketCounterSystem {
    public static void main(String[] args) {
        Show show = new Show("Movie Night", LocalDateTime.of(2026, 3, 20, 19, 0));
        for (String id : new String[]{"A1", "A2"}) show.addSeat(new RegularSeat(id));
        show.addSeat(new PremiumSeat("F5"));
        show.addSeat(new ReclinerSeat("R1"));

        BookingService svc = new BookingService();
        Customer asha = new Customer("Asha"), ravi = new Customer("Ravi"), neha = new Customer("Neha");
        LocalDateTime now = LocalDateTime.of(2026, 3, 20, 15, 0);

        Booking ashaB = svc.book(asha, show, "A1", "A2", "F5");
        svc.book(ravi, show, "A2");
        svc.book(ravi, show, "R1");
        svc.cancel(ashaB, now);
        svc.book(neha, show, "A2");
    }
}
