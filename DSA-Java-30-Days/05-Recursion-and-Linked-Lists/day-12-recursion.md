# Day 12 — Recursion

## 🎯 Learning Objectives

By the end of today, you should be able to:

- Identify the base case and recursive case in any recursion
- Trace the call stack manually
- Compute recursion depth and time complexity
- Implement factorial, Fibonacci (memoised), reverse, sum
- Use tail recursion
- Convert recursion → iteration (and vice versa)

---

# 1. Introduction

Recursion is the most important technique for trees, graphs, and DP. If your recursion is shaky, the next 18 days will be hard. Today we make it solid.

---

# 2. Why Do We Need This?

Many data structures are **inherently recursive**:

- A tree is a node pointing to other trees.
- A linked list is a node pointing to a smaller list.
- A Fibonacci number is defined in terms of smaller Fibonaccis.

Trying to write iterative code for these often produces tangled, buggy code. Recursion expresses the structure directly.

---

# 3. Core Concept

A recursive function:

1. Calls itself with a smaller input.
2. Has a **base case** that stops the recursion.

### Anatomy

```java
int factorial(int n) {
    if (n <= 1) return 1;       // base case
    return n * factorial(n - 1); // recursive case
}
```

### Call Stack

Each recursive call creates a new **stack frame** holding local variables and the return address. The OS allocates these on a real call stack; deep recursion can cause **StackOverflowError**.

---

# 4. Real-World Analogy

Russian nesting dolls: each doll contains a smaller doll. To count them, you open the smallest and add 1, then pass back.

---

# 5. Java Implementation — `RecursionDemos.java`

```java
public class RecursionDemos {

    static int factorial(int n) {
        if (n <= 1) return 1;
        return n * factorial(n - 1);
    }

    static long fibNaive(int n) {
        if (n < 2) return n;
        return fibNaive(n - 1) + fibNaive(n - 2);
    }

    static long fibMemo(int n, long[] memo) {
        if (n < 2) return n;
        if (memo[n] != 0) return memo[n];
        return memo[n] = fibMemo(n - 1, memo) + fibMemo(n - 2, memo);
    }

    static long fibIter(int n) {
        if (n < 2) return n;
        long a = 0, b = 1;
        for (int i = 2; i <= n; i++) { long c = a + b; a = b; b = c; }
        return b;
    }

    static int sum(int[] a, int i) {
        if (i == a.length) return 0;
        return a[i] + sum(a, i + 1);
    }

    static String reverse(String s) {
        if (s.length() <= 1) return s;
        return reverse(s.substring(1)) + s.charAt(0);
    }

    static void reverseInPlace(char[] a, int lo, int hi) {
        if (lo >= hi) return;
        char t = a[lo]; a[lo] = a[hi]; a[hi] = t;
        reverseInPlace(a, lo + 1, hi - 1);
    }

    static int power(int b, int e) {
        if (e == 0) return 1;
        if (e % 2 == 0) {
            int half = power(b, e / 2);
            return half * half;
        }
        return b * power(b, e - 1);
    }

    static int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }

    static void towerOfHanoi(int n, char from, char to, char aux) {
        if (n == 0) return;
        towerOfHanoi(n - 1, from, aux, to);
        System.out.println("move disk " + n + ": " + from + " -> " + to);
        towerOfHanoi(n - 1, aux, to, from);
    }

    public static void main(String[] args) {
        System.out.println("5!       = " + factorial(5));
        System.out.println("fib(40)  = " + fibMemo(40, new long[41]));
        System.out.println("fib(40)  = " + fibIter(40));
        System.out.println("sum      = " + sum(new int[]{1,2,3,4,5}, 0));
        System.out.println("reverse  = " + reverse("hello"));
        char[] arr = {'h','e','l','l','o'};
        reverseInPlace(arr, 0, arr.length - 1);
        System.out.println("reverseInPlace = " + new String(arr));
        System.out.println("2^10     = " + power(2, 10));
        System.out.println("gcd(12,8)= " + gcd(12, 8));
        System.out.println("hanoi(3):");
        towerOfHanoi(3, 'A', 'C', 'B');
    }
}
```

Walkthrough:

- `factorial`: classic linear recursion; depth = n.
- `fibNaive`: exponential without memo (O(2ⁿ)); each call spawns two more.
- `fibMemo`: O(n) — caches computed values.
- `fibIter`: O(n) time, O(1) space.
- `sum(a, i)`: sum from index `i` onward. Base case `i == a.length` returns 0.
- `reverse(s)`: rotate the first char to the end of the reversed suffix.
- `reverseInPlace`: two-pointer recursion.
- `power`: exponentiation by squaring — O(log e).
- `gcd`: Euclidean algorithm.
- `towerOfHanoi`: classic example. 2ⁿ − 1 moves.

---

# 6. The Call Stack — `factorial(3)`

