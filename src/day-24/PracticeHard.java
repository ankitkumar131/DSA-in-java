/**
 * Day 24 — Hard practice.
 *
 * Compile: javac src/day-24/PracticeHard.java
 * Run    : java -cp src/day-24 PracticeHard
 */
import java.util.*;

public class PracticeHard {

    // Q11: Word Ladder
    static int ladderLength(String begin, String end, List<String> list) {
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

    // Q12: Kosaraju SCC
    static int scc(List<List<Integer>> adj) {
        int n = adj.size();
        boolean[] vis = new boolean[n];
        Deque<Integer> order = new ArrayDeque<>();
        for (int i = 0; i < n; i++) if (!vis[i]) dfsOrder(adj, i, vis, order);
        List<List<Integer>> rev = new ArrayList<>();
        for (int i = 0; i < n; i++) rev.add(new ArrayList<>());
        for (int u = 0; u < n; u++) for (int v : adj.get(u)) rev.get(v).add(u);
        Arrays.fill(vis, false);
        int scc = 0;
        while (!order.isEmpty()) {
            int u = order.pop();
            if (!vis[u]) { dfsScc(rev, u, vis); scc++; }
        }
        return scc;
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

    // Q13: Critical connections (Tarjan-style low-link)
    static List<List<Integer>> criticalConnections(int n, List<List<Integer>> conn) {
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (List<Integer> e : conn) { adj.get(e.get(0)).add(e.get(1)); adj.get(e.get(1)).add(e.get(0)); }
        List<List<Integer>> out = new ArrayList<>();
        int[] disc = new int[n], low = new int[n];
        Arrays.fill(disc, -1);
        int[] time = {0};
        for (int i = 0; i < n; i++) if (disc[i] == -1) tarjan(adj, i, -1, disc, low, time, out);
        return out;
    }
    static void tarjan(List<List<Integer>> adj, int u, int parent, int[] disc, int[] low, int[] time, List<List<Integer>> out) {
        disc[u] = low[u] = time[0]++;
        for (int v : adj.get(u)) {
            if (v == parent) continue;
            if (disc[v] == -1) {
                tarjan(adj, v, u, disc, low, time, out);
                low[u] = Math.min(low[u], low[v]);
                if (low[v] > disc[u]) out.add(Arrays.asList(u, v));
            } else low[u] = Math.min(low[u], disc[v]);
        }
    }

    // Q14: Alien dictionary
    static String alienOrder(String[] words) {
        Map<Character, Set<Character>> adj = new HashMap<>();
        Map<Character, Integer> indeg = new HashMap<>();
        for (String w : words) for (char c : w.toCharArray()) { adj.putIfAbsent(c, new HashSet<>()); indeg.putIfAbsent(c, 0); }
        for (int i = 0; i < words.length - 1; i++) {
            String a = words[i], b = words[i + 1];
            int len = Math.min(a.length(), b.length());
            for (int j = 0; j < len; j++) {
                if (a.charAt(j) != b.charAt(j)) {
                    if (adj.get(a.charAt(j)).add(b.charAt(j))) indeg.merge(b.charAt(j), 1, Integer::sum);
                    break;
                }
            }
        }
        Deque<Character> q = new ArrayDeque<>();
        for (var e : indeg.entrySet()) if (e.getValue() == 0) q.offer(e.getKey());
        StringBuilder sb = new StringBuilder();
        while (!q.isEmpty()) {
            char c = q.poll(); sb.append(c);
            for (char nb : adj.get(c)) {
                indeg.merge(nb, -1, Integer::sum);
                if (indeg.get(nb) == 0) q.offer(nb);
            }
        }
        return sb.length() == indeg.size() ? sb.toString() : "";
    }

    // Q15: Reconstruct itinerary (Eulerian path)
    static List<String> findItinerary(List<List<String>> tickets) {
        Map<String, Deque<String>> adj = new HashMap<>();
        for (List<String> t : tickets) adj.computeIfAbsent(t.get(0), k -> new ArrayDeque<>()).add(t.get(1));
        for (var e : adj.entrySet()) Collections.sort(e.getValue());
        List<String> out = new LinkedList<>();
        dfs(adj, "JFK", out);
        return out;
    }
    static void dfs(Map<String, Deque<String>> adj, String from, List<String> out) {
        Deque<String> q = adj.get(from);
        while (q != null && !q.isEmpty()) dfs(adj, q.pollFirst(), out);
        out.add(0, from);
    }

    public static void main(String[] args) {
        System.out.println("Q11 ladder     = " + ladderLength("hit", "cog", List.of("hot","dot","dog","lot","log","cog")));

        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < 5; i++) adj.add(new ArrayList<>());
        adj.get(0).add(2); adj.get(2).add(1); adj.get(1).add(0); adj.get(3).add(4);
        System.out.println("Q12 SCC        = " + scc(adj));

        System.out.println("Q13 critical   = " + criticalConnections(4, List.of(List.of(0,1),List.of(1,2),List.of(2,0),List.of(1,3))));

        System.out.println("Q14 alienOrder = " + alienOrder(new String[]{"wrt","wrf","er","ett","rftt"}));

        System.out.println("Q15 itinerary  = " + findItinerary(List.of(List.of("MUC","LHR"), List.of("JFK","MUC"), List.of("SFO","SJC"), List.of("LHR","SFO"))));
    }
}
