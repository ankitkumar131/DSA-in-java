# Day 24 — Graph Fundamentals

## 🎯 Learning Objectives

By the end of today, you should be able to:

- Define graph terminology (vertex, edge, directed, undirected, weighted)
- Choose between adjacency matrix, adjacency list, edge list
- Implement a graph in Java
- Perform BFS and DFS

---

# 1. Introduction

A **graph** is a set of vertices connected by edges. Trees are special graphs (connected, acyclic). Graphs model networks: roads, friendships, dependencies, web pages.

---

# 2. Why Do We Need This?

- Social networks, maps, routing.
- Dependency graphs, course schedules.
- Anything where pairwise relationships matter.

---

# 3. Core Concept

### Terminology

- **Vertex / Node**: an entity.
- **Edge**: connection between two vertices.
- **Directed**: edges have direction (a → b).
- **Undirected**: edges are bidirectional.
- **Weighted**: edges carry a cost/distance.
- **Cyclic**: contains a cycle.
- **Connected**: every vertex reachable from every other (undirected).

---

# 4. Real-World Analogy

Google Maps: cities are vertices, roads are weighted edges. Finding the shortest path is a graph problem.

---

# 5. Representations

| Representation | Space | Edge lookup | Neighbours |
|---|---|---|---|
| Adjacency matrix | O(V²) | O(1) | O(V) |
| Adjacency list   | O(V + E) | O(deg) | O(deg) |
| Edge list        | O(E) | O(E) | O(E) |

**Adjacency list** is the go-to for sparse graphs.

---

# 6. Java Implementation — `GraphDemo.java`

```java
import java.util.*;

public class GraphDemo {

    /** Graph as adjacency list. */
    static class Graph {
        int n;
        List<List<Integer>> adj;
        Graph(int n) { this.n = n; this.adj = new ArrayList<>(); for (int i = 0; i < n; i++) adj.add(new ArrayList<>()); }
        void addEdge(int u, int v) { adj.get(u).add(v); adj.get(v).add(u); }
        void addDirected(int u, int v) { adj.get(u).add(v); }
    }

    /** BFS from start. */
    static List<Integer> bfs(Graph g, int start) {
        List<Integer> order = new ArrayList<>();
        boolean[] vis = new boolean[g.n];
        Deque<Integer> q = new ArrayDeque<>();
        q.offer(start); vis[start] = true;
        while (!q.isEmpty()) {
            int u = q.poll(); order.add(u);
            for (int v : g.adj.get(u)) if (!vis[v]) { vis[v] = true; q.offer(v); }
        }
        return order;
    }

    /** Recursive DFS. */
    static List<Integer> dfs(Graph g, int start) {
        List<Integer> out = new ArrayList<>();
        boolean[] vis = new boolean[g.n];
        dfsRec(g, start, vis, out);
        return out;
    }
    static void dfsRec(Graph g, int u, boolean[] vis, List<Integer> out) {
        vis[u] = true; out.add(u);
        for (int v : g.adj.get(u)) if (!vis[v]) dfsRec(g, v, vis, out);
    }

    /** Iterative DFS using a stack. */
    static List<Integer> dfsIter(Graph g, int start) {
        List<Integer> out = new ArrayList<>();
        boolean[] vis = new boolean[g.n];
        Deque<Integer> st = new ArrayDeque<>();
        st.push(start);
        while (!st.isEmpty()) {
            int u = st.pop();
            if (vis[u]) continue;
            vis[u] = true; out.add(u);
            for (int v : g.adj.get(u)) if (!vis[v]) st.push(v);
        }
        return out;
    }

    /** Number of connected components. */
    static int components(Graph g) {
        boolean[] vis = new boolean[g.n];
        int count = 0;
        for (int i = 0; i < g.n; i++) if (!vis[i]) { bfsComponent(g, i, vis); count++; }
        return count;
    }
    static void bfsComponent(Graph g, int start, boolean[] vis) {
        Deque<Integer> q = new ArrayDeque<>();
        q.offer(start); vis[start] = true;
        while (!q.isEmpty()) {
            int u = q.poll();
            for (int v : g.adj.get(u)) if (!vis[v]) { vis[v] = true; q.offer(v); }
        }
    }

    /** Clone a graph (BFS). */
    static class GNode { int val; List<GNode> neighbors; GNode(int v) { val = v; neighbors = new ArrayList<>(); } }
    static GNode clone(GNode n) {
        if (n == null) return null;
        Map<GNode, GNode> map = new HashMap<>();
        Deque<GNode> q = new ArrayDeque<>();
        q.offer(n); map.put(n, new GNode(n.val));
        while (!q.isEmpty()) {
            GNode cur = q.poll();
            for (GNode nb : cur.neighbors) {
                if (!map.containsKey(nb)) { map.put(nb, new GNode(nb.val)); q.offer(nb); }
                map.get(cur).neighbors.add(map.get(nb));
            }
        }
        return map.get(n);
    }

    public static void main(String[] args) {
        Graph g = new Graph(6);
        g.addEdge(0, 1); g.addEdge(0, 2);
        g.addEdge(1, 3); g.addEdge(2, 3);
        g.addEdge(3, 4); g.addEdge(4, 5);

        System.out.println("BFS from 0: " + bfs(g, 0));
        System.out.println("DFS from 0: " + dfs(g, 0));
        System.out.println("DFSiter 0:  " + dfsIter(g, 0));
        System.out.println("Components: " + components(g));
    }
}
```

