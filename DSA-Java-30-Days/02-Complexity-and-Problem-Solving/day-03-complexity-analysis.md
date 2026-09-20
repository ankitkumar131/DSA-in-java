# Day 3 — Time and Space Complexity

## 🎯 Learning Objectives

By the end of today, you should be able to:

- Define Big-O, Big-Ω, Big-Θ
- Identify the complexity of common code patterns
- Compare complexities using a table
- Analyse best, average, and worst case
- Understand auxiliary space vs total space
- Recognise when recursion uses extra space (the call stack)

---

# 1. Introduction

Two algorithms can solve the same problem. One takes 1 second on 10⁶ inputs. The other takes 3 hours. The difference isn't cleverness — it's **complexity analysis**.

Today you'll learn the language interviewers use to evaluate your solutions: Big-O.

---

# 2. Why Do We Need This?

Imagine two array-search algorithms:

- **Linear search** checks every element. For n = 10⁶ inputs → up to 10⁶ operations.
- **Binary search** halves the search space each step. For n = 10⁶ inputs → ~ 20 operations.

Both correct. But only one is acceptable for large n. Complexity analysis tells you which.

---

# 3. Core Concept — Big-O

Big-O describes the **upper bound** on how an algorithm's running time (or space) grows as input size `n` grows.

```
f(n) = O(g(n))  means
  there exist c, n0 such that
  f(n) <= c · g(n)  for all n >= n0
```

In plain English: "as n grows, this algorithm never takes more than a constant multiple of g(n) time."

---

# 4. Real-World Analogy

Big-O is a **speedometer for growth**. The actual speed depends on the car (hardware), but the speedometer tells you how fast you're accelerating. For algorithms, Big-O tells you how the time *grows* with input, not the actual milliseconds.

---

# 5. The 7 Big-O Classes You Must Know

| Big-O       | Name             | Example                              |
|-------------|------------------|--------------------------------------|
| O(1)        | Constant         | array index, hash lookup             |
| O(log n)    | Logarithmic      | binary search                        |
| O(n)        | Linear           | linear search, single loop           |
| O(n log n)  | Linearithmic     | merge sort, heap sort                |
| O(n²)       | Quadratic        | nested loops (bubble, selection sort)|
| O(2ⁿ)      | Exponential      | naive Fibonacci, subset enumeration  |
| O(n!)       | Factorial        | permutations (brute force)           |

Visualised (for n = 30):

```
O(1)              ── 1
O(log n)          ── 5
O(n)              ── 30
O(n log n)        ── 150
O(n²)             ── 900
O(2ⁿ)             ── 1,073,741,824   (already too slow)
O(n!)             ── 2.65 × 10³²     (impossible)
```

**Rule of thumb**:
- Anything O(n²) or worse is **too slow** for n ≥ 10⁵.
- O(n log n) is the typical sorting complexity.
- O(log n) and O(1) are ideal.

---

# 6. Big-Ω and Big-Θ

- **Big-O** = upper bound (worst case ceiling).
- **Big-Ω (Omega)** = lower bound (best case ceiling).
- **Big-Θ (Theta)** = tight bound (both upper and lower match).

Example: linear search.

| Case        | Behaviour            | Notation |
|-------------|----------------------|----------|
| Best        | first element        | Ω(1)     |
| Average     | middle element       | Θ(n)     |
| Worst       | last element / absent| O(n)     |

In interviews, "Big-O" usually means **worst-case** unless stated otherwise.

---

# 7. Syntax in Java — How to Read Code's Complexity

### 7.1 O(1) — constant

```java
int[] arr = {10, 20, 30};
int x = arr[1];           // O(1)
arr[0] = 5;               // O(1)
```

Reason: array indexing is a single multiplication + addition.

### 7.2 O(n) — linear

```java
for (int i = 0; i < n; i++) {
    System.out.println(arr[i]);
}
```

The loop runs `n` times. Body is O(1). Total: O(n).

### 7.3 O(n²) — quadratic

```java
for (int i = 0; i < n; i++) {
    for (int j = 0; j < n; j++) {
        System.out.println(arr[i] + "," + arr[j]);
    }
}
```

Outer loop runs `n` times. Inner runs `n` times per outer → n × n iterations.

### 7.4 O(log n)

```java
int lo = 0, hi = n - 1;
while (lo <= hi) {
    int mid = lo + (hi - lo) / 2;
    if (arr[mid] == target) return mid;
    else if (arr[mid] < target) lo = mid + 1;
    else hi = mid - 1;
}
```

Each iteration halves the search space → log₂(n) iterations.

### 7.5 O(n log n)

```java
// merge sort: T(n) = 2·T(n/2) + O(n)
// → O(n log n) by Master theorem
```

### 7.6 O(2ⁿ)

```java
int fib(int n) {
    if (n < 2) return n;
    return fib(n - 1) + fib(n - 2);   // two recursive calls
}
```

Recursion tree has ~ 2ⁿ nodes.

