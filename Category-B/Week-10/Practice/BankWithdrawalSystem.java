import java.util.*;

public class BankWithdrawalSystem {
    static String fmt(double d) {
        return d == Math.rint(d) ? String.valueOf((long) d) : String.valueOf(d);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<String> t = new ArrayList<>();
        while (sc.hasNext()) t.add(sc.next());

        Map<String, Account> accounts = new HashMap<>();
        for (int i = 0; i + 2 < t.size(); i += 3) {
            String op = t.get(i), id = t.get(i + 1);
            double val = Double.parseDouble(t.get(i + 2));
            if (op.equalsIgnoreCase("Savings")) {
                accounts.put(id, new SavingsAccount(id, val));
            } else if (op.equalsIgnoreCase("Current")) {
                accounts.put(id, new CurrentAccount(id, val));
            } else if (op.equalsIgnoreCase("WITHDRAW")) {
                Account a = accounts.get(id);
                System.out.println(a == null ? "Account not found" : a.withdraw(val));
            }
        }
    }
}

abstract class Account {
    protected final String id;
    protected double balance;

    Account(String id, double balance) { this.id = id; this.balance = balance; }

    /** Returns null if allowed, otherwise the rejection reason. */
    abstract String check(double amount);

    String withdraw(double amount) {
        if (amount <= 0) return id + " rejected: invalid amount";
        String reason = check(amount);
        if (reason != null) return id + " rejected: " + reason;
        balance -= amount;
        return id + " balance " + BankWithdrawalSystem.fmt(balance);
    }
}

class SavingsAccount extends Account {
    static final double MIN_BALANCE = 1000;
    SavingsAccount(String id, double b) { super(id, b); }
    @Override String check(double amount) {
        return (balance - amount < MIN_BALANCE) ? "minimum balance 1000" : null;
    }
}

class CurrentAccount extends Account {
    static final double OVERDRAFT_LIMIT = 5000;
    CurrentAccount(String id, double b) { super(id, b); }
    @Override String check(double amount) {
        return (balance - amount < -OVERDRAFT_LIMIT) ? "overdraft limit 5000" : null;
    }
}
