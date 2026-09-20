/**
 * Day 14 — Easy practice.
 *
 * Compile: javac src/day-14/PracticeEasy.java
 * Run    : java -cp src/day-14 PracticeEasy
 */
public class PracticeEasy {

    static class DNode { int data; DNode prev, next; DNode(int d) { data = d; } }

    static DNode dllPrepend(DNode head, int v) {
        DNode n = new DNode(v);
        n.next = head;
        if (head != null) head.prev = n;
        return n;
    }

    static int circLength(java.util.HashSet<Object> seen) {
        return seen.size();
    }

    static void printCirc(Object[] a, int start) {
        for (int i = 0; i < a.length; i++)
            System.out.print(a[(start + i) % a.length] + " ");
        System.out.println();
    }

    static DNode insertSorted(DNode head, int v) {
        DNode n = new DNode(v);
        if (head == null || head.data >= v) {
            n.next = head;
            if (head != null) head.prev = n;
            return n;
        }
        DNode cur = head;
        while (cur.next != null && cur.next.data < v) cur = cur.next;
        n.next = cur.next;
        if (cur.next != null) cur.next.prev = n;
        cur.next = n;
        n.prev = cur;
        return head;
    }

    static boolean isCircular(java.util.HashMap<String, Object> map) {
        // (mock): in practice, walk and check if we return to head
        return map.containsKey("circular") && map.get("circular").equals(true);
    }

    public static void main(String[] args) {
        // DLL prepend
        DNode head = null;
        head = dllPrepend(head, 3);
        head = dllPrepend(head, 2);
        head = dllPrepend(head, 1);
        System.out.print("Q1 dllPrepend: "); for (DNode c = head; c != null; c = c.next) System.out.print(c.data + " "); System.out.println();

        // circular length (using a hashset trick)
        java.util.HashSet<Integer> seen = new java.util.HashSet<>();
        for (int x : new int[]{1,2,3,1,2,3}) seen.add(x);
        System.out.println("Q2 circLength (unique): " + circLength(seen));

        // print circular once
        Object[] arr = {"1","2","3"};
        System.out.print("Q3 printCirc: "); printCirc(arr, 0);

        // insert sorted
        DNode sorted = null;
        for (int v : new int[]{1,3,5,7}) sorted = insertSorted(sorted, v);
        sorted = insertSorted(sorted, 4);
        System.out.print("Q4 insertSorted: "); for (DNode c = sorted; c != null; c = c.next) System.out.print(c.data + " "); System.out.println();

        java.util.HashMap<String, Object> m = new java.util.HashMap<>();
        m.put("circular", true);
        System.out.println("Q5 isCircular: " + isCircular(m));
    }
}
