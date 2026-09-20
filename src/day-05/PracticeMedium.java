/**
 * Day 5 — Medium practice solutions.
 *
 * Compile: javac src/day-05/PracticeMedium.java
 * Run    : java -cp src/day-05 PracticeMedium
 */
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PracticeMedium {

    static void reverse(int[] a, int lo, int hi) {
        while (lo < hi) { int t = a[lo]; a[lo] = a[hi]; a[hi] = t; lo++; hi--; }
    }

    static int[] rotateRight(int[] a, int k) {
        int n = a.length; k %= n;
        int[] r = a.clone();
        reverse(r, 0, n - 1);
        reverse(r, 0, k - 1);
        reverse(r, k, n - 1);
        return r;
    }

    static int kadane(int[] a) {
        int cur = a[0], best = a[0];
        for (int i = 1; i < a.length; i++) {
            cur = Math.max(a[i], cur + a[i]);
            best = Math.max(best, cur);
        }
        return best;
    }

    static int[] moveZeroes(int[] a) {
        int insert = 0;
        for (int x : a) if (x != 0) a[insert++] = x;
        while (insert < a.length) a[insert++] = 0;
        return a;
    }

    static Map<Integer, Integer> freq(int[] a) {
        Map<Integer, Integer> m = new HashMap<>();
        for (int x : a) m.merge(x, 1, Integer::sum);
        return m;
    }

    static int[][] rotateMatrix90(int[][] m) {
        // transpose + reverse each row
        int r = m.length, c = m[0].length;
        int[][] t = new int[c][r];
        for (int i = 0; i < r; i++)
            for (int j = 0; j < c; j++)
                t[j][i] = m[i][j];
        for (int[] row : t) reverse(row, 0, row.length - 1);
        return t;
    }

    public static void main(String[] args) {
        System.out.println("Q6 rotate = " + Arrays.toString(rotateRight(new int[]{1,2,3,4,5}, 2)));
        System.out.println("Q7 kadane = " + kadane(new int[]{-2,1,-3,4,-1,2,1,-5,4}));
        System.out.println("Q8 zeroes = " + Arrays.toString(moveZeroes(new int[]{0,1,0,3,12})));
        System.out.println("Q9 freq   = " + freq(new int[]{1,2,2,3,3,3}));

        int[][] m = {{1,2},{3,4}};
        int[][] r = rotateMatrix90(m);
        System.out.print("Q10 rot90 = [");
        for (int[] row : r) { System.out.print(Arrays.toString(row)); System.out.print(" "); }
        System.out.println("]");
    }
}
