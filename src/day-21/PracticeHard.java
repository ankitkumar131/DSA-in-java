/**
 * Day 21 — Hard practice.
 *
 * Compile: javac src/day-21/PracticeHard.java
 * Run    : java -cp src/day-21 PracticeHard
 */
import java.util.*;

public class PracticeHard {

    static class Node { int val; Node left, right; Node(int v) { val = v; } }

    // Q11: vertical order
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

    // Q12: serialize/deserialize
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

    // Q13: Recover BST
    static Node first, second, prev;
    static void recover(Node r) {
        first = second = prev = null; rec(r);
        if (first != null && second != null) { int t = first.val; first.val = second.val; second.val = t; }
    }
    static void rec(Node n) {
        if (n == null) return;
        rec(n.left);
        if (prev != null && prev.val > n.val) { if (first == null) first = prev; second = n; }
        prev = n; rec(n.right);
    }

    // Q14: max path sum (re-impl)
    static int maxPathSum(Node root) { int[] b = {Integer.MIN_VALUE}; mp(root, b); return b[0]; }
    static int mp(Node n, int[] b) {
        if (n == null) return 0;
        int l = Math.max(0, mp(n.left, b)), r = Math.max(0, mp(n.right, b));
        b[0] = Math.max(b[0], n.val + l + r);
        return n.val + Math.max(l, r);
    }

    // Q15: count complete tree nodes
    static int countNodes(Node root) {
        if (root == null) return 0;
        int hl = 0, hr = 0;
        for (Node l = root.left; l != null; l = l.left) hl++;
        for (Node r = root.right; r != null; r = r.right) hr++;
        if (hl == hr) return (1 << (hl + 1)) - 1;
        return 1 + countNodes(root.left) + countNodes(root.right);
    }

    public static void main(String[] args) {
        Node r = new Node(1);
        r.left = new Node(2); r.right = new Node(3);
        r.left.left = new Node(4); r.left.right = new Node(5);
        r.right.left = new Node(6); r.right.right = new Node(7);

        System.out.println("Q11 vertical     = " + vertical(r));

        String s = serialize(r);
        Node d = deserialize(s);
        System.out.println("Q12 deserialize  = " + d.val);

        Node bst = new Node(3); bst.left = new Node(2); bst.right = new Node(5);
        bst.left.left = new Node(1); bst.right.left = new Node(4);
        recover(bst);
        System.out.println("Q13 recovered root = " + bst.val);

        Node p = new Node(-10);
        p.left = new Node(9); p.right = new Node(20);
        p.right.left = new Node(15); p.right.right = new Node(7);
        System.out.println("Q14 maxPathSum   = " + maxPathSum(p));

        // Q15: build a perfect tree of height 3 → 2^4-1 = 15
        Node perfect = new Node(1);
        perfect.left = new Node(2); perfect.right = new Node(3);
        perfect.left.left = new Node(4); perfect.left.right = new Node(5);
        perfect.right.left = new Node(6); perfect.right.right = new Node(7);
        perfect.left.left.left = new Node(8); perfect.left.left.right = new Node(9);
        perfect.left.right.left = new Node(10); perfect.left.right.right = new Node(11);
        perfect.right.left.left = new Node(12); perfect.right.left.right = new Node(13);
        perfect.right.right.left = new Node(14); perfect.right.right.right = new Node(15);
        System.out.println("Q15 countNodes   = " + countNodes(perfect));
    }
}
