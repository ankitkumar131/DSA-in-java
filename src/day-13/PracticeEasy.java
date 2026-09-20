/**
 * Day 13 — Easy practice.
 *
 * Compile: javac src/day-13/PracticeEasy.java
 * Run    : java -cp src/day-13 PracticeEasy
 */
public class PracticeEasy {

    static class Node { int data; Node next; Node(int d) { data = d; } }

    static void print(Node head) {
        for (Node cur = head; cur != null; cur = cur.next) System.out.print(cur.data + " ");
        System.out.println();
    }

    static int length(Node head) {
        int c = 0; for (Node cur = head; cur != null; cur = cur.next) c++; return c;
    }

    static Node search(Node head, int t) {
        for (Node cur = head; cur != null; cur = cur.next) if (cur.data == t) return cur;
        return null;
    }

    static Node prepend(Node head, int v) { Node n = new Node(v); n.next = head; return n; }
    static Node deleteHead(Node head)     { return head == null ? null : head.next; }

    public static void main(String[] args) {
        Node head = new Node(1); head.next = new Node(2); head.next.next = new Node(3);

        System.out.print("Q1 list: "); print(head);
        System.out.println("Q2 length: " + length(head));
        System.out.println("Q3 search 2: " + (search(head, 2) != null));

        Node pre = prepend(head, 0);
        System.out.print("Q4 prepend: "); print(pre);

        Node del = deleteHead(pre);
        System.out.print("Q5 deleteHead: "); print(del);
    }
}
