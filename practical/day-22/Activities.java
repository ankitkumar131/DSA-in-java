/** Day 22 tiny project: max non-overlapping meetings (greedy by end). */
import java.util.*;
public class Activities {
    public static void main(String[] args) {
        int[][] m = {{1,3},{2,4},{3,5},{6,8},{7,9},{9,11}};
        Arrays.sort(m, (a,b) -> Integer.compare(a[1], b[1]));
        int count = 0, end = Integer.MIN_VALUE;
        for (int[] x : m) { if (x[0] >= end) { count++; end = x[1]; } }
        System.out.println("max meetings = " + count);
    }
}
