# Day 20 — Heap & Priority Queue

## 🎯 Learning Objectives

By the end of today, you should be able to:

- Define min-heap and max-heap
- Explain heap as a complete binary tree stored in an array
- Implement heapify, insert, extract-min/max, peek
- Use Java's `PriorityQueue`
- Solve top-K, Kth largest, merge K sorted, median-from-stream

---

# 1. Introduction

A heap is a complete binary tree with the heap property: each parent is smaller (min-heap) or larger (max-heap) than its children.

In Java, `PriorityQueue` is a min-heap by default.

---

# 2. Why Do We Need This?

- O(log n) insert and extract.
- O(1) peek.
- Ideal for "always serve the smallest/largest next" — scheduling, Dijkstra, top-K.

---

# 3. Core Concept

### As an array (1-indexed)

```
         1                arr: [_, 1, 3, 5, 7, 9, 8]
       /   \               index: 1  2  3  4  5  6
      3     5
     / \   / \
    7   9 8
```

- `parent(i) = i / 2`
- `left(i)  = 2 * i`
- `right(i) = 2 * i + 1`

---

# 4. Real-World Analogy

An ER triage queue: the most critical patient (highest priority) is always treated first, regardless of arrival order.

---

# 5. Java Implementation — `HeapDemo.java`

```java
import java.util.*;

public class HeapDemo {

    /** Min-heap with array storage. */
    static class MinHeap {
        int[] a = new int[8];
        int size = 0;
        void offer(int x) {
            if (size == a.length) a = Arrays.copyOf(a, a.length * 2);
            a[++size] = x;
            siftUp(size);
        }
        int poll() {
            if (size == 0) throw new RuntimeException("empty");
            int v = a[1];
            a[1] = a[size--];
            siftDown(1);
            return v;
        }
        int peek() { return a[1]; }
        void siftUp(int i) {
            while (i > 1 && a[i] < a[i / 2]) { swap(i, i / 2); i /= 2; }
        }
        void siftDown(int i) {
            while (2 * i <= size) {
                int c = 2 * i;
                if (c + 1 <= size && a[c + 1] < a[c]) c++;
                if (a[i] <= a[c]) break;
                swap(i, c); i = c;
            }
        }
        void swap(int i, int j) { int t = a[i]; a[i] = a[j]; a[j] = t; }
    }

    /** Top-K frequent using min-heap of size K. */
    static int[] topKFrequent(int[] a, int k) {
        Map<Integer, Integer> m = new HashMap<>();
        for (int x : a) m.merge(x, 1, Integer::sum);
        PriorityQueue<Map.Entry<Integer, Integer>> min =
            new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));
        for (var e : m.entrySet()) {
            min.offer(e);
            if (min.size() > k) min.poll();
        }
        int[] out = new int[k];
        for (int i = 0; i < k; i++) out[i] = min.poll().getKey();
        return out;
    }

    /** Kth largest with min-heap of size K. */
    static int kthLargest(int[] a, int k) {
        PriorityQueue<Integer> min = new PriorityQueue<>();
        for (int x : a) { min.offer(x); if (min.size() > k) min.poll(); }
        return min.peek();
    }

    /** Median from data stream using two heaps. */
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
```

Walkthrough:

- `offer`: append at end, sift up.
- `poll`: take root, move last to root, sift down.
- `topKFrequent`: keep min-heap of size k by frequency; root is the kth-most-frequent.
- `MedianFinder`: max-heap holds lower half, min-heap holds upper half. Balance after each insert.

---

# 6. Java `PriorityQueue`

```java
PriorityQueue<Integer> min = new PriorityQueue<>();                 // min-heap
PriorityQueue<Integer> max = new PriorityQueue<>(Comparator.reverseOrder());
max.offer(3); max.offer(1); max.offer(5);
System.out.println(max.poll());   // 5
```

Important: `PriorityQueue.iterator()` does **not** return sorted order — only `peek/poll` give the heap order.

---

# 7. Common Mistakes

1. **Iterating a PriorityQueue expecting sorted output** — only the top is guaranteed.
2. **Wrong heap type for Kth** — Kth largest uses min-heap of size K; Kth smallest uses max-heap.
3. **Forgetting to rebalance** in MedianFinder.

---

# 8. Interview Questions

### Q1. Why array-based heap?
Complete binary tree ⇒ no gaps ⇒ array indices give parent/children in O(1).

### Q2. Build heap complexity?
O(n), not O(n log n).

### Q3. Why two heaps for median?
Each heap is balanced; median is always at the top of one or two peeks.

---

# 9. Practice Problems

## 🟢 Easy

### 1. Last Stone Weight (heap simulation)
**Input:** `[2,7,4,1,8]` → **Output:** `1`

### 2. Kth Largest
**Input:** `[3,2,1,5,6,4], k=2` → **Output:** `5`

### 3. Top K Frequent
**Input:** `[1,1,1,2,2,3], k=2` → **Output:** `[1,2]`

### 4. Min-Heap Implementation
Build / offer / poll.

### 5. PriorityQueue Demo
Insert 5 ints, peek/poll.

## 🟡 Medium

### 6. Kth Smallest
**Input:** `[7,10,4,3,20,15], k=3` → **Output:** `7`

### 7. Sort an Almost Sorted Array
**Input:** `k=2, arr=[6,5,3,2,8,10,9]` → sort.

### 8. Meeting Rooms II (min-heap)
**Input:** intervals → min rooms.

### 9. Reorganise String (heap)
**Input:** `"aab"` → **Output:** `"aba"`.

### 10. Ugly Number II (heap)
**Input:** `n=10` → **Output:** `12`.

## 🔴 Hard

### 11. Median from Data Stream
**Input:** stream → **Output:** median so far.

### 12. Merge K Sorted Lists
**Input:** k sorted lists → merged.

### 13. Smallest Number Range (K lists)
**Input:** k lists → smallest range covering at least one from each.

### 14. Sliding Window Median
**Input:** window → medians.

### 15. Trapping Rain Water II (heap)
BFS + heap.

---

# 10. Practice Hints

## Easy
1. Max-heap, smash top two.
2. Min-heap of size K.
3. Frequency map + heap.
4. Array + siftUp/siftDown.
5. Standard usage.

## Medium
6. Max-heap of size K.
7. Min-heap of size K.
8. Sort + heap of end times.
9. Max-heap by count.
10. Multiply by 2/3/5 with set.

## Hard
11. Two heaps.
12. Heap of (head, listIndex).
13. Heap of (value, row, col).
14. Lazy removal.
15. Heap from borders.

---

# 11. Revision Checklist

- [ ] Can implement heap operations
- [ ] Can use PriorityQueue
- [ ] Knows two-heap median
- [ ] Solved 5 Easy + 5 Medium + 5 Hard

---

# 12. Key Takeaways

- Heap = complete binary tree with heap property.
- O(1) peek, O(log n) insert/extract.
- Kth largest → min-heap of size K.
- Median stream → max-heap + min-heap.

Tomorrow: **Advanced Tree Problems**.
