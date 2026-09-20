/**
 * Day 10 — Binary search variants.
 *
 * Compile: javac src/day-10/BinarySearchDemo.java
 * Run    : java -cp src/day-10 BinarySearchDemo
 */
public class BinarySearchDemo {

    static int search(int[] a, int target) {
        int lo = 0, hi = a.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (a[mid] == target) return mid;
            else if (a[mid] < target) lo = mid + 1;
            else hi = mid - 1;
        }
        return -1;
    }

    static int firstOccurrence(int[] a, int target) {
        int lo = 0, hi = a.length - 1, ans = -1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (a[mid] == target) { ans = mid; hi = mid - 1; }
            else if (a[mid] < target) lo = mid + 1;
            else hi = mid - 1;
        }
        return ans;
    }

    static int lastOccurrence(int[] a, int target) {
        int lo = 0, hi = a.length - 1, ans = -1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (a[mid] == target) { ans = mid; lo = mid + 1; }
            else if (a[mid] < target) lo = mid + 1;
            else hi = mid - 1;
        }
        return ans;
    }

    static int lowerBound(int[] a, int target) {
        int lo = 0, hi = a.length - 1, ans = a.length;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (a[mid] >= target) { ans = mid; hi = mid - 1; }
            else lo = mid + 1;
        }
        return ans;
    }

    static int upperBound(int[] a, int target) {
        int lo = 0, hi = a.length - 1, ans = a.length;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (a[mid] > target) { ans = mid; hi = mid - 1; }
            else lo = mid + 1;
        }
        return ans;
    }

    static int peakElement(int[] a) {
        int lo = 0, hi = a.length - 1;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (a[mid] < a[mid + 1]) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }

    static int searchRotated(int[] a, int target) {
        int lo = 0, hi = a.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (a[mid] == target) return mid;
            if (a[lo] <= a[mid]) {
                if (target >= a[lo] && target < a[mid]) hi = mid - 1;
                else lo = mid + 1;
            } else {
                if (target > a[mid] && target <= a[hi]) lo = mid + 1;
                else hi = mid - 1;
            }
        }
        return -1;
    }

    static int minEatingSpeed(int[] piles, int h) {
        int lo = 1, hi = 0;
        for (int p : piles) hi = Math.max(hi, p);
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            long hours = 0;
            for (int p : piles) hours += (p + mid - 1) / mid;
            if (hours <= h) hi = mid; else lo = mid + 1;
        }
        return lo;
    }

    public static void main(String[] args) {
        int[] a = {1, 2, 2, 2, 3, 4, 5};
        System.out.println("search(2)         = " + search(a, 2));
        System.out.println("firstOccurrence(2)= " + firstOccurrence(a, 2));
        System.out.println("lastOccurrence(2) = " + lastOccurrence(a, 2));
        System.out.println("lowerBound(2)     = " + lowerBound(a, 2));
        System.out.println("upperBound(2)     = " + upperBound(a, 2));
        System.out.println("peakIndex         = " + peakElement(new int[]{1,3,5,4,2}));
        System.out.println("searchRotated(0)  = " + searchRotated(new int[]{4,5,6,7,0,1,2}, 0));
        System.out.println("minEatingSpeed    = " + minEatingSpeed(new int[]{3,6,7,11}, 8));
    }
}
