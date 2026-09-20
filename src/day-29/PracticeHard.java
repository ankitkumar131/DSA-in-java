/**
 * Day 29 — Hard practice.
 *
 * Compile: javac src/day-29/PracticeHard.java
 * Run    : java -cp src/day-29 PracticeHard
 */
import java.util.*;

public class PracticeHard {

    // Q11: Burst Balloons
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

    // Q12: Matrix Chain Multiplication
    static int mcm(int[] dims) {
        int n = dims.length - 1;
        int[][] dp = new int[n][n];
        for (int len = 2; len <= n; len++)
            for (int i = 0; i + len <= n; i++) {
                int j = i + len - 1;
                dp[i][j] = Integer.MAX_VALUE;
                for (int k = i; k < j; k++)
                    dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k + 1][j] + dims[i] * dims[k + 1] * dims[j + 1]);
            }
        return dp[0][n - 1];
    }

    // Q13: bitmask TSP
    static int tsp(int[][] dist) {
        int n = dist.length;
        int INF = Integer.MAX_VALUE / 4;
        int[][] dp = new int[n][1 << n];
        for (int[] row : dp) Arrays.fill(row, INF);
        dp[0][1] = 0;
        for (int mask = 1; mask < (1 << n); mask++)
            for (int u = 0; u < n; u++) {
                if ((mask & (1 << u)) == 0 || dp[u][mask] == INF) continue;
                for (int v = 0; v < n; v++) {
                    if ((mask & (1 << v)) != 0) continue;
                    int nmask = mask | (1 << v);
                    dp[v][nmask] = Math.min(dp[v][nmask], dp[u][mask] + dist[u][v]);
                }
            }
        int best = INF;
        for (int u = 0; u < n; u++) best = Math.min(best, dp[u][(1 << n) - 1] + dist[u][0]);
        return best;
    }

    // Q14: shortest path visiting all nodes
    static int shortestPathAllKeys(String[] grid) {
        int m = grid.length, n = grid[0].length();
        int totalKeys = 0, startR = 0, startC = 0;
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++) {
                char c = grid[i].charAt(j);
                if (c == '@') { startR = i; startC = j; }
                else if (c >= 'a' && c <= 'f') totalKeys++;
            }
        int[][] dirs = {{-1,0},{1,0},{0,-1},{0,1}};
        boolean[][][] vis = new boolean[m][n][1 << totalKeys];
        Deque<int[]> q = new ArrayDeque<>(); q.offer(new int[]{startR, startC, 0}); vis[startR][startC][0] = true;
        int steps = 0;
        while (!q.isEmpty()) {
            for (int sz = q.size(); sz > 0; sz--) {
                int[] cur = q.poll();
                int r = cur[0], c = cur[1], keys = cur[2];
                if (keys == (1 << totalKeys) - 1) return steps;
                for (int[] d : dirs) {
                    int nr = r + d[0], nc = c + d[1], nk = keys;
                    if (nr < 0 || nc < 0 || nr >= m || nc >= n) continue;
                    char ch = grid[nr].charAt(nc);
                    if (ch == '#') continue;
                    if (ch >= 'A' && ch <= 'F' && (keys & (1 << (ch - 'A'))) == 0) continue;
                    if (ch >= 'a' && ch <= 'f') nk |= 1 << (ch - 'a');
                    if (!vis[nr][nc][nk]) { vis[nr][nc][nk] = true; q.offer(new int[]{nr, nc, nk}); }
                }
            }
            steps++;
        }
        return -1;
    }

    // Q15: count vowels permutation via matrix exponentiation
    static final int MOD = 1_000_000_007;
    static int vowelPermutation(int n) {
        long[][] a = {
            {0, 1, 1, 0, 1},
            {1, 0, 1, 0, 0},
            {0, 1, 0, 1, 0},
            {0, 0, 1, 0, 0},
            {0, 0, 1, 1, 0}
        };
        long[][] res = matPow(a, n - 1);
        long sum = 0;
        for (int j = 0; j < 5; j++) sum = (sum + res[0][j]) % MOD;
        return (int) sum;
    }
    static long[][] matPow(long[][] m, int p) {
        int n = m.length;
        long[][] r = new long[n][n];
        for (int i = 0; i < n; i++) r[i][i] = 1;
        while (p > 0) {
            if ((p & 1) == 1) r = mul(r, m);
            m = mul(m, m); p >>= 1;
        }
        return r;
    }
    static long[][] mul(long[][] a, long[][] b) {
        int n = a.length;
        long[][] r = new long[n][n];
        for (int i = 0; i < n; i++) for (int k = 0; k < n; k++) if (a[i][k] != 0)
            for (int j = 0; j < n; j++) r[i][j] = (r[i][j] + a[i][k] * b[k][j]) % MOD;
        return r;
    }

    public static void main(String[] args) {
        System.out.println("Q11 burst balloons = " + maxCoins(new int[]{3,1,5,8}));
        System.out.println("Q12 MCM            = " + mcm(new int[]{1,2,3,4}));
        System.out.println("Q13 tsp            = " + tsp(new int[][]{{0,10,15,20},{10,0,35,25},{15,35,0,30},{20,25,30,0}}));
        System.out.println("Q14 shortestKeys   = " + shortestPathAllKeys(new String[]{"@.a.#","###.#","b.A.B"}));
        System.out.println("Q15 vowelPerm      = " + vowelPermutation(5));
    }
}
