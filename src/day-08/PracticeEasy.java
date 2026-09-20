/**
 * Day 8 — Easy practice.
 *
 * Compile: javac src/day-08/PracticeEasy.java
 * Run    : java -cp src/day-08 PracticeEasy
 */
import java.util.*;

public class PracticeEasy {

    static int maxSumK(int[] a, int k) {
        int sum = 0;
        for (int r = 0; r < k; r++) sum += a[r];
        int best = sum;
        for (int r = k; r < a.length; r++) { sum += a[r] - a[r - k]; best = Math.max(best, sum); }
        return best;
    }

    static double[] avgK(int[] a, int k) {
        double[] out = new double[a.length - k + 1];
        int sum = 0;
        for (int r = 0; r < k; r++) sum += a[r];
        out[0] = (double) sum / k;
        for (int r = k; r < a.length; r++) {
            sum += a[r] - a[r - k];
            out[r - k + 1] = (double) sum / k;
        }
        return out;
    }

    static boolean containsNearbyDuplicate(int[] a, int k) {
        Map<Integer, Integer> last = new HashMap<>();
        for (int i = 0; i < a.length; i++) {
            if (last.containsKey(a[i]) && i - last.get(a[i]) <= k) return true;
            last.put(a[i], i);
        }
        return false;
    }

    static int[] maxSlidingWindow(int[] a, int k) {
        Deque<Integer> dq = new ArrayDeque<>();
        int[] out = new int[a.length - k + 1];
        for (int i = 0; i < a.length; i++) {
            while (!dq.isEmpty() && dq.peekFirst() <= i - k) dq.pollFirst();
            while (!dq.isEmpty() && a[dq.peekLast()] <= a[i]) dq.pollLast();
            dq.offerLast(i);
            if (i >= k - 1) out[i - k + 1] = a[dq.peekFirst()];
        }
        return out;
    }

    static int numOfSubarrays(int[] a, int k, int threshold) {
        int sum = 0, count = 0;
        for (int r = 0; r < k; r++) sum += a[r];
        if (sum / k >= threshold) count++;
        for (int r = k; r < a.length; r++) {
            sum += a[r] - a[r - k];
            if (sum / k >= threshold) count++;
        }
        return count;
    }

    public static void main(String[] args) {
        System.out.println("Q1 maxSumK  = " + maxSumK(new int[]{2,1,5,1,3,2}, 3));
        System.out.println("Q2 avgK     = " + Arrays.toString(avgK(new int[]{1,12,-5,-6,50,3}, 4)));
        System.out.println("Q3 nearby   = " + containsNearbyDuplicate(new int[]{1,2,3,1}, 3));
        System.out.println("Q4 maxWin   = " + Arrays.toString(maxSlidingWindow(new int[]{1,3,-1,-3,5,3,6,7}, 3)));
        System.out.println("Q5 numAvg   = " + numOfSubarrays(new int[]{2,2,2,2,5,5,5,8}, 3, 4));
    }
}
