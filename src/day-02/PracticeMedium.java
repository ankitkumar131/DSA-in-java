/**
 * Day 2 — Medium practice solutions.
 *
 * Compile: javac src/day-02/PracticeMedium.java
 * Run    : java -cp src/day-02 PracticeMedium
 */
public class PracticeMedium {

    // Q6: Generic Box
    static class Box<T> {
        private T value;
        public void set(T v) { value = v; }
        public T get() { return value; }
        public boolean isPresent() { return value != null; }
    }

    // Q7: MinMax with bounded generics
    static class MinMax {
        public static <T extends Comparable<T>> T findMin(T[] arr) {
            if (arr == null || arr.length == 0) return null;
            T min = arr[0];
            for (int i = 1; i < arr.length; i++)
                if (arr[i].compareTo(min) < 0) min = arr[i];
            return min;
        }
    }

    // Q8: Pair with equals/hashCode (re-implementation)
    static class Pair {
        int first, second;
        Pair(int f, int s) { first = f; second = s; }
        @Override public boolean equals(Object o) {
            if (!(o instanceof Pair)) return false;
            Pair p = (Pair) o;
            return first == p.first && second == p.second;
        }
        @Override public int hashCode() { return 31 * first + second; }
        @Override public String toString() {
            return "(" + first + "," + second + ")";
        }
    }

    // Q9: Animal polymorphism
    static abstract class Animal {
        abstract void speak();
    }
    static class Dog extends Animal {
        @Override void speak() { System.out.println("Woof"); }
    }
    static class Cat extends Animal {
        @Override void speak() { System.out.println("Meow"); }
    }

    // Q10: Shape interface
    interface Shape { double area(); }
    static class SCircle implements Shape {
        double r; SCircle(double r) { this.r = r; }
        public double area() { return Math.PI * r * r; }
    }
    static class SRectangle implements Shape {
        double w, h; SRectangle(double w, double h) { this.w = w; this.h = h; }
        public double area() { return w * h; }
    }
    static class STriangle implements Shape {
        double b, h; STriangle(double b, double h) { this.b = b; this.h = h; }
        public double area() { return 0.5 * b * h; }
    }

    public static void main(String[] args) {
        // Q6
        Box<String> bs = new Box<>();
        bs.set("hi");
        System.out.println("Q6: " + bs.get() + " present=" + bs.isPresent());

        // Q7
        Integer[] arr = {3, 1, 4, 1, 5, 9, 2, 6};
        System.out.println("Q7: min = " + MinMax.findMin(arr));

        // Q8
        Pair p1 = new Pair(1, 2), p2 = new Pair(1, 2);
        System.out.println("Q8: equals=" + p1.equals(p2)
                + " hash=" + p1.hashCode() + "/" + p2.hashCode());

        // Q9
        Animal[] pets = { new Dog(), new Cat(), new Dog() };
        System.out.print("Q9: ");
        for (Animal a : pets) a.speak();

        // Q10
        Shape[] shapes = { new SCircle(2), new SRectangle(3, 4), new STriangle(5, 6) };
        System.out.print("Q10: ");
        for (Shape s : shapes) System.out.print(s.area() + " ");
        System.out.println();
    }
}
