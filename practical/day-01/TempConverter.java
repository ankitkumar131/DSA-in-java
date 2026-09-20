import java.util.Scanner;

/** Day 1 tiny project: convert temperatures between Celsius and Fahrenheit. */
public class TempConverter {
    static double cToF(double c) { return c * 9.0 / 5.0 + 32; }
    static double fToC(double f) { return (f - 32) * 5.0 / 9.0; }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Direction (C->F or F->C): ");
        String dir = sc.next().toUpperCase();
        System.out.print("Value: ");
        double v = sc.nextDouble();
        double result = dir.equals("C->F") ? cToF(v) : fToC(v);
        System.out.println("Result: " + result);
    }
}
