/**
 * Day 9 — Prefix sum demo.
 *
 * Compile: javac src/day-09/PrefixSumDemo.java
 * Run    : java -cp src/day-09 PrefixSumDemo
 */
import java.util.*;

public class PrefixSumDemo {

    static long[] build(int[] a) {
        long[] p = new long[a.length + 1];
        for (int i = 0; i < a.length; i++) p[i + 1] = p[i] + a[i];
        return p;
    }

    static long rangeSum(long[] p, int l, int r) {
        return p[r + 1] - p[l];
    }

    static int[] rangeUpdate(int[] diff, int l, int r, int val) {
        diff[l] += val;
        if (r + 1 < diff.length) diff[r + 1] -= val;
        return diff;
    }
    static int[] finalise(int[] diff) {
        int[] a = new int[diff.length - 1];
        int running = 0;
        for (int i = 0; i < a.length; i++) { running += diff[i]; a[i] = running; }
        return a;
    }

    static int subarraySum(int[] nums, int k) {
        Map<Long, Integer> m = new HashMap<>();
        m.put(0L, 1);
        long sum = 0;
        int count = 0;
        for (int x : nums) {
            sum += x;
            count += m.getOrDefault(sum - k, 0);
            m.merge(sum, 1, Integer::sum);
        }
        return count;
    }

    static int subarrayXor(int[] a, int k) {
        Map<Integer, Integer> m = new HashMap<>();
        m.put(0, 1);
        int prefix = 0, count = 0;
        for (int x : a) {
            prefix ^= x;
            count += m.getOrDefault(prefix ^ k, 0);
            m.merge(prefix, 1, Integer::sum);
        }
        return count;
    }

    static long[][] build2D(int[][] m) {
        int r = m.length, c = m[0].length;
        long[][] p = new long[r + 1][c + 1];
        for (int i = 1; i <= r; i++)
            for (int j = 1; j <= c; j++)
                p[i][j] = m[i - 1][j - 1] + p[i - 1][j] + p[i][j - 1] - p[i - 1][j - 1];
        return p;
    }
    static long sum2D(long[][] p, int r1, int c1, int r2, int c2) {
        return p[r2 + 1][c2 + 1] - p[r1][c2 + 1] - p[r2 + 1][c1] + p[r1][c1];
    }

    public static void main(String[] args) {
        int[] arr = {3, 1, 4, 1, 5, 9, 2, 6};
        long[] p = build(arr);
        System.out.println("rangeSum [2,5] = " + rangeSum(p, 2, 5));

        int[] diff = new int[6];
        rangeUpdate(diff, 1, 3, 10);
        System.out.println("after diff     = " + Arrays.toString(finalise(diff)));

        System.out.println("subarraySum    = " + subarraySum(new int[]{1,1,1}, 2));
        System.out.println("subarrayXor    = " + subarrayXor(new int[]{4,2,2,6}, 0));

        int[][] m = {{1,2,3},{4,5,6},{7,8,9}};
        long[][] p2 = build2D(m);
        System.out.println("2D sum         = " + sum2D(p2, 0, 0, 1, 1));
    }
}
