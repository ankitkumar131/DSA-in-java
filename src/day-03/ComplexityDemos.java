/**
 * Day 3 — Big-O demonstrated by example.
 *
 * Compile: javac src/day-03/ComplexityDemos.java
 * Run    : java -cp src/day-03 ComplexityDemos
 *
 * Each method labels its own complexity.
 */
public class ComplexityDemos {

    // O(1) — constant
    static int constantAccess(int[] arr, int i) {
        return arr[i];          // single array index — fixed cost
    }

    // O(n) — linear
    static long linearSum(int[] arr) {
        long total = 0;
        for (int i = 0; i < arr.length; i++) total += arr[i];
        return total;
    }

    // O(n^2) — quadratic
    static int countPairs(int[] arr) {
        int pairs = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                pairs++;
            }
        }
        return pairs;
    }

    // O(log n) — logarithmic (binary-search-like halving)
    static int halvingCount(int n) {
        int count = 0;
        while (n > 1) {
            n = n / 2;
            count++;
        }
        return count;
    }

    // O(n log n) — linearithmic
    static int nlognCount(int n) {
        int ops = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < n; j *= 2) {
                ops++;
            }
        }
        return ops;
    }

    // O(2^n) — exponential recursion
    static int exponentialRec(int n) {
        if (n <= 1) return 1;
        return exponentialRec(n - 1) + exponentialRec(n - 2);
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5};

        System.out.println("O(1)     arr[2] = " + constantAccess(arr, 2));
        System.out.println("O(n)     sum   = " + linearSum(arr));
        System.out.println("O(n^2)   pairs = " + countPairs(arr));
        System.out.println("O(log n) halves for 1_000_000 = " + halvingCount(1_000_000));
        System.out.println("O(nlogn) ops for n=8 = " + nlognCount(8));

        // exponentialRec(30) would be too slow — show small input only
        System.out.println("O(2^n)   fib-style for n=10 = " + exponentialRec(10));
    }
}
