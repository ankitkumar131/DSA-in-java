/**
 * Day 27 — Easy practice.
 *
 * Compile: javac src/day-27/PracticeEasy.java
 * Run    : java -cp src/day-27 PracticeEasy
 */
import java.util.*;

public class PracticeEasy {

    static long fib(int n) { return n < 2 ? n : fib(n - 1) + fib(n - 2); }
    static long fibMemo(int n, long[] memo) {
        if (n < 2) return n;
        if (memo[n] != 0) return memo[n];
        return memo[n] = fibMemo(n - 1, memo) + fibMemo(n - 2, memo);
    }

    static int climbStairs(int n) {
        if (n <= 2) return n;
        int a = 1, b = 2;
        for (int i = 3; i <= n; i++) { int c = a + b; a = b; b = c; }
        return b;
    }

    static int minCostClimbing(int[] cost) {
        int a = 0, b = 0;
        for (int i = 0; i < cost.length; i++) { int c = Math.min(a, b) + cost[i]; a = b; b = c; }
        return Math.min(a, b);
    }

    static int rob(int[] a) {
        int prev = 0, cur = 0;
        for (int x : a) { int t = Math.max(cur, prev + x); prev = cur; cur = t; }
        return cur;
    }

    static List<List<Integer>> pascal(int n) {
        List<List<Integer>> out = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j <= i; j++) {
                if (j == 0 || j == i) row.add(1);
                else row.add(out.get(i - 1).get(j - 1) + out.get(i - 1).get(j));
            }
            out.add(row);
        }
        return out;
    }

    public static void main(String[] args) {
        System.out.println("Q1 fib(10) memo = " + fibMemo(10, new long[11]));
        System.out.println("Q2 climbStairs  = " + climbStairs(10));
        System.out.println("Q3 minCost      = " + minCostClimbing(new int[]{10,15,20}));
        System.out.println("Q4 rob          = " + rob(new int[]{2,7,9,3,1}));
        System.out.println("Q5 pascal(5)    = " + pascal(5));
    }
}
