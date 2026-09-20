/** Day 8 tiny project: sliding-window max-sum on request rates. */
import java.util.*;
public class LogAnalyser {
    public static void main(String[] args) {
        int[] rates = {12, 5, 8, 22, 17, 30, 14, 7, 9, 11};
        int k = 3;
        int sum = 0, best = 0;
        for (int i = 0; i < k; i++) sum += rates[i];
        best = sum;
        for (int i = k; i < rates.length; i++) {
            sum += rates[i] - rates[i - k];
            best = Math.max(best, sum);
        }
        System.out.println("max sum window of " + k + " = " + best);
    }
}
