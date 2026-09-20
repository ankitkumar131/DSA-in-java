/**
 * Day 19 — Medium practice.
 *
 * Compile: javac src/day-19/PracticeMedium.java
 * Run    : java -cp src/day-19 PracticeMedium
 */
import java.util.*;

public class PracticeMedium {

    static class Node { int val; Node left, right; Node(int v) { val = v; } }

    static Node insert(Node r, int v) {
        if (r == null) return new Node(v);
        if (v < r.val) r.left = insert(r.left, v);
        else if (v > r.val) r.right = insert(r.right, v);
        return r;
    }

    static Node delete(Node root, int v) {
        if (root == null) return null;
        if (v < root.val) root.left = delete(root.left, v);
        else if (v > root.val) root.right = delete(root.right, v);
        else {
            if (root.left == null) return root.right;
            if (root.right == null) return root.left;
            Node cur = root.right;
            while (cur.left != null) cur = cur.left;
            root.val = cur.val;
            root.right = delete(root.right, cur.val);
        }
        return root;
    }

    static Node lca(Node root, int p, int q) {
        while (root != null) {
            if (p < root.val && q < root.val) root = root.left;
            else if (p > root.val && q > root.val) root = root.right;
            else return root;
        }
        return null;
    }

    static Node sortedArrayToBST(int[] a) { return sab(a, 0, a.length - 1); }
    static Node sab(int[] a, int lo, int hi) {
        if (lo > hi) return null;
        int mid = lo + (hi - lo) / 2;
        Node n = new Node(a[mid]);
        n.left = sab(a, lo, mid - 1);
        n.right = sab(a, mid + 1, hi);
        return n;
    }

    static boolean twoSumBST(Node root, int k) {
        Set<Integer> seen = new HashSet<>();
        return dfs(root, k, seen);
    }
    static boolean dfs(Node n, int k, Set<Integer> seen) {
        if (n == null) return false;
        if (seen.contains(k - n.val)) return true;
        seen.add(n.val);
        return dfs(n.left, k, seen) || dfs(n.right, k, seen);
    }

    static Node inorderSuccessor(Node root, Node p) {
        Node succ = null;
        while (root != null) {
            if (p.val < root.val) { succ = root; root = root.left; }
            else root = root.right;
        }
        return succ;
    }

    public static void main(String[] args) {
        int[] vals = {5, 3, 7, 1, 4, 6, 8};
        Node root = null; for (int v : vals) root = insert(root, v);

        root = delete(root, 3);
        System.out.println("Q6  delete 3 done");

        int[] a = {5,3,7,1,4,6,8};
        Node r = null; for (int v : a) r = insert(r, v);
        System.out.println("Q7  LCA(1,4) = " + lca(r, 1, 4).val);
        System.out.println("Q7  LCA(1,8) = " + lca(r, 1, 8).val);

        Node bst = sortedArrayToBST(new int[]{-10,-3,0,5,9});
        System.out.println("Q8  sortedToBST root = " + bst.val);

        System.out.println("Q9  twoSumBST(9) = " + twoSumBST(r, 9));

        System.out.println("Q10 successor of 4 = " + (inorderSuccessor(r, new Node(4)) != null ? inorderSuccessor(r, new Node(4)).val : "null"));
    }
}
