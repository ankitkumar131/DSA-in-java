/**
 * Day 15 — Medium practice.
 *
 * Compile: javac src/day-15/PracticeMedium.java
 * Run    : java -cp src/day-15 PracticeMedium
 */
import java.util.*;

public class PracticeMedium {

    static int[] nextGreaterElement(int[] n1, int[] n2) {
        Map<Integer, Integer> m = new HashMap<>();
        Deque<Integer> st = new ArrayDeque<>();
        for (int x : n2) {
            while (!st.isEmpty() && st.peek() < x) m.put(st.pop(), x);
            st.push(x);
        }
        int[] out = new int[n1.length];
        for (int i = 0; i < n1.length; i++) out[i] = m.getOrDefault(n1[i], -1);
        return out;
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

    static int evalRPN(String[] tokens) {
        Deque<Integer> st = new ArrayDeque<>();
        for (String t : tokens) {
            if ("+-*/".contains(t)) {
                int b = st.pop(), a = st.pop();
                switch (t) {
                    case "+": st.push(a + b); break;
                    case "-": st.push(a - b); break;
                    case "*": st.push(a * b); break;
                    case "/": st.push(a / b); break;
                }
            } else st.push(Integer.parseInt(t));
        }
        return st.pop();
    }

    static String decodeString(String s) {
        Deque<Integer> counts = new ArrayDeque<>();
        Deque<StringBuilder> strs = new ArrayDeque<>();
        StringBuilder cur = new StringBuilder();
        int k = 0;
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) k = k * 10 + (c - '0');
            else if (c == '[') { counts.push(k); strs.push(cur); cur = new StringBuilder(); k = 0; }
            else if (c == ']') { StringBuilder prev = strs.pop(); int n = counts.pop(); StringBuilder repeat = new StringBuilder(); for (int i = 0; i < n; i++) repeat.append(cur); cur = prev.append(repeat); }
            else cur.append(c);
        }
        return cur.toString();
    }

    static int[] asteroidCollision(int[] a) {
        Deque<Integer> st = new ArrayDeque<>();
        for (int x : a) {
            while (!st.isEmpty() && st.peek() > 0 && x < 0) {
                int top = st.pop();
                if (top == -x) { x = 0; break; }
                if (top > -x)  { x = top; break; }
            }
            if (x != 0) st.push(x);
        }
        int[] out = new int[st.size()];
        for (int i = out.length - 1; i >= 0; i--) out[i] = st.pop();
        return out;
    }

    public static void main(String[] args) {
        System.out.println("Q6 nextGreater = " + Arrays.toString(nextGreaterElement(new int[]{4,1,2}, new int[]{1,3,4,2})));
        System.out.println("Q7 dailyTemps  = " + Arrays.toString(dailyTemperatures(new int[]{73,74,75,71,69,72,76,73})));
        System.out.println("Q8 evalRPN    = " + evalRPN(new String[]{"2","1","+","3","*"}));
        System.out.println("Q9 decode     = " + decodeString("3[a2[c]]"));
        System.out.println("Q10 asteroid  = " + Arrays.toString(asteroidCollision(new int[]{5,10,-5})));
    }
}
