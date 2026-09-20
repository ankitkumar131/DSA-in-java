/**
 * Day 2 — Comparable interface + sorting.
 *
 * Compile: javac src/day-02/InterfaceDemo.java
 * Run    : java -cp src/day-02 InterfaceDemo
 */
import java.util.Arrays;

class Person implements Comparable<Person> {
    String name;
    int age;
    Person(String name, int age) { this.name = name; this.age = age; }

    @Override
    public int compareTo(Person other) {
        // negative if this < other, zero if equal, positive if this > other
        return Integer.compare(this.age, other.age);
    }

    @Override
    public String toString() {
        return name + "(" + age + ")";
    }
}

public class InterfaceDemo {
    public static void main(String[] args) {
        Person[] people = {
            new Person("Alice", 30),
            new Person("Bob", 22),
            new Person("Carol", 27)
        };

        Arrays.sort(people);          // uses compareTo()
        System.out.println(Arrays.toString(people));

        // also works with custom comparator
        Arrays.sort(people, (a, b) -> a.name.compareTo(b.name));
        System.out.println(Arrays.toString(people));
    }
}