---

# 8. Asymptotic Rules

1. **Drop constants**: O(2n) → O(n).
2. **Drop lower-order terms**: O(n² + n) → O(n²).
3. **Different inputs → different variables**: O(a + b), not O(n).
4. **Recursive: count the work done at each level**.

Examples:

| Code                                     | Complexity |
|------------------------------------------|-----------:|
| `int x = a + b;`                         | O(1)       |
| Single `for` loop over n                 | O(n)       |
| Nested loops over n                      | O(n²)      |
| Two nested loops over n, sequential      | O(n²)      |
| Two nested loops over n, independent     | O(n²)      |
| Loop with `n` halved each iteration      | O(log n)   |
| Two independent loops over n             | O(n)       |
| Loop with inner halved iteration         | O(n log n) |

---

# 9. Amortised Analysis

Some operations are usually O(1) but occasionally O(n). Their **amortised** cost is O(1).

**Example**: `ArrayList.add(x)`.

- Usually O(1): just append.
- Sometimes O(n): when the internal array is full, it doubles in size.

Over n adds, total work is at most 2n → amortised O(1) per add.

---

# 10. Space Complexity

Space complexity counts **auxiliary** memory — extra space beyond the input.

### Examples:

```java
int sum(int[] arr) {
    int total = 0;            // O(1) extra
    for (int x : arr) total += x;
    return total;
}
```

```java
int[] copy(int[] arr) {
    int[] out = new int[arr.length];  // O(n) extra
    for (int i = 0; i < arr.length; i++) out[i] = arr[i];
    return out;
}
```

```java
void dfs(Node node) {
    if (node == null) return;
    dfs(node.left);            // recursion uses the call stack
    dfs(node.right);
}
// recursive depth up to height of tree → O(h) auxiliary
```

For recursion, **auxiliary space includes the call stack**.

---

# 11. The Complexity Cheat Sheet (print and pin to your wall)

| Data Structure       | Access   | Search   | Insert   | Delete   |
|----------------------|---------:|---------:|---------:|---------:|
| Array                | O(1)     | O(n)     | O(n)     | O(n)     |
| ArrayList            | O(1)     | O(n)     | O(n)     | O(n)     |
| Linked List          | O(n)     | O(n)     | O(1)*    | O(1)*    |
| HashMap              | O(1)*    | O(1)*    | O(1)*    | O(1)*    |
| BST (balanced)       | O(log n) | O(log n) | O(log n) | O(log n) |
| Heap                 | O(1) peek| O(n)     | O(log n) | O(log n) |

`*` = amortised / average.

| Algorithm            | Time       | Space      |
|----------------------|-----------:|-----------:|
| Linear search        | O(n)       | O(1)       |
| Binary search        | O(log n)   | O(1)       |
| Bubble sort          | O(n²)      | O(1)       |
| Merge sort           | O(n log n) | O(n)       |
| Quick sort           | O(n log n) avg | O(log n) |
| Heap sort            | O(n log n) | O(1)       |
| DFS / BFS (graph)    | O(V+E)     | O(V)       |
| Dijkstra             | O((V+E) log V) | O(V)   |
| Floyd-Warshall       | O(V³)      | O(V²)      |

---

# 12. Common Mistakes

1. **Confusing Big-O with actual time**. Big-O ignores constants. O(n) might still be slower than O(log n) for tiny n.
2. **Best-case trap**: saying "binary search is O(1)" because it sometimes finds the element immediately. Big-O means *worst-case* by default.
3. **Forgetting hidden costs**: `arr.contains(x)` on an `ArrayList` is O(n), not O(1).
4. **Nested loops ≠ O(n)**: nested over the same n is O(n²).
5. **Recursion space**: forgetting the call stack can under-report space complexity.
6. **String concatenation in a loop**: `String s = ""; for (...) s += x;` is O(n²) in some languages. Java optimises this somewhat with `StringBuilder`, but it's still a footgun.

---

# 13. Interview Questions

### Q1. Why do we drop constants?
Big-O describes growth rate. Constant factors depend on hardware and constant-time overheads we don't care about.

### Q2. What does Ω(n) mean?
Lower bound. Any algorithm for this problem requires at least Ω(n) time.

### Q3. When is Big-Θ the right answer?
When the best and worst cases grow at the same rate. E.g. linear search's average case is Θ(n).

### Q4. Why is binary search O(log n)?
Because each iteration halves the search space. log₂(n) halvings reach size 1.

### Q5. What is amortised O(1)?
An operation is O(n) occasionally but O(1) on average over many operations. E.g. `ArrayList.add`.

### Q6. What's the space complexity of recursive factorial?
O(n) — the call stack holds n frames.

### Q7. Is `HashMap.get` always O(1)?
Average O(1). Worst case O(n) (when all keys collide).

### Q8. Why is merge sort O(n log n)?
It splits in half log n times, and merging takes O(n) at each level → total O(n log n).

---

# 14. Practice Problems

