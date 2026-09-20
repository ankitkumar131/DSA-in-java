/**
 * Day 29 — Advanced DP demo.
 *
 * Compile: javac src/day-29/AdvancedDpDemo.java
 * Run    : java -cp src/day-29 AdvancedDpDemo
 */
import java.util.*;

public class AdvancedDpDemo {

    static class Node { int val; Node left, right; Node(int v) { val = v; } }

    static int maxCoins(int[] nums) {
        int n = nums.length;
        int[] a = new int[n + 2]; a[0] = a[n + 1] = 1;
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

    static int[] rob3(Node root) {
        if (root == null) return new int[]{0, 0};
        int[] l = rob3(root.left), r = rob3(root.right);
        return new int[]{
            Math.max(l[0], l[1]) + Math.max(r[0], r[1]),
            root.val + l[0] + r[0]
        };
    }

    static int tsp(int[][] dist) {
        int n = dist.length;
        int INF = Integer.MAX_VALUE / 4;
        int[][] dp = new int[n][1 << n];
        for (int[] row : dp) Arrays.fill(row, INF);
        dp[0][1] = 0;
        for (int mask = 1; mask < (1 << n); mask++)
            for (int u = 0; u < n; u++) {
                if ((mask & (1 << u)) == 0) continue;
                if (dp[u][mask] == INF) continue;
                for (int v = 0; v < n; v++) {
                    if ((mask & (1 << v)) != 0) continue;
                    if (dist[u][v] == INF) continue;
                    int nmask = mask | (1 << v);
                    dp[v][nmask] = Math.min(dp[v][nmask], dp[u][mask] + dist[u][v]);
                }
            }
        int best = INF;
        for (int u = 0; u < n; u++) if (dp[u][(1 << n) - 1] + dist[u][0] < best) best = dp[u][(1 << n) - 1] + dist[u][0];
        return best;
    }

    static int uniquePathsWithObstacles(int[][] g) {
        int m = g.length, n = g[0].length;
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++) {
                if (g[i][j] == 1) { dp[i][j] = 0; continue; }
                if (i == 0 && j == 0) { dp[i][j] = 1; continue; }
                int top = i > 0 ? dp[i - 1][j] : 0;
                int left = j > 0 ? dp[i][j - 1] : 0;
                dp[i][j] = top + left;
            }
        return dp[m - 1][n - 1];
    }

    static int minPathSumObstacles(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] dp = new int[m][n];
        dp[0][0] = grid[0][0];
        for (int i = 1; i < m; i++) dp[i][0] = dp[i - 1][0] + grid[i][0];
        for (int j = 1; j < n; j++) dp[0][j] = dp[0][j - 1] + grid[0][j];
        for (int i = 1; i < m; i++)
            for (int j = 1; j < n; j++)
                dp[i][j] = grid[i][j] + Math.min(dp[i - 1][j], dp[i][j - 1]);
        return dp[m - 1][n - 1];
    }

    public static void main(String[] args) {
        System.out.println("burst balloons = " + maxCoins(new int[]{3,1,5,8}));

        Node a = new Node(3), b = new Node(4), c = new Node(5), d = new Node(1), e = new Node(3), f = new Node(1);
        a.left = b; a.right = c; b.left = d; b.right = e; c.right = f;
        System.out.println("rob3           = " + Math.max(rob3(a)[0], rob3(a)[1]));

        int INF = Integer.MAX_VALUE / 4;
        int[][] dist = {{0,10,15,20},{10,0,35,25},{15,35,0,30},{20,25,30,0}};
        System.out.println("tsp            = " + tsp(dist));

        int[][] obs = {{0,0,0},{0,1,0},{0,0,0}};
        System.out.println("paths w/ obs   = " + uniquePathsWithObstacles(obs));

        int[][] grid = {{1,3,1},{1,5,1},{4,2,1}};
        System.out.println("min path       = " + minPathSumObstacles(grid));
    }
}
