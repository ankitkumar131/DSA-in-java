# Day 28 — DP Patterns

## 🎯 Learning Objectives

By the end of today, you should be able to:

- Recognise the canonical DP patterns
- Solve 0/1 knapsack, unbounded knapsack, subset sum
- Compute LCS, LIS, edit distance
- Solve grid DP problems

---

# 1. Introduction

Most DP problems reduce to one of a handful of patterns. Today we catalogue them.

---

# 2. The Five Patterns

### 2.1 0/1 Knapsack

Each item taken at most once. Traverse capacity backward.

```
dp[j] = max(dp[j], dp[j - w[i]] + v[i])  (j from cap down to w[i])
```

### 2.2 Unbounded Knapsack

Each item can be reused. Traverse forward.

```
dp[j] = max(dp[j], dp[j - w[i]] + v[i])  (j from w[i] up to cap)
```

### 2.3 Subset Sum / Partition

Boolean DP: `dp[j] = dp[j] || dp[j - a[i]]`.

### 2.4 LCS (Longest Common Subsequence)

2D: `dp[i][j] = dp[i-1][j-1] + 1 if match else max(dp[i-1][j], dp[i][j-1])`.

### 2.5 Grid DP

`dp[i][j] = dp[i-1][j] + dp[i][j-1]` for paths; min/max for obstacle grids.

---

# 3. State, Transition, Base Case

For every DP problem, define:

- **State**: what's in the dp[i][j] (or dp[i])?
- **Transition**: how to compute dp[i] from smaller states?
- **Base case**: what are dp[0], dp[0][*], dp[*][0]?
- **Answer**: where is the result?

---

# 4. Java Implementation — `DpPatternsDemo.java`

```java
import java.util.*;

public class DpPatternsDemo {

    /** 0/1 knapsack. */
    static int knapsack01(int[] w, int[] v, int cap) {
        int[] dp = new int[cap + 1];
        for (int i = 0; i < w.length; i++)
            for (int j = cap; j >= w[i]; j--)
                dp[j] = Math.max(dp[j], dp[j - w[i]] + v[i]);
        return dp[cap];
    }

    /** Unbounded knapsack. */
    static int knapsackUnbounded(int[] w, int[] v, int cap) {
        int[] dp = new int[cap + 1];
        for (int i = 0; i < w.length; i++)
            for (int j = w[i]; j <= cap; j++)
                dp[j] = Math.max(dp[j], dp[j - w[i]] + v[i]);
        return dp[cap];
    }

    /** Subset sum. */
    static boolean subsetSum(int[] a, int target) {
        boolean[] dp = new boolean[target + 1]; dp[0] = true;
        for (int x : a)
            for (int j = target; j >= x; j--)
                dp[j] |= dp[j - x];
        return dp[target];
    }

    /** Partition equal subset sum. */
    static boolean canPartition(int[] a) {
        int sum = 0; for (int x : a) sum += x;
        if (sum % 2 != 0) return false;
        return subsetSum(a, sum / 2);
    }

    /** LCS. */
    static int lcs(String a, String b) {
        int m = a.length(), n = b.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++)
            for (int j = 1; j <= n; j++)
                if (a.charAt(i - 1) == b.charAt(j - 1)) dp[i][j] = dp[i - 1][j - 1] + 1;
                else dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
        return dp[m][n];
    }

    /** Longest common substring (contiguous). */
    static int longestCommonSubstring(String a, String b) {
        int m = a.length(), n = b.length(), best = 0;
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++)
            for (int j = 1; j <= n; j++)
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    best = Math.max(best, dp[i][j]);
                }
        return best;
    }

    /** LIS (O(n log n)). */
    static int lis(int[] a) {
        List<Integer> tails = new ArrayList<>();
        for (int x : a) {
            int lo = 0, hi = tails.size();
            while (lo < hi) { int m = (lo + hi) / 2; if (tails.get(m) < x) lo = m + 1; else hi = m; }
            if (lo == tails.size()) tails.add(x); else tails.set(lo, x);
        }
        return tails.size();
    }

    /** Edit distance. */
    static int editDistance(String a, String b) {
        int m = a.length(), n = b.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++) dp[i][0] = i;
        for (int j = 0; j <= n; j++) dp[0][j] = j;
        for (int i = 1; i <= m; i++)
            for (int j = 1; j <= n; j++)
                if (a.charAt(i - 1) == b.charAt(j - 1)) dp[i][j] = dp[i - 1][j - 1];
                else dp[i][j] = 1 + Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1]));
        return dp[m][n];
    }

    /** Grid DP: min path sum. */
    static int minPathSum(int[][] g) {
        int m = g.length, n = g[0].length;
        int[][] dp = new int[m][n];
        dp[0][0] = g[0][0];
        for (int i = 1; i < m; i++) dp[i][0] = dp[i - 1][0] + g[i][0];
        for (int j = 1; j < n; j++) dp[0][j] = dp[0][j - 1] + g[0][j];
        for (int i = 1; i < m; i++)
            for (int j = 1; j < n; j++)
                dp[i][j] = g[i][j] + Math.min(dp[i - 1][j], dp[i][j - 1]);
        return dp[m - 1][n - 1];
    }

    public static void main(String[] args) {
        System.out.println("01 knapsack     = " + knapsack01(new int[]{1,3,4,5}, new int[]{10,40,50,20}, 8));
        System.out.println("unbounded       = " + knapsackUnbounded(new int[]{1,3,4}, new int[]{10,40,50}, 8));
        System.out.println("subset sum      = " + subsetSum(new int[]{3,34,4,12,5,2}, 9));
        System.out.println("can partition   = " + canPartition(new int[]{1,5,11,5}));
        System.out.println("LCS             = " + lcs("abcde", "ace"));
        System.out.println("LCS substring   = " + longestCommonSubstring("ABABC", "BABCA"));
        System.out.println("LIS             = " + lis(new int[]{10,9,2,5,3,7,101,18}));
        System.out.println("edit distance   = " + editDistance("horse", "ros"));
        System.out.println("min path sum    = " + minPathSum(new int[][]{{1,3,1},{1,5,1},{4,2,1}}));
    }
}
```

