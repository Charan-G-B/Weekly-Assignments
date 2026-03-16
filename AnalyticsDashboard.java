import java.util.*;

public class AnalyticsDashboard {

    // page -> visit count
    private HashMap<String, Integer> pageViews = new HashMap<>();

    // page -> unique users
    private HashMap<String, Set<String>> uniqueVisitors = new HashMap<>();

    // source -> count
    private HashMap<String, Integer> trafficSources = new HashMap<>();

    // Process incoming page view event
    public void processEvent(String url, String userId, String source) {

        // update page views
        pageViews.put(url, pageViews.getOrDefault(url, 0) + 1);

        // update unique visitors
        uniqueVisitors.putIfAbsent(url, new HashSet<>());
        uniqueVisitors.get(url).add(userId);

        // update traffic source
        trafficSources.put(source, trafficSources.getOrDefault(source, 0) + 1);
    }

    // Get top 10 pages
    public void getDashboard() {

        System.out.println("\nTop Pages:");

        PriorityQueue<Map.Entry<String, Integer>> pq =
                new PriorityQueue<>((a, b) -> b.getValue() - a.getValue());

        pq.addAll(pageViews.entrySet());

        int count = 0;

        while (!pq.isEmpty() && count < 10) {

            Map.Entry<String, Integer> entry = pq.poll();

            String page = entry.getKey();
            int views = entry.getValue();
            int unique = uniqueVisitors.get(page).size();

            System.out.println(
                (count + 1) + ". " + page +
                " - " + views + " views (" + unique + " unique)"
            );

            count++;
        }

        System.out.println("\nTraffic Sources:");
        for (String source : trafficSources.keySet()) {
            System.out.println(source + ": " + trafficSources.get(source));
        }
    }

    public static void main(String[] args) {

        AnalyticsDashboard dashboard = new AnalyticsDashboard();

        // simulate events
        dashboard.processEvent("/article/breaking-news", "user_123", "google");
        dashboard.processEvent("/article/breaking-news", "user_456", "facebook");
        dashboard.processEvent("/sports/championship", "user_789", "direct");
        dashboard.processEvent("/article/breaking-news", "user_123", "google");
        dashboard.processEvent("/sports/championship", "user_101", "google");

        dashboard.getDashboard();
    }
}
