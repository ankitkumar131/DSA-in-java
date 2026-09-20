/**
 * Day 2 — Student class with constructor, methods, encapsulation.
 *
 * Compile: javac src/day-02/Student.java
 * Run    : java -cp src/day-02 Student
 */
public class Student {

    // === FIELDS ===
    // `private` — encapsulated, accessible only through getters/setters.
    private String name;
    private int age;
    private double gpa;

    // === STATIC FIELD ===
    // Shared across ALL Student instances.
    private static int instanceCount = 0;

    // === CONSTRUCTOR ===
    // No return type. Same name as the class.
    public Student(String name, int age, double gpa) {
        this.name = name;       // `this.name` = the field, `name` = the parameter
        this.age = age;
        this.gpa = gpa;
        instanceCount++;        // increment shared counter
    }

    // === METHODS ===
    public String getName() { return name; }
    public int getAge() { return age; }
    public double getGpa() { return gpa; }

    public void setGpa(double gpa) {
        if (gpa < 0 || gpa > 4.0) {
            throw new IllegalArgumentException("GPA out of range");
        }
        this.gpa = gpa;
    }

    public boolean honors() {
        return gpa >= 3.5;
    }

    public void introduce() {
        System.out.println("Hi, I'm " + name + ", age " + age + ", GPA " + gpa);
    }

    // === STATIC METHOD ===
    public static int getInstanceCount() {
        return instanceCount;
    }

    // === toString / equals / hashCode ===
    @Override
    public String toString() {
        return "Student{name='" + name + "', age=" + age + ", gpa=" + gpa + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student s = (Student) o;
        return age == s.age
            && Double.compare(gpa, s.gpa) == 0
            && name.equals(s.name);
    }

    @Override
    public int hashCode() {
        int h = name.hashCode();
        h = 31 * h + age;
        h = 31 * h + Double.hashCode(gpa);
        return h;
    }

    // === MAIN ===
    public static void main(String[] args) {
        Student s1 = new Student("Alice", 20, 3.9);
        Student s2 = new Student("Bob", 22, 3.2);
        s1.introduce();
        s2.introduce();
        System.out.println(s1.honors());     // true
        System.out.println(s2.honors());     // false
        System.out.println("count = " + Student.getInstanceCount()); // 2
    }
}
