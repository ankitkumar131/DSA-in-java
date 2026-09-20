/**
 * Day 3 — Easy practice: identify complexity.
 *
 * Compile: javac src/day-03/PracticeEasy.java
 * Run    : java -cp src/day-03 PracticeEasy
 *
 * Each method labels what complexity it demonstrates.
 */
public class PracticeEasy {

    static int q1_singleLoop(int n)  { int s = 0; for (int i = 0; i < n; i++) s += i; return s; }
    static int q2_nestedLoops(int n){ int s = 0; for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) s++; return s; }
    static int q3_triple(int n)      { int s = 0; for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) for (int k = 0; k < n; k++) s++; return s; }
    static int q4_constants()        { int a = 1, b = 2, c = 3, d = 4, e = 5; return a + b + c + d + e; }
    static int q5_twoLoops(int n)    { int s = 0; for (int i = 0; i < n; i++) s++; for (int i = 0; i < n; i++) s++; return s; }

    public static void main(String[] args) {
        System.out.println("Q1 O(n)  -> " + q1_singleLoop(100));
        System.out.println("Q2 O(n^2)-> " + q2_nestedLoops(100));
        System.out.println("Q3 O(n^3)-> " + q3_triple(100));
        System.out.println("Q4 O(1)  -> " + q4_constants());
        System.out.println("Q5 O(n)  -> " + q5_twoLoops(100));
    }
}
