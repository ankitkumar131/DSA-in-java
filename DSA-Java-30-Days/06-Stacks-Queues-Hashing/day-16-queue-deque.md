# Day 16 — Queue & Deque

## 🎯 Learning Objectives

By the end of today, you should be able to:

- Explain FIFO and use cases
- Implement a queue (array + linked list)
- Implement a circular queue
- Use `ArrayDeque` as queue and deque
- Implement BFS using a queue (preview)
- Use `PriorityQueue` for top-K / min-stream

---

# 1. Introduction

A queue is FIFO (First-In-First-Out). It models "fair" processing: the first to arrive is the first to be served.

---

# 2. Why Do We Need This?

- BFS uses a queue.
- Scheduling (CPU, print, OS).
- Producer-consumer problems.
- Sliding-window max (with monotonic deque).

---

# 3. Core Concept

```
enqueue 1,2,3,4:
[1, 2, 3, 4]
 ^  ^        ^
head       tail

dequeue → 1
[_, 2, 3, 4]
 ^  ^  ^    ^
```

---

# 4. Real-World Analogy

A queue at a coffee shop: the first person in line is served first. Newcomers join the back.

---

# 5. Java Implementation — `QueueDemo.java`

```java
import java.util.*;

public class QueueDemo {

    /** Array queue with head/tail pointers (no wrap). */
    static class ArrayQueue {
        int[] a; int head = 0, tail = 0, size = 0;
        ArrayQueue(int cap) { a = new int[cap]; }
        void enqueue(int x) {
            if (size == a.length) throw new RuntimeException("full");
            a[tail++] = x; size++;
        }
        int dequeue() {
            if (size == 0) throw new RuntimeException("empty");
            int v = a[head++]; size--; return v;
        }
    }

    /** Circular queue. */
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

    /** LL queue. */
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

    /** Sliding-window maximum with deque. */
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

    /** BFS preview (level order). */
    static List<Integer> bfs(List<List<Integer>> graph, int start) {
        List<Integer> order = new ArrayList<>();
        boolean[] visited = new boolean[graph.size()];
        Deque<Integer> q = new ArrayDeque<>();
        q.offer(start); visited[start] = true;
        while (!q.isEmpty()) {
            int u = q.poll();
            order.add(u);
            for (int v : graph.get(u))
                if (!visited[v]) { visited[v] = true; q.offer(v); }
        }
        return order;
    }

    /** Priority queue demo — Kth largest. */
    static int kthLargest(int[] a, int k) {
        PriorityQueue<Integer> min = new PriorityQueue<>();
        for (int x : a) {
            min.offer(x);
            if (min.size() > k) min.poll();
        }
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
        System.out.println("kthLargest(2)    = " + kthLargest(new int[]{3,2,1,5,6,4}, 2));
        // BFS demo
        List<List<Integer>> g = new ArrayList<>();
        for (int i = 0; i < 5; i++) g.add(new ArrayList<>());
        g.get(0).addAll(Arrays.asList(1, 2));
        g.get(1).addAll(Arrays.asList(3));
        g.get(2).addAll(Arrays.asList(3, 4));
        System.out.println("bfs from 0       = " + bfs(g, 0));
    }
}
```

Walkthrough:

- `ArrayQueue`: simple, but `tail` never wraps → wasted space if many dequeues.
- `CircularQueue`: `tail = (tail + 1) % cap` reuses space.
- `LLQueue`: O(1) enqueue/dequeue, no fixed cap.
- `maxSlidingWindow`: monotonic deque of indices; front is always the max.
- `bfs`: classic level-order; visited prevents revisiting.
- `kthLargest`: a min-heap of size k — top of heap is the kth largest overall.

---

# 6. Dry Run — `maxSlidingWindow([1,3,-1,-3,5,3,6,7], k=3)`

| i | a[i] | deque (front→back, indices) | out |
|---|------|------------------------------|-----|
| 0 | 1    | [0]                          | -   |
| 1 | 3    | [1]                          | -   |
| 2 | -1   | [1, 2]                       | 3   |
| 3 | -3   | [1, 2, 3]                    | 3   |
| 4 | 5    | [4]                          | 5   |
| 5 | 3    | [4, 5]                       | 5   |
| 6 | 6    | [6]                          | 6   |
| 7 | 7    | [7]                          | 7   |

