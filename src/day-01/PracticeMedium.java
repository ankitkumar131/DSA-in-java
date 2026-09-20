import java.util.Scanner;

/**
 * Day 1 — Solutions to the 5 Medium practice problems.
 */
public class PracticeMedium {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        // Q6. Count digits
        System.out.print("Q6: enter an int: ");
        long n6 = sc.nextLong();
        int digits = 0;
        long tmp = Math.abs(n6);
        if (tmp == 0) digits = 1;
        while (tmp > 0) { digits++; tmp /= 10; }
        System.out.println("  digits = " + digits);

        // Q7. Sum of first n naturals
        System.out.print("Q7: enter n: ");
        long n7 = sc.nextLong();
        long sum7 = n7 * (n7 + 1) / 2;   // closed-form
        System.out.println("  sum = " + sum7);

        // Q8. Power of two check
        System.out.print("Q8: enter n (>0): ");
        long n8 = sc.nextLong();
        boolean pow2 = n8 > 0 && (n8 & (n8 - 1)) == 0;
        System.out.println("  power of 2? " + pow2);

        // Q9. Swap without temp
        System.out.print("Q9: enter two ints: ");
        int a9 = sc.nextInt(), b9 = sc.nextInt();
        System.out.println("  before: a=" + a9 + " b=" + b9);
        a9 = a9 + b9;   // a9 = sum
        b9 = a9 - b9;   // b9 = original a9
        a9 = a9 - b9;   // a9 = original b9
        System.out.println("  after : a=" + a9 + " b=" + b9);

        // Q10. Second largest distinct
        System.out.print("Q10: enter n then n ints: ");
        int n10 = sc.nextInt();
        long first = Long.MIN_VALUE, second = Long.MIN_VALUE;
        for (int i = 0; i < n10; i++) {
            long x = sc.nextLong();
            if (x > first) { second = first; first = x; }
            else if (x < first && x > second) { second = x; }
        }
        System.out.println("  second largest = " + second);
    }
}
