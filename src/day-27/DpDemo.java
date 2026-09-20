/**
 * Day 27 — DP fundamentals demo.
 *
 * Compile: javac src/day-27/DpDemo.java
 * Run    : java -cp src/day-27 DpDemo
 */
import java.util.*;

public class DpDemo {

    static long fibMemo(int n, long[] memo) {
        if (n < 2) return n;
        if (memo[n] != 0) return memo[n];
        return memo[n] = fibMemo(n - 1, memo) + fibMemo(n - 2, memo);
    }

    static long fibIter(int n) {
        if (n < 2) return n;
        long a = 0, b = 1;
        for (int i = 2; i <= n; i++) { long c = a + b; a = b; b = c; }
        return b;
    }

    static int climbStairs(int n) {
        if (n <= 2) return n;
        int a = 1, b = 2;
        for (int i = 3; i <= n; i++) { int c = a + b; a = b; b = c; }
        return b;
    }

    static int rob(int[] a) {
        if (a.length == 0) return 0;
        int prev = 0, cur = 0;
        for (int x : a) { int t = Math.max(cur, prev + x); prev = cur; cur = t; }
        return cur;
    }

    static int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;
        for (int i = 1; i <= amount; i++)
            for (int c : coins) if (c <= i) dp[i] = Math.min(dp[i], dp[i - c] + 1);
        return dp[amount] > amount ? -1 : dp[amount];
    }

    static int change(int amount, int[] coins) {
        int[] dp = new int[amount + 1]; dp[0] = 1;
        for (int c : coins)
            for (int i = c; i <= amount; i++) dp[i] += dp[i - c];
        return dp[amount];
    }

    public static void main(String[] args) {
        System.out.println("fibMemo(40)     = " + fibMemo(40, new long[41]));
        System.out.println("fibIter(40)     = " + fibIter(40));
        System.out.println("climbStairs(10) = " + climbStairs(10));
        System.out.println("rob             = " + rob(new int[]{2,7,9,3,1}));
        System.out.println("coinChange      = " + coinChange(new int[]{1,2,5}, 11));
        System.out.println("change          = " + change(5, new int[]{1,2,5}));
    }
}
