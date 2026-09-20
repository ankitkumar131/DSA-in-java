/**
 * Day 12 — Easy practice.
 *
 * Compile: javac src/day-12/PracticeEasy.java
 * Run    : java -cp src/day-12 PracticeEasy
 */
public class PracticeEasy {

    static int factorial(int n) { return n <= 1 ? 1 : n * factorial(n - 1); }
    static int power(int b, int e) { return e == 0 ? 1 : b * power(b, e - 1); }

    static int sumDigits(int n) { return n == 0 ? 0 : n % 10 + sumDigits(n / 10); }

    static String reverse(String s) {
        return s.length() <= 1 ? s : reverse(s.substring(1)) + s.charAt(0);
    }

    static void countdown(int n) {
        if (n == 0) { System.out.println("done"); return; }
        System.out.print(n + " ");
        countdown(n - 1);
    }

    public static void main(String[] args) {
        System.out.println("Q1 5!       = " + factorial(5));
        System.out.println("Q2 2^10     = " + power(2, 10));
        System.out.println("Q3 sumDigit = " + sumDigits(1234));
        System.out.println("Q4 reverse  = " + reverse("hello"));
        System.out.print("Q5 countdown= "); countdown(5);
    }
}
