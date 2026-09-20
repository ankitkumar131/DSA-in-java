/**
 * Day 16 — Hard practice.
 *
 * Compile: javac src/day-16/PracticeHard.java
 * Run    : java -cp src/day-16 PracticeHard
 */
import java.util.*;

public class PracticeHard {

    // Q11: sliding window median (two heaps)
    static double[] medianSlidingWindow(int[] a, int k) {
        // max-heap low, min-heap high
        PriorityQueue<Integer> low = new PriorityQueue<>(Comparator.reverseOrder());
        PriorityQueue<Integer> high = new PriorityQueue<>();
        double[] out = new double[a.length - k + 1];
        for (int i = 0; i < a.length; i++) {
            int x = a[i];
            if (low.isEmpty() || x <= low.peek()) low.offer(x); else high.offer(x);
            // rebalance
            if (low.size() > high.size() + 1) high.offer(low.poll());
            else if (high.size() > low.size()) low.offer(high.poll());
            // remove out-of-window
            if (i >= k) {
                int out_x = a[i - k];
                if (out_x <= low.peek()) { low.remove(out_x); if (low.size() < high.size()) low.offer(high.poll()); }
                else                     { high.remove(out_x); if (high.size() > low.size()) high.offer(low.poll()); }
            }
            if (i >= k - 1) {
                if (k % 2 == 1) out[i - k + 1] = low.peek();
                else            out[i - k + 1] = ((double) low.peek() + high.peek()) / 2.0;
            }
        }
        return out;
    }

    // Q12: shortest subarray with sum >= k (with negatives)
    static int shortestSubarray(int[] nums, int k) {
        int n = nums.length;
        long[] p = new long[n + 1];
        for (int i = 0; i < n; i++) p[i + 1] = p[i] + nums[i];
        Deque<Integer> dq = new ArrayDeque<>();
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i <= n; i++) {
            while (!dq.isEmpty() && p[i] - p[dq.peekFirst()] >= k) ans = Math.min(ans, i - dq.pollFirst());
            while (!dq.isEmpty() && p[i] <= p[dq.peekLast()]) dq.pollLast();
            dq.offerLast(i);
        }
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    // Q13: trap rain water II (BFS from borders)
    static int trapRainWater(int[][] h) {
        if (h.length == 0) return 0;
        int m = h.length, n = h[0].length;
        boolean[][] visited = new boolean[m][n];
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[2] - b[2]); // [r,c,height]
        for (int i = 0; i < m; i++) {
            pq.offer(new int[]{i, 0, h[i][0]}); pq.offer(new int[]{i, n - 1, h[i][n - 1]});
            visited[i][0] = visited[i][n - 1] = true;
        }
        for (int j = 1; j < n - 1; j++) {
            pq.offer(new int[]{0, j, h[0][j]}); pq.offer(new int[]{m - 1, j, h[m - 1][j]});
            visited[0][j] = visited[m - 1][j] = true;
        }
        int water = 0; int[][] d = {{-1,0},{1,0},{0,-1},{0,1}};
        while (!pq.isEmpty()) {
            int[] cell = pq.poll();
            for (int[] dd : d) {
                int ni = cell[0] + dd[0], nj = cell[1] + dd[1];
                if (ni >= 0 && nj >= 0 && ni < m && nj < n && !visited[ni][nj]) {
                    visited[ni][nj] = true;
                    water += Math.max(0, cell[2] - h[ni][nj]);
                    pq.offer(new int[]{ni, nj, Math.max(cell[2], h[ni][nj])});
                }
            }
        }
        return water;
    }

    // Q14: word ladder (BFS)
    static int ladderLength(String begin, String end, List<String> list) {
        Set<String> dict = new HashSet<>(list);
        if (!dict.contains(end)) return 0;
        Deque<String> q = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();
        q.offer(begin); visited.add(begin);
        int level = 1;
        while (!q.isEmpty()) {
            for (int sz = q.size(); sz > 0; sz--) {
                String w = q.poll();
                if (w.equals(end)) return level;
                char[] c = w.toCharArray();
                for (int i = 0; i < c.length; i++) {
                    char orig = c[i];
                    for (char ch = 'a'; ch <= 'z'; ch++) {
                        if (ch == orig) continue;
                        c[i] = ch;
                        String nw = new String(c);
                        if (dict.contains(nw) && !visited.contains(nw)) { visited.add(nw); q.offer(nw); }
                    }
                    c[i] = orig;
                }
            }
            level++;
        }
        return 0;
    }

    // Q15: shortest path in binary matrix (8-direction BFS)
    static int shortestPathBinaryMatrix(int[][] g) {
        int n = g.length; if (g[0][0] != 0 || g[n - 1][n - 1] != 0) return -1;
        int[][] dirs = {{-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1}};
        Deque<int[]> q = new ArrayDeque<>();
        q.offer(new int[]{0, 0}); g[0][0] = 1;
        int steps = 0;
        while (!q.isEmpty()) {
            steps++;
            for (int sz = q.size(); sz > 0; sz--) {
                int[] p = q.poll();
                if (p[0] == n - 1 && p[1] == n - 1) return steps;
                for (int[] d : dirs) {
                    int ni = p[0] + d[0], nj = p[1] + d[1];
                    if (ni >= 0 && nj >= 0 && ni < n && nj < n && g[ni][nj] == 0) {
                        g[ni][nj] = 1; q.offer(new int[]{ni, nj});
                    }
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println("Q11 slidingMed  = " + Arrays.toString(medianSlidingWindow(new int[]{1,3,-1,-3,5,3,6,7}, 3)));
        System.out.println("Q12 shortSubarr = " + shortestSubarray(new int[]{2,-1,2}, 3));
        int[][] hm = {{1,4,3,1,3,2},{3,2,1,3,2,4},{2,3,3,2,3,1}};
        System.out.println("Q13 trap2D      = " + trapRainWater(hm));
        List<String> wl = Arrays.asList("hot","dot","dog","lot","log","cog");
        System.out.println("Q14 ladder      = " + ladderLength("hit", "cog", wl));
        int[][] grid = {{0,1},{1,0}};
        System.out.println("Q15 shortPath   = " + shortestPathBinaryMatrix(grid));
    }
}
