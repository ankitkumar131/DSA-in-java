import java.util.*;

/**
 * Day 30 — Hard capstone solutions.
 * Word Ladder / Trapping Rain Water / Median from Data Stream.
 */
public class PracticeHard {

    // Word Ladder — BFS over word graph
    public static int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> dict = new HashSet<>(wordList);
        if (!dict.contains(endWord)) return 0;
        Deque<String> q = new ArrayDeque<>();
        Set<String> seen = new HashSet<>();
        q.offer(beginWord); seen.add(beginWord);
        int steps = 1;
        while (!q.isEmpty()) {
            int sz = q.size();
            for (int i = 0; i < sz; i++) {
                String w = q.poll();
                if (w.equals(endWord)) return steps;
                char[] a = w.toCharArray();
                for (int j = 0; j < a.length; j++) {
                    char orig = a[j];
                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == orig) continue;
                        a[j] = c;
                        String n = new String(a);
                        if (dict.contains(n) && !seen.contains(n)) { seen.add(n); q.offer(n); }
                    }
                    a[j] = orig;
                }
            }
            steps++;
        }
        return 0;
    }

    // Trapping Rain Water — two-pointer max-of-min
    public static int trap(int[] h) {
        int l = 0, r = h.length - 1, lMax = 0, rMax = 0, water = 0;
        while (l < r) {
            if (h[l] < h[r]) {
                lMax = Math.max(lMax, h[l]);
                water += lMax - h[l++];
            } else {
                rMax = Math.max(rMax, h[r]);
                water += rMax - h[r--];
            }
        }
        return water;
    }

    // MedianFinder — two heaps
    static class MedianFinder {
        PriorityQueue<Integer> lo = new PriorityQueue<>(Collections.reverseOrder()); // max
        PriorityQueue<Integer> hi = new PriorityQueue<>(); // min
        public void addNum(int num) {
            lo.offer(num);
            hi.offer(lo.poll());
            if (lo.size() < hi.size()) lo.offer(hi.poll());
        }
        public double findMedian() {
            return lo.size() > hi.size() ? lo.peek() : ((double) lo.peek() + hi.peek()) / 2.0;
        }
    }

    public static void main(String[] args) {
        List<String> wordList = Arrays.asList("hot","dot","dog","lot","log","cog");
        System.out.println("ladderLength hit->cog = " + ladderLength("hit", "cog", wordList));

        System.out.println("trap [0,1,0,2,1,0,1,3,2,1,2,1] = " + trap(new int[]{0,1,0,2,1,0,1,3,2,1,2,1}));

        MedianFinder mf = new MedianFinder();
        for (int n : new int[]{1,2,3,4,5}) mf.addNum(n);
        System.out.println("median [1..5] = " + mf.findMedian());
    }
}
