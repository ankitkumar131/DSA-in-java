/**
 * Day 22 — Easy practice.
 *
 * Compile: javac src/day-22/PracticeEasy.java
 * Run    : java -cp src/day-22 PracticeEasy
 */
import java.util.*;

public class PracticeEasy {

    // Q1: assign cookies
    static int assignCookies(int[] greed, int[] cookies) {
        Arrays.sort(greed); Arrays.sort(cookies);
        int i = 0, j = 0, count = 0;
        while (i < greed.length && j < cookies.length) {
            if (cookies[j] >= greed[i]) { count++; i++; j++; } else j++;
        }
        return count;
    }

    // Q2: lemonade change
    static boolean lemonade(int[] bills) {
        int five = 0, ten = 0;
        for (int b : bills) {
            if (b == 5) five++;
            else if (b == 10) { if (five == 0) return false; five--; ten++; }
            else { if (ten > 0 && five > 0) { ten--; five--; } else if (five >= 3) five -= 3; else return false; }
        }
        return true;
    }

    // Q3: max profit (multiple)
    static int maxProfit(int[] p) {
        int profit = 0;
        for (int i = 1; i < p.length; i++) if (p[i] > p[i - 1]) profit += p[i] - p[i - 1];
        return profit;
    }

    // Q4: can place flowers
    static boolean canPlaceFlowers(int[] bed, int n) {
        int i = 0;
        while (i < bed.length) {
            if (bed[i] == 0 && (i == 0 || bed[i - 1] == 0) && (i == bed.length - 1 || bed[i + 1] == 0)) {
                bed[i] = 1; n--;
                i += 2;
            } else i++;
        }
        return n <= 0;
    }

    // Q5: majority element (Boyer-Moore)
    static int majority(int[] a) {
        int cand = 0, count = 0;
        for (int x : a) {
            if (count == 0) cand = x;
            count += (x == cand) ? 1 : -1;
        }
        return cand;
    }

    public static void main(String[] args) {
        System.out.println("Q1 cookies        = " + assignCookies(new int[]{1,2,3}, new int[]{1,1}));
        System.out.println("Q2 lemonade       = " + lemonade(new int[]{5,5,5,10,20}));
        System.out.println("Q3 maxProfit      = " + maxProfit(new int[]{7,1,5,3,6,4}));
        int[] bed = {1,0,0,0,1};
        System.out.println("Q4 canPlace(1)    = " + canPlaceFlowers(bed, 1));
        System.out.println("Q5 majority       = " + majority(new int[]{2,2,1,1,1,2,2}));
    }
}
