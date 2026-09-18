package pageturner;

import java.util.LinkedHashMap;
import java.util.Map;

public class AccessChecker {

    public static String classifyAccess(String fieldModifier, String accessorContext) {
        switch (fieldModifier) {
            case "private":
                return accessorContext.equals("SAME_CLASS") ? "ALLOWED" : "DENIED";
            case "default":
                return (accessorContext.equals("SAME_CLASS") || accessorContext.equals("SAME_PACKAGE"))
                        ? "ALLOWED" : "DENIED";
            case "protected":
                if (accessorContext.equals("SAME_CLASS")
                        || accessorContext.equals("SAME_PACKAGE")
                        || accessorContext.equals("SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE")) {
                    return "ALLOWED";
                }
                return "DENIED";
            case "public":
                return "ALLOWED";
            default:
                throw new IllegalArgumentException("Unknown field modifier: " + fieldModifier);
        }
    }

    public static String summarizeByModifier(String[][] attempts) {
        String[] modifiers = {"private", "default", "protected", "public"};
        Map<String, int[]> counts = new LinkedHashMap<>();
        for (String mod : modifiers) {
            counts.put(mod, new int[2]); // [allowed, denied]
        }
        for (String[] attempt : attempts) {
            String result = classifyAccess(attempt[0], attempt[1]);
            int[] c = counts.get(attempt[0]);
            if (result.equals("ALLOWED")) {
                c[0]++;
            } else {
                c[1]++;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < modifiers.length; i++) {
            int[] c = counts.get(modifiers[i]);
            sb.append(modifiers[i]).append(": ").append(c[0]).append(" allowed / ")
                    .append(c[1]).append(" denied");
            if (i < modifiers.length - 1) {
                sb.append(" | ");
            }
        }
        return sb.toString();
    }

    public static String firstDeniedAttempt(String[][] attempts) {
        for (int i = 0; i < attempts.length; i++) {
            String modifier = attempts[i][0];
            String context = attempts[i][1];
            if (classifyAccess(modifier, context).equals("DENIED")) {
                return modifier + " via " + context + " (attempt #" + (i + 1) + ")";
            }
        }
        return "None Denied";
    }
}
