/** Day 15 tiny project: stack-based bracket validator. */
import java.util.*;
public class BracketChecker {
    public static void main(String[] args) {
        String[] tests = {"()[]{}", "([)]", "{[()]}", ""};
        for (String s : tests) System.out.println(s + " -> " + check(s));
    }
    static boolean check(String s) {
        Deque<Character> st = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            if ("({[".indexOf(c) >= 0) st.push(c);
            else if (st.isEmpty()) return false;
            else { char o = st.pop(); if (c == ')' && o != '(' || c == '}' && o != '{' || c == ']' && o != '[') return false; }
        }
        return st.isEmpty();
    }
}
