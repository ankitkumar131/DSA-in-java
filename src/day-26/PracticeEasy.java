/**
 * Day 26 — Easy practice.
 *
 * Compile: javac src/day-26/PracticeEasy.java
 * Run    : java -cp src/day-26 PracticeEasy
 */
import java.util.*;

public class PracticeEasy {

    static class Edge { int to; int w; Edge(int t, int w) { to = t; this.w = w; } }

    // Q1: network delay time (Dijkstra)
    static int networkDelayTime(int[][] times, int n, int k) {
        List<List<Edge>> g = new ArrayList<>();
        for (int i = 0; i <= n; i++) g.add(new ArrayList<>());
        for (int[] t : times) g.get(t[0]).add(new Edge(t[1], t[2]));
        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE); dist[k] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));
        pq.offer(new int[]{k, 0});
        while (!pq.isEmpty()) {
            int[] cur = pq.poll(); int u = cur[0], d = cur[1];
            if (d > dist[u]) continue;
            for (Edge e : g.get(u)) if (d + e.w < dist[e.to]) { dist[e.to] = d + e.w; pq.offer(new int[]{e.to, dist[e.to]}); }
        }
        int max = 0; for (int i = 1; i <= n; i++) max = Math.max(max, dist[i]);
        return max == Integer.MAX_VALUE ? -1 : max;
    }

    // Q2: cheapest flights within k stops (Bellman-Ford with stop limit)
    static int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        long[] dist = new long[n]; Arrays.fill(dist, Long.MAX_VALUE);
        dist[src] = 0;
        for (int i = 0; i <= k; i++) {
            long[] next = dist.clone();
            for (int[] f : flights)
                if (dist[f[0]] != Long.MAX_VALUE && dist[f[0]] + f[2] < next[f[1]]) next[f[1]] = dist[f[0]] + f[2];
            dist = next;
        }
        return dist[dst] == Long.MAX_VALUE ? -1 : (int) dist[dst];
    }

    // Q3: town judge
    static int findJudge(int n, int[][] trust) {
        int[] indeg = new int[n + 1], outdeg = new int[n + 1];
        for (int[] t : trust) { outdeg[t[0]]++; indeg[t[1]]++; }
        for (int i = 1; i <= n; i++) if (indeg[i] == n - 1 && outdeg[i] == 0) return i;
        return -1;
    }

    // Q4: course schedule II
    static int[] findOrder(int n, int[][] prereq) {
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        int[] indeg = new int[n];
        for (int[] p : prereq) { adj.get(p[1]).add(p[0]); indeg[p[0]]++; }
        Deque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < n; i++) if (indeg[i] == 0) q.offer(i);
        int[] order = new int[n]; int idx = 0;
        while (!q.isEmpty()) {
            int u = q.poll(); order[idx++] = u;
            for (int v : adj.get(u)) if (--indeg[v] == 0) q.offer(v);
        }
        return idx == n ? order : new int[0];
    }

    // Q5: keys and rooms
    static boolean canVisitAllRooms(List<List<Integer>> rooms) {
        boolean[] vis = new boolean[rooms.size()];
        Deque<Integer> q = new ArrayDeque<>(); q.offer(0); vis[0] = true;
        while (!q.isEmpty()) {
            int u = q.poll();
            for (int k : rooms.get(u)) if (!vis[k]) { vis[k] = true; q.offer(k); }
        }
        for (boolean v : vis) if (!v) return false;
        return true;
    }

    public static void main(String[] args) {
        System.out.println("Q1 networkDelay = " + networkDelayTime(new int[][]{{2,1,1},{2,3,1},{3,4,1}}, 4, 2));
        System.out.println("Q2 cheapest     = " + findCheapestPrice(4, new int[][]{{0,1,1},{1,2,1},{2,3,1}}, 0, 3, 1));
        System.out.println("Q3 townJudge    = " + findJudge(3, new int[][]{{1,3},{2,3}}));
        System.out.println("Q4 findOrder    = " + Arrays.toString(findOrder(4, new int[][]{{1,0},{2,0},{3,1},{3,2}})));
        System.out.println("Q5 keysAndRooms = " + canVisitAllRooms(List.of(List.of(1), List.of(2), List.of(3), List.of())));
    }
}
