/**
 * Day 7 — Hard practice.
 *
 * Compile: javac src/day-07/PracticeHard.java
 * Run    : java -cp src/day-07 PracticeHard
 */
import java.util.*;

public class PracticeHard {

    // Q11: Trapping rain water (two-pointer)
    static int trap(int[] h) {
        int lo = 0, hi = h.length - 1, leftMax = 0, rightMax = 0, water = 0;
        while (lo < hi) {
            if (h[lo] < h[hi]) {
                if (h[lo] >= leftMax) leftMax = h[lo];
                else water += leftMax - h[lo];
                lo++;
            } else {
                if (h[hi] >= rightMax) rightMax = h[hi];
                else water += rightMax - h[hi];
                hi--;
            }
        }
        return water;
    }

    // Q12: 4Sum
    static List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);
        List<List<Integer>> out = new ArrayList<>();
        int n = nums.length;
        for (int i = 0; i < n - 3; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            for (int j = i + 1; j < n - 2; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) continue;
                int lo = j + 1, hi = n - 1;
                while (lo < hi) {
                    long s = (long) nums[i] + nums[j] + nums[lo] + nums[hi];
                    if (s == target) {
                        out.add(Arrays.asList(nums[i], nums[j], nums[lo], nums[hi]));
                        while (lo < hi && nums[lo] == nums[lo + 1]) lo++;
                        while (lo < hi && nums[hi] == nums[hi - 1]) hi--;
                        lo++; hi--;
                    } else if (s < target) lo++; else hi--;
                }
            }
        }
        return out;
    }

    // Q13: Minimum size subarray sum (sliding window preview)
    static int minSubArrayLen(int target, int[] nums) {
        int lo = 0, sum = 0, best = Integer.MAX_VALUE;
        for (int hi = 0; hi < nums.length; hi++) {
            sum += nums[hi];
            while (sum >= target) {
                best = Math.min(best, hi - lo + 1);
                sum -= nums[lo++];
            }
        }
        return best == Integer.MAX_VALUE ? 0 : best;
    }

    // Q14: Longest mountain
    static int longestMountain(int[] a) {
        int n = a.length, best = 0, i = 1;
        while (i < n - 1) {
            if (a[i - 1] < a[i] && a[i] > a[i + 1]) {
                int lo = i - 1, hi = i + 1;
                while (lo > 0 && a[lo - 1] < a[lo]) lo--;
                while (hi < n - 1 && a[hi] > a[hi + 1]) hi++;
                best = Math.max(best, hi - lo + 1);
                i = hi + 1;
            } else i++;
        }
        return best;
    }

    // Q15: Partition labels
    static List<Integer> partitionLabels(String s) {
        int[] last = new int[26];
        for (int i = 0; i < s.length(); i++) last[s.charAt(i) - 'a'] = i;
        List<Integer> out = new ArrayList<>();
        int start = 0, end = 0;
        for (int i = 0; i < s.length(); i++) {
            end = Math.max(end, last[s.charAt(i) - 'a']);
            if (i == end) { out.add(end - start + 1); start = i + 1; }
        }
        return out;
    }

    public static void main(String[] args) {
        System.out.println("Q11 trap   = " + trap(new int[]{0,1,0,2,1,0,1,3,2,1,2,1}));
        System.out.println("Q12 4Sum   = " + fourSum(new int[]{1,0,-1,0,-2,2}, 0));
        System.out.println("Q13 minSub = " + minSubArrayLen(7, new int[]{2,3,1,2,4,3}));
        System.out.println("Q14 mtn    = " + longestMountain(new int[]{2,1,4,7,3,2,5}));
        System.out.println("Q15 parts  = " + partitionLabels("ababcbacadefegdehijhklij"));
    }
}
