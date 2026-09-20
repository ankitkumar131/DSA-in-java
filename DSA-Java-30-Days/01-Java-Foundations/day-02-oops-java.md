# Day 2 — OOP in Java

## 🎯 Learning Objectives

By the end of today, you should be able to:

- Define classes, objects, constructors, fields, methods
- Use `this`, `static`, `final`
- Apply encapsulation with getters/setters
- Explain inheritance, polymorphism, abstraction
- Use abstract classes and interfaces
- Understand access modifiers (`public`, `private`, `protected`, default)
- Build small DSA-style classes like `Node`, `Student`, `Pair`

---

# 1. Introduction

Tomorrow you'll start measuring complexity. The day after, you'll start manipulating arrays. But starting on **Day 13**, every data structure we build (Linked List, Tree, Graph, Heap) is a Java **class**. Without OOP, you cannot implement these.

Today is the foundation that makes everything later possible.

---

# 2. Why Do We Need OOP in DSA?

Imagine trying to represent a singly linked list **without** a class. You'd need parallel arrays — one for `data`, one for `next` indices — and every operation would juggle multiple arrays.

With a class:

```java
class Node {
    int data;
    Node next;
}
```

A node is a single self-contained unit. Operations are clean. This is why OOP is non-negotiable for DSA.

---

# 3. Core Concepts

## 3.1 Class

A class is a **blueprint**. It describes what an object of that type looks like and what it can do.

```java
class Student {
    String name;
    int age;
    double gpa;

    void introduce() {
        System.out.println("Hi, I'm " + name + ", age " + age + ", GPA " + gpa);
    }
}
```

- `class Student` — keyword `class` declares a new type.
- `String name;`, `int age;`, `double gpa;` — **fields** (a.k.a. instance variables / attributes). Each `Student` object has its own copy.
- `void introduce()` — **method**. Describes a behaviour.

## 3.2 Object

An object is an **instance** of a class. Created with `new`.

```java
Student s = new Student();
s.name = "Alice";
s.age = 20;
s.gpa = 3.9;
s.introduce();
```

- `Student s` — declares a *reference* variable `s` that can hold a `Student`.
- `new Student()` — allocates a new `Student` object on the **heap**, initialises its fields to defaults, and returns its address.
- `s.name = "Alice"` — accesses the field via the dot operator.

### Reference vs value

`Student s = new Student();` — `s` holds a *reference* (memory address), not the object itself.

```java
Student a = new Student();
Student b = a;        // both point to the SAME object
b.name = "Bob";
System.out.println(a.name);   // Bob — same object!
```

This is critical for DSA: when you pass a `Node` to a method, the method can modify the object the reference points to.

## 3.3 Constructor

A constructor is a special method that runs when an object is created.

```java
class Student {
    String name;
    int age;

    Student(String name, int age) {   // constructor
        this.name = name;
        this.age = age;
    }
}

Student s = new Student("Alice", 20);
```

Rules:
- Same name as the class.
- No return type (not even `void`).
- Called automatically by `new`.

If you don't write a constructor, Java provides a **default no-arg constructor** that zero-initialises all fields.

## 3.4 `this`

`this` refers to **the current object** — the one the method is being called on.

```java
Student(String name, int age) {
    this.name = name;   // this.name = field, name = parameter
    this.age = age;
}
```

Without `this`, `name = name` would assign the parameter to itself, leaving the field unchanged.

## 3.5 `static`

`static` means the field/method belongs to the **class**, not to any instance.

```java
class Counter {
    static int count = 0;            // shared across all Counter objects

    Counter() { count++; }
}

new Counter();
new Counter();
System.out.println(Counter.count);   // 2
```

`main` is `static` because the JVM calls it before any objects exist.

For DSA:
- `static` fields: like a global counter.
- `static` methods: utility functions (`Math.max`, `Arrays.sort`).
- `static final` constants: like `Integer.MAX_VALUE`.

## 3.6 `final`

`final` means *cannot be changed*.

