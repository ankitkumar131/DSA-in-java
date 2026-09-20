# Day 30 — Final Revision & Interview Preparation

## 🎯 Learning Objectives

By the end of today, you should be able to:

- Recognise any major DSA pattern on sight
- Quickly recall core Java implementations
- Walk through the standard interview communication script
- Avoid common interview mistakes

---

# 1. Introduction

This is the day to consolidate everything. No new material — just recall, drill, and prepare.

---

# 2. The 30-Day Cheat Sheet

| Day | Topic | Key takeaways |
|----:|-------|---------------|
| 1   | Java Foundations     | JVM entry, primitives, loops, methods, I/O |
| 2   | OOP                  | class/Object, this/static/final, inheritance, polymorphism |
| 3   | Complexity           | Big-O/Ω/Θ, drop constants, amortised |
| 4   | Math                 | GCD Euclidean, sieve, fast power, n&(n-1) |
| 5   | Arrays               | Kadane, two-pointer rotation |
| 6   | Strings              | StringBuilder, immutable, anagram |
| 7   | Two Pointers         | Sorted pair, in-place filtering |
| 8   | Sliding Window       | Fixed + variable window |
| 9   | Prefix Sum           | O(1) range query; subarray sum = K |
| 10  | Searching            | Binary search variants, lo+lo+lo+ |
| 11  | Sorting              | Merge, quick, counting, stable |
| 12  | Recursion            | Base case, memoisation |
| 13  | Linked List          | Fast/slow pointers, reverse |
| 14  | Adv. Linked Lists    | Floyd cycle start, LRU |
| 15  | Stack                | Monotonic stack, MinStack |
| 16  | Queue & Deque        | BFS, sliding-window max |
| 17  | Hashing              | HashMap/Set, collision, equals+hashCode |
| 18  | Binary Trees         | Traversal, height/size |
| 19  | BST                  | Insert/delete/validate, LCA |
| 20  | Heap                 | PriorityQueue, Kth largest |
| 21  | Adv. Tree Problems   | Diameter, LCA, vertical |
| 22  | Greedy               | Sort + local choice |
| 23  | Backtracking         | Choose/explore/undo |
| 24  | Graph Fundamentals   | BFS/DFS, components |
| 25  | BFS/DFS deep         | Cycle, bipartite, grid |
| 26  | Shortest Paths       | Dijkstra, Bellman-Ford, Floyd, MST |
| 27  | DP Fundamentals      | Memoisation, tabulation |
| 28  | DP Patterns          | Knapsack, LCS, LIS |
| 29  | Advanced DP          | Interval, tree, bitmask |
| 30  | Revision             | YOU ARE HERE |

---

# 3. The Pattern Recognition Cheat Sheet

```
Sorted array?
  → Binary search / Two Pointers / Sliding window

Subarray (contiguous)?
  → Sliding window / Prefix sum / Kadane

Frequency?
  → HashMap / HashSet

Top-K / Kth?
  → Heap / PriorityQueue / QuickSelect

Next greater / smaller?
  → Monotonic stack

Shortest path in UNWEIGHTED graph?
  → BFS

Shortest path with positive weights?
  → Dijkstra

Shortest path with negative weights?
  → Bellman-Ford

All-pairs shortest?
  → Floyd-Warshall

MST?
  → Kruskal (sparse) / Prim (dense)

Cycle in graph?
  → DFS with parent (undirected) / 3-colour (directed)
  → Kahn's algorithm (directed, by checking order size)

Tree traversal?
  → DFS (recursive) / BFS (level order)

Connected components?
  → BFS / DFS / Union-Find

Count all ways / min cost?
  → DP

Generate all combinations?
  → Backtracking

Shortest / longest in grid?
  → BFS / DFS + memo

Subsequence?
  → DP (LIS/LCS) or two pointers + sorting

String match with KMP?
  → KMP / Rabin-Karp

Interval scheduling / non-overlap?
  → Sort by end, greedy sweep

LCA?
  → Split-point (BST) / post-order (binary tree)
```

---

# 4. Java Implementation Quick Recall