```
factorial(3)
   n = 3
   return 3 * factorial(2)
                n = 2
                return 2 * factorial(1)
                              n = 1
                              return 1
                return 2 * 1 = 2
   return 3 * 2 = 6
```

Stack frames unwind in reverse order.

---

# 7. Recursion vs Iteration

| Aspect              | Recursion                | Iteration           |
|---------------------|--------------------------|---------------------|
| Code clarity        | Direct for recursive data | Often less clear    |
| Stack space         | O(depth)                 | O(1)                |
| Overhead            | Function call per step   | None                |
| Risk                | Stack overflow           | Slow loops          |
| When to use         | Trees, graphs, divide-and-conquer | Linear loops |

Rule: prefer recursion when the structure is recursive. Otherwise, prefer iteration.

---

# 8. Tail Recursion

A call is **tail-recursive** if the recursive call is the last operation.

```java
int sumTail(int[] a, int i, int acc) {
    if (i == a.length) return acc;
    return sumTail(a, i + 1, acc + a[i]); // tail call
}
```

Java does **not** optimise tail calls (unlike Scala). Convert to iteration manually for production code:

```java
int sumIter(int[] a) {
    int acc = 0;
    for (int x : a) acc += x;
    return acc;
}
```

---

# 9. Common Mistakes

1. **No base case** — infinite recursion → `StackOverflowError`.
2. **Wrong base case** — returns the wrong value.
3. **Recursing with the wrong argument** — not making progress toward the base case.
4. **Repeated computation** — `fibNaive` is the classic example; memoise.
5. **Modifying shared state across recursion** — bugs that only show up deep in the tree.

---

# 10. Interview Questions

### Q1. What is the call stack?
A LIFO structure that holds each function's local state while it's running.

### Q2. What's the depth of factorial recursion?
n frames.

### Q3. Why memoise Fibonacci?
Without memo: O(2ⁿ). With memo: O(n).

### Q4. When is recursion dangerous?
When depth is large (> 10⁴ typically) — stack overflow.

---

# 11. Practice Problems

## 🟢 Easy

### 1. Factorial
**Input:** `5` → **Output:** `120`

### 2. Power of n
**Input:** `b=2, e=10` → **Output:** `1024`

### 3. Sum of Digits
**Input:** `1234` → **Output:** `10`

### 4. Reverse a String
**Input:** `"hello"` → **Output:** `"olleh"`

### 5. Count Down
**Input:** `n=5` → **Output:** `5 4 3 2 1 done`

## 🟡 Medium

### 6. Fibonacci (memoised)
**Input:** `n=40` → **Output:** `102334155`

### 7. GCD (Euclidean)
**Input:** `a=12, b=8` → **Output:** `4`

### 8. Tower of Hanoi
**Input:** `n=3` → print move sequence.

### 9. Check Palindrome
**Input:** `"racecar"` → **Output:** `true`

### 10. Power(x, n)
**Input:** `x=2, n=-2` → **Output:** `0.25`

## 🔴 Hard

### 11. Permutations of a String
**Input:** `"abc"` → **Output:** `[abc, acb, bac, bca, cab, cba]`

### 12. Subset Sum (count subsets with given sum)
**Input:** `[3,34,4,12,5,2], sum=9` → **Output:** `2`

### 13. Letter Case Permutation
**Input:** `"a1b"` → **Output:** `["a1b","a1B","A1b","A1B"]`

### 14. Different Ways to Add Parentheses
**Input:** `"2-1-1"` → **Output:** `[2,0]`

### 15. Strobogrammatic Number (recursive)
**Input:** `"69"` → **Output:** `true`

---

# 12. Practice Hints

## Easy
1. `n * fact(n-1)`.
2. `b * power(b, e-1)`.
3. `n%10 + sumDigits(n/10)`.
4. `reverse(s.substring(1)) + s.charAt(0)`.
5. Print then recurse.

## Medium
6. Memo array.
7. Euclid.
8. Move n-1 to aux, move biggest, move n-1 to target.
9. Compare s[0] with reverse(s[1..]).
10. Handle negative exponent.

## Hard
11. Fix each position, recurse on the rest.
12. Include or exclude each.
13. For each letter, branch.
14. Split on operators.
15. Pair-wise check 0/0, 1/1, 6/9, 8/8, 9/6.

---

# 13. Revision Checklist

- [ ] Can identify base case
- [ ] Can trace the call stack
- [ ] Can compute depth & complexity
- [ ] Can memoise
- [ ] Can convert recursion ↔ iteration

---

# 14. Key Takeaways

- Every recursion needs a base case and progress toward it.
- Each call uses O(1) stack space; depth of N uses O(N).
- Memoisation turns exponential recursion into polynomial.
- Java doesn't optimise tail calls — convert to iteration when needed.

Tomorrow: **Linked List**.