- `final` field → constant after initialisation.
- `final` variable → constant (a "local constant").
- `final` method → cannot be overridden.
- `final class` → cannot be extended.

```java
final double PI = 3.14159;
```

For DSA: use `final` for array-size hints (`final int N = 100000;`).

## 3.7 Encapsulation

Hide fields behind `private`, expose via `public` getters/setters.

```java
class Account {
    private double balance;          // hidden

    public double getBalance() { return balance; }   // controlled access

    public void deposit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException();
        balance += amount;
    }
}
```

Benefits:
- Validation in setters.
- Invariants protected.
- Internal implementation can change without breaking callers.

For DSA, encapsulation is *less* common (we expose fields directly in `Node`), but for complex classes (`TreeMap`-like structures) it's essential.

## 3.8 Access Modifiers

| Modifier       | Class | Package | Subclass | World |
|----------------|:-----:|:-------:|:--------:|:-----:|
| `private`      | ✓     |         |          |       |
| (default)      | ✓     | ✓       |          |       |
| `protected`    | ✓     | ✓       | ✓        |       |
| `public`       | ✓     | ✓       | ✓        | ✓     |

- `private` — only the same class.
- default (no modifier) — same package.
- `protected` — same package + subclasses.
- `public` — everyone.

## 3.9 Inheritance

One class extends another to reuse code.

```java
class Animal {
    String name;
    void speak() { System.out.println("..."); }
}

class Dog extends Animal {
    @Override
    void speak() { System.out.println("Woof!"); }
}

Dog d = new Dog();
d.name = "Rex";      // inherited field
d.speak();           // overridden method → "Woof!"
```

- `extends` — declares inheritance.
- `Dog` inherits all accessible fields/methods of `Animal`.
- `@Override` — annotation (optional but recommended) telling the compiler you intend to override.

Java is **single-inheritance**: a class can extend only one parent.

## 3.10 Polymorphism

A superclass reference can hold a subclass object.

```java
Animal a = new Dog();   // upcasting — always safe
a.speak();              // calls Dog's version (dynamic dispatch)
```

The actual method called is decided at **runtime** based on the object's real type — this is **dynamic dispatch**.

For DSA: a `List<Integer>` reference can point to `ArrayList` or `LinkedList`. The right method runs based on the actual object.

## 3.11 `super`

`super` refers to the parent class.

```java
class Dog extends Animal {
    Dog(String name) {
        super(name);   // calls Animal(name) constructor
    }
}
```

`super(...)` must be the first statement in a constructor (if used).

## 3.12 Abstract Classes

A class that can't be instantiated, only extended. May have abstract methods (no body).

```java
abstract class Shape {
    abstract double area();        // no body

    void print() {                 // concrete method allowed
        System.out.println("area = " + area());
    }
}

class Circle extends Shape {
    double r;
    Circle(double r) { this.r = r; }
    @Override double area() { return Math.PI * r * r; }
}
```

- `abstract class` — keyword; can't be `new`'d.
- `abstract double area()` — method without body; subclass must implement.
- Subclass must implement *all* abstract methods, or itself be abstract.

## 3.13 Interfaces

A pure contract — only method signatures (mostly). A class `implements` an interface.

```java
interface Comparable<T> {
    int compareTo(T other);   // returns negative, zero, or positive
}

class Student implements Comparable<Student> {
    int gpa;
    public int compareTo(Student other) {
        return Integer.compare(this.gpa, other.gpa);
    }
}
```

Java 8+ allows:
- `default` methods — concrete methods in interfaces.
- `static` methods in interfaces.

A class can implement **multiple** interfaces (unlike extending multiple classes).

```java
class MyList implements Iterable<Integer>, Comparable<MyList> { ... }
```

## 3.14 Composition

When a class *contains* another class as a field — "has-a" relationship.

```java
class Car {
    private Engine engine;   // Car has an Engine
}
```

Composition over inheritance: prefer it when there's no clear "is-a" relationship.

For DSA: a `LinkedList` is *composed of* `Node`s. A `Tree` is composed of `TreeNode`s.

