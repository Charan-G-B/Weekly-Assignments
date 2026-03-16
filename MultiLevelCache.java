import java.util.*;

class LRUCache<K, V> extends LinkedHashMap<K, V> {

    private int capacity;

    public LRUCache(int capacity) {
        super(capacity, 0.75f, true);
        this.capacity = capacity;
    }

    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > capacity;
    }
}

public class MultiLevelCache {

    private LRUCache<String, String> L1;
    private LRUCache<String, String> L2;
    private HashMap<String, String> database;

    private int l1Hits = 0;
    private int l2Hits = 0;
    private int dbHits = 0;

    public MultiLevelCache() {

        L1 = new LRUCache<>(100);
        L2 = new LRUCache<>(1000);
        database = new HashMap<>();

        // simulate database
        database.put("video_123", "VideoData123");
        database.put("video_456", "VideoData456");
        database.put("video_999", "VideoData999");
    }

    public String getVideo(String videoId) {

        // L1 cache
        if (L1.containsKey(videoId)) {
            l1Hits++;
            System.out.println("L1 Cache HIT");
            return L1.get(videoId);
        }

        System.out.println("L1 Cache MISS");

        // L2 cache
        if (L2.containsKey(videoId)) {

            l2Hits++;
            System.out.println("L2 Cache HIT → Promoting to L1");

            String data = L2.get(videoId);
            L1.put(videoId, data);

            return data;
        }

        System.out.println("L2 Cache MISS");

        // Database
        if (database.containsKey(videoId)) {

            dbHits++;
            System.out.println("DB HIT → Adding to L2");

            String data = database.get(videoId);

            L2.put(videoId, data);

            return data;
        }

        return null;
    }

    public void getStats() {

        int total = l1Hits + l2Hits + dbHits;

        System.out.println("\nCache Statistics:");
        System.out.println("L1 Hits: " + l1Hits);
        System.out.println("L2 Hits: " + l2Hits);
        System.out.println("DB Hits: " + dbHits);

        if (total > 0) {
            System.out.println("L1 Hit Rate: " + (l1Hits * 100 / total) + "%");
            System.out.println("L2 Hit Rate: " + (l2Hits * 100 / total) + "%");
        }
    }

    public static void main(String[] args) {

        MultiLevelCache cache = new MultiLevelCache();

        cache.getVideo("video_123");
        cache.getVideo("video_123");
        cache.getVideo("video_999");
        cache.getVideo("video_999");

        cache.getStats();
    }
}