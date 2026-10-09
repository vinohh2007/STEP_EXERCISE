import java.util.*;

// ---------- Strategy: each vehicle category owns its pricing rule ----------
abstract class Vehicle {
    private final String name;
    private boolean available = true;
    protected Vehicle(String name) { this.name = name; }
    public String getName() { return name; }
    public boolean isAvailable() { return available; }
    void markRented() { available = false; }      // package-private: only RentalService changes it
    void markReturned() { available = true; }
    public abstract double calculateCharge(int days);
}
class Sedan extends Vehicle {
    public Sedan(String n) { super(n); }
    public double calculateCharge(int d) { return 40.0 * d; }
}
class SUV extends Vehicle {
    public SUV(String n) { super(n); }
    public double calculateCharge(int d) { return 60.0 * d + 15; } // flat insurance fee
}
class Truck extends Vehicle {
    public Truck(String n) { super(n); }
    public double calculateCharge(int d) { return 80.0 * d + 50; } // flat cargo fee
}

class Customer {
    private final int id;
    public Customer(int id) { this.id = id; }
    public int getId() { return id; }
}

class Rental {
    private final Vehicle vehicle; private final Customer customer; private final int days;
    private boolean active = true;
    Rental(Vehicle v, Customer c, int days) { vehicle = v; customer = c; this.days = days; }
    public Vehicle getVehicle() { return vehicle; }
    public Customer getCustomer() { return customer; }
    public boolean isActive() { return active; }
    public double charge() { return vehicle.calculateCharge(days); } // polymorphic call
    void close() { active = false; }
}

// Common rental logic: never changes when new vehicle types are added
class RentalService {
    private final List<Rental> rentals = new ArrayList<>();

    public Rental rent(Customer c, Vehicle v, int days) {
        if (!v.isAvailable()) {
            System.out.println(v.getName() + " is currently unavailable.");
            return null;
        }
        Rental r = new Rental(v, c, days);
        v.markRented();
        rentals.add(r);
        System.out.println(v.getName() + " rented successfully by Customer " + c.getId()
                + ". Rental charge: $" + String.format("%.2f", r.charge()));
        return r;
    }

    public void returnVehicle(Customer c, Vehicle v) {
        for (Rental r : rentals)
            if (r.isActive() && r.getVehicle() == v && r.getCustomer() == c) {
                r.close(); v.markReturned();
                System.out.println(v.getName() + " returned by Customer " + c.getId() + ".");
                return;
            }
        System.out.println("No active rental of " + v.getName() + " for Customer " + c.getId() + ".");
    }
}

public class VehicleRentalSystem {
    public static void main(String[] args) {
        RentalService svc = new RentalService();
        Vehicle sedanA = new Sedan("Sedan A"), suvB = new SUV("SUV B");
        Customer c1 = new Customer(1), c2 = new Customer(2), c3 = new Customer(3);
        svc.rent(c1, sedanA, 3);
        svc.rent(c2, sedanA, 2);
        svc.returnVehicle(c1, sedanA);
        svc.rent(c3, suvB, 5);
    }
}
