/** Day 30 tiny project: pattern recogniser quiz. */
import java.util.*;
public class PatternQuiz {
    record Problem(String sig, String pat) {}
    public static void main(String[] args) {
        List<Problem> pool = List.of(
            new Problem("sorted array, find pair sum", "two-pointers"),
            new Problem("contiguous subarray constraint", "sliding-window"),
            new Problem("shortest path unweighted", "BFS"),
            new Problem("shortest path weighted", "Dijkstra"),
            new Problem("count ways / min cost", "DP"),
            new Problem("generate all permutations", "backtracking"),
            new Problem("next greater element", "monotonic-stack"),
            new Problem("top-K elements", "heap"),
            new Problem("prefix sum query", "prefix-sum"),
            new Problem("sorted array O(log n) find", "binary-search")
        );
        Random r = new Random();
        for (int i = 0; i < 5; i++) {
            Problem p = pool.get(r.nextInt(pool.size()));
            System.out.println("signal: " + p.sig() + "  ->  pattern: " + p.pat());
        }
    }
}
