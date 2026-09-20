/**
 * Day 20 — Hard practice.
 *
 * Compile: javac src/day-20/PracticeHard.java
 * Run    : java -cp src/day-20 PracticeHard
 */
import java.util.*;

public class PracticeHard {

    // Q11: Median from data stream
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

    // Q12: Merge K sorted lists (simple ListNode, copy of Day 13)
    static class Node { int data; Node next; Node(int d) { data = d; } }
    static Node mergeKLists(Node[] lists) {
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.data));
        for (Node n : lists) if (n != null) pq.offer(n);
        Node dummy = new Node(0), tail = dummy;
        while (!pq.isEmpty()) {
            Node n = pq.poll();
            tail.next = n; tail = n;
            if (n.next != null) pq.offer(n.next);
        }
        return dummy.next;
    }

    // Q13: Smallest range covering K lists
    static int[] smallestRange(List<List<Integer>> lists) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        int curMax = Integer.MIN_VALUE;
        for (int i = 0; i < lists.size(); i++) {
            pq.offer(new int[]{lists.get(i).get(0), i, 0});
            curMax = Math.max(curMax, lists.get(i).get(0));
        }
        int[] best = {pq.peek()[0], curMax};
        while (true) {
            int[] top = pq.poll();
            int r = top[1], c = top[2];
            if (c + 1 == lists.get(r).size()) break;
            int next = lists.get(r).get(c + 1);
            pq.offer(new int[]{next, r, c + 1});
            curMax = Math.max(curMax, next);
            int[] cur = {pq.peek()[0], curMax};
            if (cur[1] - cur[0] < best[1] - best[0]) best = cur;
        }
        return best;
    }

    // Q14: Sliding window median (lazy removal)
    static double[] medianSlidingWindow(int[] a, int k) {
        PriorityQueue<Integer> low = new PriorityQueue<>(Comparator.reverseOrder());
        PriorityQueue<Integer> high = new PriorityQueue<>();
        Map<Integer, Integer> delayed = new HashMap<>();
        int n = a.length; double[] out = new double[n - k + 1];
        for (int i = 0; i < k; i++) addNum(a[i], low, high, delayed);
        out[0] = getMedian(low, high);
        for (int i = k; i < n; i++) {
            addNum(a[i], low, high, delayed);
            removeNum(a[i - k], low, high, delayed);
            prune(low, delayed); prune(high, delayed);
            balance(low, high);
            out[i - k + 1] = getMedian(low, high);
        }
        return out;
    }
    static void addNum(int n, PriorityQueue<Integer> low, PriorityQueue<Integer> high, Map<Integer, Integer> d) {
        if (low.isEmpty() || n <= low.peek()) low.offer(n); else high.offer(n);
    }
    static void removeNum(int n, PriorityQueue<Integer> low, PriorityQueue<Integer> high, Map<Integer, Integer> d) {
        d.merge(n, 1, Integer::sum);
    }
    static void prune(PriorityQueue<Integer> pq, Map<Integer, Integer> d) {
        while (!pq.isEmpty() && d.getOrDefault(pq.peek(), 0) > 0) {
            d.merge(pq.peek(), -1, Integer::sum);
            if (d.get(pq.peek()) == 0) d.remove(pq.peek());
            pq.poll();
        }
    }
    static void balance(PriorityQueue<Integer> low, PriorityQueue<Integer> high) {
        if (low.size() > high.size() + 1) high.offer(low.poll());
        else if (low.size() < high.size()) low.offer(high.poll());
    }
    static double getMedian(PriorityQueue<Integer> low, PriorityQueue<Integer> high) {
        return low.size() > high.size() ? low.peek() : ((double) low.peek() + high.peek()) / 2.0;
    }

    public static void main(String[] args) {
        MedianFinder mf = new MedianFinder();
        mf.addNum(1); mf.addNum(2);
        System.out.println("Q11 median(1,2) = " + mf.findMedian());
        mf.addNum(3);
        System.out.println("Q11 median(1,2,3) = " + mf.findMedian());

        Node n1 = new Node(1); n1.next = new Node(4); n1.next.next = new Node(5);
        Node n2 = new Node(1); n2.next = new Node(3); n2.next.next = new Node(4);
        Node n3 = new Node(2); n3.next = new Node(6);
        Node merged = mergeKLists(new Node[]{n1, n2, n3});
        StringBuilder sb = new StringBuilder("Q12 merged: ");
        for (Node c = merged; c != null; c = c.next) sb.append(c.data).append(" ");
        System.out.println(sb);

        List<List<Integer>> lists = List.of(List.of(4,10,15,24,26), List.of(0,9,12,20), List.of(5,18,22,30));
        System.out.println("Q13 smallestRange = " + Arrays.toString(smallestRange(lists)));

        System.out.println("Q14 slidingMed = " + Arrays.toString(medianSlidingWindow(new int[]{1,3,-1,-3,5,3,6,7}, 3)));
    }
}
