/** Day 2 tiny project: polymorphic shape area calculator. */
import java.util.*;

abstract class Shape {
    String name;
    abstract double area();
    @Override public String toString() { return name + " area=" + area(); }
}
class Circle extends Shape {
    double r;
    Circle(double r) { this.r = r; name = "Circle"; }
    @Override double area() { return Math.PI * r * r; }
}
class Rectangle extends Shape {
    double w, h;
    Rectangle(double w, double h) { this.w = w; this.h = h; name = "Rectangle"; }
    @Override double area() { return w * h; }
}

public class Shapes {
    public static void main(String[] args) {
        List<Shape> shapes = List.of(new Circle(2), new Rectangle(3, 4));
        for (Shape s : shapes) System.out.println(s);
    }
}
