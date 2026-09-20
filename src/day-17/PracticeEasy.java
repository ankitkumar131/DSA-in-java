/**
 * Day 17 — Easy practice.
 *
 * Compile: javac src/day-17/PracticeEasy.java
 * Run    : java -cp src/day-17 PracticeEasy
 */
import java.util.*;

public class PracticeEasy {

    static int[] twoSum(int[] a, int t) {
        Map<Integer, Integer> m = new HashMap<>();
        for (int i = 0; i < a.length; i++) {
            int need = t - a[i];
            if (m.containsKey(need)) return new int[]{m.get(need), i};
            m.put(a[i], i);
        }
        return new int[]{-1, -1};
    }

    static boolean containsDuplicate(int[] a) {
        Set<Integer> s = new HashSet<>();
        for (int x : a) if (!s.add(x)) return true;
        return false;
    }

    static int[] intersection(int[] a, int[] b) {
        Set<Integer> s = new HashSet<>();
        for (int x : a) s.add(x);
        Set<Integer> out = new LinkedHashSet<>();
        for (int x : b) if (s.contains(x)) out.add(x);
        return out.stream().mapToInt(Integer::intValue).toArray();
    }

    static int firstUniqChar(String s) {
        Map<Character, Integer> m = new HashMap<>();
        for (char c : s.toCharArray()) m.merge(c, 1, Integer::sum);
        for (int i = 0; i < s.length(); i++) if (m.get(s.charAt(i)) == 1) return i;
        return -1;
    }

    static boolean isAnagram(String a, String b) {
        if (a.length() != b.length()) return false;
        int[] c = new int[26];
        for (int i = 0; i < a.length(); i++) { c[a.charAt(i) - 'a']++; c[b.charAt(i) - 'a']--; }
        for (int x : c) if (x != 0) return false;
        return true;
    }

    public static void main(String[] args) {
        System.out.println("Q1 twoSum         = " + Arrays.toString(twoSum(new int[]{2,7,11,15}, 9)));
        System.out.println("Q2 containsDup    = " + containsDuplicate(new int[]{1,2,3,1}));
        System.out.println("Q3 intersection   = " + Arrays.toString(intersection(new int[]{1,2,2,1}, new int[]{2,2})));
        System.out.println("Q4 firstUniq      = " + firstUniqChar("leetcode"));
        System.out.println("Q5 anagram        = " + isAnagram("anagram", "nagaram"));
    }
}
