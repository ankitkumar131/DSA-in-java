# Day 15 — Stack

## 🎯 Learning Objectives

By the end of today, you should be able to:

- Explain the LIFO principle and use cases
- Implement a stack with an array and with a linked list
- Use Java's `Deque`/`ArrayDeque` as a stack
- Solve balanced parentheses and next greater element
- Implement a monotonic stack
- Build a Min Stack with O(1) `getMin`

---

# 1. Introduction

A stack is the simplest non-trivial data structure: push on top, pop from top. Despite its simplicity, it's the engine behind many algorithms: DFS, expression parsing, balanced-bracket checks, monotonic-stack problems.

---

# 2. Why Do We Need This?

- **Recursive algorithms** = explicit stack frames.
- **DFS** uses a stack (or recursion).
- **Next greater / smaller element** is most cleanly solved with a **monotonic stack**.
- **Expression evaluation** is stack-based.

---

# 3. Core Concept — LIFO

Last-In-First-Out. Only the top is accessible.

```
push 1 → [1]
push 2 → [1, 2]
push 3 → [1, 2, 3]
pop    → 3, stack = [1, 2]
pop    → 2, stack = [1]
```

---

# 4. Real-World Analogy

A stack of plates: you can only add or remove the top one. A browser's back button: pages visited are pushed; back pops the most recent.

---

# 5. Java Implementation — `StackDemo.java`

```java
import java.util.*;

public class StackDemo {

    /** Array-based stack. */
    static class ArrayStack {
        int[] a = new int[4];
        int top = -1;
        void push(int x) {
            if (top == a.length - 1) a = Arrays.copyOf(a, a.length * 2);
            a[++top] = x;
        }
        int pop() { return a[top--]; }
        int peek() { return a[top]; }
        boolean isEmpty() { return top == -1; }
    }

    /** Linked-list stack. */
    static class LLStack {
        static class Node { int data; Node next; }
        Node top;
        void push(int x) { Node n = new Node(); n.data = x; n.next = top; top = n; }
        int pop() { int v = top.data; top = top.next; return v; }
        int peek() { return top.data; }
    }

    /** Balanced parentheses using Deque. */
    static boolean balanced(String s) {
        Deque<Character> st = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            if ("({[".indexOf(c) >= 0) st.push(c);
            else {
                if (st.isEmpty()) return false;
                char open = st.pop();
                if (c == ')' && open != '(') return false;
                if (c == '}' && open != '{') return false;
                if (c == ']' && open != '[') return false;
            }
        }
        return st.isEmpty();
    }

    /** Next greater element for each index. */
    static int[] nextGreater(int[] a) {
        int n = a.length;
        int[] out = new int[n];
        Arrays.fill(out, -1);
        Deque<Integer> st = new ArrayDeque<>(); // indices with decreasing values
        for (int i = 0; i < n; i++) {
            while (!st.isEmpty() && a[st.peek()] < a[i]) out[st.pop()] = a[i];
            st.push(i);
        }
        return out;
    }

    /** Min Stack: push, pop, top, getMin — all O(1). */
    static class MinStack {
        Deque<Integer> stack = new ArrayDeque<>();
        Deque<Integer> mins  = new ArrayDeque<>();
        void push(int x) {
            stack.push(x);
            if (mins.isEmpty() || x <= mins.peek()) mins.push(x);
        }
        int pop() {
            int x = stack.pop();
            if (x == mins.peek()) mins.pop();
            return x;
        }
        int top() { return stack.peek(); }
        int getMin() { return mins.peek(); }
    }

    /** Daily Temperatures: days until a warmer temperature. */
    static int[] dailyTemperatures(int[] t) {
        int[] out = new int[t.length];
        Deque<Integer> st = new ArrayDeque<>();
        for (int i = 0; i < t.length; i++) {
            while (!st.isEmpty() && t[st.peek()] < t[i]) {
                int idx = st.pop();
                out[idx] = i - idx;
            }
            st.push(i);
        }
        return out;
    }

    /** Evaluate postfix expression. */
    static int evalPostfix(String s) {
        Deque<Integer> st = new ArrayDeque<>();
        for (String tok : s.split(" ")) {
            if ("+-*/".contains(tok)) {
                int b = st.pop(), a = st.pop();
                switch (tok) {
                    case "+": st.push(a + b); break;
                    case "-": st.push(a - b); break;
                    case "*": st.push(a * b); break;
                    case "/": st.push(a / b); break;
                }
            } else st.push(Integer.parseInt(tok));
        }
        return st.pop();
    }

    public static void main(String[] args) {
        System.out.println("balanced(\"()[]{}\")   = " + balanced("()[]{}"));
        System.out.println("balanced(\"([)]\")     = " + balanced("([)]"));
        System.out.println("nextGreater           = " + Arrays.toString(nextGreater(new int[]{4,5,2,25})));
        System.out.println("dailyTemps            = " + Arrays.toString(dailyTemperatures(new int[]{73,74,75,71,69,72,76,73})));
        System.out.println("postfix 2 3 * 4 +     = " + evalPostfix("2 3 * 4 +"));

        MinStack ms = new MinStack();
        ms.push(-2); ms.push(0); ms.push(-3);
        System.out.println("minStack getMin = " + ms.getMin());
        ms.pop();
        System.out.println("after pop top = " + ms.top() + " min = " + ms.getMin());
    }
}
```

