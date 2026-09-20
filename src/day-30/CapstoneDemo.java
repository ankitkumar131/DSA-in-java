/**
 * Day 30 — Final revision capstone demo.
 * Each method is a tiny implementation of one of the canonical patterns.
 *
 * Compile: javac src/day-30/CapstoneDemo.java
 * Run    : java -cp src/day-30 CapstoneDemo
 */
import java.util.*;

public class CapstoneDemo {

    // Binary search
    static int binarySearch(int[] a, int t) {
        int lo = 0, hi = a.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (a[mid] == t) return mid;
            if (a[mid] < t) lo = mid + 1; else hi = mid - 1;
        }
        return -1;
    }

    // Two sum (sorted)
    static int[] twoSumSorted(int[] a, int t) {
        int lo = 0, hi = a.length - 1;
        while (lo < hi) {
            int s = a[lo] + a[hi];
            if (s == t) return new int[]{lo, hi};
            if (s < t) lo++; else hi--;
        }
        return new int[]{-1, -1};
    }

    // Sliding window max
    static int[] maxSlidingWindow(int[] a, int k) {
        int[] out = new int[a.length - k + 1];
        Deque<Integer> dq = new ArrayDeque<>();
        for (int i = 0; i < a.length; i++) {
            while (!dq.isEmpty() && dq.peekFirst() <= i - k) dq.pollFirst();
            while (!dq.isEmpty() && a[dq.peekLast()] <= a[i]) dq.pollLast();
            dq.offerLast(i);
            if (i >= k - 1) out[i - k + 1] = a[dq.peekFirst()];
        }
        return out;
    }

    // Linked list reverse
    static class Node { int data; Node next; Node(int d) { data = d; } }
    static Node reverse(Node h) {
        Node prev = null, cur = h;
        while (cur != null) { Node nxt = cur.next; cur.next = prev; prev = cur; cur = nxt; }
        return prev;
    }

    // BFS
    static List<Integer> bfs(List<List<Integer>> g, int start) {
        List<Integer> out = new ArrayList<>();
        boolean[] v = new boolean[g.size()];
        Deque<Integer> q = new ArrayDeque<>(); q.offer(start); v[start] = true;
        while (!q.isEmpty()) {
            int u = q.poll(); out.add(u);
            for (int x : g.get(u)) if (!v[x]) { v[x] = true; q.offer(x); }
        }
        return out;
    }

    // Dijkstra
    static class Edge { int to; long w; Edge(int t, long w) { to = t; this.w = w; } }
    static long[] dijkstra(List<List<Edge>> g, int src) {
        int n = g.size();
        long[] dist = new long[n]; Arrays.fill(dist, Long.MAX_VALUE); dist[src] = 0;
        PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> Long.compare(a[0], b[0]));
        pq.offer(new long[]{0, src});
        while (!pq.isEmpty()) {
            long[] cur = pq.poll();
            if (cur[0] > dist[(int) cur[1]]) continue;
            for (Edge e : g.get((int) cur[1]))
                if (cur[0] + e.w < dist[e.to]) { dist[e.to] = cur[0] + e.w; pq.offer(new long[]{dist[e.to], e.to}); }
        }
        return dist;
    }

    // Knapsack 0/1
    static int knapsack01(int[] w, int[] v, int cap) {
        int[] dp = new int[cap + 1];
        for (int i = 0; i < w.length; i++)
            for (int j = cap; j >= w[i]; j--)
                dp[j] = Math.max(dp[j], dp[j - w[i]] + v[i]);
        return dp[cap];
    }

    // Balanced parentheses
    static boolean balanced(String s) {
        Deque<Character> st = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            if ("({[".indexOf(c) >= 0) st.push(c);
            else if (st.isEmpty()) return false;
            else { char o = st.pop(); if (c == ')' && o != '(' || c == '}' && o != '{' || c == ']' && o != '[') return false; }
        }
        return st.isEmpty();
    }

    // LRU Cache
    static class LRU<K, V> {
        class Node { K key; V val; Node prev, next; Node(K k, V v) { key=k; val=v; } }
        final int cap; final Map<K, Node> map = new HashMap<>();
        final Node head = new Node(null, null), tail = new Node(null, null);
        LRU(int c) { cap = c; head.next = tail; tail.prev = head; }
        V get(K k) { Node n = map.get(k); if (n == null) return null; moveToHead(n); return n.val; }
        void put(K k, V v) {
            Node n = map.get(k);
            if (n != null) { n.val = v; moveToHead(n); return; }
            n = new Node(k, v); map.put(k, n); addToHead(n);
            if (map.size() > cap) { Node lru = tail.prev; remove(lru); map.remove(lru.key); }
        }
        void addToHead(Node n) { n.next = head.next; n.prev = head; head.next.prev = n; head.next = n; }
        void remove(Node n) { n.prev.next = n.next; n.next.prev = n.prev; }
        void moveToHead(Node n) { remove(n); addToHead(n); }
    }

    public static void main(String[] args) {
        System.out.println("binarySearch         = " + binarySearch(new int[]{1,3,5,7,9}, 5));
        System.out.println("twoSumSorted         = " + Arrays.toString(twoSumSorted(new int[]{2,7,11,15}, 9)));
        System.out.println("maxSlidingWindow     = " + Arrays.toString(maxSlidingWindow(new int[]{1,3,-1,-3,5,3,6,7}, 3)));

        Node h = new Node(1); h.next = new Node(2); h.next.next = new Node(3);
        Node rev = reverse(h);
        StringBuilder sb = new StringBuilder("reverse              = ");
        for (Node c = rev; c != null; c = c.next) sb.append(c.data).append(" ");
        System.out.println(sb);

        List<List<Integer>> g = new ArrayList<>();
        for (int i = 0; i < 4; i++) g.add(new ArrayList<>());
        g.get(0).addAll(Arrays.asList(1, 2)); g.get(1).add(3); g.get(2).add(3);
        System.out.println("bfs                  = " + bfs(g, 0));

        List<List<Edge>> wg = new ArrayList<>();
        for (int i = 0; i < 3; i++) wg.add(new ArrayList<>());
        wg.get(0).add(new Edge(1, 1)); wg.get(0).add(new Edge(2, 4)); wg.get(1).add(new Edge(2, 2));
        System.out.println("dijkstra             = " + Arrays.toString(dijkstra(wg, 0)));

        System.out.println("knapsack01           = " + knapsack01(new int[]{1,3,4,5}, new int[]{10,40,50,20}, 8));
        System.out.println("balanced             = " + balanced("()[]{}"));

        LRU<Integer, String> lru = new LRU<>(2);
        lru.put(1, "a"); lru.put(2, "b");
        System.out.println("LRU.get(1)           = " + lru.get(1));
        lru.put(3, "c");
        System.out.println("LRU.get(2) [evicted] = " + lru.get(2));
    }
}
