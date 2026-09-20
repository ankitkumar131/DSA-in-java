/** Day 27 tiny project: climb-stairs memoised + tabulated. */
import java.util.*;
public class ClimbStairs {
    static int memo(int n, int[] dp) {
        if (n <= 2) return n;
        if (dp[n] != 0) return dp[n];
        return dp[n] = memo(n - 1, dp) + memo(n - 2, dp);
    }
    static int tab(int n) {
        if (n <= 2) return n;
        int[] dp = new int[n + 1];
        dp[1] = 1; dp[2] = 2;
        for (int i = 3; i <= n; i++) dp[i] = dp[i - 1] + dp[i - 2];
        return dp[n];
    }
    public static void main(String[] args) {
        int n = 10;
        System.out.println("memo n=" + n + " = " + memo(n, new int[n + 1]));
        System.out.println("tab  n=" + n + " = " + tab(n));
    }
}
