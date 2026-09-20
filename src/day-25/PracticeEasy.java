/**
 * Day 25 — Easy practice.
 *
 * Compile: javac src/day-25/PracticeEasy.java
 * Run    : java -cp src/day-25 PracticeEasy
 */
import java.util.*;

public class PracticeEasy {

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

    static boolean pathExists(int n, int[][] edges, int src, int dst) {
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int[] e : edges) { adj.get(e[0]).add(e[1]); adj.get(e[1]).add(e[0]); }
        boolean[] v = new boolean[n];
        Deque<Integer> q = new ArrayDeque<>(); q.offer(src); v[src] = true;
        while (!q.isEmpty()) { int u = q.poll(); if (u == dst) return true; for (int x : adj.get(u)) if (!v[x]) { v[x] = true; q.offer(x); } }
        return false;
    }

    static class NNode { int val; List<NNode> children = new ArrayList<>(); NNode(int v) { val = v; } }
    static int maxDepth(NNode root) {
        if (root == null) return 0;
        int d = 0; for (NNode c : root.children) d = Math.max(d, maxDepth(c));
        return 1 + d;
    }

    static boolean sameTree(int[] a, int[] b) { return java.util.Arrays.equals(a, b); }

    public static void main(String[] args) {
        int[][] img = {{1,1,1},{1,1,0},{1,0,1}};
        int[][] out = floodFill(img, 1, 1, 2);
        System.out.println("Q1 floodFill = " + Arrays.deepToString(out));

        char[][] g = {{'1','1','0','0','0'},{'1','1','0','0','0'},{'0','0','1','0','0'},{'0','0','0','1','1'}};
        System.out.println("Q2 islands   = " + numIslands(g));

        System.out.println("Q3 pathExists = " + pathExists(3, new int[][]{{0,1},{1,2},{2,0}}, 0, 2));

        NNode n1 = new NNode(1), n2 = new NNode(2), n3 = new NNode(3), n4 = new NNode(4), n5 = new NNode(5), n6 = new NNode(6);
        n1.children.add(n2); n1.children.add(n3); n1.children.add(n4); n3.children.add(n5); n3.children.add(n6);
        System.out.println("Q4 maxDepth   = " + maxDepth(n1));

        System.out.println("Q5 sameTree   = " + sameTree(new int[]{1,2,3}, new int[]{1,2,3}));
    }
}
