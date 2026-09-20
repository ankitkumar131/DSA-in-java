/**
 * Day 9 — Medium practice.
 *
 * Compile: javac src/day-09/PracticeMedium.java
 * Run    : java -cp src/day-09 PracticeMedium
 */
import java.util.*;

public class PracticeMedium {

    static int subarraySumK(int[] nums, int k) {
        Map<Long, Integer> m = new HashMap<>(); m.put(0L, 1);
        long sum = 0; int c = 0;
        for (int x : nums) { sum += x; c += m.getOrDefault(sum - k, 0); m.merge(sum, 1, Integer::sum); }
        return c;
    }

    // Treat 0 as -1: longest subarray with sum 0 = equal 0s and 1s
    static int findMaxLength(int[] nums) {
        Map<Integer, Integer> m = new HashMap<>();
        m.put(0, -1);
        int sum = 0, best = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i] == 0 ? -1 : 1;
            if (m.containsKey(sum)) best = Math.max(best, i - m.get(sum));
            else m.put(sum, i);
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

    static int numSubarrayBoundedMax(int[] a, int L, int R) {
        return atMost(a, R) - atMost(a, L - 1);
    }
    static int atMost(int[] a, int bound) {
        int count = 0, consecutive = 0;
        for (int x : a) {
            if (x <= bound) { consecutive++; count += consecutive; }
            else consecutive = 0;
        }
        return count;
    }

    static int subarraysDivByK(int[] nums, int k) {
        Map<Integer, Integer> m = new HashMap<>(); m.put(0, 1);
        int sum = 0, c = 0;
        for (int x : nums) {
            sum = (sum + x) % k;
            if (sum < 0) sum += k;
            c += m.getOrDefault(sum, 0);
            m.merge(sum, 1, Integer::sum);
        }
        return c;
    }

    public static void main(String[] args) {
        System.out.println("Q6  subarraySum(3)   = " + subarraySumK(new int[]{1,2,3}, 3));
        System.out.println("Q7  findMaxLength    = " + findMaxLength(new int[]{0,1,0}));
        System.out.println("Q8  productExceptSelf= " + Arrays.toString(productExceptSelf(new int[]{1,2,3,4})));
        System.out.println("Q9  boundedMax       = " + numSubarrayBoundedMax(new int[]{2,1,4,3}, 2, 3));
        System.out.println("Q10 divByK           = " + subarraysDivByK(new int[]{4,5,0,-2,-3,1}, 5));
    }
}
