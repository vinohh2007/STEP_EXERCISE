import java.util.*;

class Product {
    private final String name; private final double price;
    Product(String n, double p) { name = n; price = p; }
    public String getName() { return name; }
    public double getPrice() { return price; }
}
class OrderItem {
    final Product product; final int qty;
    OrderItem(Product p, int q) { product = p; qty = q; }
    double subtotal() { return product.getPrice() * qty; }
}
class Customer {
    private final String name;
    Customer(String n) { name = n; }
    public String getName() { return name; }
}

// ---------- Order depends only on this abstraction ----------
interface PaymentMethod {
    String name();
    boolean processPayment(double amount);
}
class CreditCardPayment implements PaymentMethod {
    private final boolean approved;
    CreditCardPayment(boolean approved) { this.approved = approved; }
    public String name() { return "Credit Card"; }
    public boolean processPayment(double amt) { return approved; }   // would call card gateway
}
class PayPalPayment implements PaymentMethod {
    private final boolean approved;
    PayPalPayment(boolean approved) { this.approved = approved; }
    public String name() { return "PayPal"; }
    public boolean processPayment(double amt) { return approved; }   // would call PayPal API
}
class BankTransferPayment implements PaymentMethod {
    private final boolean approved;
    BankTransferPayment(boolean approved) { this.approved = approved; }
    public String name() { return "Bank Transfer"; }
    public boolean processPayment(double amt) { return approved; }
}

class Order {
    enum Status { PENDING, PAID }
    private final Customer customer;
    private final List<OrderItem> items = new ArrayList<>();
    private Status status = Status.PENDING;
    Order(Customer c) { customer = c; System.out.println("Order created for " + c.getName() + "."); }
    public void addItem(Product p, int qty) { if (qty > 0) items.add(new OrderItem(p, qty)); }
    public double total() { return items.stream().mapToDouble(OrderItem::subtotal).sum(); }
    public Status getStatus() { return status; }

    public boolean pay(PaymentMethod method) {
        if (items.isEmpty()) { System.out.println("Cannot process payment for an empty order."); return false; }
        if (status == Status.PAID) { System.out.println("Order already paid."); return false; }
        System.out.println("Payment initiated via " + method.name() + " for Order " + customer.getName() + ".");
        boolean ok = method.processPayment(total());
        if (ok) {
            status = Status.PAID;
            System.out.println("Payment for Order " + customer.getName() + " successful. Order status: Paid.");
        } else {
            System.out.println("Payment for Order " + customer.getName() + " failed. Order status: Pending.");
        }
        return ok;
    }
}

public class PaymentProcessingSystem {
    public static void main(String[] args) {
        Product a = new Product("Product A", 20), b = new Product("Product B", 35), c = new Product("Product C", 50);

        Order ox = new Order(new Customer("X"));
        ox.addItem(a, 2); ox.addItem(b, 1);
        ox.pay(new CreditCardPayment(true));

        Order oy = new Order(new Customer("Y"));
        oy.pay(new CreditCardPayment(true));

        Order oz = new Order(new Customer("Z"));
        oz.addItem(c, 1);
        oz.pay(new PayPalPayment(false));
    }
}
