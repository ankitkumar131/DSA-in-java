/**
 * Day 2 — Easy practice solutions.
 *
 * Compile: javac src/day-02/PracticeEasy.java
 * Run    : java -cp src/day-02 PracticeEasy
 */
public class PracticeEasy {

    // Q1: Student with honors()
    static class Student {
        String name; double gpa;
        Student(String n, double g) { name = n; gpa = g; }
        boolean honors() { return gpa >= 3.5; }
    }

    // Q2: Rectangle
    static class Rectangle {
        double w, h;
        Rectangle(double w, double h) { this.w = w; this.h = h; }
        double area() { return w * h; }
        double perimeter() { return 2 * (w + h); }
    }

    // Q3: Counter with static field
    static class Counter {
        static int count = 0;
        Counter() { count++; }
        static int getCount() { return count; }
    }

    // Q4: BankAccount with encapsulation
    static class BankAccount {
        private double balance = 0;
        public void deposit(double amt) {
            if (amt <= 0) throw new IllegalArgumentException();
            balance += amt;
        }
        public void withdraw(double amt) {
            if (amt <= 0 || amt > balance) throw new IllegalArgumentException();
            balance -= amt;
        }
        public double getBalance() { return balance; }
    }

    // Q5: Point with distanceTo
    static class Point {
        double x, y;
        Point(double x, double y) { this.x = x; this.y = y; }
        double distanceTo(Point other) {
            double dx = x - other.x, dy = y - other.y;
            return Math.sqrt(dx * dx + dy * dy);
        }
    }

    public static void main(String[] args) {
        // Q1
        Student s = new Student("Alice", 3.9);
        System.out.println("Q1 honors: " + s.honors());

        // Q2
        Rectangle r = new Rectangle(3, 4);
        System.out.println("Q2 area=" + r.area() + " peri=" + r.perimeter());

        // Q3
        new Counter(); new Counter(); new Counter();
        System.out.println("Q3 count = " + Counter.getCount());

        // Q4
        BankAccount a = new BankAccount();
        a.deposit(100); a.withdraw(30);
        System.out.println("Q4 balance = " + a.getBalance());

        // Q5
        Point p1 = new Point(0, 0), p2 = new Point(3, 4);
        System.out.println("Q5 distance = " + p1.distanceTo(p2));
    }
}
