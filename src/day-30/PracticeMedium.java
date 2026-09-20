import java.util.*;

/**
 * Day 30 — Medium capstone solutions.
 * Group Anagrams / Course Schedule / Coin Change / LRU Cache.
 */
public class PracticeMedium {

    // Group Anagrams
    public static List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for (String s : strs) {
            char[] c = s.toCharArray();
            Arrays.sort(c);
            String key = new String(c);
            map.computeIfAbsent(key, k -> new ArrayList<>()).add(s);
        }
        return new ArrayList<>(map.values());
    }

    // Course Schedule — DFS detect cycle
    public static boolean canFinish(int numCourses, int[][] prerequisites) {
        List<List<Integer>> g = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) g.add(new ArrayList<>());
        for (int[] p : prerequisites) g.get(p[1]).add(p[0]);

        int[] state = new int[numCourses]; // 0=white 1=gray 2=black
        for (int i = 0; i < numCourses; i++)
            if (!dfs(i, g, state)) return false;
        return true;
    }
    private static boolean dfs(int u, List<List<Integer>> g, int[] state) {
        if (state[u] == 1) return false;
        if (state[u] == 2) return true;
        state[u] = 1;
        for (int v : g.get(u))
            if (!dfs(v, g, state)) return false;
        state[u] = 2;
        return true;
    }

    // Coin Change — bottom-up DP
    public static int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;
        for (int a = 1; a <= amount; a++)
            for (int c : coins)
                if (c <= a) dp[a] = Math.min(dp[a], dp[a - c] + 1);
        return dp[amount] > amount ? -1 : dp[amount];
    }

    // LRU Cache
    static class LRU {
        class Node { int key, val; Node prev, next; Node(int k, int v) { key=k; val=v; } }
        final int cap; final Map<Integer, Node> m = new HashMap<>();
        final Node head = new Node(0,0), tail = new Node(0,0);
        LRU(int c) { cap=c; head.next=tail; tail.prev=head; }
        int get(int k) {
            if (!m.containsKey(k)) return -1;
            Node n = m.get(k); remove(n); add(n); return n.val;
        }
        void put(int k, int v) {
            if (m.containsKey(k)) { Node n = m.get(k); n.val=v; remove(n); add(n); return; }
            Node n = new Node(k,v); m.put(k,n); add(n);
            if (m.size()>cap) { Node r = tail.prev; remove(r); m.remove(r.key); }
        }
        void remove(Node n) { n.prev.next=n.next; n.next.prev=n.prev; }
        void add(Node n) { n.next=head.next; n.prev=head; head.next.prev=n; head.next=n; }
    }

    public static void main(String[] args) {
        System.out.println(groupAnagrams(new String[]{"eat","tea","tan","ate","nat","bat"}));
        System.out.println("canFinish 2=[[1,0]] = " + canFinish(2, new int[][]{{1,0}}));
        System.out.println("coinChange [1,2,5] 11 = " + coinChange(new int[]{1,2,5}, 11));
        LRU lru = new LRU(2);
        lru.put(1,1); lru.put(2,2);
        System.out.println("LRU.get(1) = " + lru.get(1));
        lru.put(3,3);
        System.out.println("LRU.get(2) [evicted] = " + lru.get(2));
    }
}
