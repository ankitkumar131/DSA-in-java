/** Day 3 tiny project: count steps of linear vs binary search. */
import java.util.*;

public class AlgoRace {
    static int linearSteps = 0;
    static int linear(int[] a, int t) {
        for (int i = 0; i < a.length; i++) { linearSteps++; if (a[i] == t) return i; }
        return -1;
    }
    static int binarySteps = 0;
    static int binary(int[] a, int t) {
        int lo = 0, hi = a.length - 1;
        while (lo <= hi) {
            binarySteps++;
            int mid = lo + (hi - lo) / 2;
            if (a[mid] == t) return mid;
            if (a[mid] < t) lo = mid + 1; else hi = mid - 1;
        }
        return -1;
    }
    public static void main(String[] args) {
        int n = 1_000_000;
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = i;
        int t = n - 1;
        System.out.println("linear idx=" + linear(a, t) + " steps=" + linearSteps);
        System.out.println("binary idx=" + binary(a, t) + " steps=" + binarySteps);
    }
}