Walkthrough:

- **0/1 knapsack**: backward loop to ensure each item is used at most once.
- **Unbounded**: forward loop to allow reuse.
- **Subset sum**: same backward pattern with boolean.
- **LCS**: standard 2D recurrence.
- **LIS**: patience sorting — track the smallest tail of each length.
- **Edit distance**: classic recurrence.
- **Grid DP**: accumulate, take min of two incoming directions.

---

# 5. Pattern Selection Cheat Sheet

| Problem type | DP shape |
|---|---|
| Choose / don't choose, items | 1D capacity |
| Match two strings | 2D i, j |
| Paths on grid | 2D i, j |
| Subsequence of array | 1D i |
| Game with two players | Often with state encoded |

---

# 6. Common Mistakes

1. **Forward vs backward loop** — wrong direction = wrong answer.
2. **Off-by-one on dp[0][*] or dp[*][0]**.
3. **Using 0 as the "uncomputed" sentinel** for memoised DP when 0 is a valid value.

---

# 7. Interview Questions

### Q1. Why backward loop in 0/1 knapsack?
Otherwise we'd reuse the same item multiple times in the same iteration.

### Q2. LIS in O(n log n)?
Patience sorting.

---

# 8. Practice Problems

## 🟢 Easy

### 1. 0/1 Knapsack (basic)
Standard.

### 2. Subset Sum
Standard.

### 3. Fibonacci (recap)
Standard.

### 4. Climbing Stairs (recap)
Standard.

### 5. Min Cost Climbing Stairs (recap)
Standard.

## 🟡 Medium

### 6. Coin Change
Standard.

### 7. Coin Change II
Standard.

### 8. Partition Equal Subset Sum
Standard.

### 9. LCS
Standard.

### 10. Edit Distance
Standard.

## 🔴 Hard

### 11. Distinct Subsequences
Standard.

### 12. Interleaving String
Standard.

### 13. Regular Expression Matching (DP)
Standard.

### 14. Word Break II (backtracking + memo)
Standard.

### 15. Longest Increasing Path in Matrix
Standard.

---

# 9. Practice Hints

## Easy
1. 1D capacity, backward loop.
2. Boolean DP.
3. dp[i] = dp[i-1] + dp[i-2].
4. Same.
5. Take min(a, b) + cost.

## Medium
6. Unbounded.
7. Outer coin loop.
8. Sum/2 subset sum.
9. 2D DP.
10. 2D DP.

## Hard
11. 2D DP.
12. 2D DP.
13. 2D DP with `*`.
14. DFS + memo.
15. DFS with memo.

---

# 10. Revision Checklist

- [ ] Can identify the 5 patterns
- [ ] Can solve knapsack variants
- [ ] Can do LCS / LIS
- [ ] Solved 5 Easy + 5 Medium + 5 Hard

---

# 11. Key Takeaways

- 5 patterns cover ~80% of DP problems.
- Always define state, transition, base, answer.
- Space optimisation often drops the table to 1–2 rows.

Tomorrow: **Advanced DP**.
