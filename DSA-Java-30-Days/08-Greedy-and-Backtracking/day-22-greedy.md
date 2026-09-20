# Day 22 — Greedy Algorithms

## 🎯 Learning Objectives

By the end of today, you should be able to:

- Define greedy choice, local optimum, global optimum
- Identify when greedy works (and when it fails)
- Solve activity selection, jump game, gas station, interval problems
- Prove greedy choices via exchange arguments (intuition level)

---

# 1. Introduction

Greedy builds a solution step by step, always picking the option that looks best **right now**. It's the simplest paradigm but also the trickiest — greedy works only when local choices lead to a global optimum.

---

# 2. Why Do We Need This?

- Many optimisation problems have greedy solutions: scheduling, intervals, minimum coins (sometimes).
- Greedy is usually O(n log n) due to sorting; often optimal enough.

---

# 3. Core Concept

A greedy algorithm has two properties:

1. **Greedy-choice property**: a local optimum leads to a global optimum.
2. **Optimal substructure**: an optimal solution contains optimal solutions to subproblems.

If both hold, greedy works.

---

# 4. Real-World Analogy

To pay with the fewest coins, take the largest coin that doesn't exceed the remaining amount. Works for US-style coin systems (canonical). Fails for arbitrary denominations — counterexample: coins {1, 3, 4}, target 6 → greedy gives 4+1+1 = 3 coins, but optimal is 3+3 = 2.

---

# 5. Java Implementation — `GreedyDemo.java`

```java
import java.util.*;

public class GreedyDemo {

    /** Activity selection: max non-overlapping intervals. */
    static int activitySelection(int[][] iv) {
        Arrays.sort(iv, (a, b) -> Integer.compare(a[1], b[1]));
        int count = 0, end = Integer.MIN_VALUE;
        for (int[] x : iv) if (x[0] >= end) { count++; end = x[1]; }
        return count;
    }

    /** Jump game: can you reach the last index? */
    static boolean canJump(int[] a) {
        int reach = 0;
        for (int i = 0; i < a.length; i++) {
            if (i > reach) return false;
            reach = Math.max(reach, i + a[i]);
        }
        return true;
    }

    /** Gas station. */
    static int gasStation(int[] gas, int[] cost) {
        int total = 0, tank = 0, start = 0;
        for (int i = 0; i < gas.length; i++) {
            int diff = gas[i] - cost[i];
            total += diff; tank += diff;
            if (tank < 0) { start = i + 1; tank = 0; }
        }
        return total >= 0 ? start : -1;
    }

    /** Minimum platforms. */
    static int minPlatforms(int[] arr, int[] dep) {
        Arrays.sort(arr); Arrays.sort(dep);
        int plat = 1, max = 1, i = 1, j = 0;
        while (i < arr.length && j < dep.length) {
            if (arr[i] <= dep[j]) { plat++; i++; max = Math.max(max, plat); }
            else { plat--; j++; }
        }
        return max;
    }

    /** Fractional knapsack (allow fractions). */
    static double fractionalKnapsack(int[] w, int[] v, int cap) {
        Integer[] idx = new Integer[w.length];
        for (int i = 0; i < idx.length; i++) idx[i] = i;
        Arrays.sort(idx, (a, b) -> Double.compare((double) v[b] / w[b], (double) v[a] / w[a]));
        double total = 0;
        for (int i : idx) {
            if (cap >= w[i]) { cap -= w[i]; total += v[i]; }
            else { total += (double) cap * v[i] / w[i]; break; }
        }
        return total;
    }

    /** Merge intervals. */
    static int[][] merge(int[][] iv) {
        Arrays.sort(iv, (a, b) -> Integer.compare(a[0], b[0]));
        List<int[]> out = new ArrayList<>();
        for (int[] x : iv) {
            if (out.isEmpty() || out.get(out.size() - 1)[1] < x[0]) out.add(x);
            else out.get(out.size() - 1)[1] = Math.max(out.get(out.size() - 1)[1], x[1]);
        }
        return out.toArray(new int[0][]);
    }

    public static void main(String[] args) {
        System.out.println("activitySelection = " + activitySelection(new int[][]{{1,3},{2,4},{3,5},{5,7},{6,9}}));
        System.out.println("canJump           = " + canJump(new int[]{2,3,1,1,4}));
        System.out.println("gasStation        = " + gasStation(new int[]{1,2,3,4,5}, new int[]{3,4,5,1,2}));
        System.out.println("minPlatforms      = " + minPlatforms(new int[]{900, 940, 950, 1100, 1500, 1800}, new int[]{910, 1200, 1120, 1130, 1900, 2000}));
        System.out.println("fractional        = " + fractionalKnapsack(new int[]{10,20,30}, new int[]{60,100,120}, 50));
        System.out.println("merge             = " + Arrays.deepToString(merge(new int[][]{{1,3},{2,6},{8,10},{15,18}})));
    }
}
```

