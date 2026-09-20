/**
 * Day 20 — Heap demo.
 *
 * Compile: javac src/day-20/HeapDemo.java
 * Run    : java -cp src/day-20 HeapDemo
 */
import java.util.*;

public class HeapDemo {

    static class MinHeap {
        int[] a = new int[8];
        int size = 0;
        void offer(int x) {
            if (size == a.length) a = Arrays.copyOf(a, a.length * 2);
            a[++size] = x; siftUp(size);
        }
        int poll() {
            if (size == 0) throw new RuntimeException("empty");
            int v = a[1]; a[1] = a[size--]; siftDown(1); return v;
        }
        int peek() { return a[1]; }
        void siftUp(int i) {
            while (i > 1 && a[i] < a[i / 2]) { int t = a[i]; a[i] = a[i / 2]; a[i / 2] = t; i /= 2; }
        }
        void siftDown(int i) {
            while (2 * i <= size) {
                int c = 2 * i;
                if (c + 1 <= size && a[c + 1] < a[c]) c++;
                if (a[i] <= a[c]) break;
                int t = a[i]; a[i] = a[c]; a[c] = t; i = c;
            }
        }
    }

    static int[] topKFrequent(int[] a, int k) {
        Map<Integer, Integer> m = new HashMap<>();
        for (int x : a) m.merge(x, 1, Integer::sum);
        PriorityQueue<Map.Entry<Integer, Integer>> pq = new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));
        for (var e : m.entrySet()) { pq.offer(e); if (pq.size() > k) pq.poll(); }
        int[] out = new int[k];
        for (int i = 0; i < k; i++) out[i] = pq.poll().getKey();
        return out;
    }

    static int kthLargest(int[] a, int k) {
        PriorityQueue<Integer> min = new PriorityQueue<>();
        for (int x : a) { min.offer(x); if (min.size() > k) min.poll(); }
        return min.peek();
    }

    static class MedianFinder {
        PriorityQueue<Integer> low = new PriorityQueue<>(Comparator.reverseOrder());
        PriorityQueue<Integer> high = new PriorityQueue<>();
        void addNum(int n) {
            low.offer(n); high.offer(low.poll());
            if (low.size() < high.size()) low.offer(high.poll());
        }
        double findMedian() {
            return low.size() > high.size() ? low.peek() : (low.peek() + high.peek()) / 2.0;
        }
    }

    public static void main(String[] args) {
        MinHeap h = new MinHeap();
        for (int v : new int[]{5, 3, 8, 1, 9, 2}) h.offer(v);
        System.out.print("poll order: ");
        while (h.size > 0) System.out.print(h.poll() + " ");
        System.out.println();

        System.out.println("topKFrequent = " + Arrays.toString(topKFrequent(new int[]{1,1,1,2,2,3}, 2)));
        System.out.println("kthLargest   = " + kthLargest(new int[]{3,2,1,5,6,4}, 2));

        MedianFinder mf = new MedianFinder();
        for (int v : new int[]{1, 2, 3}) mf.addNum(v);
        System.out.println("median = " + mf.findMedian());
    }
}
