/**
 * Day 23 — Easy practice.
 *
 * Compile: javac src/day-23/PracticeEasy.java
 * Run    : java -cp src/day-23 PracticeEasy
 */
import java.util.*;

public class PracticeEasy {

    static List<List<Integer>> subsets(int[] a) {
        List<List<Integer>> out = new ArrayList<>();
        back(a, 0, new ArrayList<>(), out); return out;
    }
    static void back(int[] a, int i, List<Integer> cur, List<List<Integer>> out) {
        out.add(new ArrayList<>(cur));
        for (int j = i; j < a.length; j++) {
            cur.add(a[j]); back(a, j + 1, cur, out); cur.remove(cur.size() - 1);
        }
    }

    static List<List<Integer>> powerSet(int[] a) { return subsets(a); }

    static List<String> letterCasePerm(String s) {
        List<String> out = new ArrayList<>();
        back2(s.toCharArray(), 0, out); return out;
    }
    static void back2(char[] a, int i, List<String> out) {
        if (i == a.length) { out.add(new String(a)); return; }
        if (Character.isLetter(a[i])) {
            a[i] = Character.toLowerCase(a[i]); back2(a, i + 1, out);
            a[i] = Character.toUpperCase(a[i]); back2(a, i + 1, out);
        } else back2(a, i + 1, out);
    }

    static List<String> genParens(int n) {
        List<String> out = new ArrayList<>();
        parens(out, "", 0, 0, n); return out;
    }
    static void parens(List<String> out, String cur, int open, int close, int n) {
        if (cur.length() == 2 * n) { out.add(cur); return; }
        if (open < n)  parens(out, cur + "(", open + 1, close, n);
        if (close < open) parens(out, cur + ")", open, close + 1, n);
    }

    static List<String> binaryWatch(int turnedOn) {
        List<String> out = new ArrayList<>();
        for (int h = 0; h < 12; h++)
            for (int m = 0; m < 60; m++)
                if (Integer.bitCount(h) + Integer.bitCount(m) == turnedOn)
                    out.add(String.format("%d:%02d", h, m));
        return out;
    }

    public static void main(String[] args) {
        System.out.println("Q1 subsets      = " + subsets(new int[]{1,2,3}));
        System.out.println("Q2 powerSet     = " + powerSet(new int[]{1,2}));
        System.out.println("Q3 letterCase   = " + letterCasePerm("a1b"));
        System.out.println("Q4 parens       = " + genParens(3));
        System.out.println("Q5 binaryWatch  = " + binaryWatch(1));
    }
}
