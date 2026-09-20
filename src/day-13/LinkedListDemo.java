/**
 * Day 13 — Singly linked list demo.
 *
 * Compile: javac src/day-13/LinkedListDemo.java
 * Run    : java -cp src/day-13 LinkedListDemo
 */
public class LinkedListDemo {

    static class Node {
        int data;
        Node next;
        Node(int data) { this.data = data; }
    }

    static Node append(Node head, int data) {
        Node n = new Node(data);
        if (head == null) return n;
        Node cur = head;
        while (cur.next != null) cur = cur.next;
        cur.next = n;
        return head;
    }

    static Node prepend(Node head, int data) {
        Node n = new Node(data); n.next = head; return n;
    }

    static Node delete(Node head, int data) {
        if (head == null) return null;
        if (head.data == data) return head.next;
        Node cur = head;
        while (cur.next != null && cur.next.data != data) cur = cur.next;
        if (cur.next != null) cur.next = cur.next.next;
        return head;
    }

    static void printList(Node head) {
        for (Node cur = head; cur != null; cur = cur.next) System.out.print(cur.data + " -> ");
        System.out.println("null");
    }

    static int length(Node head) {
        int c = 0; for (Node cur = head; cur != null; cur = cur.next) c++; return c;
    }

    static Node search(Node head, int t) {
        for (Node cur = head; cur != null; cur = cur.next) if (cur.data == t) return cur;
        return null;
    }

    static Node reverse(Node head) {
        Node prev = null, cur = head;
        while (cur != null) { Node nxt = cur.next; cur.next = prev; prev = cur; cur = nxt; }
        return prev;
    }

    static Node middle(Node head) {
        Node slow = head, fast = head;
        while (fast != null && fast.next != null) { slow = slow.next; fast = fast.next.next; }
        return slow;
    }

    static Node nthFromEnd(Node head, int n) {
        Node ahead = head;
        for (int i = 0; i < n; i++) { if (ahead == null) return null; ahead = ahead.next; }
        Node behind = head;
        while (ahead != null) { ahead = ahead.next; behind = behind.next; }
        return behind;
    }

    static boolean hasCycle(Node head) {
        Node slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next; fast = fast.next.next;
            if (slow == fast) return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Node head = null;
        for (int v : new int[]{1, 2, 3, 4}) head = append(head, v);
        head = prepend(head, 0);
        printList(head);
        head = delete(head, 2);
        printList(head);
        System.out.println("length      = " + length(head));
        System.out.println("search(3)   = " + (search(head, 3) != null));
        head = reverse(head);
        printList(head);
        System.out.println("middle      = " + middle(head).data);
        System.out.println("2nd fromEnd = " + nthFromEnd(head, 2).data);

        Node cyc = new Node(1); cyc.next = new Node(2); cyc.next.next = new Node(3);
        cyc.next.next.next = new Node(4); cyc.next.next.next.next = cyc.next;
        System.out.println("hasCycle    = " + hasCycle(cyc));
    }
}
