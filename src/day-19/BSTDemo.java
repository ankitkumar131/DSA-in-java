/**
 * Day 19 — BST demo.
 *
 * Compile: javac src/day-19/BSTDemo.java
 * Run    : java -cp src/day-19 BSTDemo
 */
import java.util.*;

public class BSTDemo {

    static class Node { int val; Node left, right; Node(int v) { val = v; } }

    static Node insert(Node root, int v) {
        if (root == null) return new Node(v);
        if (v < root.val) root.left = insert(root.left, v);
        else if (v > root.val) root.right = insert(root.right, v);
        return root;
    }

    static boolean search(Node root, int v) {
        if (root == null) return false;
        if (root.val == v) return true;
        return v < root.val ? search(root.left, v) : search(root.right, v);
    }

    static Node findMin(Node r) { while (r != null && r.left != null) r = r.left; return r; }
    static Node findMax(Node r) { while (r != null && r.right != null) r = r.right; return r; }

    static Node delete(Node root, int v) {
        if (root == null) return null;
        if (v < root.val) root.left = delete(root.left, v);
        else if (v > root.val) root.right = delete(root.right, v);
        else {
            if (root.left == null) return root.right;
            if (root.right == null) return root.left;
            Node succ = findMin(root.right);
            root.val = succ.val;
            root.right = delete(root.right, succ.val);
        }
        return root;
    }

    static boolean isValidBST(Node root) { return validate(root, Long.MIN_VALUE, Long.MAX_VALUE); }
    static boolean validate(Node n, long lo, long hi) {
        if (n == null) return true;
        if (n.val <= lo || n.val >= hi) return false;
        return validate(n.left, lo, n.val) && validate(n.right, n.val, hi);
    }

    static Node lca(Node root, int p, int q) {
        if (root == null) return null;
        if (p < root.val && q < root.val) return lca(root.left, p, q);
        if (p > root.val && q > root.val) return lca(root.right, p, q);
        return root;
    }

    static void inorder(Node r) {
        if (r == null) return;
        inorder(r.left); System.out.print(r.val + " "); inorder(r.right);
    }

    public static void main(String[] args) {
        int[] vals = {8, 3, 10, 1, 6, 14, 4, 7, 13};
        Node root = null;
        for (int v : vals) root = insert(root, v);

        System.out.print("inorder: "); inorder(root); System.out.println();
        System.out.println("search 6: " + search(root, 6));
        System.out.println("min: " + findMin(root).val);
        System.out.println("max: " + findMax(root).val);
        System.out.println("valid BST: " + isValidBST(root));

        root = delete(root, 3);
        System.out.print("after delete 3: "); inorder(root); System.out.println();
        System.out.println("LCA(1,4): " + lca(root, 1, 4).val);
        System.out.println("LCA(4,7): " + lca(root, 4, 7).val);
    }
}
