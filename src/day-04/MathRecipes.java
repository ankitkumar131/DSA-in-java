/**
 * Day 4 — Math recipes: GCD, LCM, primes, sieve, power, reverse, palindrome.
 *
 * Compile: javac src/day-04/MathRecipes.java
 * Run    : java -cp src/day-04 MathRecipes
 */
public class MathRecipes {

    // Euclidean GCD
    public static long gcd(long a, long b) {
        while (b != 0) {
            long t = a % b;
            a = b;
            b = t;
        }
        return a;
    }

    // LCM = (a / gcd) * b
    public static long lcm(long a, long b) {
        return (a / gcd(a, b)) * b;
    }

    // Trial-division primality up to sqrt(n)
    public static boolean isPrime(int n) {
        if (n < 2) return false;
        if (n == 2) return true;
        if (n % 2 == 0) return false;
        for (int i = 3; (long) i * i <= n; i += 2) {
            if (n % i == 0) return false;
        }
        return true;
    }

    // Sieve of Eratosthenes
    public static boolean[] sieve(int n) {
        boolean[] prime = new boolean[n + 1];
        for (int i = 2; i <= n; i++) prime[i] = true;
        for (int i = 2; (long) i * i <= n; i++) {
            if (prime[i]) {
                for (int j = i * i; j <= n; j += i) prime[j] = false;
            }
        }
        return prime;
    }

    // Fast modular exponentiation
    public static long power(long b, long e, long mod) {
        long res = 1;
        b %= mod;
        while (e > 0) {
            if ((e & 1) == 1) res = (res * b) % mod;
            b = (b * b) % mod;
            e >>= 1;
        }
        return res;
    }

    // Reverse an int, return 0 on overflow
    public static int reverseInt(int n) {
        long rev = 0;
        while (n != 0) {
            rev = rev * 10 + n % 10;
            n /= 10;
        }
        if (rev < Integer.MIN_VALUE || rev > Integer.MAX_VALUE) return 0;
        return (int) rev;
    }

    public static boolean isPalindrome(int n) {
        if (n < 0) return false;
        return n == reverseInt(n);
    }

    public static void main(String[] args) {
        System.out.println("gcd(12,18)  = " + gcd(12, 18));
        System.out.println("lcm(4,6)    = " + lcm(4, 6));
        System.out.println("isPrime(29) = " + isPrime(29));
        System.out.println("isPrime(15) = " + isPrime(15));

        boolean[] primes = sieve(30);
        System.out.print("sieve(30): ");
        for (int i = 2; i <= 30; i++) if (primes[i]) System.out.print(i + " ");
        System.out.println();

        System.out.println("2^10 mod 1000 = " + power(2, 10, 1000));
        System.out.println("reverse(123) = " + reverseInt(123));
        System.out.println("palindrome(121) = " + isPalindrome(121));
    }
}
