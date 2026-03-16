import java.util.*;

class DNSEntry {
    String ipAddress;
    long expiryTime;

    public DNSEntry(String ipAddress, long ttlSeconds) {
        this.ipAddress = ipAddress;
        this.expiryTime = System.currentTimeMillis() + ttlSeconds * 1000;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > expiryTime;
    }
}

public class DNSCache {

    private final int capacity;

    private LinkedHashMap<String, DNSEntry> cache;

    private int hits = 0;
    private int misses = 0;

    public DNSCache(int capacity) {
        this.capacity = capacity;

        cache = new LinkedHashMap<String, DNSEntry>(capacity, 0.75f, true) {
            protected boolean removeEldestEntry(Map.Entry<String, DNSEntry> eldest) {
                return size() > DNSCache.this.capacity;
            }
        };
    }

    // Resolve domain
    public synchronized String resolve(String domain) {

        DNSEntry entry = cache.get(domain);

        if (entry != null && !entry.isExpired()) {
            hits++;
            return "Cache HIT → " + entry.ipAddress;
        }

        misses++;

        if (entry != null && entry.isExpired()) {
            cache.remove(domain);
        }

        // Simulate upstream DNS query
        String ip = queryUpstreamDNS(domain);

        cache.put(domain, new DNSEntry(ip, 300));

        return "Cache MISS → Upstream → " + ip;
    }

    // Simulated upstream DNS lookup
    private String queryUpstreamDNS(String domain) {

        Random rand = new Random();

        return "172.217.14." + rand.nextInt(255);
    }

    // Cache statistics
    public void getCacheStats() {

        int total = hits + misses;

        double hitRate = total == 0 ? 0 : ((double) hits / total) * 100;

        System.out.println("Cache Hits: " + hits);
        System.out.println("Cache Misses: " + misses);
        System.out.println("Hit Rate: " + String.format("%.2f", hitRate) + "%");
    }

    public static void main(String[] args) throws InterruptedException {

        DNSCache cache = new DNSCache(3);

        System.out.println(cache.resolve("google.com"));
        System.out.println(cache.resolve("google.com"));
        System.out.println(cache.resolve("openai.com"));

        Thread.sleep(1000);

        System.out.println(cache.resolve("google.com"));

        cache.getCacheStats();
    }
}