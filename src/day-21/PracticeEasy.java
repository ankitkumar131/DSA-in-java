/**
 * Day 21 — Easy practice.
 *
 * Compile: javac src/day-21/PracticeEasy.java
 * Run    : java -cp src/day-21 PracticeEasy
 */
public class PracticeEasy {

    static class Node { int val; Node left, right; Node(int v) { val = v; } }

    static int height(Node n) { return n == null ? 0 : 1 + Math.max(height(n.left), height(n.right)); }

    static boolean isBal(Node n) { return check(n) != -1; }
    static int check(Node n) {
        if (n == null) return 0;
        int l = check(n.left); if (l == -1) return -1;
        int r = check(n.right); if (r == -1) return -1;
        if (Math.abs(l - r) > 1) return -1;
        return 1 + Math.max(l, r);
    }

    static int maxDepth(Node r) { return height(r); }

    static boolean same(Node a, Node b) {
        if (a == null && b == null) return true;
        if (a == null || b == null) return false;
        return a.val == b.val && same(a.left, b.left) && same(a.right, b.right);
    }
    static boolean sym(Node a, Node b) {
        if (a == null && b == null) return true;
        if (a == null || b == null) return false;
        return a.val == b.val && sym(a.left, b.right) && sym(a.right, b.left);
    }
    static boolean symmetric(Node r) { return r == null || sym(r.left, r.right); }

    static Node invert(Node n) {
        if (n == null) return null;
        Node t = invert(n.left); n.left = invert(n.right); n.right = t;
        return n;
    }

    public static void main(String[] args) {
        Node r = new Node(1);
        r.left = new Node(2); r.right = new Node(3);
        r.left.left = new Node(4); r.left.right = new Node(5);

        System.out.println("Q1 height   = " + height(r));
        System.out.println("Q2 balanced = " + isBal(r));
        System.out.println("Q3 maxDepth = " + maxDepth(r));

        Node a = new Node(1); a.left = new Node(2); a.right = new Node(3);
        Node b = new Node(1); b.left = new Node(2); b.right = new Node(3);
        System.out.println("Q4 sameTree = " + same(a, b));

        Node s = new Node(1); s.left = new Node(2); s.right = new Node(2);
        System.out.println("Q4 symmetric = " + symmetric(s));

        Node inv = invert(r);
        System.out.println("Q5 invert root = " + inv.val);
    }
}
