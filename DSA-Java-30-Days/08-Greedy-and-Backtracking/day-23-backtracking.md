# Day 23 — Backtracking

## 🎯 Learning Objectives

By the end of today, you should be able to:

- Recognise backtracking problems (decision tree + prune)
- Apply the choose-explore-undo pattern
- Generate subsets, permutations, combinations
- Solve N-Queens, Sudoku, rat-in-maze, word search

---

# 1. Introduction

Backtracking = recursion + pruning. You build a solution incrementally; whenever a partial choice can't lead to a valid answer, you "undo" and try another.

---

# 2. Why Do We Need This?

Many problems require enumerating all valid configurations:

- Subsets, permutations, combinations
- N-Queens, Sudoku
- Word search, maze paths

Brute force is exponential; pruning makes it tractable.

---

# 3. Core Concept — The Template

```java
void backtrack(state, choices):
    if state is complete:
        record answer
        return
    for each choice in choices:
        if choice is valid:
            make choice      // choose
            backtrack(state, choices) // explore
            undo choice      // undo
```

---

# 4. Real-World Analogy

Solving a maze: at each intersection, try one path. If you hit a dead end, back up to the last intersection and try another.

---

# 5. Java Implementation — `BacktrackingDemo.java`

```java
import java.util.*;

public class BacktrackingDemo {

    /** Subsets. */
    static List<List<Integer>> subsets(int[] a) {
        List<List<Integer>> out = new ArrayList<>();
        back(a, 0, new ArrayList<>(), out);
        return out;
    }
    static void back(int[] a, int i, List<Integer> cur, List<List<Integer>> out) {
        out.add(new ArrayList<>(cur));
        for (int j = i; j < a.length; j++) {
            cur.add(a[j]);
            back(a, j + 1, cur, out);
            cur.remove(cur.size() - 1);
        }
    }

    /** Permutations. */
    static List<List<Integer>> permutations(int[] a) {
        List<List<Integer>> out = new ArrayList<>();
        boolean[] used = new boolean[a.length];
        perm(a, new ArrayList<>(), used, out);
        return out;
    }
    static void perm(int[] a, List<Integer> cur, boolean[] used, List<List<Integer>> out) {
        if (cur.size() == a.length) { out.add(new ArrayList<>(cur)); return; }
        for (int i = 0; i < a.length; i++) {
            if (used[i]) continue;
            used[i] = true; cur.add(a[i]);
            perm(a, cur, used, out);
            used[i] = false; cur.remove(cur.size() - 1);
        }
    }

    /** Combination sum. */
    static List<List<Integer>> combinationSum(int[] a, int target) {
        Arrays.sort(a);
        List<List<Integer>> out = new ArrayList<>();
        cs(a, target, 0, new ArrayList<>(), out);
        return out;
    }
    static void cs(int[] a, int rem, int start, List<Integer> cur, List<List<Integer>> out) {
        if (rem == 0) { out.add(new ArrayList<>(cur)); return; }
        for (int i = start; i < a.length; i++) {
            if (a[i] > rem) break;
            cur.add(a[i]);
            cs(a, rem - a[i], i, cur, out);
            cur.remove(cur.size() - 1);
        }
    }

    /** N-Queens. */
    static List<List<String>> nQueens(int n) {
        List<List<String>> out = new ArrayList<>();
        char[][] board = new char[n][n];
        for (char[] row : board) Arrays.fill(row, '.');
        solve(board, 0, out);
        return out;
    }
    static void solve(char[][] b, int row, List<List<String>> out) {
        if (row == b.length) {
            List<String> snap = new ArrayList<>();
            for (char[] r : b) snap.add(new String(r));
            out.add(snap);
            return;
        }
        for (int c = 0; c < b.length; c++) {
            if (isSafe(b, row, c)) {
                b[row][c] = 'Q';
                solve(b, row + 1, out);
                b[row][c] = '.';
            }
        }
    }
    static boolean isSafe(char[][] b, int r, int c) {
        for (int i = 0; i < r; i++) if (b[i][c] == 'Q') return false;
        for (int i = r - 1, j = c - 1; i >= 0 && j >= 0; i--, j--) if (b[i][j] == 'Q') return false;
        for (int i = r - 1, j = c + 1; i >= 0 && j < b.length; i--, j++) if (b[i][j] == 'Q') return false;
        return true;
    }

    /** Word search. */
    static boolean wordSearch(char[][] b, String word) {
        for (int r = 0; r < b.length; r++)
            for (int c = 0; c < b[0].length; c++)
                if (dfs(b, word, r, c, 0)) return true;
        return false;
    }
    static boolean dfs(char[][] b, String w, int r, int c, int idx) {
        if (idx == w.length()) return true;
        if (r < 0 || c < 0 || r >= b.length || c >= b[0].length || b[r][c] != w.charAt(idx)) return false;
        char tmp = b[r][c]; b[r][c] = '#';
        boolean ok = dfs(b, w, r + 1, c, idx + 1) || dfs(b, w, r - 1, c, idx + 1)
                  || dfs(b, w, r, c + 1, idx + 1) || dfs(b, w, r, c - 1, idx + 1);
        b[r][c] = tmp;
        return ok;
    }

    /** Rat in a maze. */
    static List<String> ratInMaze(int[][] m) {
        List<String> out = new ArrayList<>();
        int n = m.length;
        boolean[][] vis = new boolean[n][n];
        if (m[0][0] == 0 || m[n - 1][n - 1] == 0) return out;
        mazeHelper(m, n, 0, 0, vis, "", out);
        Collections.sort(out);
        return out;
    }
    static void mazeHelper(int[][] m, int n, int r, int c, boolean[][] vis, String path, List<String> out) {
        if (r < 0 || c < 0 || r >= n || c >= n || m[r][c] == 0 || vis[r][c]) return;
        if (r == n - 1 && c == n - 1) { out.add(path); return; }
        vis[r][c] = true;
        mazeHelper(m, n, r + 1, c, vis, path + "D", out);
        mazeHelper(m, n, r, c - 1, vis, path + "L", out);
        mazeHelper(m, n, r, c + 1, vis, path + "R", out);
        mazeHelper(m, n, r - 1, c, vis, path + "U", out);
        vis[r][c] = false;
    }

    public static void main(String[] args) {
        System.out.println("subsets([1,2,3])     = " + subsets(new int[]{1,2,3}));
        System.out.println("perms([1,2,3])       = " + permutations(new int[]{1,2,3}));
        System.out.println("comboSum 7           = " + combinationSum(new int[]{2,3,6,7}, 7));
        System.out.println("nQueens(4)           = " + nQueens(4).size() + " solutions");
        char[][] grid = {{'A','B','C','E'},{'S','F','C','S'},{'A','D','E','E'}};
        System.out.println("wordSearch ABCCED    = " + wordSearch(grid, "ABCCED"));
        int[][] maze = {{1,0,0,0},{1,1,0,1},{1,1,0,0},{0,1,1,1}};
        System.out.println("ratInMaze            = " + ratInMaze(maze));
    }
}
```