Walkthrough:

- `Graph`: adjacency list — list of neighbours per node.
- `bfs`: queue; visit; enqueue unvisited neighbours.
- `dfsRec`: recursive — pushes the call stack. Returns the preorder traversal.
- `dfsIter`: stack; pop; mark visited; push children. Equivalent order to recursive (depending on push order).
- `components`: run BFS/DFS from each unvisited vertex.
- `clone`: BFS with HashMap old→new.

---

# 7. BFS vs DFS

| | BFS | DFS |
|---|---|---|
| Data structure | Queue | Stack / recursion |
| Order | Level-by-level | As deep as possible |
| Shortest path (unweighted) | ✅ | ❌ |
| Memory | O(width) | O(depth) |

---

# 8. Common Mistakes

1. **Forgetting `visited`** in DFS — infinite recursion.
2. **Resetting `visited` between components** when counting — keep it global.
3. **Iterative DFS order ≠ recursive** if push order differs.

---

# 9. Interview Questions

### Q1. Adjacency matrix vs list?
Matrix: O(V²) space, O(1) edge check. List: O(V+E), preferred for sparse.

### Q2. When to use BFS over DFS?
Shortest path in unweighted graph, level-order.

### Q3. What's a DAG?
Directed acyclic graph — used for topological sort, dependencies.

---

# 10. Practice Problems

## 🟢 Easy

### 1. BFS Traversal
**Input:** graph, start → **Output:** bfs order.

### 2. DFS Traversal
Same.

### 3. Find if Path Exists
**Input:** graph, src, dst → **Output:** true/false.

### 4. Count Connected Components
Standard.

### 5. Clone Graph
Standard.

## 🟡 Medium

### 6. Number of Islands
**Input:** grid → **Output:** count.

### 7. Max Area of Island
**Input:** grid → **Output:** max area.

### 8. Surrounded Regions
**Input:** grid → capture regions.

### 9. Course Schedule (cycle detection)
**Input:** n, prereqs → can finish?

### 10. Pacific Atlantic Water Flow
**Input:** heights → cells reaching both oceans.

## 🔴 Hard

### 11. Word Ladder
**Input:** begin, end, list → min length.

### 12. Strongly Connected Components (Kosaraju/Tarjan)

### 13. Critical Connections (Tarjan's bridges)

### 14. Alien Dictionary
**Input:** sorted words → order.

### 15. Reconstruct Itinerary
**Input:** tickets → itinerary.

---

# 11. Practice Hints

## Easy
1. Queue BFS.
2. Recursive DFS.
3. BFS visit check.
4. Loop BFS from each.
5. BFS + HashMap.

## Medium
6. DFS on grid.
7. DFS with area count.
8. Boundary DFS then flip.
9. DFS cycle detect.
10. Multi-source DFS.

## Hard
11. BFS over word graph.
12. Two DFS passes.
13. Tarjan's low-link.
14. Build graph, topo sort.
15. Eulerian path (Hierholzer).

---

# 12. Revision Checklist

- [ ] Can pick a graph representation
- [ ] Can implement BFS and DFS
- [ ] Can count connected components
- [ ] Solved 5 Easy + 5 Medium + 5 Hard

---

# 13. Key Takeaways

- Adjacency list = default representation.
- BFS for shortest path in unweighted graphs.
- DFS for cycle detection, topological sort, connectivity.
- Iterative DFS uses a stack explicitly.

Tomorrow: **BFS & DFS deep**.
