public class TypingAccuracyChecker {

    void checkTypingAccuracy(String original, String typed) {
        int length = original.length();
        int matched = 0;
        int firstMismatchIndex = -1;

        for (int i = 0; i < length; i++) {
            if (original.charAt(i) == typed.charAt(i)) {
                matched++;
            } else if (firstMismatchIndex == -1) {
                firstMismatchIndex = i;
            }
        }

        double accuracy = (matched * 100.0) / length;

        if (firstMismatchIndex == -1) {
            System.out.printf("Matched: %d/%d | Accuracy: %.2f%% | No Mismatches%n", matched, length, accuracy);
        } else {
            System.out.printf("Matched: %d/%d | Accuracy: %.2f%% | First Mismatch at position %d ('%c' vs '%c')%n",
                    matched, length, accuracy, firstMismatchIndex + 1,
                    original.charAt(firstMismatchIndex), typed.charAt(firstMismatchIndex));
        }
    }

    public static void main(String[] args) {
        TypingAccuracyChecker checker = new TypingAccuracyChecker();
        checker.checkTypingAccuracy("hello world", "hello worlt");
        checker.checkTypingAccuracy("coding", "coding");
    }
}
