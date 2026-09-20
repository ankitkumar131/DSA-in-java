/**
 * Day 22 — Greedy demo.
 *
 * Compile: javac src/day-22/GreedyDemo.java
 * Run    : java -cp src/day-22 GreedyDemo
 */
import java.util.*;

public class GreedyDemo {

    static int activitySelection(int[][] iv) {
        Arrays.sort(iv, (a, b) -> Integer.compare(a[1], b[1]));
        int count = 0, end = Integer.MIN_VALUE;
        for (int[] x : iv) if (x[0] >= end) { count++; end = x[1]; }
        return count;
    }

    static boolean canJump(int[] a) {
        int reach = 0;
        for (int i = 0; i < a.length; i++) {
            if (i > reach) return false;
            reach = Math.max(reach, i + a[i]);
        }
        return true;
    }

    static int gasStation(int[] gas, int[] cost) {
        int total = 0, tank = 0, start = 0;
        for (int i = 0; i < gas.length; i++) {
            int diff = gas[i] - cost[i];
            total += diff; tank += diff;
            if (tank < 0) { start = i + 1; tank = 0; }
        }
        return total >= 0 ? start : -1;
    }

    static int minPlatforms(int[] arr, int[] dep) {
        Arrays.sort(arr); Arrays.sort(dep);
        int plat = 1, max = 1, i = 1, j = 0;
        while (i < arr.length && j < dep.length) {
            if (arr[i] <= dep[j]) { plat++; i++; max = Math.max(max, plat); }
            else { plat--; j++; }
        }
        return max;
    }

    static double fractionalKnapsack(int[] w, int[] v, int cap) {
        Integer[] idx = new Integer[w.length];
        for (int i = 0; i < idx.length; i++) idx[i] = i;
        Arrays.sort(idx, (a, b) -> Double.compare((double) v[b] / w[b], (double) v[a] / w[a]));
        double total = 0;
        for (int i : idx) {
            if (cap >= w[i]) { cap -= w[i]; total += v[i]; }
            else { total += (double) cap * v[i] / w[i]; break; }
        }
        return total;
    }

    static int[][] merge(int[][] iv) {
        Arrays.sort(iv, (a, b) -> Integer.compare(a[0], b[0]));
        List<int[]> out = new ArrayList<>();
        for (int[] x : iv) {
            if (out.isEmpty() || out.get(out.size() - 1)[1] < x[0]) out.add(x);
            else out.get(out.size() - 1)[1] = Math.max(out.get(out.size() - 1)[1], x[1]);
        }
        return out.toArray(new int[0][]);
    }

    public static void main(String[] args) {
        System.out.println("activitySelection = " + activitySelection(new int[][]{{1,3},{2,4},{3,5},{5,7},{6,9}}));
        System.out.println("canJump           = " + canJump(new int[]{2,3,1,1,4}));
        System.out.println("gasStation        = " + gasStation(new int[]{1,2,3,4,5}, new int[]{3,4,5,1,2}));
        System.out.println("minPlatforms      = " + minPlatforms(new int[]{900, 940, 950, 1100, 1500, 1800}, new int[]{910, 1200, 1120, 1130, 1900, 2000}));
        System.out.println("fractional        = " + fractionalKnapsack(new int[]{10,20,30}, new int[]{60,100,120}, 50));
        System.out.println("merge             = " + Arrays.deepToString(merge(new int[][]{{1,3},{2,6},{8,10},{15,18}})));
    }
}
