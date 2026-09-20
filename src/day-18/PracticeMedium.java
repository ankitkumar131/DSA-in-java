/**
 * Day 18 — Medium practice.
 *
 * Compile: javac src/day-18/PracticeMedium.java
 * Run    : java -cp src/day-18 PracticeMedium
 */
import java.util.*;

public class PracticeMedium {

    static class Node { int val; Node left, right; Node(int v) { val = v; } }

    static boolean sameTree(Node a, Node b) {
        if (a == null && b == null) return true;
        if (a == null || b == null) return false;
        return a.val == b.val && sameTree(a.left, b.left) && sameTree(a.right, b.right);
    }

    static Node invert(Node r) {
        if (r == null) return null;
        Node tmp = invert(r.left);
        r.left = invert(r.right);
        r.right = tmp;
        return r;
    }

    static boolean symmetric(Node r) { return r == null || sym(r.left, r.right); }
    static boolean sym(Node a, Node b) {
        if (a == null && b == null) return true;
        if (a == null || b == null) return false;
        return a.val == b.val && sym(a.left, b.right) && sym(a.right, b.left);
    }

    static int preIdx = 0;
    static Node buildTree(int[] pre, int[] in) {
        preIdx = 0;
        Map<Integer, Integer> m = new HashMap<>();
        for (int i = 0; i < in.length; i++) m.put(in[i], i);
        return build(pre, in, 0, in.length - 1, m);
    }
    static Node build(int[] pre, int[] in, int lo, int hi, Map<Integer, Integer> m) {
        if (lo > hi) return null;
        int rootVal = pre[preIdx++];
        Node root = new Node(rootVal);
        int mid = m.get(rootVal);
        root.left = build(pre, in, lo, mid - 1, m);
        root.right = build(pre, in, mid + 1, hi, m);
        return root;
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

    static void inorderPrint(Node r) {
        if (r == null) return;
        inorderPrint(r.left); System.out.print(r.val + " "); inorderPrint(r.right);
    }

    public static void main(String[] args) {
        Node a = new Node(1); a.left = new Node(2); a.right = new Node(3);
        Node b = new Node(1); b.left = new Node(2); b.right = new Node(3);
        System.out.println("Q6 sameTree   = " + sameTree(a, b));

        Node t = new Node(4); t.left = new Node(2); t.right = new Node(7);
        Node inv = invert(t);
        System.out.print("Q7 invert     = "); inorderPrint(inv); System.out.println();

        Node s = new Node(1); s.left = new Node(2); s.right = new Node(2);
        s.left.right = new Node(3); s.right.right = new Node(3);
        System.out.println("Q8 symmetric  = " + symmetric(s));

        int[] pre = {3, 9, 20, 15, 7}, in = {9, 3, 15, 20, 7};
        Node built = buildTree(pre, in);
        System.out.print("Q9 build      = "); inorderPrint(built); System.out.println();

        Node rs = new Node(1); rs.left = new Node(2); rs.right = new Node(3);
        rs.left.right = new Node(5); rs.right.right = new Node(4);
        System.out.println("Q10 rightView = " + rightSideView(rs));
    }
}
