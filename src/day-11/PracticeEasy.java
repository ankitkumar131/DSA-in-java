/**
 * Day 11 — Easy practice.
 *
 * Compile: javac src/day-11/PracticeEasy.java
 * Run    : java -cp src/day-11 PracticeEasy
 */
import java.util.*;

public class PracticeEasy {

    static void insertion(int[] a) {
        for (int i = 1; i < a.length; i++) {
            int key = a[i], j = i - 1;
            while (j >= 0 && a[j] > key) { a[j + 1] = a[j]; j--; }
            a[j + 1] = key;
        }
    }

    static Integer[] sortByAbs(Integer[] a) {
        Arrays.sort(a, (x, y) -> Integer.compare(Math.abs(x), Math.abs(y)));
        return a;
    }

    static int[] sortedSquares(int[] a) {
        int n = a.length, lo = 0, hi = n - 1, idx = n - 1, out[] = new int[n];
        while (lo <= hi) {
            int ls = a[lo] * a[lo], hs = a[hi] * a[hi];
            if (ls > hs) { out[idx--] = ls; lo++; } else { out[idx--] = hs; hi--; }
        }
        return out;
    }

    static String[][] sortByHeight(String[][] people) {
        Arrays.sort(people, (a, b) -> Integer.compare(Integer.parseInt(a[1]), Integer.parseInt(b[1])));
        return people;
    }

    static int[][] bubbleTrace(int[] a) {
        int n = a.length;
        int[][] trace = new int[n][];
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++)
                if (a[j] > a[j + 1]) { int t = a[j]; a[j] = a[j + 1]; a[j + 1] = t; }
            trace[i] = a.clone();
        }
        return trace;
    }

    public static void main(String[] args) {
        int[] a = {5,2,3,1}; insertion(a); System.out.println("Q1 " + Arrays.toString(a));
        System.out.println("Q2 " + Arrays.toString(sortByAbs(new Integer[]{1,-3,2,-5,4})));
        System.out.println("Q3 " + Arrays.toString(sortedSquares(new int[]{-4,-1,0,3,10})));
        System.out.println("Q4 " + Arrays.deepToString(sortByHeight(new String[][]{{"Alice","165"},{"Bob","180"},{"Eve","170"}})));
        int[][] trace = bubbleTrace(new int[]{5,1,4,2,8});
        System.out.println("Q5 bubbleTrace:");
        for (int[] row : trace) System.out.println("   " + Arrays.toString(row));
    }
}
