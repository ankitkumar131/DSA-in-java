/**
 * Day 24 — Easy practice.
 *
 * Compile: javac src/day-24/PracticeEasy.java
 * Run    : java -cp src/day-24 PracticeEasy
 */
import java.util.*;

public class PracticeEasy {

    static class Graph {
        int n; List<List<Integer>> adj;
        Graph(int n) { this.n = n; adj = new ArrayList<>(); for (int i = 0; i < n; i++) adj.add(new ArrayList<>()); }
        void addEdge(int u, int v) { adj.get(u).add(v); adj.get(v).add(u); }
    }

    static List<Integer> bfs(Graph g, int s) {
        List<Integer> out = new ArrayList<>();
        boolean[] v = new boolean[g.n];
        Deque<Integer> q = new ArrayDeque<>(); q.offer(s); v[s] = true;
        while (!q.isEmpty()) { int u = q.poll(); out.add(u); for (int x : g.adj.get(u)) if (!v[x]) { v[x] = true; q.offer(x); } }
        return out;
    }

    static List<Integer> dfs(Graph g, int s) {
        List<Integer> out = new ArrayList<>();
        boolean[] v = new boolean[g.n];
        dfsR(g, s, v, out); return out;
    }
    static void dfsR(Graph g, int u, boolean[] v, List<Integer> out) {
        v[u] = true; out.add(u);
        for (int x : g.adj.get(u)) if (!v[x]) dfsR(g, x, v, out);
    }

    static boolean pathExists(Graph g, int src, int dst) {
        boolean[] v = new boolean[g.n];
        Deque<Integer> q = new ArrayDeque<>(); q.offer(src); v[src] = true;
        while (!q.isEmpty()) {
            int u = q.poll(); if (u == dst) return true;
            for (int x : g.adj.get(u)) if (!v[x]) { v[x] = true; q.offer(x); }
        }
        return false;
    }

    static int components(Graph g) {
        boolean[] v = new boolean[g.n]; int c = 0;
        for (int i = 0; i < g.n; i++) if (!v[i]) { cb(g, i, v); c++; }
        return c;
    }
    static void cb(Graph g, int s, boolean[] v) {
        Deque<Integer> q = new ArrayDeque<>(); q.offer(s); v[s] = true;
        while (!q.isEmpty()) { int u = q.poll(); for (int x : g.adj.get(u)) if (!v[x]) { v[x] = true; q.offer(x); } }
    }

    static class GNode { int val; List<GNode> nbrs = new ArrayList<>(); GNode(int v) { val = v; } }
    static GNode clone(GNode n) {
        if (n == null) return null;
        Map<GNode, GNode> m = new HashMap<>();
        Deque<GNode> q = new ArrayDeque<>(); q.offer(n); m.put(n, new GNode(n.val));
        while (!q.isEmpty()) {
            GNode c = q.poll();
            for (GNode nb : c.nbrs) {
                if (!m.containsKey(nb)) { m.put(nb, new GNode(nb.val)); q.offer(nb); }
                m.get(c).nbrs.add(m.get(nb));
            }
        }
        return m.get(n);
    }

    public static void main(String[] args) {
        Graph g = new Graph(5);
        g.addEdge(0, 1); g.addEdge(1, 2); g.addEdge(3, 4);
        System.out.println("Q1 bfs      = " + bfs(g, 0));
        System.out.println("Q2 dfs      = " + dfs(g, 0));
        System.out.println("Q3 path 0→2 = " + pathExists(g, 0, 2));
        System.out.println("Q3 path 0→3 = " + pathExists(g, 0, 3));
        System.out.println("Q4 comps    = " + components(g));

        GNode n1 = new GNode(1), n2 = new GNode(2), n3 = new GNode(3);
        n1.nbrs.add(n2); n2.nbrs.add(n3); n3.nbrs.add(n1);
        GNode c = clone(n1);
        System.out.println("Q5 clone val = " + c.val + " nbrs = " + c.nbrs.size());
    }
}
