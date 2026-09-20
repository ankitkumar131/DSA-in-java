/**
 * Day 26 — Medium practice.
 *
 * Compile: javac src/day-26/PracticeMedium.java
 * Run    : java -cp src/day-26 PracticeMedium
 */
import java.util.*;

public class PracticeMedium {

    static class Edge { int to; int w; Edge(int t, int w) { to = t; this.w = w; } }

    // Q6: Dijkstra
    static long[] dijkstra(List<List<Edge>> g, int src) {
        int n = g.size();
        long[] dist = new long[n];
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[src] = 0;
        PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> Long.compare(a[0], b[0]));
        pq.offer(new long[]{0, src});
        while (!pq.isEmpty()) {
            long[] c = pq.poll(); long d = c[0]; int u = (int) c[1];
            if (d > dist[u]) continue;
            for (Edge e : g.get(u))
                if (dist[u] + e.w < dist[e.to]) { dist[e.to] = dist[u] + e.w; pq.offer(new long[]{dist[e.to], e.to}); }
        }
        return dist;
    }

    // Q7: Bellman-Ford
    static long[] bellman(int n, int[][] edges, int src) {
        long[] d = new long[n]; Arrays.fill(d, Long.MAX_VALUE); d[src] = 0;
        for (int i = 0; i < n - 1; i++)
            for (int[] e : edges) if (d[e[0]] != Long.MAX_VALUE && d[e[0]] + e[2] < d[e[1]]) d[e[1]] = d[e[0]] + e[2];
        return d;
    }

    // Q8: Floyd
    static long[][] floyd(int n, int[][] edges) {
        long[][] d = new long[n][n];
        for (long[] row : d) Arrays.fill(row, Long.MAX_VALUE / 4);
        for (int i = 0; i < n; i++) d[i][i] = 0;
        for (int[] e : edges) d[e[0]][e[1]] = e[2];
        for (int k = 0; k < n; k++)
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++) if (d[i][k] + d[k][j] < d[i][j]) d[i][j] = d[i][k] + d[k][j];
        return d;
    }

    // Q9: topological sort
    static List<Integer> topo(int n, List<List<Integer>> adj) {
        int[] indeg = new int[n];
        for (int u = 0; u < n; u++) for (int v : adj.get(u)) indeg[v]++;
        Deque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < n; i++) if (indeg[i] == 0) q.offer(i);
        List<Integer> out = new ArrayList<>();
        while (!q.isEmpty()) { int u = q.poll(); out.add(u); for (int v : adj.get(u)) if (--indeg[v] == 0) q.offer(v); }
        return out.size() == n ? out : new ArrayList<>();
    }

    // Q10: union-find
    static class DSU {
        int[] p, r;
        DSU(int n) { p = new int[n]; r = new int[n]; for (int i = 0; i < n; i++) p[i] = i; }
        int find(int x) { while (p[x] != x) { p[x] = p[p[x]]; x = p[x]; } return x; }
        boolean union(int a, int b) {
            int ra = find(a), rb = find(b); if (ra == rb) return false;
            if (r[ra] < r[rb]) p[ra] = rb; else if (r[ra] > r[rb]) p[rb] = ra; else { p[rb] = ra; r[ra]++; }
            return true;
        }
    }

    public static void main(String[] args) {
        int n = 4;
        List<List<Edge>> g = new ArrayList<>();
        for (int i = 0; i < n; i++) g.add(new ArrayList<>());
        g.get(0).add(new Edge(1, 1)); g.get(0).add(new Edge(2, 4));
        g.get(1).add(new Edge(2, 2)); g.get(1).add(new Edge(3, 5));
        g.get(2).add(new Edge(3, 1));
        System.out.println("Q6 dijkstra  = " + Arrays.toString(dijkstra(g, 0)));
        System.out.println("Q7 bellman   = " + Arrays.toString(bellman(n, new int[][]{{0,1,1},{0,2,4},{1,2,2},{1,3,5},{2,3,1}}, 0)));

        long[][] fw = floyd(n, new int[][]{{0,1,1},{0,2,4},{1,2,2},{1,3,5},{2,3,1}});
        System.out.println("Q8 floyd 0→3 = " + fw[0][3]);

        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < 4; i++) adj.add(new ArrayList<>());
        adj.get(0).add(1); adj.get(0).add(2); adj.get(1).add(3); adj.get(2).add(3);
        System.out.println("Q9 topo       = " + topo(4, adj));

        DSU d = new DSU(5); d.union(0, 1); d.union(1, 2);
        System.out.println("Q10 DSU 0=2? " + (d.find(0) == d.find(2)) + " 3=0? " + (d.find(3) == d.find(0)));
    }
}
