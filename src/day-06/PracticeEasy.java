/**
 * Day 6 — Easy practice.
 *
 * Compile: javac src/day-06/PracticeEasy.java
 * Run    : java -cp src/day-06 PracticeEasy
 */
public class PracticeEasy {

    static String reverse(String s) { return new StringBuilder(s).reverse().toString(); }

    static boolean isPalindrome(String s) {
        int lo = 0, hi = s.length() - 1;
        while (lo < hi) if (s.charAt(lo++) != s.charAt(hi--)) return false;
        return true;
    }

    static int countVowels(String s) {
        int c = 0;
        for (int i = 0; i < s.length(); i++) {
            char ch = Character.toLowerCase(s.charAt(i));
            if ("aeiou".indexOf(ch) >= 0) c++;
        }
        return c;
    }

    static String firstNonRepeat(String s) {
        int[] f = new int[256];
        for (int i = 0; i < s.length(); i++) f[s.charAt(i)]++;
        for (int i = 0; i < s.length(); i++)
            if (f[s.charAt(i)] == 1) return String.valueOf(s.charAt(i));
        return "";
    }

    public static void main(String[] args) {
        System.out.println("Q1 reverse(hello)        = " + reverse("hello"));
        System.out.println("Q2 palindrome(racecar)   = " + isPalindrome("racecar"));
        System.out.println("Q3 vowels(hello)         = " + countVowels("hello"));
        System.out.println("Q4 upper/lower(Hello)    = " + "Hello".toUpperCase() + " / " + "Hello".toLowerCase());
        System.out.println("Q5 firstNonRep(aabbcdd)  = " + firstNonRepeat("aabbcdd"));
    }
}
