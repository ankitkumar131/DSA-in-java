/**
 * Day 5 — Easy practice solutions.
 *
 * Compile: javac src/day-05/PracticeEasy.java
 * Run    : java -cp src/day-05 PracticeEasy
 */
public class PracticeEasy {
    static int findMax(int[] a) {
        int m = a[0];
        for (int i = 1; i < a.length; i++) if (a[i] > m) m = a[i];
        return m;
    }

    static int[] reverse(int[] a) {
        int[] r = a.clone();
        for (int i = 0, j = r.length - 1; i < j; i++, j--) {
            int t = r[i]; r[i] = r[j]; r[j] = t;
        }
        return r;
    }

    static long sum(int[] a) {
        long s = 0; for (int x : a) s += x; return s;
    }

    static boolean hasDuplicate(int[] a) {
        java.util.Arrays.sort(a);
        for (int i = 1; i < a.length; i++) if (a[i] == a[i - 1]) return true;
        return false;
    }

    static int secondLargest(int[] a) {
        int first = Integer.MIN_VALUE, second = Integer.MIN_VALUE;
        for (int x : a) {
            if (x > first)       { second = first; first = x; }
            else if (x < first && x > second) second = x;
        }
        return second;
    }

    public static void main(String[] args) {
        int[] a = {1, 5, 3, 9, 2};
        System.out.println("Q1 max = " + findMax(a));
        System.out.println("Q2 rev = " + java.util.Arrays.toString(reverse(new int[]{1,2,3,4})));
        System.out.println("Q3 sum = " + sum(new int[]{1,2,3,4,5}));
        System.out.println("Q4 dup = " + hasDuplicate(new int[]{1,2,3,1}));
        System.out.println("Q5 sec = " + secondLargest(new int[]{5,2,8,8,3}));
    }
}
