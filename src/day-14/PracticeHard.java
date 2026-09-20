/**
 * Day 14 — Hard practice.
 *
 * Compile: javac src/day-14/PracticeHard.java
 * Run    : java -cp src/day-14 PracticeHard.java
 */
import java.util.*;

public class PracticeHard {

    // Q11: LRU cache (O(1) get/put)
    static class LRU<K, V> {
        class Node { K key; V val; Node prev, next; Node(K k, V v) { key = k; val = v; } }
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
            map.put(k, n);
            addToHead(n);
            if (map.size() > cap) {
                Node lru = tail.prev;
                remove(lru);
                map.remove(lru.key);
            }
        }
        void addToHead(Node n) { n.next = head.next; n.prev = head; head.next.prev = n; head.next = n; }
        void remove(Node n) { n.prev.next = n.next; n.next.prev = n.prev; }
        void moveToHead(Node n) { remove(n); addToHead(n); }
    }

    // Q12: copy list with random pointer
    static class RN { int data; RN next, random; RN(int d) { data = d; } }
    static RN copyRandom(RN head) {
        if (head == null) return null;
        Map<RN, RN> map = new HashMap<>();
        for (RN c = head; c != null; c = c.next) map.put(c, new RN(c.data));
        for (RN c = head; c != null; c = c.next) {
            map.get(c).next = map.get(c.next);
            map.get(c).random = map.get(c.random);
        }
        return map.get(head);
    }

    // Q13: flatten multilevel doubly list (DFS)
    static class MN { int data; MN prev, next, child; MN(int d) { data = d; } }
    static MN flatten(MN head) {
        if (head == null) return null;
        MN dummy = new MN(0); dummy.next = head; head.prev = dummy;
        for (MN cur = dummy; cur != null; cur = cur.next) {
            if (cur.child != null) {
                MN child = flatten(cur.child);
                MN nxt = cur.next;
                cur.next = child; child.prev = cur;
                MN last = child;
                while (last.next != null) last = last.next;
                last.next = nxt; if (nxt != null) nxt.prev = last;
                cur.child = null;
            }
        }
        MN real = dummy.next; real.prev = null;
        return real;
    }

    // Q14: reverse nodes in k-group (iterative, O(1) extra)
    static class Node { int data; Node next; Node(int d) { data = d; } }
    static Node reverseKIter(Node head, int k) {
        Node dummy = new Node(0); dummy.next = head;
        Node prevGroupEnd = dummy;
        while (true) {
            Node kth = prevGroupEnd;
            for (int i = 0; i < k && kth != null; i++) kth = kth.next;
            if (kth == null) break;
            Node groupStart = prevGroupEnd.next;
            Node nextGroupStart = kth.next;
            Node prev = nextGroupStart, cur = groupStart;
            while (cur != nextGroupStart) {
                Node nxt = cur.next; cur.next = prev; prev = cur; cur = nxt;
            }
            prevGroupEnd.next = prev;
            prevGroupEnd = groupStart;
        }
        return dummy.next;
    }

    // Q15: trap water in DLL heights
    static int trapWater(Node head) {
        if (head == null) return 0;
        List<Integer> arr = new ArrayList<>();
        for (Node c = head; c != null; c = c.next) arr.add(c.data);
        int l = 0, r = arr.size() - 1, lmax = 0, rmax = 0, water = 0;
        while (l < r) {
            if (arr.get(l) < arr.get(r)) {
                lmax = Math.max(lmax, arr.get(l));
                water += lmax - arr.get(l);
                l++;
            } else {
                rmax = Math.max(rmax, arr.get(r));
                water += rmax - arr.get(r);
                r--;
            }
        }
        return water;
    }

    public static void main(String[] args) {
        LRU<Integer, String> lru = new LRU<>(2);
        lru.put(1, "a"); lru.put(2, "b");
        System.out.println("Q11 get(1) = " + lru.get(1));
        lru.put(3, "c");                            // evicts 2
        System.out.println("Q11 get(2) = " + lru.get(2));

        RN r1 = new RN(7), r2 = new RN(13), r3 = new RN(11), r4 = new RN(10), r5 = new RN(1);
        r1.next = r2; r2.next = r3; r3.next = r4; r4.next = r5;
        r1.random = null; r2.random = r4; r3.random = r2; r4.random = r5; r5.random = r2;
        RN copy = copyRandom(r1);
        System.out.println("Q12 copy data = " + copy.data + " copy.random.data = " + copy.random.data);

        Node rg = new Node(1); rg.next = new Node(2); rg.next.next = new Node(3);
        rg.next.next.next = new Node(4); rg.next.next.next.next = new Node(5);
        Node revK = reverseKIter(rg, 2);
        StringBuilder sb = new StringBuilder("Q14 reverseKIter: ");
        for (Node c = revK; c != null; c = c.next) sb.append(c.data).append(" ");
        System.out.println(sb);

        Node heights = new Node(0); heights.next = new Node(1); heights.next.next = new Node(0);
        heights.next.next.next = new Node(2); heights.next.next.next.next = new Node(1);
        heights.next.next.next.next.next = new Node(0); heights.next.next.next.next.next.next = new Node(1);
        heights.next.next.next.next.next.next.next = new Node(3); heights.next.next.next.next.next.next.next.next = new Node(2);
        heights.next.next.next.next.next.next.next.next.next = new Node(1);
        heights.next.next.next.next.next.next.next.next.next.next = new Node(2);
        heights.next.next.next.next.next.next.next.next.next.next.next = new Node(1);
        System.out.println("Q15 trap = " + trapWater(heights));
    }
}
