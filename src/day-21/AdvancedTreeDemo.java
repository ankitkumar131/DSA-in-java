/**
 * Day 21 — Advanced tree problems.
 *
 * Compile: javac src/day-21/AdvancedTreeDemo.java
 * Run    : java -cp src/day-21 AdvancedTreeDemo
 */
import java.util.*;

public class AdvancedTreeDemo {

    static class Node { int val; Node left, right; Node(int v) { val = v; } }

    static int height(Node n) { return n == null ? 0 : 1 + Math.max(height(n.left), height(n.right)); }

    static boolean isBalanced(Node n) { return checkBal(n) != -1; }
    static int checkBal(Node n) {
        if (n == null) return 0;
        int l = checkBal(n.left); if (l == -1) return -1;
        int r = checkBal(n.right); if (r == -1) return -1;
        if (Math.abs(l - r) > 1) return -1;
        return 1 + Math.max(l, r);
    }

    static int diameter(Node root) { int[] b = {0}; hDiam(root, b); return b[0]; }
    static int hDiam(Node n, int[] b) {
        if (n == null) return 0;
        int l = hDiam(n.left, b), r = hDiam(n.right, b);
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

    static List<List<Integer>> vertical(Node root) {
        List<List<Integer>> out = new ArrayList<>();
        if (root == null) return out;
        TreeMap<Integer, List<Integer>> map = new TreeMap<>();
        Deque<Node> q = new ArrayDeque<>();
        Deque<Integer> cols = new ArrayDeque<>();
        q.offer(root); cols.offer(0);
        while (!q.isEmpty()) {
            Node n = q.poll(); int c = cols.poll();
            map.computeIfAbsent(c, k -> new ArrayList<>()).add(n.val);
            if (n.left != null)  { q.offer(n.left);  cols.offer(c - 1); }
            if (n.right != null) { q.offer(n.right); cols.offer(c + 1); }
        }
        out.addAll(map.values());
        return out;
    }

    static String serialize(Node r) {
        StringBuilder sb = new StringBuilder();
        ser(r, sb); return sb.toString();
    }
    static void ser(Node n, StringBuilder sb) {
        if (n == null) { sb.append("# "); return; }
        sb.append(n.val).append(" ");
        ser(n.left, sb); ser(n.right, sb);
    }
    static Node deserialize(String s) {
        Deque<String> t = new ArrayDeque<>(Arrays.asList(s.split(" ")));
        return deser(t);
    }
    static Node deser(Deque<String> t) {
        String x = t.poll();
        if (x.equals("#")) return null;
        Node n = new Node(Integer.parseInt(x));
        n.left = deser(t); n.right = deser(t);
        return n;
    }

    public static void main(String[] args) {
        Node r = new Node(1);
        r.left = new Node(2); r.right = new Node(3);
        r.left.left = new Node(4); r.left.right = new Node(5);
        r.right.left = new Node(6); r.right.right = new Node(7);

        System.out.println("height       = " + height(r));
        System.out.println("balanced     = " + isBalanced(r));
        System.out.println("diameter     = " + diameter(r));

        Node p = new Node(-10);
        p.left = new Node(9); p.right = new Node(20);
        p.right.left = new Node(15); p.right.right = new Node(7);
        System.out.println("maxPathSum   = " + maxPathSum(p));

        Node p1 = r.left.left, p2 = r.left.right;
        System.out.println("LCA(4,5)     = " + lca(r, p1, p2).val);

        System.out.println("rightView    = " + rightSideView(r));
        System.out.println("vertical     = " + vertical(r));

        String s = serialize(r);
        Node d = deserialize(s);
        System.out.println("deser root   = " + d.val);
    }
}
