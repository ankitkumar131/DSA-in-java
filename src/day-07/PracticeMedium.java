/**
 * Day 7 — Medium practice.
 *
 * Compile: javac src/day-07/PracticeMedium.java
 * Run    : java -cp src/day-07 PracticeMedium
 */
import java.util.*;

public class PracticeMedium {

    static List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> out = new ArrayList<>();
        for (int i = 0; i < nums.length - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            int lo = i + 1, hi = nums.length - 1;
            while (lo < hi) {
                int s = nums[i] + nums[lo] + nums[hi];
                if (s == 0) {
                    out.add(Arrays.asList(nums[i], nums[lo], nums[hi]));
                    while (lo < hi && nums[lo] == nums[lo + 1]) lo++;
                    while (lo < hi && nums[hi] == nums[hi - 1]) hi--;
                    lo++; hi--;
                } else if (s < 0) lo++; else hi--;
            }
        }
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

    static void sortColors(int[] a) {
        int lo = 0, mid = 0, hi = a.length - 1;
        while (mid <= hi) {
            if (a[mid] == 0)      { swap(a, lo, mid); lo++; mid++; }
            else if (a[mid] == 1) { mid++; }
            else                  { swap(a, mid, hi); hi--; }
        }
    }
    static void swap(int[] a, int i, int j) { int t = a[i]; a[i] = a[j]; a[j] = t; }

    static int removeElement(int[] a, int val) {
        int w = 0;
        for (int r = 0; r < a.length; r++) if (a[r] != val) a[w++] = a[r];
        return w;
    }

    static int[] sortedSquares(int[] a) {
        int n = a.length, lo = 0, hi = n - 1, idx = n - 1;
        int[] out = new int[n];
        while (lo <= hi) {
            int lsq = a[lo] * a[lo], hsq = a[hi] * a[hi];
            if (lsq > hsq) { out[idx--] = lsq; lo++; } else { out[idx--] = hsq; hi--; }
        }
        return out;
    }

    public static void main(String[] args) {
        System.out.println("Q6  3Sum    = " + threeSum(new int[]{-1,0,1,2,-1,-4}));
        System.out.println("Q7  water   = " + maxWater(new int[]{1,8,6,2,5,4,8,3,7}));
        int[] c = {2,0,2,1,1,0}; sortColors(c);
        System.out.println("Q8  colors  = " + Arrays.toString(c));
        int[] a = {3,2,2,3}; int v = removeElement(a, 3);
        System.out.println("Q9  remEl(" + v + ") = " + Arrays.toString(Arrays.copyOf(a, v)));
        System.out.println("Q10 squares = " + Arrays.toString(sortedSquares(new int[]{-4,-1,0,3,10})));
    }
}