Walkthrough:

- **activitySelection**: sort by end time; greedy pick the next that starts after the last chosen end.
- **canJump**: maintain `reach` = farthest reachable index. If current index > reach, fail.
- **gasStation**: total gas ≥ total cost is necessary. Track where tank becomes negative — reset start.
- **minPlatforms**: classic "merge two sorted arrays" sweep.
- **fractionalKnapsack**: sort by value/weight descending; take as much as possible.
- **merge**: sort by start; merge overlapping.

---

# 6. When Greedy Works

A useful test: **think about whether an exchange argument holds**. Could you swap your greedy choice for some other choice and strictly improve? If no, greedy is correct.

Examples where greedy works:
- Activity selection (sort by end)
- Fractional knapsack
- Jump game (reach tracking)
- Gas station (tank tracking)

Examples where greedy fails:
- 0/1 knapsack (use DP)
- Coin change with arbitrary denominations (use DP)

---

# 7. Common Mistakes

1. **Assuming greedy always works** — try a counter-example.
2. **Wrong sort key** — sort by what determines greedy choice.
3. **Forgetting to sort** — many greedy problems need sorted input first.

---

# 8. Interview Questions

### Q1. Greedy vs DP?
Greedy: make a local choice, never look back. DP: consider all subproblems and combine.

### Q2. When does greedy fail?
When local optimum doesn't lead to global optimum (e.g., 0/1 knapsack).

### Q3. How to prove greedy?
Exchange argument or matroid structure (advanced).

---

# 9. Practice Problems

## 🟢 Easy

### 1. Assign Cookies
**Input:** kids greed, cookies → max satisfied.

### 2. Lemonade Change
**Input:** bills stream → can give change?

### 3. Best Time to Buy & Sell Stock II
**Input:** prices → max profit with multiple transactions.

### 4. Can Place Flowers
**Input:** flowerbed → can place n flowers?

### 5. Majority Element
**Input:** array → **Output:** element > n/2.

## 🟡 Medium

### 6. Jump Game
**Input:** `[2,3,1,1,4]` → **Output:** `true`.

### 7. Jump Game II
Min jumps to reach end.

### 8. Gas Station
**Input:** gas/cost → **Output:** start index.

### 9. Minimum Platforms
At railway station.

### 10. Partition Labels
**Input:** string → max partition size.

## 🔴 Hard

### 11. Candy
**Input:** ratings → min candies for higher-rated neighbors.

### 12. Insert Interval
Merge intervals with new one.

### 13. Merge Intervals
Standard.

### 14. Minimum Number of Arrows
**Input:** balloons → min arrows.

### 15. Task Scheduler
**Input:** tasks + cooldown → min time.

---

# 10. Practice Hints

## Easy
1. Sort both, greedy match.
2. Track $5/$10/$20.
3. Sum positive deltas.
4. Greedy scan with skips.
5. Boyer-Moore.

## Medium
6. Track reach.
7. BFS-style expansion.
8. Total + tank reset.
9. Two-pointer sweep.
10. Last-occurrence greedy.

## Hard
11. Two passes.
12. Insert + merge.
13. Sort + sweep.
14. Sort by end.
15. Max frequency + idle slots.

---

# 11. Revision Checklist

- [ ] Understand greedy-choice property
- [ ] Can solve activity selection
- [ ] Can solve jump game variants
- [ ] Knows when greedy fails

---

# 12. Key Takeaways

- Greedy: locally optimal, globally optimal (if problem allows).
- Sort often first; the sort key is the decision.
- Always validate with a counter-example.

Tomorrow: **Backtracking**.
