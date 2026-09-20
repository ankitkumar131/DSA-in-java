/**
 * Day 12 — Hard practice.
 *
 * Compile: javac src/day-12/PracticeHard.java
 * Run    : java -cp src/day-12 PracticeHard
 */
import java.util.*;

public class PracticeHard {

    // Q11: Permutations of a string
    static List<String> permutations(String s) {
        List<String> out = new ArrayList<>();
        permute("", s, out);
        return out;
    }
    static void permute(String prefix, String rest, List<String> out) {
        if (rest.isEmpty()) { out.add(prefix); return; }
        for (int i = 0; i < rest.length(); i++)
            permute(prefix + rest.charAt(i), rest.substring(0, i) + rest.substring(i + 1), out);
    }

    // Q12: subset sum count
    static int subsetSumCount(int[] nums, int sum) {
        return subsetSumCount(nums, sum, 0, 0);
    }
    static int subsetSumCount(int[] nums, int target, int idx, int cur) {
        if (idx == nums.length) return cur == target ? 1 : 0;
        return subsetSumCount(nums, target, idx + 1, cur + nums[idx])
             + subsetSumCount(nums, target, idx + 1, cur);
    }

    // Q13: letter case permutation
    static List<String> letterCasePerm(String s) {
        List<String> out = new ArrayList<>();
        backtrack(s.toCharArray(), 0, out);
        return out;
    }
    static void backtrack(char[] a, int i, List<String> out) {
        if (i == a.length) { out.add(new String(a)); return; }
        if (Character.isLetter(a[i])) {
            a[i] = Character.toLowerCase(a[i]); backtrack(a, i + 1, out);
            a[i] = Character.toUpperCase(a[i]); backtrack(a, i + 1, out);
        } else {
            backtrack(a, i + 1, out);
        }
    }

    // Q14: Different ways to add parentheses (operator precedence)
    static List<Integer> diffWays(String expr) {
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);
            if (c == '+' || c == '-' || c == '*') {
                List<Integer> left = diffWays(expr.substring(0, i));
                List<Integer> right = diffWays(expr.substring(i + 1));
                for (int l : left) for (int r : right)
                    if (c == '+') res.add(l + r);
                    else if (c == '-') res.add(l - r);
                    else res.add(l * r);
            }
        }
        if (res.isEmpty()) res.add(Integer.parseInt(expr));
        return res;
    }

    // Q15: strobogrammatic
    static boolean isStrobogrammatic(String s) {
        return strobHelper(s.toCharArray(), 0, s.length() - 1);
    }
    static boolean strobHelper(char[] a, int lo, int hi) {
        if (lo > hi) return true;
        char p = a[lo], q = a[hi];
        boolean ok = (p == '0' && q == '0') || (p == '1' && q == '1')
                  || (p == '6' && q == '9') || (p == '9' && q == '6')
                  || (p == '8' && q == '8');
        return ok && strobHelper(a, lo + 1, hi - 1);
    }

    public static void main(String[] args) {
        System.out.println("Q11 perms       = " + permutations("abc"));
        System.out.println("Q12 subsetCount = " + subsetSumCount(new int[]{3,34,4,12,5,2}, 9));
        System.out.println("Q13 letterCase  = " + letterCasePerm("a1b"));
        System.out.println("Q14 diffWays    = " + diffWays("2-1-1"));
        System.out.println("Q15 strobogr    = " + isStrobogrammatic("69"));
    }
}
