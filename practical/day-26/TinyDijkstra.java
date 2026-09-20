/** Day 26 tiny project: Dijkstra on a 4-city map. */
import java.util.*;
public class TinyDijkstra {
    static class E { int to; long w; E(int t, long w) { to = t; this.w = w; } }
    public static void main(String[] args) {
        List<List<E>> g = new ArrayList<>();
        for (int i = 0; i < 4; i++) g.add(new ArrayList<>());
        g.get(0).add(new E(1, 4)); g.get(0).add(new E(2, 1));
        g.get(1).add(new E(3, 1)); g.get(2).add(new E(1, 2)); g.get(2).add(new E(3, 5));
        long INF = Long.MAX_VALUE / 4;
        long[] d = {0, INF, INF, INF};
        PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> Long.compare(a[0], b[0]));
        pq.offer(new long[]{0, 0});
        while (!pq.isEmpty()) {
            long[] cur = pq.poll();
            if (cur[0] > d[(int) cur[1]]) continue;
            for (E e : g.get((int) cur[1]))
                if (cur[0] + e.w < d[e.to]) { d[e.to] = cur[0] + e.w; pq.offer(new long[]{d[e.to], e.to}); }
        }
        System.out.println("distances from 0 = " + Arrays.toString(d));
    }
}
