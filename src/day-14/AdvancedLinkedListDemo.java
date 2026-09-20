/**
 * Day 14 — Advanced linked lists.
 *
 * Compile: javac src/day-14/AdvancedLinkedListDemo.java
 * Run    : java -cp src/day-14 AdvancedLinkedListDemo
 */
public class AdvancedLinkedListDemo {

    static class Node { int data; Node next; Node(int d) { data = d; } }
    static class DNode { int data; DNode prev, next; DNode(int d) { data = d; } }

    static DNode dllAppend(DNode head, int data) {
        DNode n = new DNode(data);
        if (head == null) return n;
        DNode cur = head;
        while (cur.next != null) cur = cur.next;
        cur.next = n; n.prev = cur;
        return head;
    }
    static void dllPrint(DNode head) {
        for (DNode cur = head; cur != null; cur = cur.next) System.out.print(cur.data + " ");
        System.out.println();
    }

    static Node cycleStart(Node head) {
        Node slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next; fast = fast.next.next;
            if (slow == fast) {
                Node p1 = head, p2 = slow;
                while (p1 != p2) { p1 = p1.next; p2 = p2.next; }
                return p1;
            }
        }
        return null;
    }

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

    static Node intersection(Node a, Node b) {
        int la = 0, lb = 0;
        for (Node c = a; c != null; c = c.next) la++;
        for (Node c = b; c != null; c = c.next) lb++;
        Node p1 = a, p2 = b;
        for (int i = 0; i < Math.abs(la - lb); i++) if (la > lb) p1 = p1.next; else p2 = p2.next;
        while (p1 != null && p2 != null) { if (p1 == p2) return p1; p1 = p1.next; p2 = p2.next; }
        return null;
    }

    static Node reverse(Node head) {
        Node prev = null, cur = head;
        while (cur != null) { Node nxt = cur.next; cur.next = prev; prev = cur; cur = nxt; }
        return prev;
    }

    static boolean isPalindrome(Node head) {
        if (head == null || head.next == null) return true;
        Node slow = head, fast = head;
        while (fast.next != null && fast.next.next != null) { slow = slow.next; fast = fast.next.next; }
        Node second = reverse(slow.next);
        Node p1 = head, p2 = second;
        boolean ok = true;
        while (p2 != null) { if (p1.data != p2.data) { ok = false; break; } p1 = p1.next; p2 = p2.next; }
        slow.next = reverse(second);
        return ok;
    }

    static Node reverseKGroup(Node head, int k) {
        Node cur = head; int count = 0;
        while (cur != null && count < k) { cur = cur.next; count++; }
        if (count < k) return head;
        Node prev = null; cur = head;
        for (int i = 0; i < k; i++) { Node nxt = cur.next; cur.next = prev; prev = cur; cur = nxt; }
        head.next = reverseKGroup(cur, k);
        return prev;
    }

    public static void main(String[] args) {
        DNode dh = null;
        for (int v : new int[]{1,2,3}) dh = dllAppend(dh, v);
        System.out.print("DLL: "); dllPrint(dh);

        Node cs = new Node(1); cs.next = new Node(2); cs.next.next = new Node(3);
        cs.next.next.next = new Node(4); cs.next.next.next.next = cs.next.next;
        System.out.println("cycleStart.data = " + cycleStart(cs).data);

        Node a = new Node(1); a.next = new Node(2); a.next.next = new Node(4);
        Node b = new Node(1); b.next = new Node(3); b.next.next = new Node(4);
        Node m = merge(a, b);
        StringBuilder sb = new StringBuilder("merge: ");
        for (Node c = m; c != null; c = c.next) sb.append(c.data).append(" ");
        System.out.println(sb);

        Node x1 = new Node(4); x1.next = new Node(1); x1.next.next = new Node(8); x1.next.next.next = new Node(9);
        Node x2 = new Node(5); x2.next = x1.next.next;
        System.out.println("intersection.data = " + intersection(x1, x2).data);

        Node pal = new Node(1); pal.next = new Node(2); pal.next.next = new Node(2); pal.next.next.next = new Node(1);
        System.out.println("isPalindrome = " + isPalindrome(pal));

        Node rg = new Node(1); rg.next = new Node(2); rg.next.next = new Node(3);
        rg.next.next.next = new Node(4); rg.next.next.next.next = new Node(5);
        Node revK = reverseKGroup(rg, 2);
        StringBuilder sb2 = new StringBuilder("reverseKGroup: ");
        for (Node c = revK; c != null; c = c.next) sb2.append(c.data).append(" ");
        System.out.println(sb2);
    }
}
