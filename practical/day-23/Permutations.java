/** Day 23 tiny project: all permutations via backtracking. */
import java.util.*;
public class Permutations {
    static void perm(char[] a, int i, List<String> out) {
        if (i == a.length - 1) { out.add(new String(a)); return; }
        for (int j = i; j < a.length; j++) {
            swap(a, i, j);
            perm(a, i + 1, out);
            swap(a, i, j);
        }
    }
    static void swap(char[] a, int i, int j) { char t = a[i]; a[i] = a[j]; a[j] = t; }
    public static void main(String[] args) {
        List<String> out = new ArrayList<>();
        perm("abc".toCharArray(), 0, out);
        System.out.println(out);
    }
}
