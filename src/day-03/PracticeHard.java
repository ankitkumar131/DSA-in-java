/**
 * Day 3 — Hard practice: identify and reason about complexity.
 *
 * Compile: javac src/day-03/PracticeHard.java
 * Run    : java -cp src/day-03 PracticeHard
 */
public class PracticeHard {

    // Q11: branching recursion
    static int q11_branching(int n) {
        if (n <= 1) return 1;
        return q11_branching(n - 1) + q11_branching(n - 1);
    }

    // Q12: merge sort recursion (simple counting of merge work)
    static int q12_mergeWork(int n) {
        // each level does O(n) merges; log n levels => O(n log n)
        return n * (int)(Math.log(n) / Math.log(2));
    }

    // Q13: quicksort worst-case — partition that always picks the minimum
    static int q13_qsWorst(int n) { return n * (n - 1) / 2; } // n + (n-1) + ... + 1

    // Q14: recursive Fibonacci — count calls
    static int q14_calls = 0;
    static int q14_fib(int n) {
        q14_calls++;
        if (n <= 1) return n;
        return q14_fib(n - 1) + q14_fib(n - 2);
    }

    // Q15: Master theorem case where f(n) = n^k log n
    static double q15_n2Logn(int n) {
        // T(n) = 4 T(n/2) + n^2 -> a = 4, b = 2, k = 2, b^k = 4 == a
        // case: O(n^k log n) = O(n^2 log n)
        return n * n * (Math.log(n) / Math.log(2));
    }

    public static void main(String[] args) {
        System.out.println("Q11 calls (2^10 - 1) ish -> " + q11_branching(10));
        System.out.println("Q12 merge sort O(n log n) for n=16 -> " + q12_mergeWork(16));
        System.out.println("Q13 quicksort worst-case ops for n=10 -> " + q13_qsWorst(10));
        q14_fib(20);
        System.out.println("Q14 fib(20) calls = " + q14_calls);
        System.out.println("Q15 n^2 log n estimate for n=16 -> " + (int) q15_n2Logn(16));
    }
}
