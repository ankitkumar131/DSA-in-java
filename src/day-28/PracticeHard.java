/**
 * Day 28 — Hard practice.
 *
 * Compile: javac src/day-28/PracticeHard.java
 * Run    : java -cp src/day-28 PracticeHard
 */
import java.util.*;

public class PracticeHard {

    // Q11: distinct subsequences
    static int numDistinct(String s, String t) {
        int m = s.length(), n = t.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++) dp[i][0] = 1;
        for (int j = 1; j <= n; j++) dp[0][j] = 0;
        for (int i = 1; i <= m; i++)
            for (int j = 1; j <= n; j++)
                dp[i][j] = dp[i - 1][j] + (s.charAt(i - 1) == t.charAt(j - 1) ? dp[i - 1][j - 1] : 0);
        return dp[m][n];
    }

    // Q12: interleaving string
    static boolean isInterleave(String s1, String s2, String s3) {
        int m = s1.length(), n = s2.length();
        if (m + n != s3.length()) return false;
        boolean[] dp = new boolean[n + 1];
        dp[0] = true;
        for (int j = 1; j <= n; j++) dp[j] = dp[j - 1] && s2.charAt(j - 1) == s3.charAt(j - 1);
        for (int i = 1; i <= m; i++) {
            dp[0] = dp[0] && s1.charAt(i - 1) == s3.charAt(i - 1);
            for (int j = 1; j <= n; j++)
                dp[j] = (dp[j] && s1.charAt(i - 1) == s3.charAt(i + j - 1))
                     || (dp[j - 1] && s2.charAt(j - 1) == s3.charAt(i + j - 1));
        }
        return dp[n];
    }

    // Q13: regex matching
    static boolean regex(String s, String p) {
        int m = s.length(), n = p.length();
        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;
        for (int j = 1; j <= n; j++) if (p.charAt(j - 1) == '*') dp[0][j] = dp[0][j - 2];
        for (int i = 1; i <= m; i++)
            for (int j = 1; j <= n; j++) {
                if (p.charAt(j - 1) == '*')
                    dp[i][j] = dp[i][j - 2] || ((s.charAt(i - 1) == p.charAt(j - 2) || p.charAt(j - 2) == '.') && dp[i - 1][j]);
                else
                    dp[i][j] = dp[i - 1][j - 1] && (s.charAt(i - 1) == p.charAt(j - 1) || p.charAt(j - 1) == '.');
            }
        return dp[m][n];
    }

    // Q14: word break II
    static List<String> wordBreak(String s, List<String> wordDict) {
        Set<String> dict = new HashSet<>(wordDict);
        Map<Integer, List<String>> memo = new HashMap<>();
        return wb(s, 0, dict, memo);
    }
    static List<String> wb(String s, int start, Set<String> dict, Map<Integer, List<String>> memo) {
        if (memo.containsKey(start)) return memo.get(start);
        List<String> res = new ArrayList<>();
        if (start == s.length()) { res.add(""); return res; }
        for (int end = start + 1; end <= s.length(); end++) {
            String w = s.substring(start, end);
            if (dict.contains(w)) {
                for (String sub : wb(s, end, dict, memo)) {
                    res.add(sub.isEmpty() ? w : w + " " + sub);
                }
            }
        }
        memo.put(start, res);
        return res;
    }

    // Q15: longest increasing path
    static int[][] dirs = {{-1,0},{1,0},{0,-1},{0,1}};
    static int longestIncreasingPath(int[][] g) {
        int m = g.length, n = g[0].length;
        int[][] memo = new int[m][n];
        int best = 0;
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                best = Math.max(best, dfsLIP(g, i, j, memo));
        return best;
    }
    static int dfsLIP(int[][] g, int i, int j, int[][] memo) {
        if (memo[i][j] != 0) return memo[i][j];
        int best = 1;
        for (int[] d : dirs) {
            int ni = i + d[0], nj = j + d[1];
            if (ni >= 0 && nj >= 0 && ni < g.length && nj < g[0].length && g[ni][nj] > g[i][j])
                best = Math.max(best, 1 + dfsLIP(g, ni, nj, memo));
        }
        memo[i][j] = best;
        return best;
    }

    public static void main(String[] args) {
        System.out.println("Q11 numDistinct    = " + numDistinct("rabbbit", "rabbit"));
        System.out.println("Q12 isInterleave   = " + isInterleave("aabcc", "dbbca", "aadbbcbcac"));
        System.out.println("Q13 regex          = " + regex("aab", "c*a*b"));
        System.out.println("Q14 wordBreak      = " + wordBreak("catsanddog", List.of("cat","cats","and","sand","dog")));
        int[][] g = {{9,9,4},{6,6,8},{2,1,1}};
        System.out.println("Q15 longestIncPath = " + longestIncreasingPath(g));
    }
}
