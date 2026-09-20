/** Day 19 tiny project: BST insert + in-order print. */
import java.util.*;
public class BstDemo {
    static class Node { int v; Node l, r; Node(int v) { this.v = v; } }
    static Node insert(Node u, int x) {
        if (u == null) return new Node(x);
        if (x < u.v) u.l = insert(u.l, x); else u.r = insert(u.r, x);
        return u;
    }
    static void inorder(Node u, List<Integer> out) {
        if (u == null) return;
        inorder(u.l, out); out.add(u.v); inorder(u.r, out);
    }
    public static void main(String[] args) {
        Node root = null;
        for (int x : new int[]{5, 3, 7, 1, 4, 6, 8}) root = insert(root, x);
        List<Integer> sorted = new ArrayList<>();
        inorder(root, sorted);
        System.out.println(sorted);
    }
}
