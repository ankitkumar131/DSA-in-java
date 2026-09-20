/**
 * Day 5 — Hard practice solutions.
 *
 * Compile: javac src/day-05/PracticeHard.java
 * Run    : java -cp src/day-05 PracticeHard
 */
import java.util.Arrays;

public class PracticeHard {

    static int maxProfit(int[] prices) {
        int minSoFar = prices[0], best = 0;
        for (int i = 1; i < prices.length; i++) {
            best = Math.max(best, prices[i] - minSoFar);
            minSoFar = Math.min(minSoFar, prices[i]);
        }
        return best;
    }

    static int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] out = new int[n];
        int prefix = 1;
        for (int i = 0; i < n; i++) { out[i] = prefix; prefix *= nums[i]; }
        int suffix = 1;
        for (int i = n - 1; i >= 0; i--) { out[i] *= suffix; suffix *= nums[i]; }
        return out;
    }

    static int maxWater(int[] h) {
        int lo = 0, hi = h.length - 1, best = 0;
        while (lo < hi) {
            best = Math.max(best, (hi - lo) * Math.min(h[lo], h[hi]));
            if (h[lo] < h[hi]) lo++; else hi--;
        }
        return best;
    }

    static int trap(int[] h) {
        int n = h.length;
        if (n == 0) return 0;
        int[] left = new int[n], right = new int[n];
        left[0] = h[0];
        for (int i = 1; i < n; i++) left[i] = Math.max(left[i - 1], h[i]);
        right[n - 1] = h[n - 1];
        for (int i = n - 2; i >= 0; i--) right[i] = Math.max(right[i + 1], h[i]);
        int water = 0;
        for (int i = 0; i < n; i++) water += Math.min(left[i], right[i]) - h[i];
        return water;
    }

    static java.util.List<Integer> spiral(int[][] m) {
        java.util.List<Integer> out = new java.util.ArrayList<>();
        int top = 0, bot = m.length - 1, left = 0, right = m[0].length - 1;
        while (top <= bot && left <= right) {
            for (int c = left; c <= right; c++) out.add(m[top][c]);
            top++;
            for (int r = top; r <= bot; r++) out.add(m[r][right]);
            right--;
            if (top <= bot) {
                for (int c = right; c >= left; c--) out.add(m[bot][c]);
                bot--;
            }
            if (left <= right) {
                for (int r = bot; r >= top; r--) out.add(m[r][left]);
                left++;
            }
        }
        return out;
    }

    public static void main(String[] args) {
        System.out.println("Q11 profit = " + maxProfit(new int[]{7,1,5,3,6,4}));
        System.out.println("Q12 prodES = " + Arrays.toString(productExceptSelf(new int[]{1,2,3,4})));
        System.out.println("Q13 water = " + maxWater(new int[]{1,8,6,2,5,4,8,3,7}));
        System.out.println("Q14 trap  = " + trap(new int[]{0,1,0,2,1,0,1,3,2,1,2,1}));
        System.out.println("Q15 spiral = " + spiral(new int[][]{{1,2,3},{4,5,6},{7,8,9}}));
    }
}
