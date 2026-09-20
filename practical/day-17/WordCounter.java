/** Day 17 tiny project: word frequency counter. */
import java.util.*;
public class WordCounter {
    public static void main(String[] args) {
        String text = "the quick brown fox jumps over the lazy dog the fox";
        Map<String, Integer> freq = new HashMap<>();
        for (String w : text.split(" ")) freq.merge(w, 1, Integer::sum);
        freq.entrySet().stream()
            .sorted((a, b) -> b.getValue() - a.getValue())
            .limit(3)
            .forEach(e -> System.out.println(e.getKey() + " -> " + e.getValue()));
    }
}
