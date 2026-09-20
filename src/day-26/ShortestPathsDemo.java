/**
 * Day 26 — Shortest paths & advanced graphs demo.
 *
 * Compile: javac src/day-26/ShortestPathsDemo.java
 * Run    : java -cp src/day-26 ShortestPathsDemo
 */
import java.util.*;

public class ShortestPathsDemo {

    static class Edge { int to; int w; Edge(int t, int w) { to = t; this.w = w; } }

    static long[] dijkstra(List<List<Edge>> g, int src) {
        int n = g.size();
        long[] dist = new long[n];
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[src] = 0;
        PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> Long.compare(a[0], b[0]));
        pq.offer(new long[]{0, src});
        while (!pq.isEmpty()) {
            long[] cur = pq.poll();
            long d = cur[0]; int u = (int) cur[1];
            if (d > dist[u]) continue;
            for (Edge e : g.get(u))
                if (dist[u] + e.w < dist[e.to]) {
                    dist[e.to] = dist[u] + e.w;
                    pq.offer(new long[]{dist[e.to], e.to});
                }
        }
        return dist;
    }

    static long[] bellmanFord(int n, int[][] edges, int src) {
        long[] dist = new long[n];
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[src] = 0;
        for (int i = 0; i < n - 1; i++)
            for (int[] e : edges)
                if (dist[e[0]] != Long.MAX_VALUE && dist[e[0]] + e[2] < dist[e[1]])
                    dist[e[1]] = dist[e[0]] + e[2];
        return dist;
    }

    static long[][] floyd(int n, int[][] edges) {
        long[][] d = new long[n][n];
        for (long[] row : d) Arrays.fill(row, Long.MAX_VALUE / 4);
        for (int i = 0; i < n; i++) d[i][i] = 0;
        for (int[] e : edges) d[e[0]][e[1]] = e[2];
        for (int k = 0; k < n; k++)
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    if (d[i][k] + d[k][j] < d[i][j]) d[i][j] = d[i][k] + d[k][j];
        return d;
    }

    static List<Integer> topo(int n, List<List<Integer>> adj) {
        int[] indeg = new int[n];
        for (int u = 0; u < n; u++) for (int v : adj.get(u)) indeg[v]++;
        Deque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < n; i++) if (indeg[i] == 0) q.offer(i);
        List<Integer> out = new ArrayList<>();
        while (!q.isEmpty()) {
            int u = q.poll(); out.add(u);
            for (int v : adj.get(u)) if (--indeg[v] == 0) q.offer(v);
        }
        return out.size() == n ? out : new ArrayList<>();
    }

    static class DSU {
        int[] parent, rank;
        DSU(int n) { parent = new int[n]; rank = new int[n]; for (int i = 0; i < n; i++) parent[i] = i; }
        int find(int x) { while (parent[x] != x) { parent[x] = parent[parent[x]]; x = parent[x]; } return x; }
        boolean union(int a, int b) {
            int ra = find(a), rb = find(b);
            if (ra == rb) return false;
            if (rank[ra] < rank[rb]) { parent[ra] = rb; }
            else if (rank[ra] > rank[rb]) { parent[rb] = ra; }
            else { parent[rb] = ra; rank[ra]++; }
            return true;
        }
    }

    static int kruskal(int n, int[][] edges) {
        Arrays.sort(edges, (a, b) -> Integer.compare(a[2], b[2]));
        DSU d = new DSU(n);
        int total = 0, used = 0;
        for (int[] e : edges) {
            if (d.union(e[0], e[1])) { total += e[2]; if (++used == n - 1) break; }
        }
        return total;
    }

    static int prim(List<List<Edge>> g, int n) {
        boolean[] inMST = new boolean[n];
        PriorityQueue<Edge> pq = new PriorityQueue<>((a, b) -> Integer.compare(a.w, b.w));
        pq.offer(new Edge(0, 0));
        int total = 0, edges = 0;
        while (!pq.isEmpty() && edges < n) {
            Edge e = pq.poll();
            if (inMST[e.to]) continue;
            inMST[e.to] = true; total += e.w; edges++;
            for (Edge nb : g.get(e.to)) if (!inMST[nb.to]) pq.offer(nb);
        }
        return total;
    }

    public static void main(String[] args) {
        int n = 4;
        List<List<Edge>> g = new ArrayList<>();
        for (int i = 0; i < n; i++) g.add(new ArrayList<>());
        g.get(0).add(new Edge(1, 1)); g.get(0).add(new Edge(2, 4));
        g.get(1).add(new Edge(2, 2)); g.get(1).add(new Edge(3, 5));
        g.get(2).add(new Edge(3, 1));

        System.out.println("dijkstra   = " + Arrays.toString(dijkstra(g, 0)));
        System.out.println("bellman    = " + Arrays.toString(bellmanFord(n, new int[][]{{0,1,1},{0,2,4},{1,2,2},{1,3,5},{2,3,1}}, 0)));

        long[][] fw = floyd(n, new int[][]{{0,1,1},{0,2,4},{1,2,2},{1,3,5},{2,3,1}});
        System.out.println("floyd[0][3] = " + fw[0][3]);

        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < 4; i++) adj.add(new ArrayList<>());
        adj.get(0).add(1); adj.get(0).add(2); adj.get(1).add(3); adj.get(2).add(3);
        System.out.println("topo        = " + topo(4, adj));

        System.out.println("kruskal     = " + kruskal(n, new int[][]{{0,1,1},{0,2,4},{1,2,2},{1,3,5},{2,3,1}}));
        System.out.println("prim        = " + prim(g, n));
    }
}
