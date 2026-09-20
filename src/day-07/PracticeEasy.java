/**
 * Day 7 — Easy practice.
 *
 * Compile: javac src/day-07/PracticeEasy.java
 * Run    : java -cp src/day-07 PracticeEasy
 */
import java.util.Arrays;

public class PracticeEasy {

    static int[] twoSumSorted(int[] a, int t) {
        int lo = 0, hi = a.length - 1;
        while (lo < hi) {
            int s = a[lo] + a[hi];
            if (s == t) return new int[]{lo, hi};
            else if (s < t) lo++; else hi--;
        }
        return new int[]{-1, -1};
    }

    static int removeDup(int[] a) {
        if (a.length == 0) return 0;
        int w = 1;
        for (int r = 1; r < a.length; r++) if (a[r] != a[r - 1]) a[w++] = a[r];
        return w;
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

    static void moveZeroes(int[] a) {
        int w = 0;
        for (int r = 0; r < a.length; r++) if (a[r] != 0) a[w++] = a[r];
        while (w < a.length) a[w++] = 0;
    }

    static void reverse(char[] a) {
        int lo = 0, hi = a.length - 1;
        while (lo < hi) { char t = a[lo]; a[lo] = a[hi]; a[hi] = t; lo++; hi--; }
    }

    public static void main(String[] args) {
        System.out.println("Q1 twoSumSorted = " + Arrays.toString(twoSumSorted(new int[]{2,7,11,15}, 9)));
        int[] d = {1,1,2,3,3}; int n = removeDup(d);
        System.out.println("Q2 dedup (len=" + n + ") = " + Arrays.toString(Arrays.copyOf(d, n)));
        System.out.println("Q3 palindrome   = " + isPalindrome("A man, a plan, a canal: Panama"));
        int[] a = {0,1,0,3,12}; moveZeroes(a);
        System.out.println("Q4 moveZeroes   = " + Arrays.toString(a));
        char[] c = {'h','e','l','l','o'}; reverse(c);
        System.out.println("Q5 reverse      = " + Arrays.toString(c));
    }
}
