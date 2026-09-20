/**
 * Day 25 — BFS/DFS deep demo.
 *
 * Compile: javac src/day-25/BfsDfsDeepDemo.java
 * Run    : java -cp src/day-25 BfsDfsDeepDemo
 */
import java.util.*;

public class BfsDfsDeepDemo {

    static class Graph {
        int n; List<List<Integer>> adj;
        Graph(int n) { this.n = n; this.adj = new ArrayList<>(); for (int i = 0; i < n; i++) adj.add(new ArrayList<>()); }
        void addEdge(int u, int v) { adj.get(u).add(v); adj.get(v).add(u); }
        void addDir(int u, int v) { adj.get(u).add(v); }
    }

    static boolean undirectedCycle(Graph g) {
        boolean[] vis = new boolean[g.n];
        for (int i = 0; i < g.n; i++) if (!vis[i] && ucDfs(g, i, -1, vis)) return true;
        return false;
    }
    static boolean ucDfs(Graph g, int u, int parent, boolean[] vis) {
        vis[u] = true;
        for (int v : g.adj.get(u)) {
            if (!vis[v]) { if (ucDfs(g, v, u, vis)) return true; }
            else if (v != parent) return true;
        }
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

    static boolean isBipartite(Graph g) {
        int[] c = new int[g.n];
        Arrays.fill(c, -1);
        for (int s = 0; s < g.n; s++) {
            if (c[s] != -1) continue;
            c[s] = 0;
            Deque<Integer> q = new ArrayDeque<>(); q.offer(s);
            while (!q.isEmpty()) {
                int u = q.poll();
                for (int v : g.adj.get(u)) {
                    if (c[v] == -1) { c[v] = 1 - c[u]; q.offer(v); }
                    else if (c[v] == c[u]) return false;
                }
            }
        }
        return true;
    }

    static int numIslands(char[][] g) {
        int c = 0;
        for (int i = 0; i < g.length; i++) for (int j = 0; j < g[0].length; j++)
            if (g[i][j] == '1') { flood(g, i, j); c++; }
        return c;
    }
    static void flood(char[][] g, int i, int j) {
        if (i < 0 || j < 0 || i >= g.length || j >= g[0].length || g[i][j] != '1') return;
        g[i][j] = '0';
        flood(g, i + 1, j); flood(g, i - 1, j); flood(g, i, j + 1); flood(g, i, j - 1);
    }

    static int[][] floodFill(int[][] image, int sr, int sc, int color) {
        int orig = image[sr][sc]; if (orig == color) return image;
        ff(image, sr, sc, orig, color); return image;
    }
    static void ff(int[][] g, int i, int j, int orig, int color) {
        if (i < 0 || j < 0 || i >= g.length || j >= g[0].length || g[i][j] != orig) return;
        g[i][j] = color;
        ff(g, i + 1, j, orig, color); ff(g, i - 1, j, orig, color);
        ff(g, i, j + 1, orig, color); ff(g, i, j - 1, orig, color);
    }

    static int shortestGridPath(int[][] g, int[] src, int[] dst) {
        int m = g.length, n = g[0].length;
        boolean[][] vis = new boolean[m][n];
        Deque<int[]> q = new ArrayDeque<>(); q.offer(src); vis[src[0]][src[1]] = true;
        int steps = 0; int[][] d = {{-1,0},{1,0},{0,-1},{0,1}};
        while (!q.isEmpty()) {
            for (int sz = q.size(); sz > 0; sz--) {
                int[] p = q.poll();
                if (p[0] == dst[0] && p[1] == dst[1]) return steps;
                for (int[] dd : d) {
                    int ni = p[0] + dd[0], nj = p[1] + dd[1];
                    if (ni >= 0 && nj >= 0 && ni < m && nj < n && !vis[ni][nj] && g[ni][nj] == 0) {
                        vis[ni][nj] = true; q.offer(new int[]{ni, nj});
                    }
                }
            }
            steps++;
        }
        return -1;
    }

    public static void main(String[] args) {
        Graph ug = new Graph(5);
        ug.addEdge(0, 1); ug.addEdge(1, 2); ug.addEdge(2, 0);
        System.out.println("undirected cycle = " + undirectedCycle(ug));

        Graph dg = new Graph(4);
        dg.addDir(0, 1); dg.addDir(1, 2); dg.addDir(2, 0);
        System.out.println("directed cycle   = " + directedCycle(dg));

        Graph bg = new Graph(4);
        bg.addEdge(0, 1); bg.addEdge(1, 2); bg.addEdge(2, 3); bg.addEdge(3, 0);
        System.out.println("bipartite        = " + isBipartite(bg));

        char[][] g = {{'1','1','0','0','0'},{'1','1','0','0','0'},{'0','0','1','0','0'},{'0','0','0','1','1'}};
        System.out.println("islands          = " + numIslands(g));

        int[][] img = {{1,1,1},{1,1,0},{1,0,1}};
        int[][] filled = floodFill(img, 1, 1, 2);
        System.out.print("floodFill        = ");
        for (int[] row : filled) System.out.print(Arrays.toString(row) + " ");
        System.out.println();

        int[][] grid = {{0,0,0},{0,1,0},{0,0,0}};
        System.out.println("shortest path    = " + shortestGridPath(grid, new int[]{0,0}, new int[]{2,2}));
    }
}
