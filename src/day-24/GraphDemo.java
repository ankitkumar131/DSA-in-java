/**
 * Day 24 — Graph fundamentals demo.
 *
 * Compile: javac src/day-24/GraphDemo.java
 * Run    : java -cp src/day-24 GraphDemo
 */
import java.util.*;

public class GraphDemo {

    static class Graph {
        int n;
        List<List<Integer>> adj;
        Graph(int n) { this.n = n; this.adj = new ArrayList<>(); for (int i = 0; i < n; i++) adj.add(new ArrayList<>()); }
        void addEdge(int u, int v) { adj.get(u).add(v); adj.get(v).add(u); }
        void addDirected(int u, int v) { adj.get(u).add(v); }
    }

    static List<Integer> bfs(Graph g, int start) {
        List<Integer> order = new ArrayList<>();
        boolean[] vis = new boolean[g.n];
        Deque<Integer> q = new ArrayDeque<>();
        q.offer(start); vis[start] = true;
        while (!q.isEmpty()) {
            int u = q.poll(); order.add(u);
            for (int v : g.adj.get(u)) if (!vis[v]) { vis[v] = true; q.offer(v); }
        }
        return order;
    }

    static List<Integer> dfs(Graph g, int start) {
        List<Integer> out = new ArrayList<>();
        boolean[] vis = new boolean[g.n];
        dfsRec(g, start, vis, out);
        return out;
    }
    static void dfsRec(Graph g, int u, boolean[] vis, List<Integer> out) {
        vis[u] = true; out.add(u);
        for (int v : g.adj.get(u)) if (!vis[v]) dfsRec(g, v, vis, out);
    }

    static List<Integer> dfsIter(Graph g, int start) {
        List<Integer> out = new ArrayList<>();
        boolean[] vis = new boolean[g.n];
        Deque<Integer> st = new ArrayDeque<>();
        st.push(start);
        while (!st.isEmpty()) {
            int u = st.pop();
            if (vis[u]) continue;
            vis[u] = true; out.add(u);
            for (int v : g.adj.get(u)) if (!vis[v]) st.push(v);
        }
        return out;
    }

    static int components(Graph g) {
        boolean[] vis = new boolean[g.n];
        int count = 0;
        for (int i = 0; i < g.n; i++) if (!vis[i]) { compBfs(g, i, vis); count++; }
        return count;
    }
    static void compBfs(Graph g, int start, boolean[] vis) {
        Deque<Integer> q = new ArrayDeque<>(); q.offer(start); vis[start] = true;
        while (!q.isEmpty()) { int u = q.poll(); for (int v : g.adj.get(u)) if (!vis[v]) { vis[v] = true; q.offer(v); } }
    }

    static class GNode { int val; List<GNode> nbrs = new ArrayList<>(); GNode(int v) { val = v; } }
    static GNode clone(GNode n) {
        if (n == null) return null;
        Map<GNode, GNode> m = new HashMap<>();
        Deque<GNode> q = new ArrayDeque<>(); q.offer(n); m.put(n, new GNode(n.val));
        while (!q.isEmpty()) {
            GNode cur = q.poll();
            for (GNode nb : cur.nbrs) {
                if (!m.containsKey(nb)) { m.put(nb, new GNode(nb.val)); q.offer(nb); }
                m.get(cur).nbrs.add(m.get(nb));
            }
        }
        return m.get(n);
    }

    public static void main(String[] args) {
        Graph g = new Graph(6);
        g.addEdge(0, 1); g.addEdge(0, 2);
        g.addEdge(1, 3); g.addEdge(2, 3);
        g.addEdge(3, 4); g.addEdge(4, 5);

        System.out.println("BFS = " + bfs(g, 0));
        System.out.println("DFS = " + dfs(g, 0));
        System.out.println("DFS iter = " + dfsIter(g, 0));
        System.out.println("Components = " + components(g));
    }
}
