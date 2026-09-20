# Day 25 — BFS & DFS (Deep)

## 🎯 Learning Objectives

By the end of today, you should be able to:

- Iterate DFS cleanly (both recursive and stack versions)
- Solve number of islands, flood fill, bipartiteness
- Apply BFS for shortest path in a grid
- Distinguish cycle detection in directed vs undirected graphs
- Solve grid traversal problems

---

# 1. Introduction

Yesterday we covered graph fundamentals. Today we go deeper on two workhorse algorithms: BFS for shortest paths and components, DFS for cycle detection and backtracking.

---

# 2. Cycle Detection

### Undirected

```java
boolean hasCycleUndirected(Graph g) {
    boolean[] vis = new boolean[g.n];
    for (int i = 0; i < g.n; i++) if (!vis[i] && dfsCycle(g, i, -1, vis)) return true;
    return false;
}
boolean dfsCycle(Graph g, int u, int parent, boolean[] vis) {
    vis[u] = true;
    for (int v : g.adj.get(u)) {
        if (!vis[v]) { if (dfsCycle(g, v, u, vis)) return true; }
        else if (v != parent) return true;
    }
    return false;
}
```

### Directed (white/grey/black)

```java
boolean hasCycleDirected(Graph g) {
    int[] state = new int[g.n]; // 0 white, 1 grey, 2 black
    for (int i = 0; i < g.n; i++) if (state[i] == 0 && dfsDC(g, i, state)) return true;
    return false;
}
boolean dfsDC(Graph g, int u, int[] state) {
    state[u] = 1;
    for (int v : g.adj.get(u)) {
        if (state[v] == 1) return true;
        if (state[v] == 0 && dfsDC(g, v, state)) return true;
    }
    state[u] = 2;
    return false;
}
```

---

# 3. Bipartite Check

A graph is bipartite iff it has no odd cycle. BFS-color:

```java
boolean isBipartite(Graph g) {
    int[] color = new int[g.n];
    Arrays.fill(color, -1);
    for (int s = 0; s < g.n; s++) {
        if (color[s] != -1) continue;
        color[s] = 0;
        Deque<Integer> q = new ArrayDeque<>(); q.offer(s);
        while (!q.isEmpty()) {
            int u = q.poll();
            for (int v : g.adj.get(u)) {
                if (color[v] == -1) { color[v] = 1 - color[u]; q.offer(v); }
                else if (color[v] == color[u]) return false;
            }
        }
    }
    return true;
}
```

---

# 4. Grid Traversal

For an `m × n` grid, treat each cell as a node. Neighbours: 4-direction (`{−1,0},{1,0},{0,−1},{0,1}`) or 8-direction (diagonals).

---

# 5. Java Implementation — `BfsDfsDeepDemo.java`

```java
import java.util.*;

public class BfsDfsDeepDemo {

    static class Graph {
        int n; List<List<Integer>> adj;
        Graph(int n) { this.n = n; this.adj = new ArrayList<>(); for (int i = 0; i < n; i++) adj.add(new ArrayList<>()); }
        void addEdge(int u, int v) { adj.get(u).add(v); adj.get(v).add(u); }
        void addDir(int u, int v) { adj.get(u).add(v); }
    }

    /** Undirected cycle detection. */
    static boolean undirectedCycle(Graph g) {
        boolean[] vis = new boolean[g.n];
        for (int i = 0; i < g.n; i++) if (!vis[i] && ucDfs(g, i, -1, vis)) return true;
        return false;
    }
    static boolean ucDfs(Graph g, int u, int parent, boolean[] vis) {
        vis[u] = true;
        for (int v : g.adj.get(u)) {
            if (!vis[v]) { if (ucDfs(g, v, u, vis)) return true; }
            else if (v != parent) return true;
        }
        return false;
    }

    /** Directed cycle detection. */
    static boolean directedCycle(Graph g) {
        int[] state = new int[g.n];
        for (int i = 0; i < g.n; i++) if (state[i] == 0 && dcDfs(g, i, state)) return true;
        return false;
    }
    static boolean dcDfs(Graph g, int u, int[] state) {
        state[u] = 1;
        for (int v : g.adj.get(u)) {
            if (state[v] == 1) return true;
            if (state[v] == 0 && dcDfs(g, v, state)) return true;
        }
        state[u] = 2;
        return false;
    }

    /** Bipartite check. */
    static boolean isBipartite(Graph g) {
        int[] c = new int[g.n];
        Arrays.fill(c, -1);
        for (int s = 0; s < g.n; s++) {
            if (c[s] != -1) continue;
            c[s] = 0;
            Deque<Integer> q = new ArrayDeque<>(); q.offer(s);
            while (!q.isEmpty()) {
                int u = q.poll();
                for (int v : g.adj.get(u)) {
                    if (c[v] == -1) { c[v] = 1 - c[u]; q.offer(v); }
                    else if (c[v] == c[u]) return false;
                }
            }
        }
        return true;
    }

    /** Number of islands. */
    static int numIslands(char[][] g) {
        int c = 0;
        for (int i = 0; i < g.length; i++) for (int j = 0; j < g[0].length; j++)
            if (g[i][j] == '1') { flood(g, i, j); c++; }
        return c;
    }
    static void flood(char[][] g, int i, int j) {
        if (i < 0 || j < 0 || i >= g.length || j >= g[0].length || g[i][j] != '1') return;
        g[i][j] = '0';
        flood(g, i + 1, j); flood(g, i - 1, j); flood(g, i, j + 1); flood(g, i, j - 1);
    }

    /** Flood fill. */
    static int[][] floodFill(int[][] image, int sr, int sc, int color) {
        int orig = image[sr][sc];
        if (orig == color) return image;
        ff(image, sr, sc, orig, color);
        return image;
    }
    static void ff(int[][] g, int i, int j, int orig, int color) {
        if (i < 0 || j < 0 || i >= g.length || j >= g[0].length || g[i][j] != orig) return;
        g[i][j] = color;
        ff(g, i + 1, j, orig, color); ff(g, i - 1, j, orig, color);
        ff(g, i, j + 1, orig, color); ff(g, i, j - 1, orig, color);
    }

    /** Shortest path in grid. */
    static int shortestGridPath(int[][] g, int[] src, int[] dst) {
        int m = g.length, n = g[0].length;
        boolean[][] vis = new boolean[m][n];
        Deque<int[]> q = new ArrayDeque<>(); q.offer(src); vis[src[0]][src[1]] = true;
        int steps = 0;
        int[][] d = {{-1,0},{1,0},{0,-1},{0,1}};
        while (!q.isEmpty()) {
            for (int sz = q.size(); sz > 0; sz--) {
                int[] p = q.poll();
                if (p[0] == dst[0] && p[1] == dst[1]) return steps;
                for (int[] dd : d) {
                    int ni = p[0] + dd[0], nj = p[1] + dd[1];
                    if (ni >= 0 && nj >= 0 && ni < m && nj < n && !vis[ni][nj] && g[ni][nj] == 0) {
                        vis[ni][nj] = true; q.offer(new int[]{ni, nj});
                    }
                }
            }
            steps++;
        }
        return -1;
    }

    public static void main(String[] args) {
        Graph ug = new Graph(5);
        ug.addEdge(0, 1); ug.addEdge(1, 2); ug.addEdge(2, 0);
        System.out.println("undirected cycle = " + undirectedCycle(ug));

        Graph dg = new Graph(4);
        dg.addDir(0, 1); dg.addDir(1, 2); dg.addDir(2, 0);
        System.out.println("directed cycle   = " + directedCycle(dg));

        Graph bg = new Graph(4);
        bg.addEdge(0, 1); bg.addEdge(1, 2); bg.addEdge(2, 3); bg.addEdge(3, 0);
        System.out.println("bipartite        = " + isBipartite(bg));

        char[][] g = {{'1','1','0','0','0'},{'1','1','0','0','0'},{'0','0','1','0','0'},{'0','0','0','1','1'}};
        System.out.println("islands          = " + numIslands(g));

        int[][] img = {{1,1,1},{1,1,0},{1,0,1}};
        int[][] filled = floodFill(img, 1, 1, 2);
        System.out.print("floodFill        = ");
        for (int[] row : filled) System.out.print(Arrays.toString(row) + " ");
        System.out.println();

        int[][] grid = {{0,0,0},{0,1,0},{0,0,0}};
        System.out.println("shortest path    = " + shortestGridPath(grid, new int[]{0,0}, new int[]{2,2}));
    }
}
```

