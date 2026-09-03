public class BankReferenceValidator {

    String normalizeReference(String raw) {
        String trimmed = raw.trim();
        return trimmed.substring(0, 3).toUpperCase() + trimmed.substring(3);
    }

    String validateAndFormat(String reference) {
        if (reference.length() != 14) {
            return "Invalid: wrong length";
        }

        String bankCode = reference.substring(0, 3);
        for (int i = 0; i < bankCode.length(); i++) {
            if (!Character.isLetter(bankCode.charAt(i))) {
                return "Invalid: bank code must be 3 letters";
            }
        }

        String body = reference.substring(3);
        for (int i = 0; i < body.length(); i++) {
            if (!Character.isDigit(body.charAt(i))) {
                return "Invalid: reference body must be digits";
            }
        }

        String date = body.substring(0, 6);
        String seq = body.substring(6);
        String dd = date.substring(0, 2);
        String mm = date.substring(2, 4);
        String yy = date.substring(4, 6);

        StringBuilder result = new StringBuilder();
        result.append("[").append(bankCode).append("] DATE: ")
              .append(dd).append("/").append(mm).append("/").append(yy)
              .append(" | SEQ: ").append(seq);

        return result.toString();
    }

    public static void main(String[] args) {
        BankReferenceValidator validator = new BankReferenceValidator();

        String normalized1 = validator.normalizeReference(" hdf03022600042 ");
        System.out.println(validator.validateAndFormat(normalized1));

        String normalized2 = validator.normalizeReference("12F03022600042");
        System.out.println(validator.validateAndFormat(normalized2));
    }
}
