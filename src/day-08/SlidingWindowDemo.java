/**
 * Day 8 — Sliding window demo.
 *
 * Compile: javac src/day-08/SlidingWindowDemo.java
 * Run    : java -cp src/day-08 SlidingWindowDemo
 */
import java.util.*;

public class SlidingWindowDemo {

    static int maxSumK(int[] a, int k) {
        int sum = 0;
        for (int r = 0; r < k; r++) sum += a[r];
        int best = sum;
        for (int r = k; r < a.length; r++) {
            sum += a[r] - a[r - k];
            best = Math.max(best, sum);
        }
        return best;
    }

    static int longestUnique(String s) {
        int[] last = new int[256];
        Arrays.fill(last, -1);
        int best = 0, start = 0;
        for (int i = 0; i < s.length(); i++) {
            if (last[s.charAt(i)] >= start) start = last[s.charAt(i)] + 1;
            last[s.charAt(i)] = i;
            best = Math.max(best, i - start + 1);
        }
        return best;
    }

    static int minSubArrayLen(int target, int[] a) {
        int lo = 0, sum = 0, best = Integer.MAX_VALUE;
        for (int hi = 0; hi < a.length; hi++) {
            sum += a[hi];
            while (sum >= target) {
                best = Math.min(best, hi - lo + 1);
                sum -= a[lo++];
            }
        }
        return best == Integer.MAX_VALUE ? 0 : best;
    }

    static String minWindow(String s, String t) {
        int[] need = new int[128];
        for (char c : t.toCharArray()) need[c]++;
        int missing = t.length();
        int lo = 0, bestLo = 0, bestHi = Integer.MAX_VALUE;
        for (int hi = 0; hi < s.length(); hi++) {
            if (need[s.charAt(hi)]-- > 0) missing--;
            while (missing == 0) {
                if (hi - lo < bestHi - bestLo) { bestLo = lo; bestHi = hi; }
                if (need[s.charAt(lo)]++ == 0) missing++;
                lo++;
            }
        }
        return bestHi == Integer.MAX_VALUE ? "" : s.substring(bestLo, bestHi + 1);
    }

    static List<Integer> findAnagrams(String s, String p) {
        int[] need = new int[26], have = new int[26];
        for (char c : p.toCharArray()) need[c - 'a']++;
        List<Integer> out = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            have[s.charAt(i) - 'a']++;
            if (i >= p.length()) have[s.charAt(i - p.length()) - 'a']--;
            if (i >= p.length() - 1 && Arrays.equals(need, have)) out.add(i - p.length() + 1);
        }
        return out;
    }

    public static void main(String[] args) {
        System.out.println("maxSumK(3)        = " + maxSumK(new int[]{2,1,5,1,3,2}, 3));
        System.out.println("longestUnique     = " + longestUnique("abcabcbb"));
        System.out.println("minSubArrayLen(7) = " + minSubArrayLen(7, new int[]{2,3,1,2,4,3}));
        System.out.println("minWindow         = " + minWindow("ADOBECODEBANC", "ABC"));
        System.out.println("findAnagrams      = " + findAnagrams("cbaebabacd", "abc"));
    }
}
