/**
 * Day 16 — Medium practice.
 *
 * Compile: javac src/day-16/PracticeMedium.java
 * Run    : java -cp src/day-16 PracticeMedium
 */
import java.util.*;

public class PracticeMedium {

    static int[] maxSlidingWindow(int[] a, int k) {
        int[] out = new int[a.length - k + 1];
        Deque<Integer> dq = new ArrayDeque<>();
        for (int i = 0; i < a.length; i++) {
            while (!dq.isEmpty() && dq.peekFirst() <= i - k) dq.pollFirst();
            while (!dq.isEmpty() && a[dq.peekLast()] <= a[i]) dq.pollLast();
            dq.offerLast(i);
            if (i >= k - 1) out[i - k + 1] = a[dq.peekFirst()];
        }
        return out;
    }

    static int kthLargest(int[] a, int k) {
        PriorityQueue<Integer> min = new PriorityQueue<>();
        for (int x : a) { min.offer(x); if (min.size() > k) min.poll(); }
        return min.peek();
    }

    static int[] topKFrequent(int[] a, int k) {
        Map<Integer, Integer> m = new HashMap<>();
        for (int x : a) m.merge(x, 1, Integer::sum);
        List<Integer>[] bucket = new List[a.length + 1];
        for (var e : m.entrySet()) {
            int freq = e.getValue();
            if (bucket[freq] == null) bucket[freq] = new ArrayList<>();
            bucket[freq].add(e.getKey());
        }
        int[] out = new int[k]; int idx = 0;
        for (int f = a.length; f >= 0 && idx < k; f--)
            if (bucket[f] != null) for (int x : bucket[f]) out[idx++] = x;
        return Arrays.copyOf(out, idx);
    }

    // Q9: level order (simple tree)
    static class TreeNode { int val; TreeNode left, right; TreeNode(int v) { val = v; } }
    static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> out = new ArrayList<>();
        if (root == null) return out;
        Deque<TreeNode> q = new ArrayDeque<>();
        q.offer(root);
        while (!q.isEmpty()) {
            List<Integer> level = new ArrayList<>();
            for (int sz = q.size(); sz > 0; sz--) {
                TreeNode n = q.poll(); level.add(n.val);
                if (n.left != null)  q.offer(n.left);
                if (n.right != null) q.offer(n.right);
            }
            out.add(level);
        }
        return out;
    }

    // Q10: rotting oranges
    static int orangesRotting(int[][] g) {
        Deque<int[]> q = new ArrayDeque<>();
        int fresh = 0;
        for (int i = 0; i < g.length; i++)
            for (int j = 0; j < g[0].length; j++) {
                if (g[i][j] == 2) q.offer(new int[]{i, j});
                if (g[i][j] == 1) fresh++;
            }
        int minutes = 0;
        int[][] dirs = {{-1,0},{1,0},{0,-1},{0,1}};
        while (fresh > 0 && !q.isEmpty()) {
            minutes++;
            for (int sz = q.size(); sz > 0; sz--) {
                int[] p = q.poll();
                for (int[] d : dirs) {
                    int ni = p[0] + d[0], nj = p[1] + d[1];
                    if (ni >= 0 && nj >= 0 && ni < g.length && nj < g[0].length && g[ni][nj] == 1) {
                        g[ni][nj] = 2; fresh--; q.offer(new int[]{ni, nj});
                    }
                }
            }
        }
        return fresh == 0 ? minutes : -1;
    }

    public static void main(String[] args) {
        System.out.println("Q6  maxWin       = " + Arrays.toString(maxSlidingWindow(new int[]{1,3,-1,-3,5,3,6,7}, 3)));
        System.out.println("Q7  kthLargest   = " + kthLargest(new int[]{3,2,1,5,6,4}, 2));
        System.out.println("Q8  topKFreq     = " + Arrays.toString(topKFrequent(new int[]{1,1,1,2,2,3}, 2)));

        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9); root.right = new TreeNode(20);
        root.right.left = new TreeNode(15); root.right.right = new TreeNode(7);
        System.out.println("Q9  levelOrder   = " + levelOrder(root));

        int[][] g = {{2,1,1},{1,1,0},{0,1,1}};
        System.out.println("Q10 rotting      = " + orangesRotting(g));
    }
}