Walkthrough:

- **Undirected cycle**: when DFS sees a visited neighbour that isn't the parent, there's a cycle.
- **Directed cycle**: use 0/1/2 (white/grey/black). Back-edge to a grey node = cycle.
- **Bipartite**: 2-coloring. Conflict on equal colours = not bipartite.
- **Islands / flood fill**: classic grid DFS.
- **Shortest grid path**: BFS by levels.

---

# 6. Common Mistakes

1. **Mixing directed and undirected cycle checks**.
2. **Forgetting to mark visited** in flood fill.
3. **BFS without levels** — `steps++` after each layer, not per node.

---

# 7. Interview Questions

### Q1. Bipartite = 2-colourable?
Yes. A graph is bipartite iff it has no odd-length cycle.

### Q2. Why is BFS shortest path in unweighted?
First time we reach a node in BFS is via the shortest path.

---

# 8. Practice Problems

## 🟢 Easy

### 1. Flood Fill
**Input:** image, sr, sc, color → filled image.

### 2. Number of Islands
Standard.

### 3. Find if Path Exists in Graph
Standard.

### 4. Maximum Depth of N-ary Tree (BFS)
Standard.

### 5. Same Tree (recap)
Standard.

## 🟡 Medium

### 6. Rotting Oranges
Standard.

### 7. Walls and Gates
Standard.

### 8. Cycle Detection (Undirected)
Standard.

### 9. Cycle Detection (Directed)
Standard.

### 10. 01 Matrix (BFS from 0s)
**Input:** binary matrix → dist to nearest 0.

## 🔴 Hard

### 11. Bipartite Check
Standard.

### 12. Shortest Path in Grid with Obstacles
Standard.

### 13. Word Ladder (re-impl)
Standard.

### 14. Reachable Nodes in Subdivided Graph
Standard.

### 15. Bus Routes
**Input:** routes → min buses to reach target.

---

# 9. Practice Hints

## Easy
1. DFS flood.
2. Grid DFS.
3. BFS existence.
4. BFS level order.
5. Recursion.

## Medium
6. Multi-source BFS.
7. Multi-source BFS.
8. DFS with parent.
9. DFS with state.
10. BFS from each 0.

## Hard
11. 2-colour.
12. BFS with turns.
13. BFS over words.
14. Modified BFS.
15. BFS on bus lines.

---

# 10. Revision Checklist

- [ ] Can detect cycles in both kinds of graph
- [ ] Can check bipartiteness
- [ ] Can solve grid BFS/DFS
- [ ] Solved 5 Easy + 5 Medium + 5 Hard

---

# 11. Key Takeaways

- Cycle: undirected uses parent; directed uses 3-state colouring.
- Bipartite: 2-colour check.
- BFS gives shortest path in unweighted.

Tomorrow: **Shortest Paths & Advanced Graphs**.
