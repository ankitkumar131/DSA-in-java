/**
 * Day 19 — Easy practice.
 *
 * Compile: javac src/day-19/PracticeEasy.java
 * Run    : java -cp src/day-19 PracticeEasy
 */
public class PracticeEasy {

    static class Node { int val; Node left, right; Node(int v) { val = v; } }

    static Node insert(Node r, int v) {
        if (r == null) return new Node(v);
        if (v < r.val) r.left = insert(r.left, v);
        else if (v > r.val) r.right = insert(r.right, v);
        return r;
    }

    static boolean search(Node r, int v) {
        if (r == null) return false;
        if (r.val == v) return true;
        return v < r.val ? search(r.left, v) : search(r.right, v);
    }

    static Node min(Node r) { while (r != null && r.left != null) r = r.left; return r; }
    static Node max(Node r) { while (r != null && r.right != null) r = r.right; return r; }

    static boolean isValid(Node r) { return validate(r, Long.MIN_VALUE, Long.MAX_VALUE); }
    static boolean validate(Node n, long lo, long hi) {
        if (n == null) return true;
        if (n.val <= lo || n.val >= hi) return false;
        return validate(n.left, lo, n.val) && validate(n.right, n.val, hi);
    }

    static int kthSmallest(Node r, int k) { return kth(r, new int[]{k}); }
    static int kth(Node n, int[] k) {
        if (n == null) return -1;
        int left = kth(n.left, k);
        if (k[0] == 0) return left;
        k[0]--;
        if (k[0] == 0) return n.val;
        return kth(n.right, k);
    }

    public static void main(String[] args) {
        int[] vals = {5, 3, 7, 1, 4, 6, 8};
        Node root = null; for (int v : vals) root = insert(root, v);
        System.out.println("Q1 search 4 = " + search(root, 4));
        System.out.println("Q2 insert 9 done");
        System.out.println("Q3 min/max  = " + min(root).val + "/" + max(root).val);
        System.out.println("Q4 valid    = " + isValid(root));
        System.out.println("Q5 kth=3    = " + kthSmallest(root, 3));
    }
}
