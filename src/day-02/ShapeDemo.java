/**
 * Day 2 — Inheritance + polymorphism with Shape / Circle / Rectangle.
 *
 * Compile: javac src/day-02/ShapeDemo.java
 * Run    : java -cp src/day-02 ShapeDemo
 */
abstract class Shape {
    abstract double area();

    void describe() {
        System.out.println(getClass().getSimpleName()
                + " area = " + area());
    }
}

class Circle extends Shape {
    double r;
    Circle(double r) { this.r = r; }
    @Override double area() { return Math.PI * r * r; }
}

class Rectangle extends Shape {
    double w, h;
    Rectangle(double w, double h) { this.w = w; this.h = h; }
    @Override double area() { return w * h; }
}

public class ShapeDemo {
    public static void main(String[] args) {
        Shape[] shapes = {
            new Circle(2.0),
            new Rectangle(3, 4),
            new Circle(1.5)
        };
        for (Shape s : shapes) {
            s.describe();    // dynamic dispatch → correct area() runs
        }
    }
}
