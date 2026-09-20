/**
 * Day 15 — Stack demo.
 *
 * Compile: javac src/day-15/StackDemo.java
 * Run    : java -cp src/day-15 StackDemo
 */
import java.util.*;

public class StackDemo {

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

    static class LLStack {
        static class Node { int data; Node next; }
        Node top;
        void push(int x) { Node n = new Node(); n.data = x; n.next = top; top = n; }
        int pop() { int v = top.data; top = top.next; return v; }
        int peek() { return top.data; }
    }

    static boolean balanced(String s) {
        Deque<Character> st = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            if ("({[".indexOf(c) >= 0) st.push(c);
            else {
                if (st.isEmpty()) return false;
                char o = st.pop();
                if (c == ')' && o != '(') return false;
                if (c == '}' && o != '{') return false;
                if (c == ']' && o != '[') return false;
            }
        }
        return st.isEmpty();
    }

    static int[] nextGreater(int[] a) {
        int n = a.length;
        int[] out = new int[n];
        Arrays.fill(out, -1);
        Deque<Integer> st = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            while (!st.isEmpty() && a[st.peek()] < a[i]) out[st.pop()] = a[i];
            st.push(i);
        }
        return out;
    }

    static class MinStack {
        Deque<Integer> stack = new ArrayDeque<>();
        Deque<Integer> mins  = new ArrayDeque<>();
        void push(int x) { stack.push(x); if (mins.isEmpty() || x <= mins.peek()) mins.push(x); }
        int pop() { int x = stack.pop(); if (x == mins.peek()) mins.pop(); return x; }
        int top() { return stack.peek(); }
        int getMin() { return mins.peek(); }
    }

    static int[] dailyTemperatures(int[] t) {
        int[] out = new int[t.length];
        Deque<Integer> st = new ArrayDeque<>();
        for (int i = 0; i < t.length; i++) {
            while (!st.isEmpty() && t[st.peek()] < t[i]) { int idx = st.pop(); out[idx] = i - idx; }
            st.push(i);
        }
        return out;
    }

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
        System.out.println("balanced(\"()[]{}\") = " + balanced("()[]{}"));
        System.out.println("balanced(\"([)]\")   = " + balanced("([)]"));
        System.out.println("nextGreater = " + Arrays.toString(nextGreater(new int[]{4,5,2,25})));
        System.out.println("dailyTemps  = " + Arrays.toString(dailyTemperatures(new int[]{73,74,75,71,69,72,76,73})));
        System.out.println("postfix 2 3 * 4 + = " + evalPostfix("2 3 * 4 +"));

        MinStack ms = new MinStack();
        ms.push(-2); ms.push(0); ms.push(-3);
        System.out.println("minStack getMin = " + ms.getMin());
        ms.pop();
        System.out.println("after pop top=" + ms.top() + " min=" + ms.getMin());
    }
}