Walkthrough:

- `subsets`: at each index, decide to include `a[i]` or not. Adding a snapshot at every node.
- `permutations`: same idea but with `used[]` flag to prevent reuse.
- `combinationSum`: candidates may repeat (`i` not `i+1`); sort so we can prune with `break`.
- `nQueens`: try each column; check safety; recurse; undo.
- `wordSearch`: mark cell visited (`#`), recurse, restore.
- `ratInMaze`: visit, try D/L/R/U, undo visit.

---

# 6. Dry Run — `subsets([1,2,3])`

```
back(0, []):  out=[[]]
  add 1; back(1, [1]): out=[[],[1]]
    add 2; back(2, [1,2]): out=[[],[1],[1,2]]
      add 3; back(3, [1,2,3])
    add 3; back(2, [1,3])
  add 2; back(1, [2])
  add 3; back(1, [3])
```

Output: `[[], [1], [1,2], [1,2,3], [1,3], [2], [2,3], [3]]`.

---

# 7. Common Mistakes

1. **Forgetting to undo** the choice — leaves stale state.
2. **Not copying** when adding to results — reference gets mutated later.
3. **Wrong bounds** in grid problems.

---

# 8. Interview Questions

### Q1. Subsets vs permutations?
Subsets: each element either in or out → 2ⁿ. Permutations: ordering matters → n!.

### Q2. How to optimise?
- Pruning: skip invalid branches early.
- Sorting: enables early break.

### Q3. Backtracking vs brute force?
Backtracking prunes during recursion, avoiding exponential blowup.

---

# 9. Practice Problems

## 🟢 Easy

### 1. Subsets
**Input:** `[1,2,3]` → **Output:** all 8 subsets.

### 2. Power Set
Same as subsets.

### 3. Letter Case Permutation
**Input:** `"a1b"` → **Output:** `["a1b","a1B","A1b","A1B"]`.

### 4. Generate Parentheses
**Input:** `n=3` → **Output:** `["((()))","(()())","(())()","()(())","()()()"]`.

### 5. Binary Watch
Read time combinations.

## 🟡 Medium

### 6. Permutations
Standard.

### 7. Permutations II (with duplicates)
Standard.

### 8. Combination Sum
Standard.

### 9. Combination Sum II (no reuse)
Standard.

### 10. Word Search
Standard.

## 🔴 Hard

### 11. N-Queens
Standard.

### 12. Sudoku Solver
Standard.

### 13. Rat in a Maze
Standard.

### 14. Regular Expression Matching
DP, but backtracking helps.

### 15. Palindrome Partitioning
Standard.

---

# 10. Practice Hints

## Easy
1. Choose / not choose.
2. Same.
3. Branch on each letter.
4. Track open/close count.
5. Backtrack over bits.

## Medium
6. Used[] array.
7. Sort + skip duplicates.
8. Recurse with `i` not `i+1`.
9. `i+1`, skip dup.
10. Mark visited.

## Hard
11. Row/col/diag checks.
12. Try digits, validate.
13. DFS, track path.
14. Recurse on char patterns.
15. Partition + palindrome check.

---

# 11. Revision Checklist

- [ ] Know the choose-explore-undo template
- [ ] Can generate subsets/permutations
- [ ] Can solve N-Queens and Sudoku
- [ ] Solved 5 Easy + 5 Medium + 5 Hard

---

# 12. Key Takeaways

- Backtracking = DFS + prune.
- Always undo state.
- Sort inputs when possible for early break.

Tomorrow: **Graph Fundamentals**.
