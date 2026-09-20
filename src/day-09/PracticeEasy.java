/**
 * Day 9 — Easy practice.
 *
 * Compile: javac src/day-09/PracticeEasy.java
 * Run    : java -cp src/day-09 PracticeEasy
 */
import java.util.*;

public class PracticeEasy {

    static class NumArray {
        long[] p;
        NumArray(int[] a) {
            p = new long[a.length + 1];
            for (int i = 0; i < a.length; i++) p[i + 1] = p[i] + a[i];
        }
        long sumRange(int l, int r) { return p[r + 1] - p[l]; }
    }

    static int[] runningSum(int[] a) {
        for (int i = 1; i < a.length; i++) a[i] += a[i - 1];
        return a;
    }

    static int pivotIndex(int[] a) {
        long total = 0; for (int x : a) total += x;
        long left = 0;
        for (int i = 0; i < a.length; i++) {
            if (left == total - left - a[i]) return i;
            left += a[i];
        }
        return -1;
    }

    static int subarraySumK(int[] nums, int k) {
        Map<Long, Integer> m = new HashMap<>();
        m.put(0L, 1);
        long sum = 0; int c = 0;
        for (int x : nums) {
            sum += x;
            c += m.getOrDefault(sum - k, 0);
            m.merge(sum, 1, Integer::sum);
        }
        return c;
    }

    static int[] rangeAddition(int length, int[][] ops) {
        int[] diff = new int[length + 1];
        for (int[] op : ops) {
            diff[op[0]] += op[2];
            if (op[1] + 1 <= length) diff[op[1] + 1] -= op[2];
        }
        int[] res = new int[length];
        int running = 0;
        for (int i = 0; i < length; i++) { running += diff[i]; res[i] = running; }
        return res;
    }

    public static void main(String[] args) {
        NumArray na = new NumArray(new int[]{1,2,3,4,5});
        System.out.println("Q1 sum(0,2) = " + na.sumRange(0, 2));
        System.out.println("Q1 sum(2,4) = " + na.sumRange(2, 4));
        System.out.println("Q2 running  = " + Arrays.toString(runningSum(new int[]{1,2,3,4})));
        System.out.println("Q3 pivot    = " + pivotIndex(new int[]{1,7,3,6,5,6}));
        System.out.println("Q4 subSumK  = " + subarraySumK(new int[]{1,1,1}, 2));
        System.out.println("Q5 rangeAdd = " + Arrays.toString(rangeAddition(5, new int[][]{{1,3,2},{2,4,3},{0,2,-2}})));
    }
}
