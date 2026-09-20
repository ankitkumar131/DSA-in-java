/** Day 6 tiny project: group anagrams from a sentence. */
import java.util.*;

public class AnagramGrouper {
    public static void main(String[] args) {
        String sentence = "listen silent enlist inlets dog god hello";
        Map<String, List<String>> groups = new HashMap<>();
        for (String w : sentence.split(" ")) {
            char[] c = w.toCharArray(); Arrays.sort(c);
            groups.computeIfAbsent(new String(c), k -> new ArrayList<>()).add(w);
        }
        for (var e : groups.entrySet()) {
            if (e.getValue().size() > 1) System.out.println(e.getValue());
        }
    }
}
