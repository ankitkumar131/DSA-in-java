/**
 * Day 4 — Easy practice.
 *
 * Compile: javac src/day-04/PracticeEasy.java
 * Run    : java -cp src/day-04 PracticeEasy
 */
public class PracticeEasy {

    static long gcd(long a, long b) { while (b != 0) { long t = a % b; a = b; b = t; } return a; }
    static long lcm(long a, long b) { return (a / gcd(a, b)) * b; }
    static boolean isPrime(int n) {
        if (n < 2) return false; if (n == 2) return true; if (n % 2 == 0) return false;
        for (int i = 3; (long) i * i <= n; i += 2) if (n % i == 0) return false;
        return true;
    }
    static int reverseInt(int n) {
        long r = 0; while (n != 0) { r = r * 10 + n % 10; n /= 10; }
        if (r < Integer.MIN_VALUE || r > Integer.MAX_VALUE) return 0;
        return (int) r;
    }
    static int countSetBits(int n) {
        int c = 0; while (n != 0) { n &= (n - 1); c++; } return c;
    }

    public static void main(String[] args) {
        System.out.println("Q1 gcd(12,18) = " + gcd(12, 18));
        System.out.println("Q2 lcm(4,6)   = " + lcm(4, 6));
        System.out.println("Q3 isPrime(29)= " + isPrime(29));
        System.out.println("Q4 reverse(123)= " + reverseInt(123));
        System.out.println("Q5 setBits(11)= " + countSetBits(11));
    }
}
