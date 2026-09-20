/** Day 18 tiny project: tree height via recursion. */
public class TreeHeight {
    static class Node { int v; Node l, r; Node(int v) { this.v = v; } }
    static int height(Node u) { return u == null ? 0 : 1 + Math.max(height(u.l), height(u.r)); }
    public static void main(String[] args) {
        Node root = new Node(1);
        root.l = new Node(2); root.r = new Node(3);
        root.l.l = new Node(4); root.l.l.r = new Node(5);
        System.out.println("height = " + height(root));
    }
}
