/** Day 10 tiny project: lower-bound binary search over a sorted word list. */
import java.util.*;
public class WordLookup {
    static int lowerBound(String[] w, String t) {
        int lo = 0, hi = w.length;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (w[mid].compareTo(t) < 0) lo = mid + 1; else hi = mid;
        }
        return lo;
    }
    public static void main(String[] args) {
        String[] dict = {"apple", "banana", "cherry", "date", "fig", "grape"};
        String q = "cherry";
        int idx = lowerBound(dict, q);
        System.out.println("first match >= '" + q + "' = " + (idx < dict.length ? dict[idx] : "(none)"));
    }
}
