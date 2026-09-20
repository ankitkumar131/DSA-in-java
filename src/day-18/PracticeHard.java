/**
 * Day 18 — Hard practice.
 *
 * Compile: javac src/day-18/PracticeHard.java
 * Run    : java -cp src/day-18 PracticeHard
 */
import java.util.*;

public class PracticeHard {

    static class Node { int val; Node left, right; Node(int v) { val = v; } }

    // Q11: Vertical order traversal
    static List<List<Integer>> verticalOrder(Node root) {
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

    // Q12: Recover BST (two nodes swapped)
    static Node first, second, prev;
    static void recover(Node r) {
        first = second = prev = null;
        recoverIn(r);
        if (first != null && second != null) {
            int t = first.val; first.val = second.val; second.val = t;
        }
    }
    static void recoverIn(Node n) {
        if (n == null) return;
        recoverIn(n.left);
        if (prev != null && prev.val > n.val) {
            if (first == null) first = prev;
            second = n;
        }
        prev = n;
        recoverIn(n.right);
    }

    // Q13: Serialize / Deserialize (preorder)
    static String serialize(Node r) {
        StringBuilder sb = new StringBuilder();
        ser(r, sb);
        return sb.toString();
    }
    static void ser(Node n, StringBuilder sb) {
        if (n == null) { sb.append("# "); return; }
        sb.append(n.val).append(" ");
        ser(n.left, sb); ser(n.right, sb);
    }
    static Node deserialize(String data) {
        Deque<String> tokens = new ArrayDeque<>(Arrays.asList(data.split(" ")));
        return deser(tokens);
    }
    static Node deser(Deque<String> t) {
        String s = t.poll();
        if (s.equals("#")) return null;
        Node n = new Node(Integer.parseInt(s));
        n.left = deser(t); n.right = deser(t);
        return n;
    }

    // Q14: Morris inorder (O(1) space)
    static List<Integer> morrisInorder(Node root) {
        List<Integer> out = new ArrayList<>();
        Node cur = root;
        while (cur != null) {
            if (cur.left == null) { out.add(cur.val); cur = cur.right; }
            else {
                Node pre = cur.left;
                while (pre.right != null && pre.right != cur) pre = pre.right;
                if (pre.right == null) { pre.right = cur; cur = cur.left; }
                else { pre.right = null; out.add(cur.val); cur = cur.right; }
            }
        }
        return out;
    }

    // Q15: Max path sum
    static int maxPathSum(Node root) {
        int[] best = {Integer.MIN_VALUE};
        pathSum(root, best);
        return best[0];
    }
    static int pathSum(Node n, int[] best) {
        if (n == null) return 0;
        int left = Math.max(0, pathSum(n.left, best));
        int right = Math.max(0, pathSum(n.right, best));
        best[0] = Math.max(best[0], n.val + left + right);
        return n.val + Math.max(left, right);
    }

    public static void main(String[] args) {
        Node r = new Node(3);
        r.left = new Node(9); r.right = new Node(20);
        r.right.left = new Node(15); r.right.right = new Node(7);

        System.out.println("Q11 vertical = " + verticalOrder(r));

        Node bst = new Node(3); bst.left = new Node(2); bst.right = new Node(5);
        bst.left.left = new Node(1); bst.right.left = new Node(4);
        recover(bst);
        System.out.println("Q12 recovered root = " + bst.val);

        Node s = serialize(r);
        Node d = deserialize(s);
        System.out.println("Q13 deserialize root = " + d.val);

        System.out.println("Q14 morris = " + morrisInorder(r));

        Node p = new Node(-10);
        p.left = new Node(9); p.right = new Node(20);
        p.right.left = new Node(15); p.right.right = new Node(7);
        System.out.println("Q15 maxPath = " + maxPathSum(p));
    }
}
