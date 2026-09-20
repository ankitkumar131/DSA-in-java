/**
 * Day 15 — Easy practice.
 *
 * Compile: javac src/day-15/PracticeEasy.java
 * Run    : java -cp src/day-15 PracticeEasy
 */
import java.util.*;

public class PracticeEasy {

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

    static class ArrayStack {
        int[] a = new int[4]; int top = -1;
        void push(int x) { if (top == a.length - 1) a = Arrays.copyOf(a, a.length * 2); a[++top] = x; }
        int pop() { return a[top--]; }
        int peek() { return a[top]; }
        boolean isEmpty() { return top == -1; }
    }

    static class QueueStack {
        Deque<Integer> q = new ArrayDeque<>();
        void push(int x) {
            q.offer(x);
            for (int i = 0; i < q.size() - 1; i++) q.offer(q.poll());
        }
        int pop() { return q.poll(); }
        int top() { return q.peek(); }
    }

    static class MinStack {
        Deque<int[]> st = new ArrayDeque<>(); // [value, currentMin]
        void push(int x) { int min = st.isEmpty() ? x : Math.min(st.peek()[1], x); st.push(new int[]{x, min}); }
        int pop() { return st.pop()[0]; }
        int getMin() { return st.peek()[1]; }
    }

    static String reverseWithStack(String s) {
        Deque<Character> st = new ArrayDeque<>();
        for (char c : s.toCharArray()) st.push(c);
        StringBuilder sb = new StringBuilder();
        while (!st.isEmpty()) sb.append(st.pop());
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("Q1 balanced = " + balanced("()[]{}"));
        ArrayStack as = new ArrayStack();
        as.push(1); as.push(2); as.push(3);
        as.pop(); as.pop();
        System.out.println("Q2 array top = " + as.peek());

        QueueStack qs = new QueueStack();
        qs.push(1); qs.push(2); qs.push(3);
        qs.pop();
        System.out.println("Q3 queue-stack top = " + qs.top());

        MinStack ms = new MinStack();
        ms.push(-2); ms.push(0); ms.push(-3);
        System.out.println("Q4 minStack getMin = " + ms.getMin());

        System.out.println("Q5 reverse = " + reverseWithStack("hello"));
    }
}
