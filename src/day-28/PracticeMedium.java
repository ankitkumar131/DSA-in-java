/**
 * Day 28 — Medium practice.
 *
 * Compile: javac src/day-28/PracticeMedium.java
 * Run    : java -cp src/day-28 PracticeMedium
 */
import java.util.*;

public class PracticeMedium {

    static int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1]; Arrays.fill(dp, amount + 1); dp[0] = 0;
        for (int i = 1; i <= amount; i++)
            for (int c : coins) if (c <= i) dp[i] = Math.min(dp[i], dp[i - c] + 1);
        return dp[amount] > amount ? -1 : dp[amount];
    }

    static int change(int amount, int[] coins) {
        int[] dp = new int[amount + 1]; dp[0] = 1;
        for (int c : coins) for (int i = c; i <= amount; i++) dp[i] += dp[i - c];
        return dp[amount];
    }

    static boolean canPartition(int[] a) {
        int sum = 0; for (int x : a) sum += x;
        if (sum % 2 != 0) return false;
        boolean[] dp = new boolean[sum / 2 + 1]; dp[0] = true;
        for (int x : a) for (int j = dp.length - 1; j >= x; j--) dp[j] |= dp[j - x];
        return dp[sum / 2];
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

    public static void main(String[] args) {
        System.out.println("Q6  coinChange  = " + coinChange(new int[]{1,2,5}, 11));
        System.out.println("Q7  change      = " + change(5, new int[]{1,2,5}));
        System.out.println("Q8  partition   = " + canPartition(new int[]{1,5,11,5}));
        System.out.println("Q9  LCS        = " + lcs("abcde", "ace"));
        System.out.println("Q10 edit       = " + editDistance("horse", "ros"));
    }
}
