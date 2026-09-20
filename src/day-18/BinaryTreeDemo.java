/**
 * Day 18 — Binary tree demo.
 *
 * Compile: javac src/day-18/BinaryTreeDemo.java
 * Run    : java -cp src/day-18 BinaryTreeDemo
 */
import java.util.*;

public class BinaryTreeDemo {

    static class Node { int val; Node left, right; Node(int v) { val = v; } }

    static void preorder(Node root) {
        if (root == null) return;
        System.out.print(root.val + " ");
        preorder(root.left); preorder(root.right);
    }
    static void inorder(Node root) {
        if (root == null) return;
        inorder(root.left); System.out.print(root.val + " "); inorder(root.right);
    }
    static void postorder(Node root) {
        if (root == null) return;
        postorder(root.left); postorder(root.right); System.out.print(root.val + " ");
    }

    static List<Integer> preorderIter(Node root) {
        List<Integer> out = new ArrayList<>();
        if (root == null) return out;
        Deque<Node> st = new ArrayDeque<>();
        st.push(root);
        while (!st.isEmpty()) {
            Node n = st.pop(); out.add(n.val);
            if (n.right != null) st.push(n.right);
            if (n.left  != null) st.push(n.left);
        }
        return out;
    }

    static List<Integer> inorderIter(Node root) {
        List<Integer> out = new ArrayList<>();
        Deque<Node> st = new ArrayDeque<>();
        Node cur = root;
        while (cur != null || !st.isEmpty()) {
            while (cur != null) { st.push(cur); cur = cur.left; }
            cur = st.pop(); out.add(cur.val); cur = cur.right;
        }
        return out;
    }

    static List<List<Integer>> levelOrder(Node root) {
        List<List<Integer>> out = new ArrayList<>();
        if (root == null) return out;
        Deque<Node> q = new ArrayDeque<>(); q.offer(root);
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

    static int height(Node root) { return root == null ? -1 : 1 + Math.max(height(root.left), height(root.right)); }
    static int size(Node root)   { return root == null ? 0  : 1 + size(root.left) + size(root.right); }
    static int max(Node root)    { return root == null ? Integer.MIN_VALUE : Math.max(root.val, Math.max(max(root.left), max(root.right))); }

    public static void main(String[] args) {
        Node root = new Node(1);
        root.left = new Node(2); root.right = new Node(3);
        root.left.left = new Node(4); root.left.right = new Node(5);
        root.right.right = new Node(6);

        System.out.print("preorder:  "); preorder(root);  System.out.println();
        System.out.print("inorder:   "); inorder(root);   System.out.println();
        System.out.print("postorder: "); postorder(root); System.out.println();

        System.out.println("preorderIter = " + preorderIter(root));
        System.out.println("inorderIter  = " + inorderIter(root));
        System.out.println("levelOrder   = " + levelOrder(root));
        System.out.println("height       = " + height(root));
        System.out.println("size         = " + size(root));
        System.out.println("max          = " + max(root));
    }
}
