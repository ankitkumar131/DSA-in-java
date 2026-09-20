# Day 27 — DP Fundamentals

## 🎯 Learning Objectives

By the end of today, you should be able to:

- Identify DP problems (overlapping subproblems + optimal substructure)
- Convert recursion → memoisation → tabulation
- Solve Fibonacci, climbing stairs, house robber, coin change
- Recognise top-down vs bottom-up

---

# 1. Introduction

Dynamic Programming (DP) solves problems by combining solutions to overlapping subproblems. It's essentially "memoised recursion".

---

# 2. Why Do We Need This?

Without DP, many natural recursive solutions are exponential. DP reduces them to polynomial time.

---

# 3. Core Concept — Two Properties

1. **Overlapping subproblems**: same subproblem solved many times.
2. **Optimal substructure**: optimal solution built from optimal sub-solutions.

---

# 4. Real-World Analogy

Like a chef who keeps the same mise en place ready instead of re-preparing it for every dish. Once computed, reuse.

---

# 5. Top-Down vs Bottom-Up

| | Top-Down (memoisation) | Bottom-Up (tabulation) |
|---|---|---|
| Style | Recursion + cache | Loop filling a table |
| Order | As needed | Iterative, base → top |
| Stack | Yes | No |
| Java feel | `Map<Integer,Integer>` | `int[] dp = new int[n+1]` |

---

# 6. Java Implementation — `DpDemo.java`

```java
import java.util.*;

public class DpDemo {

    /** Fibonacci: naive recursion vs memoised. */
    static long fibNaive(int n) {
        if (n < 2) return n;
        return fibNaive(n - 1) + fibNaive(n - 2);
    }
    static long fibMemo(int n, long[] memo) {
        if (n < 2) return n;
        if (memo[n] != 0) return memo[n];
        return memo[n] = fibMemo(n - 1, memo) + fibMemo(n - 2, memo);
    }
    static long fibIter(int n) {
        if (n < 2) return n;
        long a = 0, b = 1;
        for (int i = 2; i <= n; i++) { long c = a + b; a = b; b = c; }
        return b;
    }

    /** Climbing stairs: 1 or 2 steps. */
    static int climbStairs(int n) {
        if (n <= 2) return n;
        int a = 1, b = 2;
        for (int i = 3; i <= n; i++) { int c = a + b; a = b; b = c; }
        return b;
    }

    /** House robber: max sum of non-adjacent. */
    static int rob(int[] a) {
        if (a.length == 0) return 0;
        int prev = 0, cur = 0;
        for (int x : a) { int t = Math.max(cur, prev + x); prev = cur; cur = t; }
        return cur;
    }

    /** Coin change: min coins to make amount. */
    static int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;
        for (int i = 1; i <= amount; i++)
            for (int c : coins)
                if (c <= i) dp[i] = Math.min(dp[i], dp[i - c] + 1);
        return dp[amount] > amount ? -1 : dp[amount];
    }

    /** Coin change II: count combinations. */
    static int change(int amount, int[] coins) {
        int[] dp = new int[amount + 1]; dp[0] = 1;
        for (int c : coins)
            for (int i = c; i <= amount; i++) dp[i] += dp[i - c];
        return dp[amount];
    }

    public static void main(String[] args) {
        System.out.println("fib(40)         = " + fibMemo(40, new long[41]));
        System.out.println("fibIter(40)     = " + fibIter(40));
        System.out.println("climbStairs(10) = " + climbStairs(10));
        System.out.println("rob([2,7,9,3,1])= " + rob(new int[]{2,7,9,3,1}));
        System.out.println("coinChange 11   = " + coinChange(new int[]{1,2,5}, 11));
        System.out.println("change 5        = " + change(5, new int[]{1,2,5}));
    }
}
```

Walkthrough:

- **fibMemo**: check cache before computing. Top-down.
- **fibIter**: bottom-up with two variables. O(1) space.
- **climbStairs**: classic Fibonacci in disguise.
- **rob**: prev = dp[i-2], cur = dp[i-1]; new = max(cur, prev + a[i]).
- **coinChange**: dp[i] = min(dp[i-c] + 1) for c ≤ i.
- **change**: count combinations; outer loop over coins (so order doesn't matter).

---

# 7. How to Spot a DP Problem

| Signal | Use |
|---|---|
| "Count ways to ..." | DP |
| "Min/max cost to ..." | DP |
| "Is it possible to ..." | DP / BFS |
| Choices at each step | DP |
| Subproblems depend on previous | DP |

If the recursion has repeated subproblems, DP applies.

---

# 8. Common Mistakes

1. **Off-by-one in dp array bounds**.
2. **Wrong base case** (e.g., dp[0] = 1 for counting, but = 0 for min).
3. **Order of loops**: outer/inner choice affects whether order matters (combinations vs permutations).

---

# 9. Interview Questions

### Q1. Top-down vs bottom-up?
Top-down: recursive + memo. Bottom-up: iterative, builds from base.

### Q2. Space optimisation?
Often yes — keep only the last row/col needed.

### Q3. When is DP not applicable?
When subproblems are independent (use divide-and-conquer) or have no optimal substructure.

---

# 10. Practice Problems

## 🟢 Easy

### 1. Fibonacci
Standard.

### 2. Climbing Stairs
Standard.

### 3. Min Cost Climbing Stairs
**Input:** cost[] → min cost to reach top.

### 4. House Robber
Standard.

### 5. Pascal's Triangle
Standard.

## 🟡 Medium

### 6. Coin Change
Standard.

### 7. Coin Change II (combinations)
Standard.

### 8. 0/1 Knapsack
**Input:** weights, values, capacity → max value.

### 9. Unique Paths
**Input:** grid → count paths.

### 10. Decode Ways
**Input:** `s="226"` → **Output:** `3`.

## 🔴 Hard

### 11. Edit Distance
**Input:** `word1, word2` → min ops.

### 12. Longest Common Subsequence
Standard.

### 13. Longest Increasing Subsequence
Standard.

### 14. Matrix Chain Multiplication
Standard.

### 15. Burst Balloons
Standard.

---

# 11. Practice Hints

## Easy
1. `dp[n] = dp[n-1] + dp[n-2]`.
2. Same.
3. Two jumps from each.
4. Track prev/cur.
5. Build row by row.

## Medium
6. Unbounded knapsack.
7. Outer coin loop.
8. 0/1: each item once.
9. `dp[i][j] = dp[i-1][j] + dp[i][j-1]`.
10. Two ways per digit.

## Hard
11. Insert/delete/replace.
12. Match/don't match.
13. Patience sorting or DP.
14. Catalan's recurrence.
15. Interval DP.

---

# 12. Revision Checklist

- [ ] Can memoise recursion
- [ ] Can convert to tabulation
- [ ] Can identify DP problems
- [ ] Solved 5 Easy + 5 Medium + 5 Hard

---

# 13. Key Takeaways

- DP = recursion + cache.
- Two properties: overlapping subproblems + optimal substructure.
- Often space-optimisable to O(1) or O(n).

Tomorrow: **DP Patterns**.
