import java.util.Scanner;

/**
 * Day 1 — Solutions to the 5 Easy practice problems.
 * Run interactively:  java -cp src day01.PracticeEasy
 */
public class PracticeEasy {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // Q1. Sum of two numbers
        System.out.print("Q1: enter two ints: ");
        int a1 = sc.nextInt(), b1 = sc.nextInt();
        System.out.println("  sum = " + (a1 + b1));

        // Q2. Even or odd
        System.out.print("Q2: enter an int: ");
        int n2 = sc.nextInt();
        System.out.println("  " + ((n2 % 2 == 0) ? "even" : "odd"));

        // Q3. Max of three
        System.out.print("Q3: enter three ints: ");
        int a3 = sc.nextInt(), b3 = sc.nextInt(), c3 = sc.nextInt();
        int m3 = Math.max(a3, Math.max(b3, c3));
        System.out.println("  max = " + m3);

        // Q4. FizzBuzz up to n
        System.out.print("Q4: enter n: ");
        int n4 = sc.nextInt();
        System.out.print("  ");
        for (int i = 1; i <= n4; i++) {
            if (i % 15 == 0)       System.out.print("FizzBuzz ");
            else if (i % 3 == 0)   System.out.print("Fizz ");
            else if (i % 5 == 0)   System.out.print("Buzz ");
            else                   System.out.print(i + " ");
        }
        System.out.println();

        // Q5. Reverse array
        System.out.print("Q5: enter n then n ints: ");
        int n5 = sc.nextInt();
        int[] arr5 = new int[n5];
        for (int i = 0; i < n5; i++) arr5[i] = sc.nextInt();
        System.out.print("  reversed: ");
        for (int i = n5 - 1; i >= 0; i--) System.out.print(arr5[i] + " ");
        System.out.println();
    }
}
