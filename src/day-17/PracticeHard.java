/**
 * Day 17 — Hard practice.
 *
 * Compile: javac src/day-17/PracticeHard.java
 * Run    : java -cp src/day-17 PracticeHard
 */
import java.util.*;

public class PracticeHard {

    // Q11: LRU Cache
    static class LRU<K, V> {
        class Node { K key; V val; Node prev, next; Node(K k, V v) { key=k; val=v; } }
        final int cap;
        final Map<K, Node> map = new HashMap<>();
        final Node head = new Node(null, null), tail = new Node(null, null);
        LRU(int cap) { this.cap = cap; head.next = tail; tail.prev = head; }
        V get(K k) {
            Node n = map.get(k);
            if (n == null) return null;
            moveToHead(n);
            return n.val;
        }
        void put(K k, V v) {
            Node n = map.get(k);
            if (n != null) { n.val = v; moveToHead(n); return; }
            n = new Node(k, v);
            map.put(k, n); addToHead(n);
            if (map.size() > cap) { Node lru = tail.prev; remove(lru); map.remove(lru.key); }
        }
        void addToHead(Node n) { n.next = head.next; n.prev = head; head.next.prev = n; head.next = n; }
        void remove(Node n) { n.prev.next = n.next; n.next.prev = n.prev; }
        void moveToHead(Node n) { remove(n); addToHead(n); }
    }

    // Q12: Insert Delete GetRandom O(1)
    static class RandomizedSet {
        Map<Integer, Integer> map;
        List<Integer> list;
        java.util.Random rand = new java.util.Random();
        RandomizedSet() { map = new HashMap<>(); list = new ArrayList<>(); }
        boolean insert(int x) {
            if (map.containsKey(x)) return false;
            map.put(x, list.size());
            list.add(x);
            return true;
        }
        boolean remove(int x) {
            if (!map.containsKey(x)) return false;
            int idx = map.get(x), last = list.get(list.size() - 1);
            list.set(idx, last);
            map.put(last, idx);
            list.remove(list.size() - 1);
            map.remove(x);
            return true;
        }
        int getRandom() { return list.get(rand.nextInt(list.size())); }
    }

    // Q13: Word Pattern II (bijection backtracking)
    static boolean wordPatternMatch(String pattern, String s) {
        Map<Character, String> map = new HashMap<>();
        Set<String> seen = new HashSet<>();
        return backtrack(pattern, s, 0, 0, map, seen);
    }
    static boolean backtrack(String p, String s, int pi, int si, Map<Character, String> map, Set<String> seen) {
        if (pi == p.length() && si == s.length()) return true;
        if (pi == p.length() || si == s.length()) return false;
        char c = p.charAt(pi);
        if (map.containsKey(c)) {
            String w = map.get(c);
            if (!s.startsWith(w, si)) return false;
            return backtrack(p, s, pi + 1, si + w.length(), map, seen);
        }
        for (int end = si + 1; end <= s.length(); end++) {
            String w = s.substring(si, end);
            if (seen.contains(w)) continue;
            map.put(c, w); seen.add(w);
            if (backtrack(p, s, pi + 1, end, map, seen)) return true;
            map.remove(c); seen.remove(w);
        }
        return false;
    }

    // Q14: Minimum window substring
    static String minWindow(String s, String t) {
        int[] need = new int[128];
        for (char c : t.toCharArray()) need[c]++;
        int missing = t.length(), lo = 0, bestLo = 0, bestHi = Integer.MAX_VALUE;
        for (int hi = 0; hi < s.length(); hi++) {
            if (need[s.charAt(hi)]-- > 0) missing--;
            while (missing == 0) {
                if (hi - lo < bestHi - bestLo) { bestLo = lo; bestHi = hi; }
                if (need[s.charAt(lo)]++ == 0) missing++;
                lo++;
            }
        }
        return bestHi == Integer.MAX_VALUE ? "" : s.substring(bestLo, bestHi + 1);
    }

    // Q15: Substring with concatenation of all words
    static List<Integer> findSubstring(String s, String[] words) {
        List<Integer> out = new ArrayList<>();
        if (words.length == 0) return out;
        int wl = words[0].length(), total = wl * words.length;
        Map<String, Integer> need = new HashMap<>();
        for (String w : words) need.merge(w, 1, Integer::sum);
        for (int offset = 0; offset < wl; offset++) {
            Map<String, Integer> have = new HashMap<>();
            for (int i = offset; i + total <= s.length(); i += wl) {
                if (i > offset) {
                    String prev = s.substring(i - wl, i);
                    have.merge(prev, -1, Integer::sum);
                    if (have.get(prev) == 0) have.remove(prev);
                }
                String sub = s.substring(i + total - wl, i + total);
                have.merge(sub, 1, Integer::sum);
                if (have.equals(need)) out.add(i);
            }
        }
        return out;
    }

    public static void main(String[] args) {
        LRU<Integer, String> lru = new LRU<>(2);
        lru.put(1, "a"); lru.put(2, "b"); System.out.println("Q11 get(1) = " + lru.get(1));
        lru.put(3, "c"); System.out.println("Q11 get(2) = " + lru.get(2));

        RandomizedSet rs = new RandomizedSet();
        rs.insert(1); rs.insert(2); rs.remove(1);
        System.out.println("Q12 random = " + rs.getRandom());

        System.out.println("Q13 pattern = " + wordPatternMatch("abab", "redblueredblue"));
        System.out.println("Q14 minWin  = " + minWindow("ADOBECODEBANC", "ABC"));
        System.out.println("Q15 concat  = " + findSubstring("barfoothefoobarman", new String[]{"foo","bar"}));
    }
}
