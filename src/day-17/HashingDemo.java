/**
 * Day 17 — Hashing demo.
 *
 * Compile: javac src/day-17/HashingDemo.java
 * Run    : java -cp src/day-17 HashingDemo
 */
import java.util.*;

public class HashingDemo {

    static int[] twoSum(int[] a, int target) {
        Map<Integer, Integer> seen = new HashMap<>();
        for (int i = 0; i < a.length; i++) {
            int need = target - a[i];
            if (seen.containsKey(need)) return new int[]{seen.get(need), i};
            seen.put(a[i], i);
        }
        return new int[]{-1, -1};
    }

    static Map<Integer, Integer> frequency(int[] a) {
        Map<Integer, Integer> m = new HashMap<>();
        for (int x : a) m.merge(x, 1, Integer::sum);
        return m;
    }

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
        for (int x : a) {
            sum += x;
            count += m.getOrDefault(sum - k, 0);
            m.merge(sum, 1, Integer::sum);
        }
        return count;
    }

    static void mapVariants() {
        Map<String, Integer> hash = new HashMap<>();
        Map<String, Integer> linked = new LinkedHashMap<>();
        Map<String, Integer> tree = new TreeMap<>();
        for (String k : new String[]{"c", "a", "b"}) { hash.put(k, 1); linked.put(k, 1); tree.put(k, 1); }
        System.out.println("HashMap       : " + hash.keySet());
        System.out.println("LinkedHashMap : " + linked.keySet());
        System.out.println("TreeMap       : " + tree.keySet());
    }

    public static void main(String[] args) {
        System.out.println("twoSum        = " + Arrays.toString(twoSum(new int[]{2,7,11,15}, 9)));
        System.out.println("frequency     = " + frequency(new int[]{1,1,2,3,3,3}));
        System.out.println("groupAnagrams = " + groupAnagrams(new String[]{"eat","tea","tan","ate","nat","bat"}));
        System.out.println("longestConsec = " + longestConsecutive(new int[]{100,4,200,1,3,2}));
        System.out.println("subarraySum   = " + subarraySum(new int[]{1,2,3}, 3));
        mapVariants();
    }
}
