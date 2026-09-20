/**
 * Day 7 — Two pointers demo.
 *
 * Compile: javac src/day-07/TwoPointersDemo.java
 * Run    : java -cp src/day-07 TwoPointersDemo
 */
import java.util.Arrays;

public class TwoPointersDemo {

    static int[] twoSumSorted(int[] a, int t) {
        int lo = 0, hi = a.length - 1;
        while (lo < hi) {
            int s = a[lo] + a[hi];
            if (s == t) return new int[]{lo, hi};
            else if (s < t) lo++; else hi--;
        }
        return new int[]{-1, -1};
    }

    static int maxWater(int[] h) {
        int lo = 0, hi = h.length - 1, best = 0;
        while (lo < hi) {
            best = Math.max(best, (hi - lo) * Math.min(h[lo], h[hi]));
            if (h[lo] < h[hi]) lo++; else hi--;
        }
        return best;
    }

    static int removeDuplicates(int[] a) {
        if (a.length == 0) return 0;
        int w = 1;
        for (int r = 1; r < a.length; r++)
            if (a[r] != a[r - 1]) a[w++] = a[r];
        return w;
    }

    static void moveZeroes(int[] a) {
        int w = 0;
        for (int r = 0; r < a.length; r++) if (a[r] != 0) a[w++] = a[r];
        while (w < a.length) a[w++] = 0;
    }

    static boolean isPalindrome(String s) {
        int lo = 0, hi = s.length() - 1;
        while (lo < hi) {
            char a = s.charAt(lo), b = s.charAt(hi);
            if (!Character.isLetterOrDigit(a)) lo++;
            else if (!Character.isLetterOrDigit(b)) hi--;
            else if (Character.toLowerCase(a) != Character.toLowerCase(b)) return false;
            else { lo++; hi--; }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println("twoSumSorted(8) = " + Arrays.toString(twoSumSorted(new int[]{1,3,4,5,7,11}, 8)));
        System.out.println("maxWater = " + maxWater(new int[]{1,8,6,2,5,4,8,3,7}));
        int[] dup = {1,1,2,2,3,4,4};
        int len = removeDuplicates(dup);
        System.out.println("dedup (len=" + len + ") = " + Arrays.toString(Arrays.copyOf(dup, len)));
        int[] arr = {0,1,0,3,12};
        moveZeroes(arr);
        System.out.println("moveZeroes = " + Arrays.toString(arr));
        System.out.println("palindrome = " + isPalindrome("A man, a plan, a canal: Panama"));
    }
}
