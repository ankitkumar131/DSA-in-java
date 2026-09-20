/**
 * Day 23 — Hard practice.
 *
 * Compile: javac src/day-23/PracticeHard.java
 * Run    : java -cp src/day-23 PracticeHard
 */
import java.util.*;

public class PracticeHard {

    static List<List<String>> nQueens(int n) {
        List<List<String>> out = new ArrayList<>();
        char[][] b = new char[n][n]; for (char[] r : b) Arrays.fill(r, '.');
        solve(b, 0, out); return out;
    }
    static void solve(char[][] b, int row, List<List<String>> out) {
        if (row == b.length) {
            List<String> snap = new ArrayList<>();
            for (char[] r : b) snap.add(new String(r));
            out.add(snap); return;
        }
        for (int c = 0; c < b.length; c++) {
            if (isSafe(b, row, c)) { b[row][c] = 'Q'; solve(b, row + 1, out); b[row][c] = '.'; }
        }
    }
    static boolean isSafe(char[][] b, int r, int c) {
        for (int i = 0; i < r; i++) if (b[i][c] == 'Q') return false;
        for (int i = r - 1, j = c - 1; i >= 0 && j >= 0; i--, j--) if (b[i][j] == 'Q') return false;
        for (int i = r - 1, j = c + 1; i >= 0 && j < b.length; i--, j++) if (b[i][j] == 'Q') return false;
        return true;
    }

    static void sudoku(char[][] b) { solve(b); }
    static boolean solve(char[][] b) {
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++) {
                if (b[r][c] != '.') continue;
                for (char d = '1'; d <= '9'; d++) {
                    if (isValid(b, r, c, d)) {
                        b[r][c] = d;
                        if (solve(b)) return true;
                        b[r][c] = '.';
                    }
                }
                return false;
            }
        return true;
    }
    static boolean isValid(char[][] b, int r, int c, char d) {
        for (int i = 0; i < 9; i++)
            if (b[r][i] == d || b[i][c] == d || b[3 * (r / 3) + i / 3][3 * (c / 3) + i % 3] == d) return false;
        return true;
    }

    static List<String> ratInMaze(int[][] m) {
        List<String> out = new ArrayList<>();
        int n = m.length;
        boolean[][] vis = new boolean[n][n];
        if (m[0][0] == 0 || m[n - 1][n - 1] == 0) return out;
        maze(m, n, 0, 0, vis, "", out);
        Collections.sort(out); return out;
    }
    static void maze(int[][] m, int n, int r, int c, boolean[][] vis, String p, List<String> out) {
        if (r < 0 || c < 0 || r >= n || c >= n || m[r][c] == 0 || vis[r][c]) return;
        if (r == n - 1 && c == n - 1) { out.add(p); return; }
        vis[r][c] = true;
        maze(m, n, r + 1, c, vis, p + "D", out);
        maze(m, n, r, c - 1, vis, p + "L", out);
        maze(m, n, r, c + 1, vis, p + "R", out);
        maze(m, n, r - 1, c, vis, p + "U", out);
        vis[r][c] = false;
    }

    static boolean regex(String s, String p) {
        if (p.isEmpty()) return s.isEmpty();
        boolean firstMatch = !s.isEmpty() && (s.charAt(0) == p.charAt(0) || p.charAt(0) == '.');
        if (p.length() >= 2 && p.charAt(1) == '*')
            return regex(s, p.substring(2)) || (firstMatch && regex(s.substring(1), p));
        return firstMatch && regex(s.substring(1), p.substring(1));
    }

    static List<List<String>> partition(String s) {
        List<List<String>> out = new ArrayList<>();
        pp(s, 0, new ArrayList<>(), out); return out;
    }
    static void pp(String s, int start, List<String> cur, List<List<String>> out) {
        if (start == s.length()) { out.add(new ArrayList<>(cur)); return; }
        for (int end = start + 1; end <= s.length(); end++) {
            String sub = s.substring(start, end);
            if (isPal(sub)) { cur.add(sub); pp(s, end, cur, out); cur.remove(cur.size() - 1); }
        }
    }
    static boolean isPal(String s) {
        for (int i = 0, j = s.length() - 1; i < j; i++, j--) if (s.charAt(i) != s.charAt(j)) return false;
        return true;
    }

    public static void main(String[] args) {
        System.out.println("Q11 nQueens(4) count = " + nQueens(4).size());

        char[][] board = {
            {'5','3','.','.','7','.','.','.','.'},
            {'6','.','.','1','9','5','.','.','.'},
            {'.','9','8','.','.','.','.','6','.'},
            {'8','.','.','.','6','.','.','.','3'},
            {'4','.','.','8','.','3','.','.','1'},
            {'7','.','.','.','2','.','.','.','6'},
            {'.','6','.','.','.','.','2','8','.'},
            {'.','.','.','4','1','9','.','.','5'},
            {'.','.','.','.','8','.','.','7','9'}
        };
        sudoku(board);
        System.out.println("Q12 sudoku solved: " + board[0][2]);

        int[][] mz = {{1,0,0,0},{1,1,0,1},{1,1,0,0},{0,1,1,1}};
        System.out.println("Q13 ratInMaze  = " + ratInMaze(mz));

        System.out.println("Q14 regex      = " + regex("aab", "c*a*b"));

        System.out.println("Q15 partition  = " + partition("aab"));
    }
}
