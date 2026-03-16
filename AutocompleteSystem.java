import java.util.*;

public class AutocompleteSystem {

    // query -> frequency
    private HashMap<String, Integer> queryFrequency = new HashMap<>();

    // Add or update search query
    public void updateFrequency(String query) {
        queryFrequency.put(query, queryFrequency.getOrDefault(query, 0) + 1);
    }

    // Search suggestions by prefix
    public List<String> search(String prefix) {

        PriorityQueue<Map.Entry<String, Integer>> pq =
                new PriorityQueue<>((a, b) -> b.getValue() - a.getValue());

        for (Map.Entry<String, Integer> entry : queryFrequency.entrySet()) {

            if (entry.getKey().startsWith(prefix)) {
                pq.add(entry);
            }
        }

        List<String> results = new ArrayList<>();
        int count = 0;

        while (!pq.isEmpty() && count < 10) {
            Map.Entry<String, Integer> entry = pq.poll();
            results.add(entry.getKey() + " (" + entry.getValue() + ")");
            count++;
        }

        return results;
    }

    public static void main(String[] args) {

        AutocompleteSystem system = new AutocompleteSystem();

        // sample queries
        system.updateFrequency("java tutorial");
        system.updateFrequency("java tutorial");
        system.updateFrequency("java stream api");
        system.updateFrequency("javascript tutorial");
        system.updateFrequency("java download");
        system.updateFrequency("java tutorial");

        // search prefix
        List<String> results = system.search("jav");

        System.out.println("Suggestions:");
        for (String s : results) {
            System.out.println(s);
        }
    }
}