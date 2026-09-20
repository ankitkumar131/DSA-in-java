/**
 * Day 10 — Hard practice.
 *
 * Compile: javac src/day-10/PracticeHard.java
 * Run    : java -cp src/day-10 PracticeHard.java
 */
public class PracticeHard {

    // Q11: Search in rotated sorted array II
    static boolean searchRotatedDup(int[] a, int t) {
        int lo = 0, hi = a.length - 1;
        while (lo <= hi) {
            int m = lo + (hi - lo) / 2;
            if (a[m] == t) return true;
            if (a[lo] == a[m] && a[m] == a[hi]) { lo++; hi--; }
            else if (a[lo] <= a[m]) {
                if (t >= a[lo] && t < a[m]) hi = m - 1; else lo = m + 1;
            } else {
                if (t > a[m] && t <= a[hi]) lo = m + 1; else hi = m - 1;
            }
        }
        return false;
    }

    // Q12: Median of two sorted arrays
    static double findMedianSortedArrays(int[] a, int[] b) {
        if (a.length > b.length) return findMedianSortedArrays(b, a);
        int m = a.length, n = b.length;
        int lo = 0, hi = m;
        while (lo <= hi) {
            int i = lo + (hi - lo) / 2;
            int j = (m + n + 1) / 2 - i;
            int aLeft  = (i == 0) ? Integer.MIN_VALUE : a[i - 1];
            int aRight = (i == m) ? Integer.MAX_VALUE : a[i];
            int bLeft  = (j == 0) ? Integer.MIN_VALUE : b[j - 1];
            int bRight = (j == n) ? Integer.MAX_VALUE : b[j];
            if (aLeft <= bRight && bLeft <= aRight) {
                if ((m + n) % 2 == 0)
                    return (Math.max(aLeft, bLeft) + Math.min(aRight, bRight)) / 2.0;
                else
                    return Math.max(aLeft, bLeft);
            } else if (aLeft > bRight) hi = i - 1;
            else lo = i + 1;
        }
        return -1;
    }

    // Q13: Min speed to arrive on time
    static int minSpeed(int[] dist, double hour) {
        int lo = 1, hi = 10_000_000;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            double t = 0;
            for (int i = 0; i < dist.length; i++) {
                double seg = (double) dist[i] / mid;
                t += (i == dist.length - 1) ? seg : Math.ceil(seg);
            }
            if (t <= hour) hi = mid; else lo = mid + 1;
        }
        return lo;
    }

    // Q14: Aggressive cows
    static int aggressiveCows(int[] stalls, int cows) {
        java.util.Arrays.sort(stalls);
        int lo = 1, hi = stalls[stalls.length - 1] - stalls[0];
        while (lo < hi) {
            int mid = lo + (hi - lo + 1) / 2;
            if (canPlace(stalls, cows, mid)) lo = mid; else hi = mid - 1;
        }
        return lo;
    }
    static boolean canPlace(int[] stalls, int cows, int dist) {
        int placed = 1, last = stalls[0];
        for (int i = 1; i < stalls.length; i++) {
            if (stalls[i] - last >= dist) { placed++; last = stalls[i]; if (placed >= cows) return true; }
        }
        return false;
    }

    // Q15: Split array largest sum
    static int splitArray(int[] nums, int m) {
        int lo = 0, hi = 0;
        for (int x : nums) { lo = Math.max(lo, x); hi += x; }
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (feasible(nums, m, mid)) hi = mid; else lo = mid + 1;
        }
        return lo;
    }
    static boolean feasible(int[] nums, int m, int maxSum) {
        int count = 1, current = 0;
        for (int x : nums) {
            if (current + x > maxSum) { count++; current = x; if (count > m) return false; }
            else current += x;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println("Q11 rotatedDup    = " + searchRotatedDup(new int[]{2,5,6,0,0,1,2}, 0));
        System.out.println("Q12 median        = " + findMedianSortedArrays(new int[]{1,3}, new int[]{2}));
        System.out.println("Q13 minSpeed      = " + minSpeed(new int[]{1,3,4}, 6));
        System.out.println("Q14 aggressive    = " + aggressiveCows(new int[]{1,2,4,8,9}, 3));
        System.out.println("Q15 splitArray    = " + splitArray(new int[]{7,2,5,10,8}, 2));
    }
}
