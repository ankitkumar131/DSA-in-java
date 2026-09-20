/**
 * Day 9 — Hard practice.
 *
 * Compile: javac src/day-09/PracticeHard.java
 * Run    : java -cp src/day-09 PracticeHard
 */
import java.util.*;

public class PracticeHard {

    // Q11: max subarray length with sum == k (with negatives)
    static int maxSubArrayLen(int[] nums, int k) {
        Map<Long, Integer> m = new HashMap<>();
        long sum = 0; int best = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (sum == k) best = i + 1;
            if (m.containsKey(sum - k)) best = Math.max(best, i - m.get(sum - k));
            if (!m.containsKey(sum)) m.put(sum, i);
        }
        return best;
    }

    // Q12: subarrays with exactly K distinct
    static int subarraysWithKDistinct(int[] nums, int k) {
        return atMostK(nums, k) - atMostK(nums, k - 1);
    }
    static int atMostK(int[] nums, int k) {
        Map<Integer, Integer> m = new HashMap<>();
        int left = 0, ans = 0;
        for (int right = 0; right < nums.length; right++) {
            m.merge(nums[right], 1, Integer::sum);
            while (m.size() > k) {
                m.merge(nums[left], -1, Integer::sum);
                if (m.get(nums[left]) == 0) m.remove(nums[left]);
                left++;
            }
            ans += right - left + 1;
        }
        return ans;
    }

    // Q13: 2D range sum query
    static class NumMatrix {
        long[][] p;
        NumMatrix(int[][] m) {
            int r = m.length, c = m[0].length;
            p = new long[r + 1][c + 1];
            for (int i = 1; i <= r; i++)
                for (int j = 1; j <= c; j++)
                    p[i][j] = m[i - 1][j - 1] + p[i - 1][j] + p[i][j - 1] - p[i - 1][j - 1];
        }
        long sumRegion(int r1, int c1, int r2, int c2) {
            return p[r2 + 1][c2 + 1] - p[r1][c2 + 1] - p[r2 + 1][c1] + p[r1][c1];
        }
    }

    // Q14: Count of Range Sum (merge sort)
    static int countRangeSum(int[] nums, int lower, int upper) {
        long[] prefix = new long[nums.length + 1];
        for (int i = 0; i < nums.length; i++) prefix[i + 1] = prefix[i] + nums[i];
        return sortCount(prefix, 0, prefix.length - 1, lower, upper);
    }
    static int sortCount(long[] a, int lo, int hi, int lower, int upper) {
        if (lo >= hi) return 0;
        int mid = lo + (hi - lo) / 2;
        int count = sortCount(a, lo, mid, lower, upper) + sortCount(a, mid + 1, hi, lower, upper);
        int j = mid + 1, k = mid + 1;
        for (int i = lo; i <= mid; i++) {
            while (j <= hi && a[j] - a[i] < lower) j++;
            while (k <= hi && a[k] - a[i] <= upper) k++;
            count += k - j;
        }
        long[] merged = new long[hi - lo + 1];
        int p1 = lo, p2 = mid + 1, idx = 0;
        while (p1 <= mid && p2 <= hi) merged[idx++] = a[p1] < a[p2] ? a[p1++] : a[p2++];
        while (p1 <= mid) merged[idx++] = a[p1++];
        while (p2 <= hi) merged[idx++] = a[p2++];
        System.arraycopy(merged, 0, a, lo, merged.length);
        return count;
    }

    // Q15: shortest subarray with sum >= k (with negatives — monotonic deque)
    static int shortestSubarray(int[] nums, int k) {
        int n = nums.length;
        long[] p = new long[n + 1];
        for (int i = 0; i < n; i++) p[i + 1] = p[i] + nums[i];
        Deque<Integer> dq = new ArrayDeque<>();
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i <= n; i++) {
            while (!dq.isEmpty() && p[i] - p[dq.peekFirst()] >= k) {
                ans = Math.min(ans, i - dq.pollFirst());
            }
            while (!dq.isEmpty() && p[i] <= p[dq.peekLast()]) dq.pollLast();
            dq.offerLast(i);
        }
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    public static void main(String[] args) {
        System.out.println("Q11 maxSubArrayLen      = " + maxSubArrayLen(new int[]{1,-1,5,-2,3}, 3));
        System.out.println("Q12 subarraysKDistinct  = " + subarraysWithKDistinct(new int[]{1,2,1,2,3}, 2));
        NumMatrix nm = new NumMatrix(new int[][]{{1,2,3},{4,5,6},{7,8,9}});
        System.out.println("Q13 2D range sum        = " + nm.sumRegion(0, 0, 1, 1));
        System.out.println("Q14 countRangeSum       = " + countRangeSum(new int[]{-2,5,-1}, -2, 2));
        System.out.println("Q15 shortestSubarray    = " + shortestSubarray(new int[]{2,-1,2}, 3));
    }
}