## 3.15 Method Overloading vs Overriding

| | Overloading | Overriding |
|---|---|---|
| Where | Same class | Subclass |
| Signature | Different parameters | Same signature |
| Return type | Can be different | Same or covariant |
| `@Override` | Not used | Recommended |
| Polymorphism | Compile-time | Run-time |

```java
// overloading
int add(int a, int b) { return a + b; }
double add(double a, double b) { return a + b; }

// overriding
class Animal { void speak() { ... } }
class Dog extends Animal { @Override void speak() { ... } }
```

## 3.16 `toString`, `equals`, `hashCode`

Every Java object inherits three important methods from `Object`:

- `toString()` — should return a human-readable representation. **Override for debugging.**
- `equals(Object o)` — value equality. **Override for any "value" class.**
- `hashCode()` — must be consistent with `equals`. **Always override together with `equals`.**

```java
class Pair {
    int first, second;
    Pair(int first, int second) { this.first = first; this.second = second; }

    @Override public String toString() { return "(" + first + "," + second + ")"; }

    @Override public boolean equals(Object o) {
        if (!(o instanceof Pair)) return false;
        Pair p = (Pair) o;
        return this.first == p.first && this.second == p.second;
    }

    @Override public int hashCode() { return 31 * first + second; }
}
```

`hashCode` matters because `HashMap` and `HashSet` use it to bucket entries.

---

# 4. Real-World Analogy

Think of a class like an **architect's blueprint** for a house:

- The **blueprint** = class.
- Each **house built from it** = an object (instance).
- The **address of the house** = reference (you hold the address, not the house).
- A **subclass** = a blueprint variation (e.g. "bungalow extends house").
- An **interface** = a contract ("any building must have an entrance").

---

# 5. Syntax — Quick Reference

```java
class MyClass {                       // declaration
    private int field;                // private field
    public static final int K = 5;    // public constant

    public MyClass(int field) {       // constructor
        this.field = field;
    }

    public int getField() {           // getter
        return field;
    }

    public static int square(int x) { // static method
        return x * x;
    }
}

class ChildClass extends MyClass {     // inheritance
    ChildClass() { super(0); }
}
```

---

# 6. Example — Node Class

```java
class Node {
    int data;
    Node next;

    Node(int data) {
        this.data = data;
        this.next = null;
    }
}
```

Walkthrough:

- `class Node` — defines a new type `Node`.
- `int data` — the value this node holds.
- `Node next` — reference to the next node, or `null` if this is the tail.
- `Node(int data)` — constructor; initialises `data`, leaves `next = null` explicitly.

Usage:

```java
Node a = new Node(1);
Node b = new Node(2);
Node c = new Node(3);
a.next = b;
b.next = c;
// list: 1 -> 2 -> 3
```

---

# 7. Step-by-Step — Memory Layout

```
Stack              Heap
+-----+          +-----------+
|  a  |--------->| data: 1   |
+-----+          | next: ----|----+
|  b  |---+      +-----------+    |
+-----+   |                       v
|  c  |---+               +-----------+
+-----+   +-------------->| data: 2   |
                          | next: ----|----+
                          +-----------+    |
                                            v
                                    +-----------+
                                    | data: 3   |
                                    | next: null|
                                    +-----------+
```

Three local variables (`a`, `b`, `c`) on the stack. Each holds a *reference* to a Node object on the heap. The `next` references form the chain.

---

# 8. Java Implementation — Day 2 Examples

All examples are in `src/day-02/`. Run each with:

```bash
javac src/day-02/<File>.java
java -cp src/day-02 <File>
```

## 8.1 `Student.java`

A class with constructor, getters, encapsulation.

## 8.2 `Node.java`

The DSA Node we'll use from Day 13.

## 8.3 `Pair.java`

Generic pair — used in many algorithms.

## 8.4 `ShapeDemo.java`

Inheritance + polymorphism with `Shape` / `Circle` / `Rectangle`.

## 8.5 `InterfaceDemo.java`

