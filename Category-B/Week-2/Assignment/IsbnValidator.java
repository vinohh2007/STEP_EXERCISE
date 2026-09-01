public class IsbnValidator {

    String normalizeCode(String raw) {
        String trimmed = raw.trim();
        return trimmed.substring(0, 3).toUpperCase() + trimmed.substring(3);
    }

    String validateAndFormat(String code) {
        if (code.length() != 13) {
            return "Invalid: wrong length";
        }

        String pubCode = code.substring(0, 3);
        for (int i = 0; i < pubCode.length(); i++) {
            if (!Character.isLetter(pubCode.charAt(i))) {
                return "Invalid: publisher code must be 3 letters";
            }
        }

        String body = code.substring(3);
        for (int i = 0; i < body.length(); i++) {
            if (!Character.isDigit(body.charAt(i))) {
                return "Invalid: reference body must be digits";
            }
        }

        String year = body.substring(0, 4);
        String catalog = body.substring(4);

        StringBuilder result = new StringBuilder();
        result.append("[").append(pubCode).append("] YEAR: ")
              .append(year).append(" | CATALOG: ").append(catalog);

        return result.toString();
    }

    public static void main(String[] args) {
        IsbnValidator validator = new IsbnValidator();

        String normalized1 = validator.normalizeCode(" pen2026004251 ");
        System.out.println(validator.validateAndFormat(normalized1));

        String normalized2 = validator.normalizeCode("12N2026004251");
        System.out.println(validator.validateAndFormat(normalized2));
    }
}
