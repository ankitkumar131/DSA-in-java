/**
 * Day 8 — Hard practice.
 *
 * Compile: javac src/day-08/PracticeHard.java
 * Run    : java -cp src/day-08 PracticeHard
 */
import java.util.*;

public class PracticeHard {

    static String minWindow(String s, String t) {
        int[] need = new int[128];
        for (char c : t.toCharArray()) need[c]++;
        int missing = t.length(), lo = 0, bestLo = 0, bestHi = Integer.MAX_VALUE;
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

    static List<Integer> findSubstring(String s, String[] words) {
        List<Integer> out = new ArrayList<>();
        if (words.length == 0) return out;
        int wordLen = words[0].length(), total = wordLen * words.length;
        Map<String, Integer> need = new HashMap<>();
        for (String w : words) need.merge(w, 1, Integer::sum);
        for (int offset = 0; offset < wordLen; offset++) {
            Map<String, Integer> have = new HashMap<>();
            for (int i = offset; i + total <= s.length(); i += wordLen) {
                if (i > offset) {
                    String prev = s.substring(i - wordLen, i);
                    have.merge(prev, -1, Integer::sum);
                    if (have.get(prev) == 0) have.remove(prev);
                }
                String sub = s.substring(i + total - wordLen, i + total);
                have.merge(sub, 1, Integer::sum);
                if (have.equals(need)) out.add(i);
            }
        }
        return out;
    }

    static int minFlips(String s) {
        // minimal flips to make alternating (start with 0 or start with 1)
        int n = s.length();
        int diff0 = 0, diff1 = 0;
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < 2 * n; i++) {
            char c = s.charAt(i % n);
            char expect0 = (i % 2 == 0) ? '0' : '1';
            char expect1 = (i % 2 == 0) ? '1' : '0';
            if (c != expect0) diff0++;
            if (c != expect1) diff1++;
            if (i >= n) {
                char prev = s.charAt((i - n) % n);
                char pexp0 = ((i - n) % 2 == 0) ? '0' : '1';
                char pexp1 = ((i - n) % 2 == 0) ? '1' : '0';
                if (prev != pexp0) diff0--;
                if (prev != pexp1) diff1--;
            }
            if (i >= n - 1) ans = Math.min(ans, Math.min(diff0, diff1));
        }
        return ans;
    }

    static int longestKDistinct(String s, int k) {
        Map<Character, Integer> map = new HashMap<>();
        int best = 0, left = 0;
        for (int right = 0; right < s.length(); right++) {
            map.merge(s.charAt(right), 1, Integer::sum);
            while (map.size() > k) {
                map.merge(s.charAt(left), -1, Integer::sum);
                if (map.get(s.charAt(left)) == 0) map.remove(s.charAt(left));
                left++;
            }
            best = Math.max(best, right - left + 1);
        }
        return best;
    }

    public static void main(String[] args) {
        System.out.println("Q11 minWindow          = " + minWindow("ADOBECODEBANC", "ABC"));
        System.out.println("Q12 maxSlidingWindow   = " + Arrays.toString(maxSlidingWindow(new int[]{1,3,-1,-3,5,3,6,7}, 3)));
        System.out.println("Q13 findSubstring      = " + findSubstring("barfoothefoobarman", new String[]{"foo","bar"}));
        System.out.println("Q14 minFlips(111000)   = " + minFlips("111000"));
        System.out.println("Q15 longestKDistinct   = " + longestKDistinct("eceba", 2));
    }
}
