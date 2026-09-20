/** Day 14 tiny project: tiny LRU cache. */
import java.util.*;
public class MiniLRU {
    static class Node { int key, val; Node prev, next; Node(int k, int v) { key=k; val=v; } }
    final int cap; final Map<Integer, Node> map = new HashMap<>();
    final Node head = new Node(0,0), tail = new Node(0,0);
    MiniLRU(int c) { cap=c; head.next=tail; tail.prev=head; }
    int get(int k) {
        if (!map.containsKey(k)) return -1;
        Node n = map.get(k); remove(n); add(n); return n.val;
    }
    void put(int k, int v) {
        if (map.containsKey(k)) { Node n = map.get(k); n.val=v; remove(n); add(n); return; }
        Node n = new Node(k, v); map.put(k, n); add(n);
        if (map.size() > cap) { Node r = tail.prev; remove(r); map.remove(r.key); }
    }
    void remove(Node n) { n.prev.next=n.next; n.next.prev=n.prev; }
    void add(Node n) { n.next=head.next; n.prev=head; head.next.prev=n; head.next=n; }
    public static void main(String[] args) {
        MiniLRU l = new MiniLRU(2);
        l.put(1, 10); l.put(2, 20); l.get(1); l.put(3, 30);
        System.out.println("get(2)=" + l.get(2) + " (expect -1)");
    }
}
