/**
 * Day 19 — Hard practice.
 *
 * Compile: javac src/day-19/PracticeHard.java
 * Run    : java -cp src/day-19 PracticeHard
 */
import java.util.*;

public class PracticeHard {

    static class Node { int val; Node left, right; Node(int v) { val = v; } }

    // Q11: Recover BST
    static Node first, second, prev;
    static void recover(Node r) {
        first = second = prev = null; recoverIn(r);
        if (first != null && second != null) { int t = first.val; first.val = second.val; second.val = t; }
    }
    static void recoverIn(Node n) {
        if (n == null) return;
        recoverIn(n.left);
        if (prev != null && prev.val > n.val) { if (first == null) first = prev; second = n; }
        prev = n; recoverIn(n.right);
    }

    // Q12: Kth smallest iterative
    static int kthSmallest(Node r, int k) {
        Deque<Node> st = new ArrayDeque<>();
        Node cur = r;
        while (cur != null || !st.isEmpty()) {
            while (cur != null) { st.push(cur); cur = cur.left; }
            cur = st.pop();
            if (--k == 0) return cur.val;
            cur = cur.right;
        }
        return -1;
    }

    // Q13: serialize/deserialize BST
    static StringBuilder sb;
    static String serialize(Node r) { sb = new StringBuilder(); ser(r); return sb.toString(); }
    static void ser(Node n) {
        if (n == null) return;
        sb.append(n.val).append(" ");
        ser(n.left); ser(n.right);
    }
    static int idx;
    static Node deserialize(String data) {
        idx = 0; int[] a = Arrays.stream(data.trim().split(" ")).mapToInt(Integer::parseInt).toArray();
        return deser(a, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    static Node deser(int[] a, int lo, int hi) {
        if (idx >= a.length || a[idx] <= lo || a[idx] >= hi) return null;
        int v = a[idx++];
        Node n = new Node(v);
        n.left = deser(a, lo, v);
        n.right = deser(a, v, hi);
        return n;
    }

    // Q14: count smaller numbers after self (augmented BST)
    static class ANode {
        int val, count, leftSize;
        ANode left, right;
        ANode(int v) { val = v; count = 1; leftSize = 0; }
    }
    static int insertCount(ANode root, int v) {
        int smaller = 0;
        while (true) {
            if (v <= root.val) {
                root.leftSize++;
                if (root.left == null) { root.left = new ANode(v); break; }
                root = root.left;
            } else {
                smaller += root.count + root.leftSize;
                if (root.right == null) { root.right = new ANode(v); break; }
                root = root.right;
            }
        }
        return smaller;
    }
    static List<Integer> countSmaller(int[] a) {
        List<Integer> res = new ArrayList<>();
        if (a.length == 0) return res;
        ANode root = new ANode(a[a.length - 1]);
        res.add(0);
        for (int i = a.length - 2; i >= 0; i--) res.add(0, insertCount(root, a[i]));
        return res;
    }

    // Q15: median from data stream (two heaps)
    static class MedianFinder {
        PriorityQueue<Integer> low = new PriorityQueue<>(Comparator.reverseOrder());
        PriorityQueue<Integer> high = new PriorityQueue<>();
        void addNum(int n) {
            low.offer(n);
            high.offer(low.poll());
            if (low.size() < high.size()) low.offer(high.poll());
        }
        double findMedian() {
            return low.size() > high.size() ? low.peek() : (low.peek() + high.peek()) / 2.0;
        }
    }

    public static void main(String[] args) {
        // Q11
        Node bst = new Node(3); bst.left = new Node(2); bst.right = new Node(5);
        bst.left.left = new Node(1); bst.right.left = new Node(4);
        recover(bst);
        System.out.println("Q11 recovered root = " + bst.val);

        // Q12
        Node r = new Node(5); r.left = new Node(3); r.right = new Node(7);
        r.left.left = new Node(1); r.left.right = new Node(4);
        System.out.println("Q12 kth=3 = " + kthSmallest(r, 3));

        // Q13
        String s = serialize(r);
        Node d = deserialize(s);
        System.out.println("Q13 deser root = " + d.val);

        // Q14
        System.out.println("Q14 countSmaller = " + countSmaller(new int[]{5, 2, 6, 1}));

        // Q15
        MedianFinder mf = new MedianFinder();
        mf.addNum(1); mf.addNum(2);
        System.out.println("Q15 median = " + mf.findMedian());
        mf.addNum(3);
        System.out.println("Q15 median = " + mf.findMedian());
    }
}
