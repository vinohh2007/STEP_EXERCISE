import java.util.Random;

public class BmiCalculator {

    static String getBmiStatus(double bmi) {
        if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi < 25) {
            return "Normal";
        } else if (bmi < 30) {
            return "Overweight";
        } else {
            return "Obese";
        }
    }

    static void printWellnessReport(double[] heights, double[] weights) {
        System.out.println("Person | Height (m) | Weight (kg) | BMI   | Status");
        System.out.println("---------------------------------------------------");
        for (int i = 0; i < heights.length; i++) {
            double bmi = weights[i] / (heights[i] * heights[i]);
            String status = getBmiStatus(bmi);
            System.out.printf("%-6d | %-10.2f | %-11.2f | %-5.2f | %s%n",
                    i + 1, heights[i], weights[i], bmi, status);
        }
    }

    public static void main(String[] args) {
        int teamSize = 10;
        double[] heights = new double[teamSize];
        double[] weights = new double[teamSize];

        Random random = new Random();
        for (int i = 0; i < teamSize; i++) {
            heights[i] = 1.50 + random.nextDouble() * (2.00 - 1.50);   // 1.50m - 2.00m
            weights[i] = 50 + random.nextDouble() * (110 - 50);        // 50kg - 110kg
        }

        printWellnessReport(heights, weights);
    }
}