### Binary search (no overflow)

```java
int lo = 0, hi = a.length - 1;
while (lo <= hi) {
    int mid = lo + (hi - lo) / 2;
    if (a[mid] == target) return mid;
    if (a[mid] < target) lo = mid + 1;
    else hi = mid - 1;
}
```

### Sliding window

```java
int lo = 0;
for (int hi = 0; hi < a.length; hi++) {
    // add a[hi]
    while (/* invalid */) { /* remove a[lo] */ lo++; }
    // update answer
}
```

### Linked list reverse

```java
Node prev = null, cur = head;
while (cur != null) {
    Node nxt = cur.next;
    cur.next = prev;
    prev = cur;
    cur = nxt;
}
```

### BFS

```java
Deque<Integer> q = new ArrayDeque<>();
boolean[] vis = new boolean[n];
q.offer(start); vis[start] = true;
while (!q.isEmpty()) {
    int u = q.poll();
    for (int v : g.adj.get(u))
        if (!vis[v]) { vis[v] = true; q.offer(v); }
}
```

### Dijkstra

```java
PriorityQueue<long[]> pq = new PriorityQueue<>((a,b) -> Long.compare(a[0], b[0]));
long[] dist = new long[n]; Arrays.fill(dist, INF); dist[src] = 0;
pq.offer(new long[]{0, src});
while (!pq.isEmpty()) {
    long[] cur = pq.poll();
    if (cur[0] > dist[(int) cur[1]]) continue;
    for (Edge e : g.get((int) cur[1]))
        if (cur[0] + e.w < dist[e.to]) { dist[e.to] = cur[0] + e.w; pq.offer(new long[]{dist[e.to], e.to}); }
}
```

### DP tabulation

```java
int[] dp = new int[n + 1];
dp[0] = base;
for (int i = 1; i <= n; i++)
    dp[i] = transition(dp[i - 1], ...);
```

### Backtracking

```java
void bt(int i, List<Choice> cur) {
    if (done) { record(cur); return; }
    for (Choice c : choices(i)) {
        if (valid(c)) {
            apply(c); bt(i + 1, cur); undo(c);
        }
    }
}
```

---

# 5. The Interview Script

When given a problem:

```
1. "Let me restate the problem in my own words."
2. "Here are 3 examples — minimum, typical, edge."
3. "Brute force: [describe]." Code it. Test.
4. "The bottleneck is X. The pattern that fits is Y."
5. "Here's the optimised version." Code it. Test.
6. "Time: O(?), Space: O(?)."
7. "Edge cases I tested: empty, single, all same."
```

Time-box:
- 5 min: clarify + examples + brute force
- 10 min: code brute force
- 15 min: optimised solution
- 5 min: test + complexity + edge cases

---

# 6. Common Interview Mistakes

| Mistake | Fix |
|---|---|
| Silent coding | Narrate constantly |
| No examples | Always give 3 |
| Skipping complexity | Always state it |
| Not testing | Run your code mentally |
| One attempt only | If stuck, restart |
| Ignoring hints | Take them gracefully |
| Wrong data structure | Justify your choice |
| Mutable key in HashMap | Use immutable key |

---

# 7. Java Gotchas to Remember

1. `int[] arr = new int[n]` — primitive, default 0.
2. `Integer[] arr` — boxed, default null.
3. `arr.length` (field) vs `s.length()` (method) vs `list.size()` (method).
4. `String.equals` for comparison; `==` is reference.
5. `ArrayList` resizes; pre-size if you know.
6. `PriorityQueue` is a **min-heap** by default.
7. `HashMap` is **not** thread-safe; use `ConcurrentHashMap`.
8. Use `long` for accumulators to avoid overflow.
9. `Math.abs(Integer.MIN_VALUE)` is still negative — beware.
10. `Integer.parseInt` throws on invalid input.

---

# 8. Behavioural / Soft Skills

- Have 2–3 STAR stories ready (Technical challenge, Conflict, Failure, Leadership).
- Know your top projects cold.
- Ask thoughtful questions at the end:
  - "What does success look like in the first 90 days?"
  - "What's the team's approach to code review?"
  - "What does the on-call rotation look like?"

