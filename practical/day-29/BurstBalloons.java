/** Day 29 tiny project: interval-DP — burst balloons. */
import java.util.*;
public class BurstBalloons {
    public static void main(String[] args) {
        int[] a = {3, 1, 5, 8};
        int n = a.length + 2;
        int[] v = new int[n]; v[0] = v[n - 1] = 1;
        for (int i = 0; i < a.length; i++) v[i + 1] = a[i];
        int[][] dp = new int[n][n];
        for (int len = 3; len <= n; len++)
            for (int l = 0, r = l + len - 1; r < n; l++, r--)
                for (int k = l + 1; k < r; k++)
                    dp[l][r] = Math.max(dp[l][r], dp[l][k] + dp[k][r] + v[l] * v[k] * v[r]);
        System.out.println("max coins = " + dp[0][n - 1]);
    }
}
