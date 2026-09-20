/**
 * Day 23 — Backtracking demo.
 *
 * Compile: javac src/day-23/BacktrackingDemo.java
 * Run    : java -cp src/day-23 BacktrackingDemo
 */
import java.util.*;

public class BacktrackingDemo {

    static List<List<Integer>> subsets(int[] a) {
        List<List<Integer>> out = new ArrayList<>();
        back(a, 0, new ArrayList<>(), out);
        return out;
    }
    static void back(int[] a, int i, List<Integer> cur, List<List<Integer>> out) {
        out.add(new ArrayList<>(cur));
        for (int j = i; j < a.length; j++) {
            cur.add(a[j]); back(a, j + 1, cur, out); cur.remove(cur.size() - 1);
        }
    }

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
            cur.add(a[i]); cs(a, rem - a[i], i, cur, out); cur.remove(cur.size() - 1);
        }
    }

    static List<List<String>> nQueens(int n) {
        List<List<String>> out = new ArrayList<>();
        char[][] b = new char[n][n];
        for (char[] row : b) Arrays.fill(row, '.');
        solve(b, 0, out);
        return out;
    }
    static void solve(char[][] b, int row, List<List<String>> out) {
        if (row == b.length) {
            List<String> snap = new ArrayList<>();
            for (char[] r : b) snap.add(new String(r));
            out.add(snap); return;
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

    static boolean wordSearch(char[][] b, String w) {
        for (int r = 0; r < b.length; r++)
            for (int c = 0; c < b[0].length; c++)
                if (dfs(b, w, r, c, 0)) return true;
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

    static List<String> ratInMaze(int[][] m) {
        List<String> out = new ArrayList<>();
        int n = m.length;
        boolean[][] vis = new boolean[n][n];
        if (m[0][0] == 0 || m[n - 1][n - 1] == 0) return out;
        maze(m, n, 0, 0, vis, "", out);
        Collections.sort(out);
        return out;
    }
    static void maze(int[][] m, int n, int r, int c, boolean[][] vis, String path, List<String> out) {
        if (r < 0 || c < 0 || r >= n || c >= n || m[r][c] == 0 || vis[r][c]) return;
        if (r == n - 1 && c == n - 1) { out.add(path); return; }
        vis[r][c] = true;
        maze(m, n, r + 1, c, vis, path + "D", out);
        maze(m, n, r, c - 1, vis, path + "L", out);
        maze(m, n, r, c + 1, vis, path + "R", out);
        maze(m, n, r - 1, c, vis, path + "U", out);
        vis[r][c] = false;
    }

    public static void main(String[] args) {
        System.out.println("subsets         = " + subsets(new int[]{1,2,3}));
        System.out.println("perms           = " + permutations(new int[]{1,2,3}));
        System.out.println("comboSum 7      = " + combinationSum(new int[]{2,3,6,7}, 7));
        System.out.println("nQueens(4) cnt  = " + nQueens(4).size());
        char[][] grid = {{'A','B','C','E'},{'S','F','C','S'},{'A','D','E','E'}};
        System.out.println("wordSearch      = " + wordSearch(grid, "ABCCED"));
        int[][] maze = {{1,0,0,0},{1,1,0,1},{1,1,0,0},{0,1,1,1}};
        System.out.println("ratInMaze       = " + ratInMaze(maze));
    }
}
