/**
 * Day 16 — Easy practice.
 *
 * Compile: javac src/day-16/PracticeEasy.java
 * Run    : java -cp src/day-16 PracticeEasy
 */
import java.util.*;

public class PracticeEasy {

    // Q1: Queue using two stacks
    static class QueueStack {
        Deque<Integer> in = new ArrayDeque<>(), out = new ArrayDeque<>();
        void push(int x) { in.push(x); }
        int pop() { if (out.isEmpty()) while (!in.isEmpty()) out.push(in.pop()); return out.pop(); }
    }

    // Q2: Stack using two queues
    static class StackQueue {
        Deque<Integer> q1 = new ArrayDeque<>(), q2 = new ArrayDeque<>();
        void push(int x) {
            q2.offer(x);
            while (!q1.isEmpty()) q2.offer(q1.poll());
            Deque<Integer> t = q1; q1 = q2; q2 = t;
        }
        int pop() { return q1.poll(); }
    }

    // Q3: First unique char in stream
    static int firstUnique(String s) {
        Map<Character, Integer> freq = new HashMap<>();
        Deque<Character> q = new ArrayDeque<>();
        int idx = -1;
        for (char c : s.toCharArray()) {
            freq.merge(c, 1, Integer::sum);
            q.offer(c);
            while (!q.isEmpty() && freq.get(q.peek()) > 1) q.poll();
            idx++;
        }
        return idx == -1 ? -1 : idx;
    }

    // Q4: Recent counter
    static class RecentCounter {
        Deque<Integer> q = new ArrayDeque<>();
        int ping(int t) {
            q.offer(t);
            while (!q.isEmpty() && q.peek() < t - 3000) q.poll();
            return q.size();
        }
    }

    // Q5: Circular queue
    static class MyCircularQueue {
        int[] a; int head = 0, tail = 0, size = 0;
        MyCircularQueue(int k) { a = new int[k]; }
        boolean enqueue(int x) {
            if (size == a.length) return false;
            a[tail] = x; tail = (tail + 1) % a.length; size++; return true;
        }
        int dequeue() { if (size == 0) return -1; int v = a[head]; head = (head + 1) % a.length; size--; return v; }
        int front() { return size == 0 ? -1 : a[head]; }
        int rear()  { return size == 0 ? -1 : a[(tail - 1 + a.length) % a.length]; }
        boolean isEmpty() { return size == 0; }
        boolean isFull()  { return size == a.length; }
    }

    public static void main(String[] args) {
        QueueStack qs = new QueueStack(); qs.push(1); qs.push(2);
        System.out.println("Q1 pop = " + qs.pop());

        StackQueue sq = new StackQueue(); sq.push(1); sq.push(2);
        System.out.println("Q2 pop = " + sq.pop());

        // Q3: simplified index of first unique in static string
        Map<Character, Integer> f = new HashMap<>();
        for (char c : "aabc".toCharArray()) f.merge(c, 1, Integer::sum);
        int idx = 0, ans = -1;
        for (char c : "aabc".toCharArray()) { if (f.get(c) == 1) { ans = idx; break; } idx++; }
        System.out.println("Q3 firstUnique = " + ans);

        RecentCounter rc = new RecentCounter();
        System.out.println("Q4 ping(1) = " + rc.ping(1) + " ping(100) = " + rc.ping(100) + " ping(3001) = " + rc.ping(3001));

        MyCircularQueue cq = new MyCircularQueue(3);
        cq.enqueue(1); cq.enqueue(2); cq.enqueue(3);
        System.out.println("Q5 full=" + cq.isFull() + " rear=" + cq.rear());
    }
}
