/**
 * Day 20 — Easy practice.
 *
 * Compile: javac src/day-20/PracticeEasy.java
 * Run    : java -cp src/day-20 PracticeEasy
 */
import java.util.*;

public class PracticeEasy {

    // Q1: last stone weight
    static int lastStoneWeight(int[] stones) {
        PriorityQueue<Integer> max = new PriorityQueue<>(Comparator.reverseOrder());
        for (int s : stones) max.offer(s);
        while (max.size() > 1) {
            int a = max.poll(), b = max.poll();
            if (a != b) max.offer(a - b);
        }
        return max.isEmpty() ? 0 : max.peek();
    }

    // Q2: kth largest
    static int kthLargest(int[] a, int k) {
        PriorityQueue<Integer> min = new PriorityQueue<>();
        for (int x : a) { min.offer(x); if (min.size() > k) min.poll(); }
        return min.peek();
    }

    // Q3: top k frequent
    static int[] topKFrequent(int[] a, int k) {
        Map<Integer, Integer> m = new HashMap<>();
        for (int x : a) m.merge(x, 1, Integer::sum);
        PriorityQueue<Map.Entry<Integer, Integer>> pq = new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));
        for (var e : m.entrySet()) { pq.offer(e); if (pq.size() > k) pq.poll(); }
        int[] out = new int[k];
        for (int i = 0; i < k; i++) out[i] = pq.poll().getKey();
        return out;
    }

    // Q4: min-heap usage demo
    static void heapDemo() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int v : new int[]{5, 3, 8, 1}) pq.offer(v);
        System.out.print("  heap poll: ");
        while (!pq.isEmpty()) System.out.print(pq.poll() + " ");
        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println("Q1 lastStone = " + lastStoneWeight(new int[]{2,7,4,1,8}));
        System.out.println("Q2 kthLar    = " + kthLargest(new int[]{3,2,1,5,6,4}, 2));
        System.out.println("Q3 topKFreq  = " + Arrays.toString(topKFrequent(new int[]{1,1,1,2,2,3}, 2)));
        System.out.println("Q4 heapDemo:");
        heapDemo();
        // Q5 trivial
        System.out.println("Q5 PQ.peek   = " + (new PriorityQueue<>(java.util.Arrays.asList(3,1,4,1,5,9,2,6))).peek());
    }
}
