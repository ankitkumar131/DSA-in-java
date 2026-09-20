import java.util.Scanner;

/**
 * Day 1 — Solutions to the 5 Hard practice problems.
 */
public class PracticeHard {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        // Q11. Rotate array by k (reversal algorithm)
        System.out.print("Q11: enter n and k then n ints: ");
        int n11 = sc.nextInt(), k11 = sc.nextInt() % n11;
        int[] arr11 = new int[n11];
        for (int i = 0; i < n11; i++) arr11[i] = sc.nextInt();
        // 3 reversals: [0, n-k-1], [n-k, n-1], [0, n-1]
        reverse(arr11, 0, n11 - k11 - 1);
        reverse(arr11, n11 - k11, n11 - 1);
        reverse(arr11, 0, n11 - 1);
        System.out.print("  rotated: ");
        for (int x : arr11) System.out.print(x + " ");
        System.out.println();

        // Q12. Sieve of Eratosthenes
        System.out.print("Q12: enter n: ");
        int n12 = sc.nextInt();
        boolean[] isPrime = new boolean[n12 + 1];
        for (int i = 2; i <= n12; i++) isPrime[i] = true;
        for (int p = 2; (long) p * p <= n12; p++) {
            if (isPrime[p]) {
                for (int m = p * p; m <= n12; m += p) isPrime[m] = false;
            }
        }
        System.out.print("  primes: ");
        for (int i = 2; i <= n12; i++) if (isPrime[i]) System.out.print(i + " ");
        System.out.println();

        // Q13. Reverse integer (no overflow)
        System.out.print("Q13: enter x: ");
        int x13 = sc.nextInt();
        long rev13 = 0;
        int tmp = x13;
        while (tmp != 0) {
            int d = tmp % 10;
            rev13 = rev13 * 10 + d;
            tmp /= 10;
        }
        if (rev13 < Integer.MIN_VALUE || rev13 > Integer.MAX_VALUE) rev13 = 0;
        System.out.println("  reversed = " + (int) rev13);

        // Q14. Count set bits
        System.out.print("Q14: enter n: ");
        int n14 = sc.nextInt();
        int count = 0;
        int tmp14 = n14;
        while (tmp14 != 0) { count++; tmp14 &= (tmp14 - 1); }
        System.out.println("  set bits = " + count);

        // Q15. Pascal's triangle
        System.out.print("Q15: enter n: ");
        int n15 = sc.nextInt();
        long[] row = new long[n15];
        for (int r = 0; r < n15; r++) {
            // walk right-to-left to avoid overwriting values still needed
            for (int c = r; c >= 0; c--) {
                if (c == 0 || c == r) row[c] = 1;
                else row[c] = row[c] + row[c - 1];
            }
            for (int c = 0; c <= r; c++) System.out.print(row[c] + " ");
            System.out.println();
        }
    }

    /** Reverses arr[lo..hi] in place. */
    static void reverse(int[] arr, int lo, int hi) {
        while (lo < hi) {
            int t = arr[lo]; arr[lo] = arr[hi]; arr[hi] = t;
            lo++; hi--;
        }
    }
}
