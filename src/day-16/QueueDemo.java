/**
 * Day 16 — Queue & Deque demo.
 *
 * Compile: javac src/day-16/QueueDemo.java
 * Run    : java -cp src/day-16 QueueDemo
 */
import java.util.*;

public class QueueDemo {

    static class CircularQueue {
        int[] a; int head = 0, tail = 0, size = 0;
        CircularQueue(int cap) { a = new int[cap]; }
        boolean enqueue(int x) {
            if (size == a.length) return false;
            a[tail] = x; tail = (tail + 1) % a.length; size++;
            return true;
        }
        int dequeue() {
            if (size == 0) return -1;
            int v = a[head]; head = (head + 1) % a.length; size--;
            return v;
        }
        boolean isEmpty() { return size == 0; }
    }

    static class LLQueue {
        static class Node { int data; Node next; }
        Node head, tail;
        void enqueue(int x) {
            Node n = new Node(); n.data = x;
            if (tail != null) tail.next = n;
            tail = n;
            if (head == null) head = n;
        }
        int dequeue() {
            int v = head.data; head = head.next;
            if (head == null) tail = null;
            return v;
        }
    }

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

    static List<Integer> bfs(List<List<Integer>> graph, int start) {
        List<Integer> order = new ArrayList<>();
        boolean[] visited = new boolean[graph.size()];
        Deque<Integer> q = new ArrayDeque<>();
        q.offer(start); visited[start] = true;
        while (!q.isEmpty()) {
            int u = q.poll();
            order.add(u);
            for (int v : graph.get(u)) if (!visited[v]) { visited[v] = true; q.offer(v); }
        }
        return order;
    }

    static int kthLargest(int[] a, int k) {
        PriorityQueue<Integer> min = new PriorityQueue<>();
        for (int x : a) { min.offer(x); if (min.size() > k) min.poll(); }
        return min.peek();
    }

    public static void main(String[] args) {
        CircularQueue cq = new CircularQueue(3);
        cq.enqueue(1); cq.enqueue(2); cq.enqueue(3);
        System.out.println("dequeue = " + cq.dequeue());
        cq.enqueue(4);
        System.out.println("dequeue = " + cq.dequeue());
        System.out.println("dequeue = " + cq.dequeue());

        System.out.println("maxSlidingWindow = " + Arrays.toString(maxSlidingWindow(new int[]{1,3,-1,-3,5,3,6,7}, 3)));
        System.out.println("kthLargest       = " + kthLargest(new int[]{3,2,1,5,6,4}, 2));

        List<List<Integer>> g = new ArrayList<>();
        for (int i = 0; i < 5; i++) g.add(new ArrayList<>());
        g.get(0).addAll(Arrays.asList(1, 2));
        g.get(1).addAll(Arrays.asList(3));
        g.get(2).addAll(Arrays.asList(3, 4));
        System.out.println("bfs from 0       = " + bfs(g, 0));
    }
}
