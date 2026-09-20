/** Day 28 tiny project: min coins (unbounded knapsack DP). */
import java.util.*;
public class CoinChange {
    public static void main(String[] args) {
        int[] coins = {1, 5, 10, 25};
        int amount = 41;
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1); dp[0] = 0;
        for (int a = 1; a <= amount; a++)
            for (int c : coins) if (c <= a) dp[a] = Math.min(dp[a], dp[a - c] + 1);
        System.out.println("min coins for " + amount + " = " + dp[amount]);
    }
}
