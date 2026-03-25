public class RiskThresholdSearch {

    // Linear search
    static boolean linearSearch(int[] arr, int target) {
        for (int val : arr) {
            if (val == target) return true;
        }
        return false;
    }

    // Binary search (exact)
    static int binarySearch(int[] arr, int target) {
        int low = 0, high = arr.length - 1;

        while (low <= high) {
            int mid = (low + high) / 2;

            if (arr[mid] == target) return mid;
            else if (arr[mid] < target) low = mid + 1;
            else high = mid - 1;
        }
        return -1;
    }

    // Floor (largest ≤ target)
    static int floor(int[] arr, int target) {
        int low = 0, high = arr.length - 1;
        int ans = -1;

        while (low <= high) {
            int mid = (low + high) / 2;

            if (arr[mid] <= target) {
                ans = arr[mid];
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return ans;
    }

    // Ceiling (smallest ≥ target)
    static int ceiling(int[] arr, int target) {
        int low = 0, high = arr.length - 1;
        int ans = -1;

        while (low <= high) {
            int mid = (low + high) / 2;

            if (arr[mid] >= target) {
                ans = arr[mid];
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] arr = {10, 25, 50, 100};

        int target = 30;

        System.out.println("Linear found: " + linearSearch(arr, target));
        System.out.println("Binary index: " + binarySearch(arr, target));
        System.out.println("Floor: " + floor(arr, target));
        System.out.println("Ceiling: " + ceiling(arr, target));
    }
}