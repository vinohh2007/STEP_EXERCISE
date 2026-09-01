public class PhoneNumberMasker {

    String maskPhoneNumber(String phone) {
        if (phone.length() != 10 || !phone.chars().allMatch(Character::isDigit)) {
            return "Invalid phone number";
        }

        String lastFour = phone.substring(phone.length() - 4);
        StringBuilder masked = new StringBuilder("XXXXXX" + lastFour);
        masked.insert(6, "-");

        return masked.toString();
    }

    public static void main(String[] args) {
        PhoneNumberMasker masker = new PhoneNumberMasker();
        System.out.println(masker.maskPhoneNumber("9876543210"));
        System.out.println(masker.maskPhoneNumber("98765"));
    }
}
