/**
 * Day 8 — Medium practice.
 *
 * Compile: javac src/day-08/PracticeMedium.java
 * Run    : java -cp src/day-08 PracticeMedium
 */
import java.util.*;

public class PracticeMedium {

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

    static int characterReplacement(String s, int k) {
        int[] count = new int[26];
        int left = 0, maxFreq = 0, best = 0;
        for (int right = 0; right < s.length(); right++) {
            maxFreq = Math.max(maxFreq, ++count[s.charAt(right) - 'A']);
            while (right - left + 1 - maxFreq > k) {
                count[s.charAt(left) - 'A']--;
                left++;
            }
            best = Math.max(best, right - left + 1);
        }
        return best;
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

    static int totalFruit(int[] fruits) {
        Map<Integer, Integer> basket = new HashMap<>();
        int left = 0, best = 0;
        for (int right = 0; right < fruits.length; right++) {
            basket.merge(fruits[right], 1, Integer::sum);
            while (basket.size() > 2) {
                basket.merge(fruits[left], -1, Integer::sum);
                if (basket.get(fruits[left]) == 0) basket.remove(fruits[left]);
                left++;
            }
            best = Math.max(best, right - left + 1);
        }
        return best;
    }

    public static void main(String[] args) {
        System.out.println("Q6  longestUnique       = " + longestUnique("abcabcbb"));
        System.out.println("Q7  charReplacement     = " + characterReplacement("AABABBA", 1));
        System.out.println("Q8  findAnagrams        = " + findAnagrams("cbaebabacd", "abc"));
        System.out.println("Q9  minSubArrayLen      = " + minSubArrayLen(7, new int[]{2,3,1,2,4,3}));
        System.out.println("Q10 totalFruit          = " + totalFruit(new int[]{1,2,1}));
    }
}