`Comparable` interface, used by sorting algorithms.

## 8.6 `PracticeEasy.java`, `PracticeMedium.java`, `PracticeHard.java`

Solutions to today's 15 problems.

---

# 9. Code Walkthrough — `ShapeDemo.java`

```java
abstract class Shape {
    abstract double area();

    void describe() {
        System.out.println("I'm a " + getClass().getSimpleName()
                + " with area " + area());
    }
}

class Circle extends Shape {
    double r;
    Circle(double r) { this.r = r; }

    @Override
    double area() { return Math.PI * r * r; }
}

class Rectangle extends Shape {
    double w, h;
    Rectangle(double w, double h) { this.w = w; this.h = h; }

    @Override
    double area() { return w * h; }
}

public class ShapeDemo {
    public static void main(String[] args) {
        Shape[] shapes = { new Circle(2.0), new Rectangle(3, 4) };
        for (Shape s : shapes) {           // polymorphic call
            s.describe();
        }
    }
}
```

Line by line:

- `abstract class Shape` — can't be instantiated.
- `abstract double area();` — no body; subclasses must implement.
- `void describe()` — concrete method. Calls `area()` polymorphically.
- `getClass().getSimpleName()` — returns the runtime class's name.
- `class Circle extends Shape` — provides `area()`.
- `@Override` — annotation; compiler checks that you really are overriding.
- `Shape[] shapes = { ... }` — array of `Shape` references, each pointing to a subclass object.
- `for (Shape s : shapes) s.describe();` — at runtime, the right `area()` runs based on `s`'s actual type.

Output:

```
I'm a Circle with area 12.566370614359172
I'm a Rectangle with area 12.0
```

---

# 10. Dry Run

For `Shape[] shapes = { new Circle(2.0), new Rectangle(3, 4) };`:

| Iteration | `s` actual type | `s.area()` returns | `getClass().getSimpleName()` |
|-----------|-----------------|--------------------|------------------------------|
| 1         | Circle          | π × 4 ≈ 12.566     | "Circle"                     |
| 2         | Rectangle       | 3 × 4 = 12         | "Rectangle"                  |

---

# 11. Time & Space Complexity

OOP operations themselves are O(1) — field access, method call, `new`. The complexity shows up in the *algorithms* you write using these classes (Day 5+).

For today's purposes:

| Operation | Complexity |
|---|---|
| `new ClassName()` | O(1) (amortised — depends on heap) |
| field access (`obj.field`) | O(1) |
| method call (`obj.method()`) | O(1) + body complexity |
| `instanceof` check | O(1) |
| `getClass()` | O(1) |

---

# 12. Space Complexity

Each object uses O(1) extra memory beyond its fields (a small object header, ~ 16 bytes on 64-bit JVMs). The aggregate is O(n) for n objects.

---

# 13. Common Mistakes

1. **Forgetting `new`**: `Student s;` declares a reference but it's `null`. Calling `s.introduce()` throws `NullPointerException`.
2. **Confusing `==` with `.equals()`** for objects. `==` compares references; `.equals()` compares values (when overridden).
3. **Forgetting `super(...)`** in subclass constructor → compile error if parent has no default constructor.
4. **Accessing private fields from another class** → compile error. Use getters/setters.
5. **Forgetting `@Override`** when you mean to override → if you mistype the signature, you accidentally overload, no error.
6. **Trying to instantiate an abstract class** → compile error.
7. **Constructor with return type** → it's no longer a constructor, it's just a regular method with the same name as the class (legal but very confusing).
8. **Cyclic references** — `A` has a `B`, `B` has an `A`. Legal but can cause memory issues if not careful.
9. **Not implementing all abstract methods** in subclass → compile error (unless subclass is also abstract).
10. **Multiple inheritance of classes** — Java doesn't allow it. Use interfaces.

---

# 14. Interview Questions

### Q1. What's the difference between a class and an object?
A class is a blueprint; an object is an instance of that blueprint. A class exists once in the `.class` file; objects are created at runtime, one per `new` call.