---

# 9. Pre-Interview Checklist

- [ ] Solved 5+ problems from each major topic
- [ ] Can write binary search, BFS, DFS, Dijkstra, basic DP from memory
- [ ] Reviewed error log; no recurring mistakes
- [ ] Sleep at least 7 hours the night before
- [ ] Test your setup (camera, microphone, IDE)
- [ ] Have paper + pen ready
- [ ] Water bottle nearby

---

# 10. Practice Problems (capstone)

## 🟢 Easy

### 1. Two Sum
Re-solve in < 5 min.

### 2. Valid Parentheses
Re-solve.

### 3. Best Time to Buy and Sell Stock
Re-solve.

## 🟡 Medium

### 4. Group Anagrams
Re-solve.

### 5. Course Schedule
Re-solve.

### 6. Coin Change
Re-solve.

### 7. LRU Cache
Re-solve.

## 🔴 Hard

### 8. Word Ladder
Re-solve.

### 9. Trapping Rain Water
Re-solve.

### 10. Median from Data Stream
Re-solve.

---

# 11. Practice Hints

## Easy
1. HashMap value→index.
2. Stack of opens.
3. Track min so far.

## Medium
4. Sorted key.
5. Detect cycle via DFS / Kahn.
6. Unbounded knapsack.
7. HashMap + DLL.

## Hard
8. BFS over words.
9. Two-pointer max-of-min.
10. Two heaps.

---

# 12. The "Which Pattern Should I Use?" Decision Guide

```
PROBLEM
   │
   ├── Strings/arrays
   │      │
   │      ├── sorted → Binary Search / Two Pointers
   │      ├── contiguous segment → Sliding Window / Prefix Sum
   │      ├── frequency → HashMap
   │      ├── top-K → Heap
   │      ├── next greater → Monotonic Stack
   │      └── subsequence → DP (LIS/LCS)
   │
   ├── Linked List
   │      │
   │      ├── mid / cycle → Fast/slow pointers
   │      ├── reverse → Three-pointer
   │      ├── group reverse → Recursive group
   │      └── LRU → HashMap + DLL
   │
   ├── Trees
   │      │
   │      ├── traversal → DFS / BFS
   │      ├── BST ops → BST property
   │      ├── LCA → Split-point
   │      └── path sum → Post-order
   │
   ├── Graphs
   │      │
   │      ├── shortest path (unweighted) → BFS
   │      ├── shortest (weighted) → Dijkstra
   │      ├── cycle → DFS / Kahn
   │      ├── components → DFS/BFS/DSU
   │      └── MST → Kruskal/Prim
   │
   └── DP
          │
          ├── knapsack-style → 1D capacity
          ├── match strings → 2D
          ├── paths on grid → 2D
          └── interval → 2D with split point
```

---

# 13. Revision Checklist

- [ ] Can name 5+ patterns and pick them quickly
- [ ] Can code binary search, BFS, DFS, Dijkstra, basic DP from memory
- [ ] Knows all 8 Big-O classes
- [ ] Knows when to use HashMap vs TreeMap vs Heap
- [ ] Has rehearsed the interview script
- [ ] Has solved at least 50 problems in the last week

---

# 14. Final Encouragement

You have:
- ✅ Completed 30 days of structured learning
- ✅ Solved 450 practice problems
- ✅ Mastered 14 core Java implementations
- ✅ Built pattern recognition across all major topics

The course is over. The real practice begins now. Good luck — and remember: DSA is learned by **doing**, not reading. Keep solving.

---

# 15. Where to Go From Here

- **LeetCode**: https://leetcode.com — daily problem, contests
- **HackerRank**: https://hackerrank.com — topic-wise practice
- **Codeforces**: https://codeforces.com — contests
- **Books**: "Cracking the Coding Interview", "Algorithm Design Manual"
- **Mock interviews**: Pramp, interviewing.io

Stay consistent. Trust your preparation. You've earned it.
