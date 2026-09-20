/** Day 9 tiny project: prefix-sum + range query. */
import java.util.*;
public class RangeSum {
    public static void main(String[] args) {
        int[] a = {3, 1, 4, 1, 5, 9, 2, 6};
        int[] pre = new int[a.length + 1];
        for (int i = 0; i < a.length; i++) pre[i + 1] = pre[i] + a[i];
        int[][] qs = {{1, 4}, {0, 7}, {3, 6}};
        for (int[] q : qs) System.out.println("sum(" + q[0] + "," + q[1] + ") = " + (pre[q[1] + 1] - pre[q[0]]));
    }
}
