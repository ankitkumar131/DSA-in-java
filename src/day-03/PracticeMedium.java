/**
 * Day 3 — Medium practice: identify complexity.
 *
 * Compile: javac src/day-03/PracticeMedium.java
 * Run    : java -cp src/day-03 PracticeMedium
 */
public class PracticeMedium {

    static int q6_halving(int n)       { int s = 0; while (n > 1) { n /= 2; s++; } return s; }
    static int q7_nLogN(int n)         { int s = 0; for (int i = 0; i < n; i++) for (int j = 1; j < n; j *= 2) s++; return s; }
    static int q8_recursiveDepth(int n){ if (n <= 1) return 0; return 1 + q8_recursiveDepth(n / 2); }
    static long q9_twoArrays(int[] a, int[] b) { long s = 0; for (int x : a) s += x; for (int x : b) s += x; return s; }
    static String q10_stringBuilder(int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) sb.append("x");   // amortised O(1) each
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("Q6 halving steps for n=1_000_000: " + q6_halving(1_000_000));
        System.out.println("Q7 nlogn ops for n=64           : " + q7_nLogN(64));
        System.out.println("Q8 recursion depth for n=1024   : " + q8_recursiveDepth(1024));
        System.out.println("Q9 two-array sum                : " + q9_twoArrays(new int[]{1,2,3}, new int[]{4,5,6}));
        System.out.println("Q10 stringbuilder length        : " + q10_stringBuilder(20).length());
    }
}