### Q2. Why do we need constructors?
To ensure objects start in a valid state. A constructor guarantees fields are initialised before any method can use them.

### Q3. Difference between `==` and `.equals()`?
`==` compares memory addresses (or primitive values). `.equals()` compares logical content (when overridden).

### Q4. Can a class extend multiple classes?
No. Java supports single inheritance for classes. Use interfaces for multiple type inheritance.

### Q5. What's the difference between an abstract class and an interface?
Abstract class: can have state (fields) and partial implementation. Interface: pure contract (mostly); can't have instance state (until Java 8 default methods).

### Q6. Why override `equals` and `hashCode` together?
`HashMap` and `HashSet` use `hashCode()` to find the bucket, then `equals()` to find the right entry. If they're inconsistent, lookups break.

### Q7. What's polymorphism?
Same interface (method call), different behaviour based on the actual object type. Enables flexible code.

### Q8. What is `this`?
A reference to the current object — used to disambiguate fields from parameters with the same name, or to pass the current object to another method.

### Q9. What is `super`?
A reference to the parent class. Used to call the parent's constructor (`super(...)`) or methods (`super.method()`).

### Q10. Why is `main` `static`?
So the JVM can call `MyClass.main(...)` without first creating a `MyClass` instance.

---

# 15. Practice Problems

## 🟢 Easy

### 1. `Student` Class with Constructor

**Difficulty:** Easy
**Problem:** Create a `Student` class with fields `name` (String) and `gpa` (double). Provide a constructor and a method `honors()` returning `true` if `gpa >= 3.5`.
**Expected Concept:** Constructor, methods.
**Target Complexity:** O(1)
**Interview Relevance:** Low

### 2. `Rectangle` Class

**Difficulty:** Easy
**Problem:** `Rectangle(double w, double h)` with `area()` and `perimeter()` methods.
**Expected Concept:** Methods.
**Target Complexity:** O(1)
**Interview Relevance:** Low

### 3. `Counter` Class with Static Field

**Difficulty:** Easy
**Problem:** `Counter` increments a static field each time a new `Counter` is created. Add `getCount()`.
**Expected Concept:** Static field.
**Target Complexity:** O(1)
**Interview Relevance:** Low

### 4. `BankAccount` with Encapsulation

**Difficulty:** Easy
**Problem:** Private `balance`. Methods `deposit`, `withdraw` (reject negative or overdraw), `getBalance`.
**Expected Concept:** Encapsulation.
**Target Complexity:** O(1)
**Interview Relevance:** Medium

### 5. `Point` Distance

**Difficulty:** Easy
**Problem:** `Point(x, y)` with `distanceTo(Point other)`.
**Expected Concept:** Methods, parameters.
**Target Complexity:** O(1)
**Interview Relevance:** Low

## 🟡 Medium

### 6. `Box<T>` Generic

**Difficulty:** Medium
**Problem:** Generic `Box<T>` storing a single value with `get`, `set`, `isPresent` methods.
**Expected Concept:** Generics.
**Target Complexity:** O(1)
**Interview Relevance:** High

### 7. `MinMax<T extends Comparable<T>>`

**Difficulty:** Medium
**Problem:** A class with a static `findMin(T[] arr)` method.
**Expected Concept:** Bounded generics, static methods, `Comparable`.
**Target Complexity:** O(n)
**Interview Relevance:** High

### 8. Override `equals`/`hashCode`

**Difficulty:** Medium
**Problem:** For a `Pair` class, override `equals` and `hashCode` correctly.
**Expected Concept:** `Object` methods.
**Target Complexity:** O(1)
**Interview Relevance:** High

### 9. `Animal`/`Dog`/`Cat` Polymorphism

**Difficulty:** Medium
**Problem:** Abstract `Animal` with `speak()`. `Dog` says "Woof", `Cat` says "Meow". Array of `Animal` polymorphically calls speak.
**Expected Concept:** Inheritance, polymorphism.
**Target Complexity:** O(n)
**Interview Relevance:** Low

