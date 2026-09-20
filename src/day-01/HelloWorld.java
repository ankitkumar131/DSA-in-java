/**
 * Day 1 — Hello World
 * The smallest possible Java program. Each token explained below.
 */
public class HelloWorld {

    /**
     * The JVM entry point. When you run `java HelloWorld`, the JVM:
     *   1. Loads the HelloWorld.class file
     *   2. Looks for a method with the exact signature:
     *      public static void main(String[] args)
     *   3. Calls it.
     */
    public static void main(String[] args) {
        // System is a final class in java.lang.
        // `out` is a public static field of type PrintStream (the standard output stream).
        // println(String) prints the argument followed by a newline.
        System.out.println("Hello, DSA!");
    }
}
