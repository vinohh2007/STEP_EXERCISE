public class PalindromeChecker {

    static boolean isPalindromeIterative(String text) {
        int left = 0, right = text.length() - 1;
        while (left < right) {
            if (text.charAt(left) != text.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    static boolean isPalindromeRecursive(String text) {
        if (text.length() <= 1) {
            return true;
        }
        if (text.charAt(0) != text.charAt(text.length() - 1)) {
            return false;
        }
        return isPalindromeRecursive(text.substring(1, text.length() - 1));
    }

    static boolean isPalindromeArrayReversal(String text) {
        char[] original = text.toCharArray();
        char[] reversed = new char[original.length];

        for (int i = 0; i < original.length; i++) {
            reversed[i] = original[original.length - 1 - i];
        }

        return new String(original).equals(new String(reversed));
    }

    static void checkAndPrint(String input) {
        boolean iterative = isPalindromeIterative(input);
        boolean recursive = isPalindromeRecursive(input);
        boolean arrayReversal = isPalindromeArrayReversal(input);

        System.out.println("Input: \"" + input + "\"");
        System.out.println("Iterative: " + (iterative ? "Palindrome" : "Not Palindrome")
                + " | Recursive: " + (recursive ? "Palindrome" : "Not Palindrome")
                + " | Array Reversal: " + (arrayReversal ? "Palindrome" : "Not Palindrome"));
        System.out.println();
    }

    public static void main(String[] args) {
        checkAndPrint("madam");
        checkAndPrint("hello");
        checkAndPrint("racecar");
        checkAndPrint("java");
    }
}