### 10. `Shape` Interface

**Difficulty:** Medium
**Problem:** `interface Shape { double area(); }`. `Circle`, `Rectangle`, `Triangle` implement.
**Expected Concept:** Interface.
**Target Complexity:** O(n) for collection.
**Interview Relevance:** Medium

## 🔴 Hard

### 11. `LinkedList` Node Class

**Difficulty:** Hard
**Problem:** Implement a `Node` class plus `append`, `printList`, `length` static methods.
**Expected Concept:** OOP, reference manipulation.
**Target Complexity:** O(n) for traversal.
**Interview Relevance:** High

### 12. `MinStack` Class

**Difficulty:** Hard
**Problem:** A `MinStack` with `push`, `pop`, `peek`, `getMin` all in O(1).
**Expected Concept:** Composition, design.
**Target Complexity:** O(1) per op.
**Interview Relevance:** High

### 13. `LRUCache` Skeleton

**Difficulty:** Hard
**Problem:** Design a class skeleton with private `HashMap` + doubly linked list fields, public `get`/`put` methods (skeleton only — full impl in Day 14).
**Expected Concept:** Composition, design patterns.
**Target Complexity:** O(1) per op (target).
**Interview Relevance:** Very high.

### 14. `Fraction` Class

**Difficulty:** Hard
**Problem:** Immutable `Fraction(num, den)` with `add`, `multiply`, `equals`, `hashCode`. Reduce to lowest terms.
**Expected Concept:** Immutability, `final`, methods.
**Target Complexity:** O(log n) for reduction (GCD).
**Interview Relevance:** Medium

### 15. `TrieNode` Class

**Difficulty:** Hard
**Problem:** `TrieNode` with `children` (array of 26) and `isEndOfWord` boolean. Build a small `Trie` class around it (skeleton).
**Expected Concept:** OOP, composition.
**Target Complexity:** O(L) per insert/search.
**Interview Relevance:** Very high.

---

# 16. Practice Hints

## Easy
1. `this.name = name`; `honors()` returns `gpa >= 3.5`.
2. `area() = w*h`, `perimeter() = 2*(w+h)`.
3. `static int count = 0;` constructor does `count++`.
4. Validate in `deposit`/`withdraw`.
5. `Math.sqrt((x-other.x)² + (y-other.y)²)`.
## Medium
6. `class Box<T> { T value; ... }`.
7. `public static <T extends Comparable<T>> T findMin(T[] a)`.
8. Override both. `hashCode = 31 * first + second` (any consistent formula).
9. Override `speak()` in each subclass.
10. `implements Shape` requires `area()`.
## Hard
11. Traverse via `node.next`.
12. Use two stacks or one stack of pairs.
13. HashMap + DLL; we'll fill this in on Day 14.
14. Reduce in constructor: `num/=g; den/=g`.
15. `TrieNode[26]` children; root for prefix search.

---

# 17. Revision Checklist

- [ ] Can define a class with fields and methods
- [ ] Can write a constructor with `this`
- [ ] Know what `static` and `final` mean
- [ ] Can use inheritance with `extends`
- [ ] Can override methods with `@Override`
- [ ] Know the difference between abstract class and interface
- [ ] Can override `equals`/`hashCode`/`toString`
- [ ] Can build a `Node` class from scratch
- [ ] Solved all 5 Easy problems
- [ ] Solved all 5 Medium problems
- [ ] Attempted all 5 Hard problems

---

# 18. Key Takeaways

- A **class** is a blueprint; an **object** is an instance.
- A **constructor** initialises state; `this` disambiguates fields.
- **`static`** belongs to the class; non-static belongs to instances.
- **Encapsulation** = `private` fields + `public` methods.
- **Inheritance** = `extends`; Java allows only single class inheritance.
- **Polymorphism** = same call, different behaviour at runtime.
- **Abstract class** = partial implementation; **interface** = contract.
- Always override `equals` and `hashCode` together.

Tomorrow: **Time and Space Complexity** — the language you'll use to evaluate every algorithm.
