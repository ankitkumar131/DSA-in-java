/** Day 24 tiny project: BFS shortest path in a tiny social graph. */
import java.util.*;
public class Friends {
    public static void main(String[] args) {
        // 0=Alice 1=Bob 2=Carol 3=Dan 4=Eve
        List<List<Integer>> g = new ArrayList<>();
        for (int i = 0; i < 5; i++) g.add(new ArrayList<>());
        g.get(0).addAll(Arrays.asList(1, 2));
        g.get(1).addAll(Arrays.asList(0, 3));
        g.get(2).addAll(Arrays.asList(0, 3));
        g.get(3).addAll(Arrays.asList(1, 2, 4));

        int src = 0, dst = 4;
        int[] dist = new int[5]; Arrays.fill(dist, -1);
        Deque<Integer> q = new ArrayDeque<>();
        q.offer(src); dist[src] = 0;
        while (!q.isEmpty()) {
            int u = q.poll();
            for (int v : g.get(u)) if (dist[v] == -1) { dist[v] = dist[u] + 1; q.offer(v); }
        }
        System.out.println("Alice -> Eve distance = " + dist[dst]);
    }
}
