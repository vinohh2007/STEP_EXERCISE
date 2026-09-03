import java.util.LinkedHashMap;
import java.util.Map;

public class FirstNonRepeatingChar {

    static Character findFirstNonRepeatingChar(String text) {
        Map<Character, Integer> frequency = new LinkedHashMap<>();

        for (char c : text.toCharArray()) {
            frequency.put(c, frequency.getOrDefault(c, 0) + 1);
        }

        for (char c : text.toCharArray()) {
            if (frequency.get(c) == 1) {
                return c;
            }
        }

        return null; // no non-repeating character found
    }

    static void checkAndPrint(String input) {
        Character result = findFirstNonRepeatingChar(input);
        System.out.println("Input: \"" + input + "\"");
        if (result != null) {
            System.out.println("First Non-Repeating Character: '" + result + "'");
        } else {
            System.out.println("No Non-Repeating Character Found");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        checkAndPrint("swiss");
        checkAndPrint("aabbcc");
        checkAndPrint("teeter");
    }
}