Walkthrough:

- `ArrayStack`: dynamic array with manual `copyOf` doubling.
- `LLStack`: prepend a new node; the new top is at `top`.
- `balanced`: when closing bracket, top of stack should match.
- `nextGreater`: monotonic decreasing stack of indices. For each `i`, pop indices whose next greater is now known.
- `MinStack`: parallel stack of minima. Each push updates `mins` if new min. Each pop removes if it was a min.
- `dailyTemperatures`: classic monotonic stack — pop until warmer.
- `evalPostfix`: tokens are operands (push) or operators (pop two, apply).

---

# 6. Dry Run — `nextGreater([4,5,2,25])`

| i | a[i] | stack (indices) | action | out |
|---|------|-----------------|--------|-----|
| 0 | 4    | [0]             | push 0 | -   |
| 1 | 5    | []              | a[0]=4 < 5, set out[0]=5, pop; push 1 | out[0]=5 |
| 2 | 2    | [1]             | a[1]=5 not < 2; push 2 | - |
| 3 | 25   | []              | pop 2: a[2]=2 < 25 → out[2]=25; pop 1: out[1]=25; push 3 | out[1]=25, out[2]=25 |

Result: `[5, 25, 25, -1]`.

---

# 7. Monotonic Stack — When to Use

A **monotonic stack** maintains a stack whose elements are in increasing (or decreasing) order. Pop rules:

| Want          | Stack order    | Pop when |
|---------------|----------------|----------|
| Next greater  | decreasing     | stack top < current |
| Next smaller  | increasing     | stack top > current |
| Prev greater  | decreasing     | reverse iteration |
| Prev smaller  | increasing     | reverse iteration |

---

# 8. Common Mistakes

1. **Empty-stack pop** — check `isEmpty()` first.
2. **Wrong comparator** in monotonic stack — strict vs non-strict.
3. **MinStack pop doesn't update mins** when popping the current min.

---

# 9. Interview Questions

### Q1. Why use `ArrayDeque` over `java.util.Stack`?
`Stack` extends `Vector` and is synchronised (slow). `ArrayDeque` is faster and not synchronised.

### Q2. Min Stack complexity?
O(1) for all operations.

### Q3. Next greater in O(n)?
Yes, with a monotonic stack.

---

# 10. Practice Problems

## 🟢 Easy

### 1. Valid Parentheses
**Input:** `"()[]{}"` → **Output:** `true`

### 2. Implement Stack Using Arrays
**Input:** push 1,2,3; pop twice → **Output:** `1`

### 3. Implement Stack Using Queues
**Input:** push 1,2,3; pop → **Output:** `1`

### 4. Min Stack
**Input:** push -2, 0, -3; getMin → **Output:** `-3`

### 5. Reverse a String Using Stack
**Input:** `"hello"` → **Output:** `"olleh"`

## 🟡 Medium

### 6. Next Greater Element I
**Input:** `nums1=[4,1,2], nums2=[1,3,4,2]` → **Output:** `[-1,3,-1]`

### 7. Daily Temperatures
**Input:** `[73,74,75,71,69,72,76,73]` → **Output:** `[1,1,4,2,1,1,0,0]`

### 8. Evaluate Reverse Polish Notation
**Input:** `["2","1","+","3","*"]` → **Output:** `9`

### 9. Decode String
**Input:** `"3[a2[c]]"` → **Output:** `"accaccacc"`

### 10. Asteroid Collision
**Input:** `[5,10,-5]` → **Output:** `[5,10]`

## 🔴 Hard

### 11. Largest Rectangle in Histogram
**Input:** `[2,1,5,6,2,3]` → **Output:** `10`

### 12. Trapping Rain Water (stack)
**Input:** `[0,1,0,2,1,0,1,3,2,1,2,1]` → **Output:** `6`

### 13. Basic Calculator
**Input:** `"1 + 1"` → **Output:** `2`

### 14. Maximal Rectangle
**Input:** binary matrix → **Output:** max rectangle of 1s.

### 15. Remove K Digits
**Input:** `"1432219", k=3` → **Output:** `"1219"`

---

# 11. Practice Hints

## Easy
1. Stack, map open↔close.
2. `int[]` + `top`.
3. Two queues or one queue (rotate on push).
4. Parallel min stack.
5. Push chars, pop to build.

## Medium
6. Monotonic stack on nums2.
7. Monotonic stack.
8. Stack of operands.
9. Stack of counts.
10. Stack simulation.

## Hard
11. Monotonic stack, track heights.
12. Monotonic stack of indices.
13. Stack + sign.
14. Largest rectangle per row.
15. Monotonic stack, pop K.

---

# 12. Revision Checklist

- [ ] Can implement stack from array and LL
- [ ] Can solve balanced parentheses
- [ ] Can implement MinStack
- [ ] Can apply monotonic stack

---

# 13. Key Takeaways

- Stack: LIFO. Use `ArrayDeque` in Java.
- Min Stack: parallel stack of minima.
- Monotonic stack: next/prev greater/smaller in O(n).

Tomorrow: **Queue & Deque**.
