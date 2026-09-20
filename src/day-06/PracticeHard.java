/**
 * Day 6 — Hard practice.
 *
 * Compile: javac src/day-06/PracticeHard.java
 * Run    : java -cp src/day-06 PracticeHard
 */
import java.util.*;

public class PracticeHard {

    // Q11: Longest palindromic substring (expand-around-centre)
    static String longestPalindrome(String s) {
        if (s == null || s.length() < 1) return "";
        int start = 0, end = 0;
        for (int i = 0; i < s.length(); i++) {
            int[] odd = expand(s, i, i);
            int[] even = expand(s, i, i + 1);
            int[] best = (odd[1] - odd[0] > even[1] - even[0]) ? odd : even;
            if (best[1] - best[0] > end - start) { start = best[0]; end = best[1]; }
        }
        return s.substring(start, end + 1);
    }
    static int[] expand(String s, int lo, int hi) {
        while (lo >= 0 && hi < s.length() && s.charAt(lo) == s.charAt(hi)) { lo--; hi++; }
        return new int[]{lo + 1, hi - 1};
    }

    // Q12: Minimum window substring
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

    // Q13: Edit distance (Levenshtein)
    static int editDistance(String a, String b) {
        int m = a.length(), n = b.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++) dp[i][0] = i;
        for (int j = 0; j <= n; j++) dp[0][j] = j;
        for (int i = 1; i <= m; i++)
            for (int j = 1; j <= n; j++)
                if (a.charAt(i - 1) == b.charAt(j - 1)) dp[i][j] = dp[i - 1][j - 1];
                else dp[i][j] = 1 + Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1]));
        return dp[m][n];
    }

    // Q14: Longest common prefix
    static String longestCommonPrefix(String[] strs) {
        if (strs.length == 0) return "";
        String pre = strs[0];
        for (int i = 1; i < strs.length; i++)
            while (!strs[i].startsWith(pre)) pre = pre.substring(0, pre.length() - 1);
        return pre;
    }

    // Q15: Rabin-Karp substring search
    static int rabinKarp(String text, String pat) {
        int n = text.length(), m = pat.length();
        long base = 256, mod = 1_000_000_007L;
        long h = 1, ph = 0, th = 0;
        for (int i = 0; i < m - 1; i++) h = (h * base) % mod;
        for (int i = 0; i < m; i++) { ph = (ph * base + pat.charAt(i)) % mod; th = (th * base + text.charAt(i)) % mod; }
        for (int i = 0; i <= n - m; i++) {
            if (ph == th && text.substring(i, i + m).equals(pat)) return i;
            if (i < n - m) th = ((th - text.charAt(i) * h) * base + text.charAt(i + m)) % mod;
            if (th < 0) th += mod;
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println("Q11 longestPalindrome(babad) = " + longestPalindrome("babad"));
        System.out.println("Q12 minWindow                 = " + minWindow("ADOBECODEBANC", "ABC"));
        System.out.println("Q13 editDistance(horse,ros)   = " + editDistance("horse", "ros"));
        System.out.println("Q14 lcp                       = " + longestCommonPrefix(new String[]{"flower","flow","flight"}));
        System.out.println("Q15 rabinKarp                 = " + rabinKarp("abcxabcdabcy", "abcd"));
    }
}
