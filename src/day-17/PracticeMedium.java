/**
 * Day 17 — Medium practice.
 *
 * Compile: javac src/day-17/PracticeMedium.java
 * Run    : java -cp src/day-17 PracticeMedium
 */
import java.util.*;

public class PracticeMedium {

    static List<List<String>> groupAnagrams(String[] words) {
        Map<String, List<String>> m = new HashMap<>();
        for (String w : words) {
            char[] c = w.toCharArray();
            Arrays.sort(c);
            String key = new String(c);
            m.computeIfAbsent(key, k -> new ArrayList<>()).add(w);
        }
        return new ArrayList<>(m.values());
    }

    static int[] topKFrequent(int[] a, int k) {
        Map<Integer, Integer> m = new HashMap<>();
        for (int x : a) m.merge(x, 1, Integer::sum);
        List<Integer>[] bucket = new List[a.length + 1];
        for (var e : m.entrySet()) {
            int f = e.getValue();
            if (bucket[f] == null) bucket[f] = new ArrayList<>();
            bucket[f].add(e.getKey());
        }
        int[] out = new int[k]; int idx = 0;
        for (int f = a.length; f >= 0 && idx < k; f--)
            if (bucket[f] != null) for (int x : bucket[f]) out[idx++] = x;
        return Arrays.copyOf(out, idx);
    }

    static int longestConsecutive(int[] a) {
        Set<Integer> s = new HashSet<>();
        for (int x : a) s.add(x);
        int best = 0;
        for (int x : s) {
            if (!s.contains(x - 1)) {
                int len = 1, cur = x;
                while (s.contains(cur + 1)) { cur++; len++; }
                best = Math.max(best, len);
            }
        }
        return best;
    }

    static int subarraySum(int[] a, int k) {
        Map<Integer, Integer> m = new HashMap<>();
        m.put(0, 1);
        int sum = 0, count = 0;
        for (int x : a) { sum += x; count += m.getOrDefault(sum - k, 0); m.merge(sum, 1, Integer::sum); }
        return count;
    }

    static int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
        Map<Integer, Integer> m = new HashMap<>();
        for (int a : A) for (int b : B) m.merge(a + b, 1, Integer::sum);
        int count = 0;
        for (int c : C) for (int d : D) count += m.getOrDefault(-(c + d), 0);
        return count;
    }

    public static void main(String[] args) {
        System.out.println("Q6  groupAnagrams = " + groupAnagrams(new String[]{"eat","tea","tan","ate","nat","bat"}));
        System.out.println("Q7  topKFreq      = " + Arrays.toString(topKFrequent(new int[]{1,1,1,2,2,3}, 2)));
        System.out.println("Q8  longestConsec = " + longestConsecutive(new int[]{100,4,200,1,3,2}));
        System.out.println("Q9  subarraySum   = " + subarraySum(new int[]{1,1,1}, 2));
        System.out.println("Q10 fourSumCount  = " + fourSumCount(new int[]{1,2}, new int[]{-2,-1}, new int[]{-1,2}, new int[]{0,2}));
    }
}
