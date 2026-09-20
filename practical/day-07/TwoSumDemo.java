/** Day 7 tiny project: two-pointer two-sum on a sorted array. */
import java.util.*;
public class TwoSumDemo {
    public static void main(String[] args) {
        int[] a = {1, 4, 6, 8, 10, 15};
        int target = 14;
        int lo = 0, hi = a.length - 1;
        while (lo < hi) {
            int s = a[lo] + a[hi];
            if (s == target) { System.out.println("(" + a[lo] + "," + a[hi] + ")"); return; }
            if (s < target) lo++; else hi--;
        }
        System.out.println("no pair");
    }
}
