/**
 * Day 10 — Medium practice.
 *
 * Compile: javac src/day-10/PracticeMedium.java
 * Run    : java -cp src/day-10 PracticeMedium
 */
import java.util.*;

public class PracticeMedium {

    static int[] searchRange(int[] a, int t) {
        return new int[]{first(a, t), last(a, t)};
    }
    static int first(int[] a, int t) {
        int lo = 0, hi = a.length - 1, ans = -1;
        while (lo <= hi) {
            int m = lo + (hi - lo) / 2;
            if (a[m] == t) { ans = m; hi = m - 1; }
            else if (a[m] < t) lo = m + 1; else hi = m - 1;
        }
        return ans;
    }
    static int last(int[] a, int t) {
        int lo = 0, hi = a.length - 1, ans = -1;
        while (lo <= hi) {
            int m = lo + (hi - lo) / 2;
            if (a[m] == t) { ans = m; lo = m + 1; }
            else if (a[m] < t) lo = m + 1; else hi = m - 1;
        }
        return ans;
    }

    static int searchRotated(int[] a, int t) {
        int lo = 0, hi = a.length - 1;
        while (lo <= hi) {
            int m = lo + (hi - lo) / 2;
            if (a[m] == t) return m;
            if (a[lo] <= a[m]) {
                if (t >= a[lo] && t < a[m]) hi = m - 1; else lo = m + 1;
            } else {
                if (t > a[m] && t <= a[hi]) lo = m + 1; else hi = m - 1;
            }
        }
        return -1;
    }

    static int findMinRotated(int[] a) {
        int lo = 0, hi = a.length - 1;
        while (lo < hi) {
            int m = lo + (hi - lo) / 2;
            if (a[m] > a[hi]) lo = m + 1; else hi = m;
        }
        return a[lo];
    }

    static int findPeak(int[] a) {
        int lo = 0, hi = a.length - 1;
        while (lo < hi) {
            int m = lo + (hi - lo) / 2;
            if (a[m] < a[m + 1]) lo = m + 1; else hi = m;
        }
        return lo;
    }

    static int singleNonDuplicate(int[] a) {
        int lo = 0, hi = a.length - 1;
        while (lo < hi) {
            int m = lo + (hi - lo) / 2;
            if (m % 2 == 1) m--;
            if (a[m] == a[m + 1]) lo = m + 2;
            else hi = m;
        }
        return a[lo];
    }

    public static void main(String[] args) {
        System.out.println("Q6  searchRange    = " + Arrays.toString(searchRange(new int[]{5,7,7,8,8,10}, 8)));
        System.out.println("Q7  rotatedSearch  = " + searchRotated(new int[]{4,5,6,7,0,1,2}, 0));
        System.out.println("Q8  findMinRotated = " + findMinRotated(new int[]{3,4,5,1,2}));
        System.out.println("Q9  peak           = " + findPeak(new int[]{1,2,3,1}));
        System.out.println("Q10 singleNonDup   = " + singleNonDuplicate(new int[]{1,1,2,3,3,4,4,8,8}));
    }
}
