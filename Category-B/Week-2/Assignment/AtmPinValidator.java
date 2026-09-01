public class AtmPinValidator {

    void checkPinLength(String pin) {
        if (pin.length() != 4) {
            System.out.println("Invalid PIN — must be exactly 4 digits.");
        } else {
            System.out.println("PIN length OK.");
        }
    }

    public static void main(String[] args) {
        AtmPinValidator validator = new AtmPinValidator();
        validator.checkPinLength("482");
        validator.checkPinLength("4820");
    }
}
