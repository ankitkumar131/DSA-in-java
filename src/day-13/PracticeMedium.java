/**
 * Day 13 — Medium practice.
 *
 * Compile: javac src/day-13/PracticeMedium.java
 * Run    : java -cp src/day-13 PracticeMedium
 */
import java.util.HashSet;
import java.util.Set;

public class PracticeMedium {

    static class Node { int data; Node next; Node(int d) { data = d; } }
    static void print(Node head) { for (Node c = head; c != null; c = c.next) System.out.print(c.data + " "); System.out.println(); }

    static Node reverse(Node head) {
        Node prev = null, cur = head;
        while (cur != null) { Node nxt = cur.next; cur.next = prev; prev = cur; cur = nxt; }
        return prev;
    }

    static Node middle(Node head) {
        Node s = head, f = head;
        while (f != null && f.next != null) { s = s.next; f = f.next.next; }
        return s;
    }

    static boolean hasCycle(Node head) {
        Node s = head, f = head;
        while (f != null && f.next != null) {
            s = s.next; f = f.next.next;
            if (s == f) return true;
        }
        return false;
    }

    static Node nthFromEnd(Node head, int n) {
        Node ahead = head;
        for (int i = 0; i < n; i++) { if (ahead == null) return null; ahead = ahead.next; }
        Node behind = head;
        while (ahead != null) { ahead = ahead.next; behind = behind.next; }
        return behind;
    }

    static Node removeDup(Node head) {
        Set<Integer> seen = new HashSet<>();
        Node dummy = new Node(0); dummy.next = head;
        Node prev = dummy;
        while (head != null) {
            if (seen.add(head.data)) { prev = head; head = head.next; }
            else { prev.next = head.next; head = head.next; }
        }
        return dummy.next;
    }

    public static void main(String[] args) {
        Node head = new Node(1); head.next = new Node(2); head.next.next = new Node(3); head.next.next.next = new Node(4);

        Node rev = reverse(head);
        System.out.print("Q6 reverse: "); print(rev);

        System.out.println("Q7 middle: " + middle(rev).data);

        Node cyc = new Node(1); cyc.next = new Node(2); cyc.next.next = new Node(3);
        cyc.next.next.next = new Node(4); cyc.next.next.next.next = cyc.next;
        System.out.println("Q8 hasCycle: " + hasCycle(cyc));

        System.out.println("Q9 2nd from end: " + nthFromEnd(rev, 2).data);

        Node dup = new Node(1); dup.next = new Node(1); dup.next.next = new Node(2);
        dup.next.next.next = new Node(3); dup.next.next.next.next = new Node(3);
        System.out.print("Q10 removeDup: "); print(removeDup(dup));
    }
}
