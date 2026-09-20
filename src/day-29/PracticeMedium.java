/**
 * Day 29 — Medium practice.
 *
 * Compile: javac src/day-29/PracticeMedium.java
 * Run    : java -cp src/day-29 PracticeMedium
 */
import java.util.*;

public class PracticeMedium {

    static int uniquePathsWithObstacles(int[][] g) {
        int m = g.length, n = g[0].length;
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++) {
                if (g[i][j] == 1) { dp[i][j] = 0; continue; }
                if (i == 0 && j == 0) { dp[i][j] = 1; continue; }
                dp[i][j] = (i > 0 ? dp[i - 1][j] : 0) + (j > 0 ? dp[i][j - 1] : 0);
            }
        return dp[m - 1][n - 1];
    }

    static int minPathSum(int[][] grid) {
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

    static class Node { int val; Node left, right; Node(int v) { val = v; } }
    static int[] rob3(Node root) {
        if (root == null) return new int[]{0, 0};
        int[] l = rob3(root.left), r = rob3(root.right);
        return new int[]{Math.max(l[0], l[1]) + Math.max(r[0], r[1]), root.val + l[0] + r[0]};
    }

    static int[][] dirs = {{-1,0},{1,0},{0,-1},{0,1}};
    static int longestIncreasingPath(int[][] g) {
        int m = g.length, n = g[0].length;
        int[][] memo = new int[m][n];
        int best = 0;
        for (int i = 0; i < m; i++) for (int j = 0; j < n; j++) best = Math.max(best, dfsLIP(g, i, j, memo));
        return best;
    }
    static int dfsLIP(int[][] g, int i, int j, int[][] memo) {
        if (memo[i][j] != 0) return memo[i][j];
        int best = 1;
        for (int[] d : dirs) {
            int ni = i + d[0], nj = j + d[1];
            if (ni >= 0 && nj >= 0 && ni < g.length && nj < g[0].length && g[ni][nj] > g[i][j])
                best = Math.max(best, 1 + dfsLIP(g, ni, nj, memo));
        }
        memo[i][j] = best;
        return best;
    }

    static int change(int amount, int[] coins) {
        int[] dp = new int[amount + 1]; dp[0] = 1;
        for (int c : coins) for (int i = c; i <= amount; i++) dp[i] += dp[i - c];
        return dp[amount];
    }

    public static void main(String[] args) {
        int[][] obs = {{0,0,0},{0,1,0},{0,0,0}};
        System.out.println("Q6 paths w/ obs  = " + uniquePathsWithObstacles(obs));

        int[][] g = {{1,3,1},{1,5,1},{4,2,1}};
        System.out.println("Q7 minPathSum    = " + minPathSum(g));

        Node a = new Node(3), b = new Node(4), c = new Node(5), d = new Node(1), e = new Node(3), f = new Node(1);
        a.left = b; a.right = c; b.left = d; b.right = e; c.right = f;
        System.out.println("Q8 rob3          = " + Math.max(rob3(a)[0], rob3(a)[1]));

        int[][] m = {{9,9,4},{6,6,8},{2,1,1}};
        System.out.println("Q9 longestIncPath= " + longestIncreasingPath(m));

        System.out.println("Q10 change        = " + change(5, new int[]{1,2,5}));
    }
}
