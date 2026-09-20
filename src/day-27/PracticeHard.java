/**
 * Day 27 — Hard practice.
 *
 * Compile: javac src/day-27/PracticeHard.java
 * Run    : java -cp src/day-27 PracticeHard
 */
import java.util.*;

public class PracticeHard {

    // Q11: edit distance
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

    // Q12: longest common subsequence
    static int lcs(String a, String b) {
        int m = a.length(), n = b.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++)
            for (int j = 1; j <= n; j++)
                if (a.charAt(i - 1) == b.charAt(j - 1)) dp[i][j] = dp[i - 1][j - 1] + 1;
                else dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
        return dp[m][n];
    }

    // Q13: longest increasing subsequence (O(n log n))
    static int lis(int[] a) {
        List<Integer> tails = new ArrayList<>();
        for (int x : a) {
            int lo = 0, hi = tails.size();
            while (lo < hi) { int mid = (lo + hi) / 2; if (tails.get(mid) < x) lo = mid + 1; else hi = mid; }
            if (lo == tails.size()) tails.add(x); else tails.set(lo, x);
        }
        return tails.size();
    }

    // Q14: matrix chain multiplication
    static int mcm(int[] dims) {
        int n = dims.length - 1;
        int[][] dp = new int[n][n];
        for (int len = 2; len <= n; len++)
            for (int i = 0; i + len <= n; i++) {
                int j = i + len - 1;
                dp[i][j] = Integer.MAX_VALUE;
                for (int k = i; k < j; k++) {
                    int cost = dp[i][k] + dp[k + 1][j] + dims[i] * dims[k + 1] * dims[j + 1];
                    dp[i][j] = Math.min(dp[i][j], cost);
                }
            }
        return dp[0][n - 1];
    }

    // Q15: burst balloons
    static int maxCoins(int[] nums) {
        int n = nums.length;
        int[] a = new int[n + 2];
        a[0] = a[n + 1] = 1;
        for (int i = 0; i < n; i++) a[i + 1] = nums[i];
        int[][] dp = new int[n + 2][n + 2];
        for (int len = 1; len <= n; len++)
            for (int i = 1; i + len <= n + 1; i++) {
                int j = i + len - 1;
                for (int k = i; k <= j; k++)
                    dp[i][j] = Math.max(dp[i][j], dp[i][k - 1] + dp[k + 1][j] + a[i - 1] * a[k] * a[j + 1]);
            }
        return dp[1][n];
    }

    public static void main(String[] args) {
        System.out.println("Q11 edit    = " + editDistance("horse", "ros"));
        System.out.println("Q12 LCS     = " + lcs("abcde", "ace"));
        System.out.println("Q13 LIS     = " + lis(new int[]{10,9,2,5,3,7,101,18}));
        System.out.println("Q14 MCM     = " + mcm(new int[]{1,2,3,4}));
        System.out.println("Q15 balloons = " + maxCoins(new int[]{3,1,5,8}));
    }
}