Result: `[3, 3, 5, 5, 6, 7]` ✓

---

# 7. Priority Queue

`PriorityQueue<Integer>` is a min-heap by default.

```java
PriorityQueue<Integer> min = new PriorityQueue<>();
min.offer(5); min.offer(2); min.offer(8);
min.poll();   // 2 (smallest)
```

Custom comparator:

```java
PriorityQueue<Integer> max = new PriorityQueue<>(Comparator.reverseOrder());
```

For **Kth largest**, keep a min-heap of size K. For **Kth smallest**, keep a max-heap of size K.

---

# 8. When to Use Each

| Need | Structure |
|---|---|
| FIFO processing | Queue / ArrayDeque |
| Sliding window max / min | Deque (monotonic) |
| Top K frequent / Kth largest | PriorityQueue |
| LIFO | Stack / ArrayDeque |
| BFS | Queue |
| DFS (iterative) | Stack |

---

# 9. Common Mistakes

1. **`java.util.Queue` is an interface** — use `LinkedList` or `ArrayDeque` (preferred) as implementation.
2. **Circular queue empty vs full**: track `size` to disambiguate.
3. **PriorityQueue is not sorted** — only `peek()` gives the smallest.

---

# 10. Interview Questions

### Q1. ArrayDeque vs LinkedList for queue?
`ArrayDeque` is faster (cache-friendly). Use it unless you need `null`s or specific LinkedList ops.

### Q2. Why use a min-heap for kth largest?
After processing all elements, the heap's smallest is the kth largest overall.

### Q3. Sliding window max time?
O(n) with monotonic deque.

---

# 11. Practice Problems

## 🟢 Easy

### 1. Implement Queue Using Stacks
**Input:** push 1,2; pop → **Output:** `1`

### 2. Implement Stack Using Queues
**Input:** push 1,2; pop → **Output:** `2`

### 3. First Unique Character in Stream
**Input:** stream "aabc" → first unique at index 2.

### 4. Number of Recent Calls
**Input:** calls at t=1, 100, 3001 → counts within 3000.

### 5. Design Circular Queue
Standard.

## 🟡 Medium

### 6. Sliding Window Maximum
**Input:** `[1,3,-1,-3,5,3,6,7], k=3` → **Output:** `[3,3,5,5,6,7]`

### 7. Kth Largest Element
**Input:** `[3,2,1,5,6,4], k=2` → **Output:** `5`

### 8. Top K Frequent Elements
**Input:** `[1,1,1,2,2,3], k=2` → **Output:** `[1,2]`

### 9. Binary Tree Level Order (BFS)
**Input:** tree → **Output:** `[[3],[9,20],[15,7]]`

### 10. Rotting Oranges (BFS)
**Input:** grid → **Output:** minutes.

## 🔴 Hard

### 11. Sliding Window Median
**Input:** `[1,3,-1,-3,5,3,6,7], k=3` → medians.

### 12. Shortest Subarray with Sum ≥ K
Reuse Day 9.

### 13. Trapping Rain Water II (2D BFS)
**Input:** heightmap → **Output:** water units.

### 14. Word Ladder (BFS)
**Input:** begin="hit", end="cog", list → **Output:** `5`

### 15. Shortest Path in Binary Matrix (BFS)
**Input:** grid → **Output:** shortest path length.

---

# 12. Practice Hints

## Easy
1. Two stacks (in, out).
2. One queue, rotate on push.
3. Queue + freq map.
4. Queue of timestamps.
5. Circular array with size tracking.

## Medium
6. Monotonic deque.
7. Min-heap of size K.
8. Bucket or heap.
9. Queue per level.
10. Multi-source BFS.

## Hard
11. Two heaps.
12. Prefix + monotonic deque.
13. BFS from borders.
14. BFS over word graph.
15. 8-direction BFS.

---

# 13. Revision Checklist

- [ ] Can implement queue (array / LL / circular)
- [ ] Can solve sliding-window maximum
- [ ] Can use PriorityQueue for top-K
- [ ] Knows BFS skeleton

---

# 14. Key Takeaways

- Queue = FIFO. Use `ArrayDeque`.
- Sliding window max = monotonic deque.
- PriorityQueue = min-heap by default.
- BFS = queue + visited.

Tomorrow: **Hashing**.
