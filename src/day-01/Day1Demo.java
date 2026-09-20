/**
 * Day 1 — Comprehensive demo of every Java primitive concept you'll need for DSA.
 * Compile: javac src/day-01/Day1Demo.java
 * Run    : java -cp src day01.Day1Demo
 */
public class Day1Demo {

    // Class-level constant. `static final` means it's a constant belonging to the class.
    // `PI` is in upper case by Java naming convention for constants.
    static final double PI = 3.14159;

    public static void main(String[] args) {

        // ============================================================
        // 1. PRIMITIVES & VARIABLES
        // ============================================================
        int    age   = 25;        // 32-bit signed integer
        long   pop   = 8_000_000_000L; // 64-bit; 'L' suffix forces long literal
        double gpa   = 3.85;      // 64-bit floating point
        char   grade = 'A';       // 16-bit Unicode character
        boolean pass = true;      // true or false

        // Underscores in numeric literals (Java 7+) are visual separators only.
        int million = 1_000_000;   // == 1000000

        System.out.println("age=" + age + " pop=" + pop + " gpa=" + gpa
                + " grade=" + grade + " pass=" + pass + " million=" + million);

        // ============================================================
        // 2. OPERATORS
        // ============================================================
        int a = 10, b = 3;
        System.out.println("a+b=" + (a + b));   // 13
        System.out.println("a-b=" + (a - b));   // 7
        System.out.println("a*b=" + (a * b));   // 30
        System.out.println("a/b=" + (a / b));   // 3  -- integer division!
        System.out.println("a%b=" + (a % b));   // 1  -- modulo

        // Comparison and logical
        System.out.println("a>b: " + (a > b));           // true
        System.out.println("a>5 && b<5: " + (a > 5 && b < 5)); // true

        // Increment / decrement
        int i = 0;
        i++;   // i becomes 1
        ++i;   // i becomes 2
        i--;   // i becomes 1
        System.out.println("i after ++/--: " + i);

        // ============================================================
        // 3. CONTROL FLOW
        // ============================================================
        int n = 10;
        if (n % 2 == 0) {
            System.out.println(n + " is even");
        } else {
            System.out.println(n + " is odd");
        }

        // Ternary
        String parity = (n % 2 == 0) ? "even" : "odd";
        System.out.println("parity: " + parity);

        // switch
        int day = 3;
        switch (day) {
            case 1: System.out.println("Mon"); break;
            case 2: System.out.println("Tue"); break;
            case 3: System.out.println("Wed"); break;
            default: System.out.println("?");
        }

        // ============================================================
        // 4. LOOPS
        // ============================================================
        // for loop
        System.out.print("for: ");
        for (int k = 0; k < 5; k++) {
            System.out.print(k + " ");
        }
        System.out.println();

        // while
        System.out.print("while: ");
        int k = 0;
        while (k < 5) {
            System.out.print(k + " ");
            k++;
        }
        System.out.println();

        // do-while
        System.out.print("do-while: ");
        int j = 0;
        do {
            System.out.print(j + " ");
            j++;
        } while (j < 3);
        System.out.println();

        // break & continue
        System.out.print("break/continue: ");
        for (int x = 0; x < 10; x++) {
            if (x == 5) break;          // exits loop when x reaches 5
            if (x % 2 == 0) continue;   // skips evens
            System.out.print(x + " ");
        }
        System.out.println();

        // ============================================================
        // 5. ARRAYS
        // ============================================================
        int[] arr = {3, 1, 4, 1, 5, 9, 2, 6};   // initialised array literal
        int len = arr.length;                   // length is a FIELD, not method
        System.out.println("arr length = " + len);

        // find max
        int max = arr[0];                       // initialise to first element (NOT 0)
        for (int idx = 1; idx < len; idx++) {
            if (arr[idx] > max) {
                max = arr[idx];
            }
        }
        System.out.println("max = " + max);

        // find sum
        long sum = 0;
        for (int idx = 0; idx < len; idx++) {
            sum += arr[idx];
        }
        System.out.println("sum = " + sum);

        // for-each (read-only copy)
        System.out.print("for-each: ");
        for (int v : arr) {
            System.out.print(v + " ");
        }
        System.out.println();

        // ============================================================
        // 6. METHODS
        // ============================================================
        int result = add(3, 4);
        System.out.println("add(3,4) = " + result);

        System.out.println("add(double) = " + add(3.5, 4.5)); // overloaded

        // ============================================================
        // 7. COMMAND-LINE ARGS
        // ============================================================
        System.out.println("args length: " + args.length);
        for (String arg : args) {
            System.out.println("arg: " + arg);
        }
    }

    /**
     * Adds two integers.
     * @param a first addend
     * @param b second addend
     * @return a + b
     */
    public static int add(int a, int b) {
        return a + b;
    }

    /** Overloaded: adds two doubles. */
    public static double add(double a, double b) {
        return a + b;
    }
}
