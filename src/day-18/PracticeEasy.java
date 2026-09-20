/**
 * Day 18 — Easy practice.
 *
 * Compile: javac src/day-18/PracticeEasy.java
 * Run    : java -cp src/day-18 PracticeEasy
 */
import java.util.*;

public class PracticeEasy {

    static class Node { int val; Node left, right; Node(int v) { val = v; } }

    static List<Integer> preorder(Node r) {
        List<Integer> out = new ArrayList<>();
        pre(r, out); return out;
    }
    static void pre(Node n, List<Integer> out) {
        if (n == null) return;
        out.add(n.val); pre(n.left, out); pre(n.right, out);
    }

    static List<Integer> inorder(Node r) {
        List<Integer> out = new ArrayList<>();
        in(r, out); return out;
    }
    static void in(Node n, List<Integer> out) {
        if (n == null) return;
        in(n.left, out); out.add(n.val); in(n.right, out);
    }

    static List<Integer> postorder(Node r) {
        List<Integer> out = new ArrayList<>();
        post(r, out); return out;
    }
    static void post(Node n, List<Integer> out) {
        if (n == null) return;
        post(n.left, out); post(n.right, out); out.add(n.val);
    }

    static List<List<Integer>> levelOrder(Node r) {
        List<List<Integer>> out = new ArrayList<>();
        if (r == null) return out;
        Deque<Node> q = new ArrayDeque<>(); q.offer(r);
        while (!q.isEmpty()) {
            List<Integer> level = new ArrayList<>();
            for (int sz = q.size(); sz > 0; sz--) {
                Node n = q.poll(); level.add(n.val);
                if (n.left != null)  q.offer(n.left);
                if (n.right != null) q.offer(n.right);
            }
            out.add(level);
        }
        return out;
    }

    static int maxDepth(Node r) { return r == null ? 0 : 1 + Math.max(maxDepth(r.left), maxDepth(r.right)); }

    public static void main(String[] args) {
        Node r = new Node(1);
        r.left = new Node(2); r.right = new Node(3);
        r.left.left = new Node(4); r.left.right = new Node(5);
        r.right.right = new Node(6);

        System.out.println("Q1 preorder  = " + preorder(r));
        System.out.println("Q2 inorder   = " + inorder(r));
        System.out.println("Q3 postorder = " + postorder(r));
        System.out.println("Q4 level     = " + levelOrder(r));
        System.out.println("Q5 maxDepth  = " + maxDepth(r));
    }
}
