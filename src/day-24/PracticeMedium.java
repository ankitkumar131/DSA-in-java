/**
 * Day 24 — Medium practice.
 *
 * Compile: javac src/day-24/PracticeMedium.java
 * Run    : java -cp src/day-24 PracticeMedium
 */
import java.util.*;

public class PracticeMedium {

    // Q6: number of islands
    static int numIslands(char[][] g) {
        int c = 0;
        for (int i = 0; i < g.length; i++)
            for (int j = 0; j < g[0].length; j++)
                if (g[i][j] == '1') { dfs(g, i, j); c++; }
        return c;
    }
    static void dfs(char[][] g, int i, int j) {
        if (i < 0 || j < 0 || i >= g.length || j >= g[0].length || g[i][j] != '1') return;
        g[i][j] = '0';
        dfs(g, i + 1, j); dfs(g, i - 1, j); dfs(g, i, j + 1); dfs(g, i, j - 1);
    }

    // Q7: max area of island
    static int maxArea(int[][] g) {
        int best = 0;
        for (int i = 0; i < g.length; i++)
            for (int j = 0; j < g[0].length; j++)
                if (g[i][j] == 1) best = Math.max(best, area(g, i, j));
        return best;
    }
    static int area(int[][] g, int i, int j) {
        if (i < 0 || j < 0 || i >= g.length || j >= g[0].length || g[i][j] == 0) return 0;
        g[i][j] = 0;
        return 1 + area(g, i + 1, j) + area(g, i - 1, j) + area(g, i, j + 1) + area(g, i, j - 1);
    }

    // Q8: surrounded regions
    static void solve(char[][] b) {
        int m = b.length, n = b[0].length;
        for (int i = 0; i < m; i++) { dfs2(b, i, 0); dfs2(b, i, n - 1); }
        for (int j = 0; j < n; j++) { dfs2(b, 0, j); dfs2(b, m - 1, j); }
        for (int i = 0; i < m; i++) for (int j = 0; j < n; j++) b[i][j] = b[i][j] == 'O' ? 'X' : (b[i][j] == 'V' ? 'O' : b[i][j]);
    }
    static void dfs2(char[][] b, int i, int j) {
        if (i < 0 || j < 0 || i >= b.length || j >= b[0].length || b[i][j] != 'O') return;
        b[i][j] = 'V';
        dfs2(b, i + 1, j); dfs2(b, i - 1, j); dfs2(b, i, j + 1); dfs2(b, i, j - 1);
    }

    // Q9: course schedule
    static boolean canFinish(int n, int[][] prereq) {
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int[] p : prereq) adj.get(p[1]).add(p[0]);
        int[] state = new int[n]; // 0 unvisited, 1 visiting, 2 done
        for (int i = 0; i < n; i++) if (hasCycle(adj, i, state)) return false;
        return true;
    }
    static boolean hasCycle(List<List<Integer>> adj, int u, int[] state) {
        if (state[u] == 1) return true;
        if (state[u] == 2) return false;
        state[u] = 1;
        for (int v : adj.get(u)) if (hasCycle(adj, v, state)) return true;
        state[u] = 2;
        return false;
    }

    // Q10: pacific atlantic water flow
    static List<List<Integer>> pacificAtlantic(int[][] h) {
        List<List<Integer>> out = new ArrayList<>();
        if (h.length == 0) return out;
        int m = h.length, n = h[0].length;
        boolean[][] pac = new boolean[m][n], atl = new boolean[m][n];
        for (int i = 0; i < m; i++) { dfs3(h, i, 0, pac, h[i][0]); dfs3(h, i, n - 1, atl, h[i][n - 1]); }
        for (int j = 0; j < n; j++) { dfs3(h, 0, j, pac, h[0][j]); dfs3(h, m - 1, j, atl, h[m - 1][j]); }
        for (int i = 0; i < m; i++) for (int j = 0; j < n; j++) if (pac[i][j] && atl[i][j]) out.add(Arrays.asList(i, j));
        return out;
    }
    static void dfs3(int[][] h, int i, int j, boolean[][] vis, int prev) {
        if (i < 0 || j < 0 || i >= h.length || j >= h[0].length || vis[i][j] || h[i][j] < prev) return;
        vis[i][j] = true;
        dfs3(h, i + 1, j, vis, h[i][j]); dfs3(h, i - 1, j, vis, h[i][j]);
        dfs3(h, i, j + 1, vis, h[i][j]); dfs3(h, i, j - 1, vis, h[i][j]);
    }

    public static void main(String[] args) {
        char[][] g = {{'1','1','0','0','0'},{'1','1','0','0','0'},{'0','0','1','0','0'},{'0','0','0','1','1'}};
        System.out.println("Q6 islands     = " + numIslands(g));

        int[][] a = {{0,0,1,0,0},{0,0,0,0,0},{0,0,0,0,1}};
        System.out.println("Q7 maxArea     = " + maxArea(a));

        char[][] b = {{'X','X','X','X'},{'X','O','O','X'},{'X','X','O','X'},{'X','O','X','X'}};
        solve(b);
        System.out.print("Q8 surrounded  = ");
        for (char[] row : b) System.out.println(new String(row));

        System.out.println("Q9 canFinish   = " + canFinish(2, new int[][]{{1,0}}));

        int[][] h = {{1,2,2,3,5},{3,2,3,4,4},{2,4,5,3,1},{6,7,1,4,5},{5,1,1,2,4}};
        System.out.println("Q10 pacAtl     = " + pacificAtlantic(h));
    }
}
