/**
 * Day 5 — Arrays demo: traversal, max, reverse, rotate, frequency, 2D, Kadane.
 *
 * Compile: javac src/day-05/ArraysDemo.java
 * Run    : java -cp src/day-05 ArraysDemo
 */
import java.util.Arrays;

public class ArraysDemo {

    static void reverseRange(int[] a, int lo, int hi) {
        while (lo < hi) {
            int t = a[lo]; a[lo] = a[hi]; a[hi] = t;
            lo++; hi--;
        }
    }

    static int maxSubarrayKadane(int[] arr) {
        int cur = arr[0], best = arr[0];
        for (int i = 1; i < arr.length; i++) {
            cur = Math.max(arr[i], cur + arr[i]);
            best = Math.max(best, cur);
        }
        return best;
    }

    static void printMatrix(int[][] m) {
        for (int[] row : m) {
            for (int v : row) System.out.print(v + " ");
            System.out.println();
        }
    }

    static int[][] transpose(int[][] m) {
        int r = m.length, c = m[0].length;
        int[][] t = new int[c][r];
        for (int i = 0; i < r; i++)
            for (int j = 0; j < c; j++)
                t[j][i] = m[i][j];
        return t;
    }

    public static void main(String[] args) {
        int[] arr = {3, 1, 4, 1, 5, 9, 2, 6};

        // max — note arr[0] not 0
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) if (arr[i] > max) max = arr[i];

        // reverse via clone
        int[] rev = arr.clone();
        for (int i = 0, j = rev.length - 1; i < j; i++, j--) {
            int t = rev[i]; rev[i] = rev[j]; rev[j] = t;
        }

        // rotate right by 3 using reversal algorithm
        int k = 3;
        int[] rot = arr.clone();
        reverseRange(rot, 0, rot.length - 1);
        reverseRange(rot, 0, k - 1);
        reverseRange(rot, k, rot.length - 1);

        // frequency (values in [0,10))
        int[] freq = new int[10];
        for (int x : arr) freq[x]++;

        System.out.println("max  = " + max);
        System.out.println("rev  = " + Arrays.toString(rev));
        System.out.println("rot  = " + Arrays.toString(rot));
        System.out.println("freq = " + Arrays.toString(freq));

        // Kadane
        int[] kad = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println("kadane = " + maxSubarrayKadane(kad));

        // 2D
        int[][] mat = { {1, 2, 3}, {4, 5, 6}, {7, 8, 9} };
        System.out.println("matrix:");
        printMatrix(mat);
        System.out.println("transpose:");
        printMatrix(transpose(mat));
    }
}
