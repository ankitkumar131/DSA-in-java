/**
 * Day 13 — Hard practice.
 *
 * Compile: javac src/day-13/PracticeHard.java
 * Run    : java -cp src/day-13 PracticeHard
 */
public class PracticeHard {

    static class Node { int data; Node next; Node(int d) { data = d; } }
    static void print(Node head) { for (Node c = head; c != null; c = c.next) System.out.print(c.data + " "); System.out.println(); }

    // Q11: reverse in groups of k
    static Node reverseKGroup(Node head, int k) {
        Node cur = head;
        int count = 0;
        while (cur != null && count < k) { cur = cur.next; count++; }
        if (count < k) return head;
        Node prev = null; cur = head;
        for (int i = 0; i < k; i++) { Node nxt = cur.next; cur.next = prev; prev = cur; cur = nxt; }
        head.next = reverseKGroup(cur, k);
        return prev;
    }

    // Q12: merge two sorted lists
    static Node merge(Node a, Node b) {
        Node dummy = new Node(0), tail = dummy;
        while (a != null && b != null) {
            if (a.data <= b.data) { tail.next = a; a = a.next; }
            else                  { tail.next = b; b = b.next; }
            tail = tail.next;
        }
        tail.next = (a != null) ? a : b;
        return dummy.next;
    }

    // Q13: add two numbers (LeetCode 2)
    static Node addTwo(Node a, Node b) {
        Node dummy = new Node(0), tail = dummy;
        int carry = 0;
        while (a != null || b != null || carry != 0) {
            int sum = carry;
            if (a != null) { sum += a.data; a = a.next; }
            if (b != null) { sum += b.data; b = b.next; }
            carry = sum / 10;
            tail.next = new Node(sum % 10);
            tail = tail.next;
        }
        return dummy.next;
    }

    // Q14: sort list (merge sort)
    static Node sortList(Node head) {
        if (head == null || head.next == null) return head;
        Node slow = head, fast = head.next;
        while (fast != null && fast.next != null) { slow = slow.next; fast = fast.next.next; }
        Node mid = slow.next; slow.next = null;
        return merge(sortList(head), sortList(mid));
    }

    // Q15: rotate right by k
    static Node rotateRight(Node head, int k) {
        if (head == null || k == 0) return head;
        int len = 1; Node tail = head;
        while (tail.next != null) { tail = tail.next; len++; }
        k %= len; if (k == 0) return head;
        tail.next = head;                       // circular
        Node newTail = head;
        for (int i = 0; i < len - k - 1; i++) newTail = newTail.next;
        Node newHead = newTail.next;
        newTail.next = null;
        return newHead;
    }

    static Node build(int[] a) {
        Node dummy = new Node(0), t = dummy;
        for (int v : a) { t.next = new Node(v); t = t.next; }
        return dummy.next;
    }

    public static void main(String[] args) {
        System.out.print("Q11 reverseKGroup: "); print(reverseKGroup(build(new int[]{1,2,3,4,5}), 2));
        System.out.print("Q12 merge: "); print(merge(build(new int[]{1,2,4}), build(new int[]{1,3,4})));
        System.out.print("Q13 addTwo: "); print(addTwo(build(new int[]{2,4,3}), build(new int[]{5,6,4})));
        System.out.print("Q14 sortList: "); print(sortList(build(new int[]{4,2,1,3})));
        System.out.print("Q15 rotate: "); print(rotateRight(build(new int[]{1,2,3,4,5}), 2));
    }
}
