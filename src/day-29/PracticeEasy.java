/**
 * Day 29 — Easy practice.
 *
 * Compile: javac src/day-29/PracticeEasy.java
 * Run    : java -cp src/day-29 PracticeEasy
 */
public class PracticeEasy {

    static int climbStairs(int n) {
        if (n <= 2) return n;
        int a = 1, b = 2;
        for (int i = 3; i <= n; i++) { int c = a + b; a = b; b = c; }
        return b;
    }

    static long fibMemo(int n, long[] memo) {
        if (n < 2) return n;
        if (memo[n] != 0) return memo[n];
        return memo[n] = fibMemo(n - 1, memo) + fibMemo(n - 2, memo);
    }

    static int minCost(int[] cost) {
        int a = 0, b = 0;
        for (int i = 0; i < cost.length; i++) { int c = Math.min(a, b) + cost[i]; a = b; b = c; }
        return Math.min(a, b);
    }

    static int rob(int[] a) {
        int prev = 0, cur = 0;
        for (int x : a) { int t = Math.max(cur, prev + x); prev = cur; cur = t; }
        return cur;
    }

    static int uniquePaths(int m, int n) {
        int[] dp = new int[n]; java.util.Arrays.fill(dp, 1);
        for (int i = 1; i < m; i++)
            for (int j = 1; j < n; j++) dp[j] += dp[j - 1];
        return dp[n - 1];
    }

    public static void main(String[] args) {
        System.out.println("Q1 climbStairs  = " + climbStairs(10));
        System.out.println("Q2 fibMemo      = " + fibMemo(10, new long[11]));
        System.out.println("Q3 minCost      = " + minCost(new int[]{10,15,20}));
        System.out.println("Q4 rob          = " + rob(new int[]{2,7,9,3,1}));
        System.out.println("Q5 uniquePaths  = " + uniquePaths(3, 7));
    }
}