## 🟢 Easy

### 1. Identify the Complexity

**Difficulty:** Easy
**Problem:** What is the Big-O of `for (i = 0; i < n; i++) sum += arr[i];` ?
**Expected answer:** O(n).
**Concept:** Single loop.

### 2. Nested Loop

**Difficulty:** Easy
**Problem:** What is the Big-O of two nested loops each running n times?
**Expected answer:** O(n²).
**Concept:** Quadratic.

### 3. Triple Nested Loop

**Difficulty:** Easy
**Problem:** Three nested loops each running n times?
**Expected answer:** O(n³).
**Concept:** Cubic.

### 4. Constant Operations

**Difficulty:** Easy
**Problem:** Complexity of: declare 5 ints, print them, add them?
**Expected answer:** O(1).
**Concept:** Constants.

### 5. Two Independent Loops

**Difficulty:** Easy
**Problem:** Two separate loops over n — total?
**Expected answer:** O(n), not O(2n).
**Concept:** Drop constants.

## 🟡 Medium

### 6. Loop Halving

**Difficulty:** Medium
**Problem:** `while (n > 1) n = n / 2;` runs how many times?
**Expected answer:** O(log n).
**Concept:** Halving.

### 7. Loop Doubling Inside Linear Loop

**Difficulty:** Medium
**Problem:** Outer loop runs n times; inner loop runs `log i` times. Total?
**Expected answer:** O(n log n).
**Concept:** Sum of logs.

### 8. Recursive Halving

**Difficulty:** Medium
**Problem:** `void f(int n) { if (n <= 1) return; f(n / 2); }` — depth?
**Expected answer:** O(log n) depth, O(log n) stack space.
**Concept:** Recursion.

### 9. Two Arrays

**Difficulty:** Medium
**Problem:** Loop over `a` of length `m`, then over `b` of length `n`. Total?
**Expected answer:** O(m + n).
**Concept:** Different inputs.

### 10. `StringBuilder.append` in a Loop

**Difficulty:** Medium
**Problem:** Looping n times, appending a constant-sized string. Total?
**Expected answer:** O(n) amortised.
**Concept:** Amortised.

## 🔴 Hard

### 11. Recursion Tree Size

**Difficulty:** Hard
**Problem:** `f(n) = 2 f(n-1) + 1` — total calls?
**Expected answer:** O(2ⁿ).
**Concept:** Branching recursion.

### 12. Merge Sort Levels

**Difficulty:** Hard
**Problem:** Merge sort: T(n) = 2 T(n/2) + O(n). Solve.
**Expected answer:** O(n log n) (Master theorem, case 2).
**Concept:** Recurrence.

### 13. Quicksort Worst Case

**Difficulty:** Hard
**Problem:** Quicksort picks the first element as pivot on already-sorted input. Worst-case complexity?
**Expected answer:** O(n²).
**Concept:** Pathological pivot.

### 14. Recursive Fibonacci

**Difficulty:** Hard
**Problem:** `fib(n) = fib(n-1) + fib(n-2)` — time and space?
**Expected answer:** Time O(2ⁿ), space O(n) (stack).
**Concept:** Exponential recursion.

### 15. Master Theorem Case 3

**Difficulty:** Hard
**Problem:** T(n) = 4 T(n/2) + O(n²). Solve.
**Expected answer:** O(n² log n) (case where a = bᵏ → multiply by log).
**Concept:** Advanced recurrence.

---

# 15. Practice Hints

## Easy
1. One loop → O(n).
2. n × n → O(n²).
3. n³ → O(n³).
4. Constant number of operations → O(1).
5. Sum, not nested → O(n + n) = O(n).
## Medium
6. Halving → log n.
7. Sum of log i ≈ n log n.
8. Each level halves n.
9. Different variables.
10. Array doubling is amortised O(1).
## Hard
11. Branching factor 2, depth n → 2ⁿ nodes.
12. Master theorem case 2.
13. Already sorted → bad pivot.
14. Tree size grows exponentially.
15. `a = 4`, `bᵏ = 4` → f(n) = nᵏ log n.

---

# 16. Revision Checklist

- [ ] Can define Big-O, Ω, Θ
- [ ] Know the 7 main complexity classes
- [ ] Can analyse loops, nested loops, recursion
- [ ] Understand amortised analysis
- [ ] Understand auxiliary vs total space
- [ ] Memorise the complexity cheatsheet
- [ ] Solved all 5 Easy
- [ ] Solved all 5 Medium
- [ ] Attempted all 5 Hard

---

# 17. Key Takeaways

- Big-O = worst-case growth rate.
- O(1) < O(log n) < O(n) < O(n log n) < O(n²) < O(2ⁿ) < O(n!).
- Drop constants and lower-order terms.
- Recursion uses O(depth) auxiliary space.
- Amortised O(1) ≠ always O(1).

Tomorrow: **Math & Problem-Solving Fundamentals** — GCD, primes, bit manipulation, and the problem-solving framework.
