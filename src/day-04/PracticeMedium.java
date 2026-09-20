/**
 * Day 4 — Medium practice.
 *
 * Compile: javac src/day-04/PracticeMedium.java
 * Run    : java -cp src/day-04 PracticeMedium
 */
public class PracticeMedium {

    static long power(long b, long e, long mod) {
        long r = 1; b %= mod;
        while (e > 0) {
            if ((e & 1) == 1) r = (r * b) % mod;
            b = (b * b) % mod; e >>= 1;
        }
        return r;
    }

    static int reverseInt(int n) {
        long r = 0; while (n != 0) { r = r * 10 + n % 10; n /= 10; }
        return (int) r;
    }
    static boolean isPowerOfTwo(int n) { return n > 0 && (n & (n - 1)) == 0; }
    static int sumDigits(int n) { int s = 0; while (n > 0) { s += n % 10; n /= 10; } return s; }

    static void sieve(int n, java.util.List<Integer> out) {
        boolean[] p = new boolean[n + 1];
        for (int i = 2; i <= n; i++) p[i] = true;
        for (int i = 2; (long) i * i <= n; i++)
            if (p[i]) for (int j = i * i; j <= n; j += i) p[j] = false;
        for (int i = 2; i <= n; i++) if (p[i]) out.add(i);
    }

    public static void main(String[] args) {
        System.out.println("Q6 primes up to 30:");
        java.util.List<Integer> primes = new java.util.ArrayList<>();
        sieve(30, primes);
        System.out.println("    " + primes);

        System.out.println("Q7 2^10 mod 1000 = " + power(2, 10, 1000));
        System.out.println("Q8 palindrome(121) = " + (121 == reverseInt(121)));
        System.out.println("Q9 isPowerOfTwo(16) = " + isPowerOfTwo(16));
        System.out.println("Q10 sumDigits(12345) = " + sumDigits(12345));
    }
}
