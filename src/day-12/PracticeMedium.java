/**
 * Day 12 — Medium practice.
 *
 * Compile: javac src/day-12/PracticeMedium.java
 * Run    : java -cp src/day-12 PracticeMedium
 */
public class PracticeMedium {

    static long fibMemo(int n, long[] memo) {
        if (n < 2) return n;
        if (memo[n] != 0) return memo[n];
        return memo[n] = fibMemo(n - 1, memo) + fibMemo(n - 2, memo);
    }

    static int gcd(int a, int b) { return b == 0 ? a : gcd(b, a % b); }

    static void hanoi(int n, char from, char to, char aux) {
        if (n == 0) return;
        hanoi(n - 1, from, aux, to);
        System.out.println("disk " + n + ": " + from + " -> " + to);
        hanoi(n - 1, aux, to, from);
    }

    static boolean isPalindrome(String s) {
        if (s.length() <= 1) return true;
        if (s.charAt(0) != s.charAt(s.length() - 1)) return false;
        return isPalindrome(s.substring(1, s.length() - 1));
    }

    static double power(double x, int n) {
        if (n == 0) return 1;
        if (n < 0) return 1 / power(x, -n);
        if (n % 2 == 0) { double h = power(x, n / 2); return h * h; }
        return x * power(x, n - 1);
    }

    public static void main(String[] args) {
        System.out.println("Q6 fib(40)  = " + fibMemo(40, new long[41]));
        System.out.println("Q7 gcd      = " + gcd(12, 8));
        System.out.println("Q8 hanoi(3):");
        hanoi(3, 'A', 'C', 'B');
        System.out.println("Q9 palind   = " + isPalindrome("racecar"));
        System.out.println("Q10 2^-2    = " + power(2.0, -2));
    }
}
