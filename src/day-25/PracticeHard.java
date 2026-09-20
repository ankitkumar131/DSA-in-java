/**
 * Day 25 — Hard practice.
 *
 * Compile: javac src/day-25/PracticeHard.java
 * Run    : java -cp src/day-25 PracticeHard
 */
import java.util.*;

public class PracticeHard {

    static class Graph { int n; List<List<Integer>> adj; Graph(int n) { this.n = n; adj = new ArrayList<>(); for (int i = 0; i < n; i++) adj.add(new ArrayList<>()); } void addEdge(int u, int v) { adj.get(u).add(v); adj.get(v).add(u); } }

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

    static int shortestPathWithTurns(int[][] grid) {
        // 4-direction, can turn freely; track (i, j, dir)
        int m = grid.length, n = grid[0].length;
        if (grid[0][0] == 1 || grid[m - 1][n - 1] == 1) return -1;
        Deque<int[]> q = new ArrayDeque<>(); q.offer(new int[]{0, 0, -1, 0});
        boolean[][][] seen = new boolean[m][n][4];
        int[][] d = {{-1,0},{1,0},{0,-1},{0,1}};
        while (!q.isEmpty()) {
            int[] p = q.poll();
            int i = p[0], j = p[1], dir = p[2], steps = p[3];
            if (i == m - 1 && j == n - 1) return steps;
            for (int k = 0; k < 4; k++) {
                if (dir != -1 && Math.abs(k - dir) == 2) continue; // no 180-turns
                int ni = i + d[k][0], nj = j + d[k][1];
                if (ni >= 0 && nj >= 0 && ni < m && nj < n && grid[ni][nj] == 0 && !seen[ni][nj][k]) {
                    seen[ni][nj][k] = true;
                    q.offer(new int[]{ni, nj, k, steps + 1});
                }
            }
        }
        return -1;
    }

    static int wordLadder(String begin, String end, List<String> list) {
        Set<String> dict = new HashSet<>(list);
        if (!dict.contains(end)) return 0;
        Deque<String> q = new ArrayDeque<>(); Set<String> vis = new HashSet<>();
        q.offer(begin); vis.add(begin);
        int lvl = 1;
        while (!q.isEmpty()) {
            for (int sz = q.size(); sz > 0; sz--) {
                String w = q.poll(); if (w.equals(end)) return lvl;
                char[] c = w.toCharArray();
                for (int i = 0; i < c.length; i++) {
                    char orig = c[i];
                    for (char ch = 'a'; ch <= 'z'; ch++) {
                        if (ch == orig) continue;
                        c[i] = ch;
                        String nw = new String(c);
                        if (dict.contains(nw) && !vis.contains(nw)) { vis.add(nw); q.offer(nw); }
                    }
                    c[i] = orig;
                }
            }
            lvl++;
        }
        return 0;
    }

    static int reachableNodes(int[][] edges, int M, int n) {
        Map<Integer, Map<Integer, Integer>> adj = new HashMap<>();
        for (int[] e : edges) {
            adj.computeIfAbsent(e[0], k -> new HashMap<>()).put(e[1], e[2]);
            adj.computeIfAbsent(e[1], k -> new HashMap<>()).put(e[0], e[2]);
        }
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> b[1] - a[1]);
        pq.offer(new int[]{0, M});
        Set<Integer> seen = new HashSet<>();
        int count = 0;
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int u = cur[0], moves = cur[1];
            if (seen.contains(u)) continue;
            seen.add(u); count++;
            for (var e : adj.getOrDefault(u, java.util.Collections.emptyMap()).entrySet()) {
                int v = e.getKey(), w = e.getValue();
                if (!seen.contains(v) && moves > w) pq.offer(new int[]{v, moves - w - 1});
                if (moves > 0) count += Math.min(w, moves);
            }
        }
        return count;
    }

    static int numBusesToDestination(int[][] routes, int src, int dst) {
        if (src == dst) return 0;
        Map<Integer, List<Integer>> stopToBuses = new HashMap<>();
        for (int bus = 0; bus < routes.length; bus++)
            for (int stop : routes[bus]) stopToBuses.computeIfAbsent(stop, k -> new ArrayList<>()).add(bus);
        Deque<Integer> q = new ArrayDeque<>(); Set<Integer> visitedBuses = new HashSet<>();
        q.offer(src); int depth = 0;
        Set<Integer> visitedStops = new HashSet<>(); visitedStops.add(src);
        while (!q.isEmpty()) {
            depth++;
            for (int sz = q.size(); sz > 0; sz--) {
                int s = q.poll();
                for (int bus : stopToBuses.getOrDefault(s, java.util.Collections.emptyList())) {
                    if (!visitedBuses.add(bus)) continue;
                    for (int stop : routes[bus]) {
                        if (stop == dst) return depth;
                        if (visitedStops.add(stop)) q.offer(stop);
                    }
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        Graph g = new Graph(4);
        g.addEdge(0, 1); g.addEdge(1, 2); g.addEdge(2, 3); g.addEdge(3, 0);
        System.out.println("Q11 bipartite     = " + isBipartite(g));

        int[][] grid = {{0,0,0},{0,1,0},{0,0,0}};
        System.out.println("Q12 shortestGrid  = " + shortestPathWithTurns(grid));

        System.out.println("Q13 wordLadder    = " + wordLadder("hit", "cog", List.of("hot","dot","dog","lot","log","cog")));

        System.out.println("Q14 reachable     = " + reachableNodes(new int[][]{{0,1,10},{0,2,1},{1,2,2},{1,3,1},{2,3,3}}, 4, 4));

        System.out.println("Q15 numBuses      = " + numBusesToDestination(new int[][]{{1,2,7},{3,6,7}}, 1, 6));
    }
}
