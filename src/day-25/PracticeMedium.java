/**
 * Day 25 — Medium practice.
 *
 * Compile: javac src/day-25/PracticeMedium.java
 * Run    : java -cp src/day-25 PracticeMedium
 */
import java.util.*;

public class PracticeMedium {

    static int orangesRotting(int[][] g) {
        Deque<int[]> q = new ArrayDeque<>(); int fresh = 0;
        for (int i = 0; i < g.length; i++) for (int j = 0; j < g[0].length; j++) {
            if (g[i][j] == 2) q.offer(new int[]{i, j});
            if (g[i][j] == 1) fresh++;
        }
        int mins = 0; int[][] d = {{-1,0},{1,0},{0,-1},{0,1}};
        while (fresh > 0 && !q.isEmpty()) {
            mins++;
            for (int sz = q.size(); sz > 0; sz--) {
                int[] p = q.poll();
                for (int[] dd : d) {
                    int ni = p[0] + dd[0], nj = p[1] + dd[1];
                    if (ni >= 0 && nj >= 0 && ni < g.length && nj < g[0].length && g[ni][nj] == 1) {
                        g[ni][nj] = 2; fresh--; q.offer(new int[]{ni, nj});
                    }
                }
            }
        }
        return fresh == 0 ? mins : -1;
    }

    static void wallsAndGates(int[][] rooms) {
        Deque<int[]> q = new ArrayDeque<>();
        for (int i = 0; i < rooms.length; i++) for (int j = 0; j < rooms[0].length; j++) if (rooms[i][j] == 0) q.offer(new int[]{i, j});
        int[][] d = {{-1,0},{1,0},{0,-1},{0,1}};
        while (!q.isEmpty()) {
            int[] p = q.poll();
            for (int[] dd : d) {
                int ni = p[0] + dd[0], nj = p[1] + dd[1];
                if (ni >= 0 && nj >= 0 && ni < rooms.length && nj < rooms[0].length && rooms[ni][nj] == Integer.MAX_VALUE) {
                    rooms[ni][nj] = rooms[p[0]][p[1]] + 1; q.offer(new int[]{ni, nj});
                }
            }
        }
    }

    static class Graph { int n; List<List<Integer>> adj; Graph(int n) { this.n = n; adj = new ArrayList<>(); for (int i = 0; i < n; i++) adj.add(new ArrayList<>()); } void addEdge(int u, int v) { adj.get(u).add(v); adj.get(v).add(u); } }

    static boolean undirectedCycle(Graph g) {
        boolean[] v = new boolean[g.n];
        for (int i = 0; i < g.n; i++) if (!v[i] && ucDfs(g, i, -1, v)) return true;
        return false;
    }
    static boolean ucDfs(Graph g, int u, int parent, boolean[] v) {
        v[u] = true;
        for (int x : g.adj.get(u)) { if (!v[x]) { if (ucDfs(g, x, u, v)) return true; } else if (x != parent) return true; }
        return false;
    }

    static boolean directedCycle(Graph g) {
        int[] state = new int[g.n];
        for (int i = 0; i < g.n; i++) if (state[i] == 0 && dcDfs(g, i, state)) return true;
        return false;
    }
    static boolean dcDfs(Graph g, int u, int[] state) {
        state[u] = 1;
        for (int v : g.adj.get(u)) {
            if (state[v] == 1) return true;
            if (state[v] == 0 && dcDfs(g, v, state)) return true;
        }
        state[u] = 2;
        return false;
    }

    static int[][] updateMatrix(int[][] mat) {
        int m = mat.length, n = mat[0].length;
        int[][] out = new int[m][n];
        for (int[] row : out) Arrays.fill(row, -1);
        Deque<int[]> q = new ArrayDeque<>();
        for (int i = 0; i < m; i++) for (int j = 0; j < n; j++) if (mat[i][j] == 0) { q.offer(new int[]{i, j}); out[i][j] = 0; }
        int[][] d = {{-1,0},{1,0},{0,-1},{0,1}};
        while (!q.isEmpty()) {
            int[] p = q.poll();
            for (int[] dd : d) {
                int ni = p[0] + dd[0], nj = p[1] + dd[1];
                if (ni >= 0 && nj >= 0 && ni < m && nj < n && out[ni][nj] == -1) {
                    out[ni][nj] = out[p[0]][p[1]] + 1; q.offer(new int[]{ni, nj});
                }
            }
        }
        return out;
    }

    public static void main(String[] args) {
        int[][] g = {{2,1,1},{1,1,0},{0,1,1}};
        System.out.println("Q6 rotting    = " + orangesRotting(g));

        int INF = Integer.MAX_VALUE;
        int[][] rooms = {{INF,-1,0,INF},{INF,INF,INF,-1},{INF,-1,INF,-1},{0,-1,INF,INF}};
        wallsAndGates(rooms);
        System.out.println("Q7 walls      = " + Arrays.deepToString(rooms));

        Graph ug = new Graph(3); ug.addEdge(0, 1); ug.addEdge(1, 2); ug.addEdge(2, 0);
        System.out.println("Q8 undirectedCycle = " + undirectedCycle(ug));

        Graph dg = new Graph(4); dg.adj.get(0).add(1); dg.adj.get(1).add(2); dg.adj.get(2).add(0);
        System.out.println("Q9 directedCycle   = " + directedCycle(dg));

        int[][] mat = {{0,0,0},{0,1,0},{1,1,1}};
        System.out.println("Q10 01matrix = " + Arrays.deepToString(updateMatrix(mat)));
    }
}
