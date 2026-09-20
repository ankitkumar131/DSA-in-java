/**
 * Day 10 — Easy practice.
 *
 * Compile: javac src/day-10/PracticeEasy.java
 * Run    : java -cp src/day-10 PracticeEasy
 */
public class PracticeEasy {

    static int search(int[] a, int t) {
        int lo = 0, hi = a.length - 1;
        while (lo <= hi) {
            int m = lo + (hi - lo) / 2;
            if (a[m] == t) return m;
            if (a[m] < t) lo = m + 1; else hi = m - 1;
        }
        return -1;
    }

    static int searchInsert(int[] a, int t) {
        int lo = 0, hi = a.length;
        while (lo < hi) {
            int m = lo + (hi - lo) / 2;
            if (a[m] < t) lo = m + 1; else hi = m;
        }
        return lo;
    }

    static int firstBadVersion(int n, int firstBad) {
        int lo = 1, hi = n;
        while (lo < hi) {
            int m = lo + (hi - lo) / 2;
            if (m >= firstBad) hi = m; else lo = m + 1;
        }
        return lo;
    }

    static boolean isPerfectSquare(int n) {
        long lo = 1, hi = n;
        while (lo <= hi) {
            long m = lo + (hi - lo) / 2;
            if (m * m == n) return true;
            if (m * m < n) lo = m + 1; else hi = m - 1;
        }
        return false;
    }

    static char nextGreatestLetter(char[] letters, char target) {
        int lo = 0, hi = letters.length;
        while (lo < hi) {
            int m = lo + (hi - lo) / 2;
            if (letters[m] <= target) lo = m + 1; else hi = m;
        }
        return letters[lo % letters.length];
    }

    public static void main(String[] args) {
        System.out.println("Q1 search    = " + search(new int[]{1,3,5,7,9}, 5));
        System.out.println("Q2 searchIns = " + searchInsert(new int[]{1,3,5,6}, 2));
        System.out.println("Q3 firstBad  = " + firstBadVersion(5, 4));
        System.out.println("Q4 perfectSq = " + isPerfectSquare(16));
        System.out.println("Q5 nextLetter= " + nextGreatestLetter(new char[]{'c','f','j'}, 'a'));
    }
}
