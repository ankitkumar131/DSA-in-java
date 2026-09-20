/** Day 21 tiny project: compute tree diameter. */
public class Diameter {
    static class Node { int v; Node l, r; Node(int v) { this.v = v; } }
    static int best = 0;
    static int depth(Node u) {
        if (u == null) return 0;
        int L = depth(u.l), R = depth(u.r);
        best = Math.max(best, L + R);
        return 1 + Math.max(L, R);
    }
    public static void main(String[] args) {
        Node root = new Node(1);
        root.l = new Node(2); root.r = new Node(3);
        root.l.l = new Node(4); root.l.r = new Node(5);
        root.l.l.l = new Node(6);
        depth(root);
        System.out.println("diameter = " + best);
    }
}
