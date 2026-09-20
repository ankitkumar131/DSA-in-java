/**
 * Day 28 — Easy practice.
 *
 * Compile: javac src/day-28/PracticeEasy.java
 * Run    : java -cp src/day-28 PracticeEasy
 */
import java.util.*;

public class PracticeEasy {

    static int knapsack01(int[] w, int[] v, int cap) {
        int[] dp = new int[cap + 1];
        for (int i = 0; i < w.length; i++)
            for (int j = cap; j >= w[i]; j--)
                dp[j] = Math.max(dp[j], dp[j - w[i]] + v[i]);
        return dp[cap];
    }

    static boolean subsetSum(int[] a, int target) {
        boolean[] dp = new boolean[target + 1]; dp[0] = true;
        for (int x : a) for (int j = target; j >= x; j--) dp[j] |= dp[j - x];
        return dp[target];
    }

    static long fibMemo(int n, long[] memo) {
        if (n < 2) return n;
        if (memo[n] != 0) return memo[n];
        return memo[n] = fibMemo(n - 1, memo) + fibMemo(n - 2, memo);
    }

    static int climbStairs(int n) {
        if (n <= 2) return n;
        int a = 1, b = 2;
        for (int i = 3; i <= n; i++) { int c = a + b; a = b; b = c; }
        return b;
    }

    static int minCost(int[] cost) {
        int a = 0, b = 0;
        for (int i = 0; i < cost.length; i++) { int c = Math.min(a, b) + cost[i]; a = b; b = c; }
        return Math.min(a, b);
    }

    public static void main(String[] args) {
        System.out.println("Q1 knapsack01   = " + knapsack01(new int[]{1,3,4,5}, new int[]{10,40,50,20}, 8));
        System.out.println("Q2 subset sum   = " + subsetSum(new int[]{3,34,4,12,5,2}, 9));
        System.out.println("Q3 fib memo     = " + fibMemo(10, new long[11]));
        System.out.println("Q4 climbStairs  = " + climbStairs(10));
        System.out.println("Q5 minCost      = " + minCost(new int[]{10,15,20}));
    }
}
