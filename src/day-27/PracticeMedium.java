/**
 * Day 27 — Medium practice.
 *
 * Compile: javac src/day-27/PracticeMedium.java
 * Run    : java -cp src/day-27 PracticeMedium
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

    static int knapsack01(int[] w, int[] v, int cap) {
        int[] dp = new int[cap + 1];
        for (int i = 0; i < w.length; i++)
            for (int j = cap; j >= w[i]; j--)
                dp[j] = Math.max(dp[j], dp[j - w[i]] + v[i]);
        return dp[cap];
    }

    static int uniquePaths(int m, int n) {
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        for (int i = 1; i < m; i++)
            for (int j = 1; j < n; j++) dp[j] += dp[j - 1];
        return dp[n - 1];
    }

    static int numDecodings(String s) {
        if (s.isEmpty() || s.charAt(0) == '0') return 0;
        int prev2 = 1, prev1 = 1;
        for (int i = 1; i < s.length(); i++) {
            int cur = 0;
            if (s.charAt(i) != '0') cur = prev1;
            int two = Integer.parseInt(s.substring(i - 1, i + 1));
            if (two >= 10 && two <= 26) cur += prev2;
            prev2 = prev1; prev1 = cur;
        }
        return prev1;
    }

    public static void main(String[] args) {
        System.out.println("Q6  coinChange    = " + coinChange(new int[]{1,2,5}, 11));
        System.out.println("Q7  change        = " + change(5, new int[]{1,2,5}));
        System.out.println("Q8  knapsack01    = " + knapsack01(new int[]{1,3,4,5}, new int[]{10,40,50,20}, 8));
        System.out.println("Q9  uniquePaths   = " + uniquePaths(3, 7));
        System.out.println("Q10 decodeWays    = " + numDecodings("226"));
    }
}
