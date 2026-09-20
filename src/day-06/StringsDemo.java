/**
 * Day 6 — Strings demo.
 *
 * Compile: javac src/day-06/StringsDemo.java
 * Run    : java -cp src/day-06 StringsDemo
 */
import java.util.*;

public class StringsDemo {

    static boolean isPalindrome(String s) {
        int lo = 0, hi = s.length() - 1;
        while (lo < hi) {
            if (s.charAt(lo) != s.charAt(hi)) return false;
            lo++; hi--;
        }
        return true;
    }

    static boolean isAnagram(String a, String b) {
        if (a.length() != b.length()) return false;
        int[] f = new int[26];
        for (int i = 0; i < a.length(); i++) { f[a.charAt(i) - 'a']++; f[b.charAt(i) - 'a']--; }
        for (int x : f) if (x != 0) return false;
        return true;
    }

    static int[] frequency(String s) {
        int[] f = new int[256];
        for (int i = 0; i < s.length(); i++) f[s.charAt(i)]++;
        return f;
    }

    static String reverseBuilder(String s) {
        return new StringBuilder(s).reverse().toString();
    }

    static List<String> allSubstrings(String s) {
        List<String> out = new ArrayList<>();
        for (int i = 0; i < s.length(); i++)
            for (int j = i + 1; j <= s.length(); j++)
                out.add(s.substring(i, j));
        return out;
    }

    public static void main(String[] args) {
        // Immutability demo
        String s = "Hello";
        s.concat(" World");
        System.out.println("after concat (s unchanged): " + s);
        s = s.concat(" World");
        System.out.println("after reassign: " + s);

        // Pool
        String a = "Java", b = "Java", c = new String("Java");
        System.out.println("a == b : " + (a == b));
        System.out.println("a == c : " + (a == c));
        System.out.println("a.equals(c) : " + a.equals(c));

        // Palindrome, anagram, frequency
        System.out.println("palindrome(racecar): " + isPalindrome("racecar"));
        System.out.println("anagram(listen,silent): " + isAnagram("listen", "silent"));
        int[] freq = frequency("aabbc");
        System.out.print("freq(aabbc): ");
        for (int i = 0; i < freq.length; i++)
            if (freq[i] > 0) System.out.print((char) i + "=" + freq[i] + " ");
        System.out.println();

        System.out.println("reverse(hello): " + reverseBuilder("hello"));
        System.out.println("substrings(abc): " + allSubstrings("abc"));
    }
}
