/**
 * Day 23 — Medium practice.
 *
 * Compile: javac src/day-23/PracticeMedium.java
 * Run    : java -cp src/day-23 PracticeMedium
 */
import java.util.*;

public class PracticeMedium {

    static List<List<Integer>> permutations(int[] a) {
        List<List<Integer>> out = new ArrayList<>();
        boolean[] used = new boolean[a.length];
        perm(a, new ArrayList<>(), used, out); return out;
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

    static List<List<Integer>> permutationsDup(int[] a) {
        Arrays.sort(a);
        List<List<Integer>> out = new ArrayList<>();
        boolean[] used = new boolean[a.length];
        permDup(a, new ArrayList<>(), used, out); return out;
    }
    static void permDup(int[] a, List<Integer> cur, boolean[] used, List<List<Integer>> out) {
        if (cur.size() == a.length) { out.add(new ArrayList<>(cur)); return; }
        for (int i = 0; i < a.length; i++) {
            if (used[i] || (i > 0 && a[i] == a[i - 1] && !used[i - 1])) continue;
            used[i] = true; cur.add(a[i]);
            permDup(a, cur, used, out);
            used[i] = false; cur.remove(cur.size() - 1);
        }
    }

    static List<List<Integer>> combinationSum(int[] a, int target) {
        Arrays.sort(a);
        List<List<Integer>> out = new ArrayList<>();
        cs(a, target, 0, new ArrayList<>(), out); return out;
    }
    static void cs(int[] a, int rem, int start, List<Integer> cur, List<List<Integer>> out) {
        if (rem == 0) { out.add(new ArrayList<>(cur)); return; }
        for (int i = start; i < a.length; i++) {
            if (a[i] > rem) break;
            cur.add(a[i]); cs(a, rem - a[i], i, cur, out); cur.remove(cur.size() - 1);
        }
    }

    static List<List<Integer>> combinationSum2(int[] a, int target) {
        Arrays.sort(a);
        List<List<Integer>> out = new ArrayList<>();
        cs2(a, target, 0, new ArrayList<>(), out); return out;
    }
    static void cs2(int[] a, int rem, int start, List<Integer> cur, List<List<Integer>> out) {
        if (rem == 0) { out.add(new ArrayList<>(cur)); return; }
        for (int i = start; i < a.length; i++) {
            if (a[i] > rem) break;
            if (i > start && a[i] == a[i - 1]) continue;
            cur.add(a[i]); cs2(a, rem - a[i], i + 1, cur, out); cur.remove(cur.size() - 1);
        }
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

    public static void main(String[] args) {
        System.out.println("Q6 perms      = " + permutations(new int[]{1,2,3}));
        System.out.println("Q7 permsDup   = " + permutationsDup(new int[]{1,1,2}));
        System.out.println("Q8 comboSum   = " + combinationSum(new int[]{2,3,6,7}, 7));
        System.out.println("Q9 comboSum2  = " + combinationSum2(new int[]{10,1,2,7,6,1,5}, 8));
        char[][] grid = {{'A','B','C','E'},{'S','F','C','S'},{'A','D','E','E'}};
        System.out.println("Q10 wordSearch = " + wordSearch(grid, "ABCCED"));
    }
}
