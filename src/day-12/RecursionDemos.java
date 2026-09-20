/**
 * Day 12 — Recursion demos.
 *
 * Compile: javac src/day-12/RecursionDemos.java
 * Run    : java -cp src/day-12 RecursionDemos
 */
public class RecursionDemos {

    static int factorial(int n) { return n <= 1 ? 1 : n * factorial(n - 1); }

    static long fibMemo(int n, long[] memo) {
        if (n < 2) return n;
        if (memo[n] != 0) return memo[n];
        return memo[n] = fibMemo(n - 1, memo) + fibMemo(n - 2, memo);
    }

    static long fibIter(int n) {
        if (n < 2) return n;
        long a = 0, b = 1;
        for (int i = 2; i <= n; i++) { long c = a + b; a = b; b = c; }
        return b;
    }

    static int sum(int[] a, int i) { return i == a.length ? 0 : a[i] + sum(a, i + 1); }

    static String reverse(String s) {
        return s.length() <= 1 ? s : reverse(s.substring(1)) + s.charAt(0);
    }

    static void reverseInPlace(char[] a, int lo, int hi) {
        if (lo >= hi) return;
        char t = a[lo]; a[lo] = a[hi]; a[hi] = t;
        reverseInPlace(a, lo + 1, hi - 1);
    }

    static int power(int b, int e) {
        if (e == 0) return 1;
        if (e % 2 == 0) { int h = power(b, e / 2); return h * h; }
        return b * power(b, e - 1);
    }

    static int gcd(int a, int b) { return b == 0 ? a : gcd(b, a % b); }

    static void hanoi(int n, char from, char to, char aux) {
        if (n == 0) return;
        hanoi(n - 1, from, aux, to);
        System.out.println("disk " + n + ": " + from + " -> " + to);
        hanoi(n - 1, aux, to, from);
    }

    public static void main(String[] args) {
        System.out.println("5!       = " + factorial(5));
        System.out.println("fib(40)  = " + fibMemo(40, new long[41]));
        System.out.println("fib(40)  = " + fibIter(40));
        System.out.println("sum      = " + sum(new int[]{1,2,3,4,5}, 0));
        System.out.println("reverse  = " + reverse("hello"));
        char[] arr = {'h','e','l','l','o'};
        reverseInPlace(arr, 0, arr.length - 1);
        System.out.println("reverseInPlace = " + new String(arr));
        System.out.println("2^10     = " + power(2, 10));
        System.out.println("gcd(12,8)= " + gcd(12, 8));
        System.out.println("hanoi(3):");
        hanoi(3, 'A', 'C', 'B');
    }
}
