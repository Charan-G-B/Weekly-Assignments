import java.util.*;

public class UsernameChecker {

    // Stores username -> userId
    private HashMap<String, Integer> users;

    // Stores username -> attempt count
    private HashMap<String, Integer> attemptCount;

    private String mostAttempted;
    private int maxAttempts;

    public UsernameChecker() {
        users = new HashMap<>();
        attemptCount = new HashMap<>();
        mostAttempted = "";
        maxAttempts = 0;
    }

    // Check if username is available
    public boolean checkAvailability(String username) {

        recordAttempt(username);

        return !users.containsKey(username);
    }

    // Register a username
    public void registerUser(String username, int userId) {
        users.put(username, userId);
    }

    // Record username attempt
    private void recordAttempt(String username) {

        int count = attemptCount.getOrDefault(username, 0) + 1;
        attemptCount.put(username, count);

        if (count > maxAttempts) {
            maxAttempts = count;
            mostAttempted = username;
        }
    }

    // Suggest alternative usernames
    public List<String> suggestAlternatives(String username) {

        List<String> suggestions = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            suggestions.add(username + i);
        }

        suggestions.add(username + "_");
        suggestions.add(username + ".");

        return suggestions;
    }

    // Get most attempted username
    public String getMostAttempted() {
        return mostAttempted + " (" + maxAttempts + " attempts)";
    }

    public static void main(String[] args) {

        UsernameChecker checker = new UsernameChecker();

        // Existing users
        checker.registerUser("john_doe", 1);
        checker.registerUser("admin", 2);

        // Check availability
        System.out.println(checker.checkAvailability("john_doe")); // false
        System.out.println(checker.checkAvailability("jane_smith")); // true

        // Suggest alternatives
        System.out.println(checker.suggestAlternatives("john_doe"));

        // Simulate attempts
        checker.checkAvailability("admin");
        checker.checkAvailability("admin");
        checker.checkAvailability("admin");

        // Most attempted username
        System.out.println(checker.getMostAttempted());
    }
}