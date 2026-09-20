/**
 * Day 4 — Hard practice.
 *
 * Compile: javac src/day-04/PracticeHard.java
 * Run    : java -cp src/day-04 PracticeHard
 */
public class PracticeHard {

    // Q11: n! mod p
    static long factorialMod(int n, long mod) {
        long r = 1;
        for (int i = 2; i <= n; i++) r = (r * i) % mod;
        return r;
    }

    // Q12: modular inverse via Fermat's little theorem (requires prime mod)
    static long modInverse(long a, long p) {
        // a^(p-2) mod p
        long r = 1; a %= p;
        long e = p - 2;
        while (e > 0) {
            if ((e & 1) == 1) r = (r * a) % p;
            a = (a * a) % p;
            e >>= 1;
        }
        return r;
    }

    // Q13: total set bits 1..n
    static long totalSetBits(int n) {
        long t = 0;
        for (int i = 1; i <= n; i++) t += Long.bitCount(i);
        return t;
    }

    // Q14: single number (XOR trick)
    static int singleNumber(int[] a) {
        int x = 0;
        for (int v : a) x ^= v;
        return x;
    }

    // Q15: power of four
    static boolean isPowerOfFour(int n) {
        return n > 0 && (n & (n - 1)) == 0 && (n & 0x55555555) != 0;
    }

    public static void main(String[] args) {
        System.out.println("Q11 5! mod 7     = " + factorialMod(5, 7));
        System.out.println("Q12 3^-1 mod 11  = " + modInverse(3, 11));
        System.out.println("Q13 totalBits(5)= " + totalSetBits(5));
        System.out.println("Q14 single      = " + singleNumber(new int[]{4, 1, 2, 1, 2}));
        System.out.println("Q15 powerOf4(16)= " + isPowerOfFour(16));
    }
}
