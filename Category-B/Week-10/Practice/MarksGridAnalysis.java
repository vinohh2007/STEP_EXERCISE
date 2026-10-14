import java.util.*;

public class MarksGridAnalysis {
    static final int SUBJECTS = 3;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<String> names = new ArrayList<>();
        List<int[]> rows = new ArrayList<>();

        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            int open = line.indexOf('['), close = line.indexOf(']');
            if (open < 0 || close < 0) continue;
            names.add(line.substring(0, open).trim());
            String[] parts = line.substring(open + 1, close).split(",");
            int[] marks = new int[SUBJECTS];
            for (int j = 0; j < SUBJECTS; j++) marks[j] = Integer.parseInt(parts[j].trim());
            rows.add(marks);
        }

        int n = rows.size();
        int[][] grid = rows.toArray(new int[0][]);
        int[] totals = new int[n];
        double[] subjectSum = new double[SUBJECTS];
        int topIdx = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < SUBJECTS; j++) {
                totals[i] += grid[i][j];
                subjectSum[j] += grid[i][j];
            }
            if (totals[i] > totals[topIdx]) topIdx = i;
        }

        StringBuilder sb = new StringBuilder("Totals ");
        for (int i = 0; i < n; i++) {
            if (i > 0) sb.append(", ");
            sb.append(names.get(i)).append(' ').append(totals[i]);
        }
        System.out.println(sb);

        StringBuilder avg = new StringBuilder("averages ");
        for (int j = 0; j < SUBJECTS; j++) {
            if (j > 0) avg.append(", ");
            avg.append(String.format(Locale.US, "%.2f", subjectSum[j] / n));
        }
        System.out.println(avg);

        if (n > 0) System.out.println("topper " + names.get(topIdx) + " (" + totals[topIdx] + ")");
    }
}


