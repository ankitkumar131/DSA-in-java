/** Day 4 tiny project: sieve of Eratosthenes + modular exponentiation. */
import java.util.*;

public class Primes {
    static boolean[] sieve(int n) {
        boolean[] p = new boolean[n + 1];
        Arrays.fill(p, true); p[0] = p[1] = false;
        for (int i = 2; (long) i * i <= n; i++)
            if (p[i]) for (int j = i * i; j <= n; j += i) p[j] = false;
        return p;
    }
    static long modPow(long base, long exp, long mod) {
        long r = 1; base %= mod;
        while (exp > 0) {
            if ((exp & 1) == 1) r = r * base % mod;
            base = base * base % mod; exp >>= 1;
        }
        return r;
    }
    public static void main(String[] args) {
        boolean[] p = sieve(50);
        int count = 0;
        for (int i = 2; i < p.length; i++) if (p[i]) count++;
        System.out.println("primes up to 50: " + count);
        System.out.println("2^10 mod 1000 = " + modPow(2, 10, 1000));
    }
}
