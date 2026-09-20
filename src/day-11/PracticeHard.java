/**
 * Day 11 — Hard practice.
 *
 * Compile: javac src/day-11/PracticeHard.java
 * Run    : java -cp src/day-11 PracticeHard
 */
import java.util.*;

public class PracticeHard {

    static int[] sortArray(int[] a) {
        mergeSort(a, 0, a.length - 1);
        return a;
    }
    static void mergeSort(int[] a, int lo, int hi) {
        if (lo >= hi) return;
        int mid = lo + (hi - lo) / 2;
        mergeSort(a, lo, mid); mergeSort(a, mid + 1, hi);
        merge(a, lo, mid, hi);
    }
    static void merge(int[] a, int lo, int mid, int hi) {
        int[] left = Arrays.copyOfRange(a, lo, mid + 1);
        int[] right = Arrays.copyOfRange(a, mid + 1, hi + 1);
        int i = 0, j = 0, k = lo;
        while (i < left.length && j < right.length) a[k++] = left[i] <= right[j] ? left[i++] : right[j++];
        while (i < left.length)  a[k++] = left[i++];
        while (j < right.length) a[k++] = right[j++];
    }

    // Q12: count inversions using merge sort
    static long countInversions(int[] a) {
        long[] count = {0};
        mergeCount(a, 0, a.length - 1, count);
        return count[0];
    }
    static void mergeCount(int[] a, int lo, int hi, long[] count) {
        if (lo >= hi) return;
        int mid = lo + (hi - lo) / 2;
        mergeCount(a, lo, mid, count); mergeCount(a, mid + 1, hi, count);
        mergeInv(a, lo, mid, hi, count);
    }
    static void mergeInv(int[] a, int lo, int mid, int hi, long[] count) {
        int[] left = Arrays.copyOfRange(a, lo, mid + 1);
        int[] right = Arrays.copyOfRange(a, mid + 1, hi + 1);
        int i = 0, j = 0, k = lo;
        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) { a[k++] = left[i++]; }
            else { count[0] += left.length - i; a[k++] = right[j++]; }
        }
        while (i < left.length)  a[k++] = left[i++];
        while (j < right.length) a[k++] = right[j++];
    }

    // Q13: Largest number
    static String largestNumber(int[] a) {
        String[] s = new String[a.length];
        for (int i = 0; i < a.length; i++) s[i] = String.valueOf(a[i]);
        Arrays.sort(s, (x, y) -> (y + x).compareTo(x + y));
        if (s[0].equals("0")) return "0";
        return String.join("", s);
    }

    // Q14: Sort linked list (uses simple Node from Day 2)
    static class LLNode { int data; LLNode next; LLNode(int d) { data = d; } }

    static LLNode sortList(LLNode head) {
        if (head == null || head.next == null) return head;
        LLNode slow = head, fast = head.next;
        while (fast != null && fast.next != null) { slow = slow.next; fast = fast.next.next; }
        LLNode mid = slow.next; slow.next = null;
        LLNode left = sortList(head);
        LLNode right = sortList(mid);
        return mergeLL(left, right);
    }
    static LLNode mergeLL(LLNode a, LLNode b) {
        LLNode dummy = new LLNode(0), tail = dummy;
        while (a != null && b != null) {
            if (a.data < b.data) { tail.next = a; a = a.next; }
            else                  { tail.next = b; b = b.next; }
            tail = tail.next;
        }
        tail.next = (a != null) ? a : b;
        return dummy.next;
    }

    // Q15: Wiggle Sort II
    static void wiggleSort(int[] a) {
        int n = a.length;
        int[] sorted = a.clone();
        Arrays.sort(sorted);
        int i = (n - 1) / 2, j = n - 1;
        for (int k = 0; k < n; k++) a[k] = (k % 2 == 0) ? sorted[i--] : sorted[j--];
    }

    public static void main(String[] args) {
        System.out.println("Q11 sortArray    = " + Arrays.toString(sortArray(new int[]{5,2,3,1})));
        System.out.println("Q12 countInv     = " + countInversions(new int[]{2,4,1,3,5}));
        System.out.println("Q13 largestNum   = " + largestNumber(new int[]{3,30,34,5,9}));

        // Build list 4->2->1->3
        LLNode head = new LLNode(4);
        head.next = new LLNode(2); head.next.next = new LLNode(1); head.next.next.next = new LLNode(3);
        LLNode sorted = sortList(head);
        StringBuilder sb = new StringBuilder("Q14 sortList = ");
        for (LLNode cur = sorted; cur != null; cur = cur.next) sb.append(cur.data).append("->");
        System.out.println(sb.append("null"));

        int[] wig = {1,5,1,1,6,4}; wiggleSort(wig);
        System.out.println("Q15 wiggle       = " + Arrays.toString(wig));
    }
}
