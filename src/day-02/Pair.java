/**
 * Day 2 — A simple immutable Pair class with equals/hashCode.
 *
 * Compile: javac src/day-02/Pair.java
 * Run    : java -cp src/day-02 Pair
 */
public class Pair {
    public final int first;   // `final` → immutable
    public final int second;

    public Pair(int first, int second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pair)) return false;
        Pair p = (Pair) o;
        return first == p.first && second == p.second;
    }

    @Override
    public int hashCode() {
        return 31 * first + second;
    }

    public static void main(String[] args) {
        Pair p = new Pair(3, 7);
        System.out.println(p);
        Pair q = new Pair(3, 7);
        System.out.println("equals? " + p.equals(q));   // true
        System.out.println("hash    " + p.hashCode() + " / " + q.hashCode());
    }
}
