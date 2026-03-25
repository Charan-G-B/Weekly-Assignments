import java.util.*;

public class AccountIDSearch {

    // Linear Search (first occurrence)
    static int linearSearch(String[] arr, String target) {
        int comparisons = 0;
        for (int i = 0; i < arr.length; i++) {
            comparisons++;
            if (arr[i].equals(target)) {
                System.out.println("Linear comparisons: " + comparisons);
                return i;
            }
        }
        System.out.println("Linear comparisons: " + comparisons);
        return -1;
    }

    // Binary Search (find one occurrence)
    static int binarySearch(String[] arr, String target) {
        int low = 0, high = arr.length - 1;
        int comparisons = 0;

        while (low <= high) {
            comparisons++;
            int mid = (low + high) / 2;

            if (arr[mid].equals(target)) {
                System.out.println("Binary comparisons: " + comparisons);
                return mid;
            } else if (arr[mid].compareTo(target) < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        System.out.println("Binary comparisons: " + comparisons);
        return -1;
    }

    // Count occurrences
    static int countOccurrences(String[] arr, String target) {
        int count = 0;
        for (String s : arr) {
            if (s.equals(target)) count++;
        }
        return count;
    }

    public static void main(String[] args) {
        String[] arr = {"accA", "accB", "accB", "accC"};

        Arrays.sort(arr); // Required for binary search

        int linearIndex = linearSearch(arr, "accB");
        System.out.println("Linear index: " + linearIndex);

        int binaryIndex = binarySearch(arr, "accB");
        System.out.println("Binary index: " + binaryIndex);

        System.out.println("Occurrences: " + countOccurrences(arr, "accB"));
    }
}