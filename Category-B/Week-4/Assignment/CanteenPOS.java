class Payment {
    double pay(double amount) {
        System.out.println("Paid (cash): Rs " + amount);
        return amount;
    }
}

class CardPayment extends Payment {
    double payWithProcessingFee(double amount) {
        double total = amount * 1.02;
        System.out.println("Charged (card, incl. fee): Rs " + total);
        return total;
    }
}

public class CanteenPOS {
    double totalCollected = 0;

    void processTransaction(Payment payment, double amount) {
        if (payment instanceof CardPayment) {
            CardPayment cardPayment = (CardPayment) payment;
            totalCollected += cardPayment.payWithProcessingFee(amount);
        } else {
            totalCollected += payment.pay(amount);
        }
    }

    public static void main(String[] args) {
        Payment[] payments = {
            new CardPayment(),
            new Payment(),
            new CardPayment(),
            new Payment(),
            new CardPayment()
        };
        double[] amounts = {100, 50, 200, 75, 120};

        CanteenPOS pos = new CanteenPOS();
        for (int i = 0; i < payments.length; i++) {
            pos.processTransaction(payments[i], amounts[i]);
        }

        System.out.println("Total Collected: Rs " + pos.totalCollected);
    }
}
