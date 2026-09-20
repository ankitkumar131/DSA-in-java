/**
 * Day 15 — Hard practice.
 *
 * Compile: javac src/day-15/PracticeHard.java
 * Run    : java -cp src/day-15 PracticeHard
 */
import java.util.*;

public class PracticeHard {

    // Q11: Largest rectangle in histogram
    static int largestRectangle(int[] h) {
        Deque<Integer> st = new ArrayDeque<>();
        int best = 0, n = h.length;
        for (int i = 0; i <= n; i++) {
            int cur = (i == n) ? 0 : h[i];
            while (!st.isEmpty() && h[st.peek()] > cur) {
                int height = h[st.pop()];
                int width = st.isEmpty() ? i : i - st.peek() - 1;
                best = Math.max(best, height * width);
            }
            st.push(i);
        }
        return best;
    }

    // Q12: Trap rain water using stack
    static int trap(int[] h) {
        Deque<Integer> st = new ArrayDeque<>();
        int water = 0;
        for (int i = 0; i < h.length; i++) {
            while (!st.isEmpty() && h[st.peek()] < h[i]) {
                int bottom = h[st.pop()];
                if (st.isEmpty()) break;
                int left = st.peek();
                water += (Math.min(h[left], h[i]) - bottom) * (i - left - 1);
            }
            st.push(i);
        }
        return water;
    }

    // Q13: Basic calculator (+, -, parens)
    static int calculate(String s) {
        Deque<Integer> stack = new ArrayDeque<>();
        int num = 0, sign = 1, result = 0;
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) num = num * 10 + (c - '0');
            else if (c == '+') { result += sign * num; num = 0; sign = 1; }
            else if (c == '-') { result += sign * num; num = 0; sign = -1; }
            else if (c == '(') { stack.push(result); stack.push(sign); result = 0; sign = 1; num = 0; }
            else if (c == ')') {
                result += sign * num;
                num = 0;
                int prevSign = stack.pop();
                int prevResult = stack.pop();
                result = prevResult + prevSign * result;
            }
        }
        return result + sign * num;
    }

    // Q14: Maximal rectangle in binary matrix
    static int maximalRectangle(char[][] m) {
        if (m.length == 0) return 0;
        int cols = m[0].length, best = 0;
        int[] heights = new int[cols];
        for (char[] row : m) {
            for (int j = 0; j < cols; j++)
                heights[j] = (row[j] == '1') ? heights[j] + 1 : 0;
            best = Math.max(best, largestRectangle(heights));
        }
        return best;
    }

    // Q15: Remove K digits
    static String removeKdigits(String num, int k) {
        Deque<Character> st = new ArrayDeque<>();
        for (char c : num.toCharArray()) {
            while (!st.isEmpty() && k > 0 && st.peek() > c) { st.pop(); k--; }
            st.push(c);
        }
        while (k-- > 0) st.pop();
        StringBuilder sb = new StringBuilder();
        for (char c : st) sb.append(c);
        while (sb.length() > 1 && sb.charAt(0) == '0') sb.deleteCharAt(0);
        return sb.length() == 0 ? "0" : sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("Q11 largestRect = " + largestRectangle(new int[]{2,1,5,6,2,3}));
        System.out.println("Q12 trap       = " + trap(new int[]{0,1,0,2,1,0,1,3,2,1,2,1}));
        System.out.println("Q13 calc       = " + calculate("1 + (4 + 5 + 2) - 3"));
        char[][] mat = {
            {'1','0','1','0','0'},
            {'1','0','1','1','1'},
            {'1','1','1','1','1'},
            {'1','0','0','1','0'}
        };
        System.out.println("Q14 maxRect    = " + maximalRectangle(mat));
        System.out.println("Q15 removeK    = " + removeKdigits("1432219", 3));
    }
}
