package cinehub;

public class AccessChecker {

    // Handles all 5 accessorContext values: SAME_CLASS, SAME_PACKAGE,
    // DIFFERENT_PACKAGE, SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE,
    // SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE.
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
                // DIFFERENT_PACKAGE and SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE are denied:
                // protected cross-package access only works through the subclass's own type.
                return "DENIED";
            case "public":
                return "ALLOWED";
            default:
                throw new IllegalArgumentException("Unknown field modifier: " + fieldModifier);
        }
    }

    public static String summarizeBatch(String[][] attempts) {
        int allowed = 0;
        int denied = 0;
        for (String[] attempt : attempts) {
            if (classifyAccess(attempt[0], attempt[1]).equals("ALLOWED")) {
                allowed++;
            } else {
                denied++;
            }
        }
        return "Allowed: " + allowed + " | Denied: " + denied;
    }
}
