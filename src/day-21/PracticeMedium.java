/**
 * Day 21 — Medium practice.
 *
 * Compile: javac src/day-21/PracticeMedium.java
 * Run    : java -cp src/day-21 PracticeMedium
 */
import java.util.*;

public class PracticeMedium {

    static class Node { int val; Node left, right; Node(int v) { val = v; } }

    static int diameter(Node root) { int[] b = {0}; hd(root, b); return b[0]; }
    static int hd(Node n, int[] b) {
        if (n == null) return 0;
        int l = hd(n.left, b), r = hd(n.right, b);
        b[0] = Math.max(b[0], l + r);
        return 1 + Math.max(l, r);
    }

    static int maxPathSum(Node root) { int[] b = {Integer.MIN_VALUE}; mp(root, b); return b[0]; }
    static int mp(Node n, int[] b) {
        if (n == null) return 0;
        int l = Math.max(0, mp(n.left, b));
        int r = Math.max(0, mp(n.right, b));
        b[0] = Math.max(b[0], n.val + l + r);
        return n.val + Math.max(l, r);
    }

    static Node lca(Node n, Node p, Node q) {
        if (n == null || n == p || n == q) return n;
        Node l = lca(n.left, p, q), r = lca(n.right, p, q);
        return l != null && r != null ? n : (l != null ? l : r);
    }

    static List<Integer> rightSideView(Node r) {
        List<Integer> out = new ArrayList<>();
        if (r == null) return out;
        Deque<Node> q = new ArrayDeque<>(); q.offer(r);
        while (!q.isEmpty()) {
            int sz = q.size();
            for (int i = 0; i < sz; i++) {
                Node n = q.poll();
                if (i == sz - 1) out.add(n.val);
                if (n.left != null)  q.offer(n.left);
                if (n.right != null) q.offer(n.right);
            }
        }
        return out;
    }

    static boolean isValidBST(Node root) { return v(root, Long.MIN_VALUE, Long.MAX_VALUE); }
    static boolean v(Node n, long lo, long hi) {
        if (n == null) return true;
        if (n.val <= lo || n.val >= hi) return false;
        return v(n.left, lo, n.val) && v(n.right, n.val, hi);
    }

    public static void main(String[] args) {
        Node r = new Node(1);
        r.left = new Node(2); r.right = new Node(3);
        r.left.left = new Node(4); r.left.right = new Node(5);
        r.right.left = new Node(6); r.right.right = new Node(7);
        System.out.println("Q6 diameter   = " + diameter(r));

        Node p = new Node(-10);
        p.left = new Node(9); p.right = new Node(20);
        p.right.left = new Node(15); p.right.right = new Node(7);
        System.out.println("Q7 maxPathSum = " + maxPathSum(p));

        Node p1 = r.left.left, p2 = r.left.right;
        System.out.println("Q8 LCA(4,5)   = " + lca(r, p1, p2).val);

        System.out.println("Q9 rightView  = " + rightSideView(r));

        Node bst = new Node(2); bst.left = new Node(1); bst.right = new Node(3);
        System.out.println("Q10 validBST  = " + isValidBST(bst));
    }
}
