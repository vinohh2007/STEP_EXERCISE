import java.util.*;

public class CampusPassSystem {
    static Vehicle create(String type, String pass, String owner) {
        switch (type.toLowerCase().replace("-", "")) {
            case "bike":  return new Bike(pass, owner, type);
            case "car":   return new Car(pass, owner, type);
            case "ebike": return new EBike(pass, owner, type);
            case "ecar":  return new ECar(pass, owner, type);
            default:      return null;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Map<String, Vehicle> registry = new LinkedHashMap<>();
        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (line.isEmpty()) continue;
            String[] p = line.split("\\s+");
            if (p[0].equalsIgnoreCase("PASS") && p.length >= 4) {
                Vehicle v = create(p[1], p[2], p[3]);
                if (v == null) { System.out.println(p[2] + " rejected: unknown vehicle type"); continue; }
                registry.put(p[2], v);
                System.out.println(v.passInfo());
            } else if (p[0].equalsIgnoreCase("CHARGE") && p.length >= 2) {
                Vehicle v = registry.get(p[1]);
                if (v == null) System.out.println(p[1] + " rejected: vehicle not found");
                else if (v instanceof Chargeable) System.out.println(((Chargeable) v).bookChargingBay());
                else System.out.println(p[1] + " rejected: charging unsupported");
            }
        }
    }
}

interface Chargeable {
    String bookChargingBay();
}

abstract class Vehicle {
    protected final String passNumber, owner, typeName;
    Vehicle(String passNumber, String owner, String typeName) {
        this.passNumber = passNumber; this.owner = owner; this.typeName = typeName;
    }
    abstract int passFee();
    String passInfo() { return passNumber + " (" + typeName + ") pass fee " + passFee(); }
}

class Bike extends Vehicle {
    Bike(String p, String o, String t) { super(p, o, t); }
    int passFee() { return 300; }
}

class Car extends Vehicle {
    Car(String p, String o, String t) { super(p, o, t); }
    int passFee() { return 1000; }
}

class EBike extends Vehicle implements Chargeable {
    EBike(String p, String o, String t) { super(p, o, t); }
    int passFee() { return 300; }
    public String bookChargingBay() { return passNumber + " charging bay allotted"; }
}

class ECar extends Vehicle implements Chargeable {
    ECar(String p, String o, String t) { super(p, o, t); }
    int passFee() { return 1000; }
    public String bookChargingBay() { return passNumber + " charging bay allotted"; }
}
