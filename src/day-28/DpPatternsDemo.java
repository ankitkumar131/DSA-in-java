/**
 * Day 28 — DP patterns demo.
 *
 * Compile: javac src/day-28/DpPatternsDemo.java
 * Run    : java -cp src/day-28 DpPatternsDemo
 */
import java.util.*;

public class DpPatternsDemo {

    static int knapsack01(int[] w, int[] v, int cap) {
        int[] dp = new int[cap + 1];
        for (int i = 0; i < w.length; i++)
            for (int j = cap; j >= w[i]; j--)
                dp[j] = Math.max(dp[j], dp[j - w[i]] + v[i]);
        return dp[cap];
    }

    static int knapsackUnbounded(int[] w, int[] v, int cap) {
        int[] dp = new int[cap + 1];
        for (int i = 0; i < w.length; i++)
            for (int j = w[i]; j <= cap; j++)
                dp[j] = Math.max(dp[j], dp[j - w[i]] + v[i]);
        return dp[cap];
    }

    static boolean subsetSum(int[] a, int target) {
        boolean[] dp = new boolean[target + 1]; dp[0] = true;
        for (int x : a)
            for (int j = target; j >= x; j--)
                dp[j] |= dp[j - x];
        return dp[target];
    }

    static boolean canPartition(int[] a) {
        int sum = 0; for (int x : a) sum += x;
        if (sum % 2 != 0) return false;
        return subsetSum(a, sum / 2);
    }

    static int lcs(String a, String b) {
        int m = a.length(), n = b.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++)
            for (int j = 1; j <= n; j++)
                if (a.charAt(i - 1) == b.charAt(j - 1)) dp[i][j] = dp[i - 1][j - 1] + 1;
                else dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
        return dp[m][n];
    }

    static int longestCommonSubstring(String a, String b) {
        int m = a.length(), n = b.length(), best = 0;
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++)
            for (int j = 1; j <= n; j++)
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    best = Math.max(best, dp[i][j]);
                }
        return best;
    }

    static int lis(int[] a) {
        List<Integer> tails = new ArrayList<>();
        for (int x : a) {
            int lo = 0, hi = tails.size();
            while (lo < hi) { int m = (lo + hi) / 2; if (tails.get(m) < x) lo = m + 1; else hi = m; }
            if (lo == tails.size()) tails.add(x); else tails.set(lo, x);
        }
        return tails.size();
    }

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

    static int minPathSum(int[][] g) {
        int m = g.length, n = g[0].length;
        int[][] dp = new int[m][n];
        dp[0][0] = g[0][0];
        for (int i = 1; i < m; i++) dp[i][0] = dp[i - 1][0] + g[i][0];
        for (int j = 1; j < n; j++) dp[0][j] = dp[0][j - 1] + g[0][j];
        for (int i = 1; i < m; i++)
            for (int j = 1; j < n; j++)
                dp[i][j] = g[i][j] + Math.min(dp[i - 1][j], dp[i][j - 1]);
        return dp[m - 1][n - 1];
    }

    public static void main(String[] args) {
        System.out.println("01 knapsack   = " + knapsack01(new int[]{1,3,4,5}, new int[]{10,40,50,20}, 8));
        System.out.println("unbounded     = " + knapsackUnbounded(new int[]{1,3,4}, new int[]{10,40,50}, 8));
        System.out.println("subset sum    = " + subsetSum(new int[]{3,34,4,12,5,2}, 9));
        System.out.println("partition     = " + canPartition(new int[]{1,5,11,5}));
        System.out.println("LCS           = " + lcs("abcde", "ace"));
        System.out.println("LCS substr    = " + longestCommonSubstring("ABABC", "BABCA"));
        System.out.println("LIS           = " + lis(new int[]{10,9,2,5,3,7,101,18}));
        System.out.println("edit distance = " + editDistance("horse", "ros"));
        System.out.println("min path sum  = " + minPathSum(new int[][]{{1,3,1},{1,5,1},{4,2,1}}));
    }
}
