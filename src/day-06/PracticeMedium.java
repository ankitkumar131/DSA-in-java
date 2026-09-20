/**
 * Day 6 — Medium practice.
 *
 * Compile: javac src/day-06/PracticeMedium.java
 * Run    : java -cp src/day-06 PracticeMedium
 */
import java.util.*;

public class PracticeMedium {

    static boolean isAnagram(String a, String b) {
        if (a.length() != b.length()) return false;
        int[] f = new int[26];
        for (int i = 0; i < a.length(); i++) { f[a.charAt(i) - 'a']++; f[b.charAt(i) - 'a']--; }
        for (int x : f) if (x != 0) return false;
        return true;
    }

    static int longestUniqueSubstr(String s) {
        int[] seen = new int[256];
        Arrays.fill(seen, -1);
        int best = 0, start = 0;
        for (int i = 0; i < s.length(); i++) {
            if (seen[s.charAt(i)] >= start) start = seen[s.charAt(i)] + 1;
            seen[s.charAt(i)] = i;
            best = Math.max(best, i - start + 1);
        }
        return best;
    }

    static String compress(String s) {
        StringBuilder sb = new StringBuilder();
        int count = 1;
        for (int i = 1; i <= s.length(); i++) {
            if (i < s.length() && s.charAt(i) == s.charAt(i - 1)) { count++; }
            else { sb.append(s.charAt(i - 1)).append(count); count = 1; }
        }
        return sb.length() < s.length() ? sb.toString() : s;
    }

    static String reverseWords(String s) {
        String[] w = s.trim().split("\\s+");
        Collections.reverse(Arrays.asList(w));
        return String.join(" ", w);
    }

    static List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> m = new HashMap<>();
        for (String s : strs) {
            char[] c = s.toCharArray();
            Arrays.sort(c);
            String key = new String(c);
            m.computeIfAbsent(key, k -> new ArrayList<>()).add(s);
        }
        return new ArrayList<>(m.values());
    }

    public static void main(String[] args) {
        System.out.println("Q6  anagram(anagram,nagaram) = " + isAnagram("anagram", "nagaram"));
        System.out.println("Q7  longestUnique(abcabcbb)  = " + longestUniqueSubstr("abcabcbb"));
        System.out.println("Q8  compress(aabcccccaaa)    = " + compress("aabcccccaaa"));
        System.out.println("Q9  reverseWords             = " + reverseWords("the sky is blue"));
        System.out.println("Q10 groupAnagrams            = " + groupAnagrams(new String[]{"eat","tea","tan","ate","nat","bat"}));
    }
}
