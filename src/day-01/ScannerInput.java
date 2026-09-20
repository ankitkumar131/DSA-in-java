import java.util.Scanner;

/**
 * Day 1 — Reading input with Scanner.
 * Run interactively:  java -cp src day01.ScannerInput
 * Then type two integers on one line.
 */
public class ScannerInput {

    public static void main(String[] args) {
        // System.in is a byte stream (raw stdin).
        // Scanner wraps it and provides token-based parsing.
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter your name: ");
        String name = sc.nextLine();           // reads full line (incl. spaces)

        System.out.print("Enter your age: ");
        int age = sc.nextInt();                // reads one int token

        System.out.print("Enter your GPA: ");
        double gpa = sc.nextDouble();

        // nextLine() trap: if you call nextLine() right after nextInt(),
        // it will read the leftover newline and return "".
        // Workaround: call sc.nextLine() once to consume it.
        sc.nextLine();
        System.out.print("Enter your city: ");
        String city = sc.nextLine();

        System.out.println("Hi " + name + ", age " + age
                + ", GPA " + gpa + ", from " + city);

        sc.close();   // releases System.in
    }
}
