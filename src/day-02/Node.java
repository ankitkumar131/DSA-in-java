/**
 * Day 2 — The Node class.
 * The fundamental building block of Linked Lists, Trees, Graphs.
 *
 * Compile: javac src/day-02/Node.java
 * Run    : java -cp src/day-02 Node
 */
public class Node {

    int data;        // the value stored in this node
    Node next;       // reference to the next node, or null if tail

    public Node(int data) {
        this.data = data;
        this.next = null;     // explicitly null for clarity
    }

    // helper: print the linked list starting at this node
    public static void printList(Node head) {
        Node cur = head;
        while (cur != null) {
            System.out.print(cur.data + " -> ");
            cur = cur.next;
        }
        System.out.println("null");
    }

    // helper: count nodes starting at head
    public static int length(Node head) {
        int count = 0;
        for (Node cur = head; cur != null; cur = cur.next) count++;
        return count;
    }

    // helper: append a new node with data at the tail
    public static Node append(Node head, int data) {
        Node newNode = new Node(data);
        if (head == null) return newNode;
        Node cur = head;
        while (cur.next != null) cur = cur.next;
        cur.next = newNode;
        return head;
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head = append(head, 2);
        head = append(head, 3);
        head = append(head, 4);
        printList(head);                  // 1 -> 2 -> 3 -> 4 -> null
        System.out.println("length = " + length(head));  // 4
    }
}
