import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class WordFrequencyReport {

    String[] stopWords = {"the", "was", "and", "a", "is", "of", "in"};

    boolean isStopWord(String word) {
        for (String stopWord : stopWords) {
            if (stopWord.equals(word)) {
                return true;
            }
        }
        return false;
    }

    void printFilteredWordFrequency(String feedback) {
        String cleaned = feedback.toLowerCase().replace(",", "").replace(".", "");
        String[] words = cleaned.split("\\s+");

        Map<String, Integer> frequency = new LinkedHashMap<>();
        for (String word : words) {
            if (!isStopWord(word) && !word.isEmpty()) {
                frequency.merge(word, 1, Integer::sum);
            }
        }

        List<Map.Entry<String, Integer>> entries = new ArrayList<>(frequency.entrySet());
        entries.sort((a, b) -> b.getValue() - a.getValue());

        for (Map.Entry<String, Integer> entry : entries) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public static void main(String[] args) {
        WordFrequencyReport report = new WordFrequencyReport();
        report.printFilteredWordFrequency("The mentor was great, the session was great and clear.");
    }
}
