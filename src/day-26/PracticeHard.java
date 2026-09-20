/**
 * Day 26 — Hard practice.
 *
 * Compile: javac src/day-26/PracticeHard.java
 * Run    : java -cp src/day-26 PracticeHard
 */
import java.util.*;

public class PracticeHard {

    static class Edge { int to; int w; Edge(int t, int w) { to = t; this.w = w; } }

    // Q11: MST
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
    static int mstKruskal(int n, int[][] edges) {
        Arrays.sort(edges, (a, b) -> Integer.compare(a[2], b[2]));
        DSU d = new DSU(n); int total = 0, used = 0;
        for (int[] e : edges) if (d.union(e[0], e[1])) { total += e[2]; if (++used == n - 1) break; }
        return total;
    }
    static int mstPrim(List<List<Edge>> g, int n) {
        boolean[] inMST = new boolean[n];
        PriorityQueue<Edge> pq = new PriorityQueue<>((a, b) -> Integer.compare(a.w, b.w));
        pq.offer(new Edge(0, 0));
        int total = 0, edges = 0;
        while (!pq.isEmpty() && edges < n) {
            Edge e = pq.poll(); if (inMST[e.to]) continue;
            inMST[e.to] = true; total += e.w; edges++;
            for (Edge nb : g.get(e.to)) if (!inMST[nb.to]) pq.offer(nb);
        }
        return total;
    }

    // Q12: Kosaraju SCC
    static int scc(List<List<Integer>> adj, int n) {
        boolean[] vis = new boolean[n];
        Deque<Integer> order = new ArrayDeque<>();
        for (int i = 0; i < n; i++) if (!vis[i]) dfsOrder(adj, i, vis, order);
        List<List<Integer>> rev = new ArrayList<>();
        for (int i = 0; i < n; i++) rev.add(new ArrayList<>());
        for (int u = 0; u < n; u++) for (int v : adj.get(u)) rev.get(v).add(u);
        Arrays.fill(vis, false);
        int count = 0;
        while (!order.isEmpty()) {
            int u = order.pop(); if (!vis[u]) { dfsScc(rev, u, vis); count++; }
        }
        return count;
    }
    static void dfsOrder(List<List<Integer>> adj, int u, boolean[] vis, Deque<Integer> order) {
        vis[u] = true;
        for (int v : adj.get(u)) if (!vis[v]) dfsOrder(adj, v, vis, order);
        order.push(u);
    }
    static void dfsScc(List<List<Integer>> rev, int u, boolean[] vis) {
        vis[u] = true;
        for (int v : rev.get(u)) if (!vis[v]) dfsScc(rev, v, vis);
    }

    // Q13: critical connections (Tarjan)
    static List<List<Integer>> critical(int n, List<List<Integer>> conn) {
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (List<Integer> e : conn) { adj.get(e.get(0)).add(e.get(1)); adj.get(e.get(1)).add(e.get(0)); }
        List<List<Integer>> out = new ArrayList<>();
        int[] disc = new int[n], low = new int[n]; Arrays.fill(disc, -1);
        int[] t = {0};
        for (int i = 0; i < n; i++) if (disc[i] == -1) tarjan(adj, i, -1, disc, low, t, out);
        return out;
    }
    static void tarjan(List<List<Integer>> adj, int u, int p, int[] disc, int[] low, int[] t, List<List<Integer>> out) {
        disc[u] = low[u] = t[0]++;
        for (int v : adj.get(u)) {
            if (v == p) continue;
            if (disc[v] == -1) {
                tarjan(adj, v, u, disc, low, t, out);
                low[u] = Math.min(low[u], low[v]);
                if (low[v] > disc[u]) out.add(Arrays.asList(u, v));
            } else low[u] = Math.min(low[u], disc[v]);
        }
    }

    // Q14: shortest path with alternating colours
    static int[] shortestAlternating(int n, int[][] redEdges, int[][] blueEdges) {
        Map<Integer, List<Integer>> red = new HashMap<>(), blue = new HashMap<>();
        for (int[] e : redEdges) red.computeIfAbsent(e[0], k -> new ArrayList<>()).add(e[1]);
        for (int[] e : blueEdges) blue.computeIfAbsent(e[0], k -> new ArrayList<>()).add(e[1]);
        int[][] dist = new int[n][2];
        for (int[] row : dist) Arrays.fill(row, Integer.MAX_VALUE);
        dist[0][0] = dist[0][1] = 0;
        Deque<int[]> q = new ArrayDeque<>(); q.offer(new int[]{0, 0}); q.offer(new int[]{0, 1});
        while (!q.isEmpty()) {
            int[] cur = q.poll(); int u = cur[0], col = cur[1];
            Map<Integer, List<Integer>> next = col == 0 ? blue : red;
            for (int v : next.getOrDefault(u, java.util.Collections.emptyList())) {
                if (dist[v][1 - col] == Integer.MAX_VALUE) {
                    dist[v][1 - col] = dist[u][col] + 1;
                    q.offer(new int[]{v, 1 - col});
                }
            }
        }
        int[] res = new int[n]; Arrays.fill(res, -1);
        for (int i = 0; i < n; i++) res[i] = Math.min(dist[i][0], dist[i][1]);
        return res;
    }

    // Q15: swim in rising water (binary search + BFS)
    static int swimInWater(int[][] grid) {
        int n = grid.length;
        int lo = grid[0][0], hi = n * n - 1;
        int[][] d = {{-1,0},{1,0},{0,-1},{0,1}};
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (can(grid, mid, d)) hi = mid; else lo = mid + 1;
        }
        return lo;
    }
    static boolean can(int[][] g, int t, int[][] d) {
        if (g[0][0] > t) return false;
        boolean[][] v = new boolean[g.length][g[0].length];
        Deque<int[]> q = new ArrayDeque<>(); q.offer(new int[]{0, 0}); v[0][0] = true;
        while (!q.isEmpty()) {
            int[] p = q.poll();
            if (p[0] == g.length - 1 && p[1] == g[0].length - 1) return true;
            for (int[] dd : d) {
                int ni = p[0] + dd[0], nj = p[1] + dd[1];
                if (ni >= 0 && nj >= 0 && ni < g.length && nj < g[0].length && !v[ni][nj] && g[ni][nj] <= t) {
                    v[ni][nj] = true; q.offer(new int[]{ni, nj});
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println("Q11 mstKruskal = " + mstKruskal(4, new int[][]{{0,1,1},{0,2,4},{1,2,2},{1,3,5},{2,3,1}}));

        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < 5; i++) adj.add(new ArrayList<>());
        adj.get(0).add(2); adj.get(2).add(1); adj.get(1).add(0); adj.get(3).add(4);
        System.out.println("Q12 scc        = " + scc(adj, 5));

        System.out.println("Q13 critical   = " + critical(4, List.of(List.of(0,1),List.of(1,2),List.of(2,0),List.of(1,3))));

        System.out.println("Q14 altPath    = " + Arrays.toString(shortestAlternating(3, new int[][]{{0,1},{1,2}}, new int[][]{})));

        int[][] g = {{0,2},{1,3}};
        System.out.println("Q15 swim       = " + swimInWater(g));
    }
}
