/** Day 11 tiny project: merge sort with step counter. */
import java.util.*;
public class MergeSort {
    static long steps = 0;
    static void sort(int[] a) {
        if (a.length < 2) return;
        int m = a.length / 2;
        int[] l = Arrays.copyOfRange(a, 0, m);
        int[] r = Arrays.copyOfRange(a, m, a.length);
        sort(l); sort(r);
        merge(a, l, r);
    }
    static void merge(int[] a, int[] l, int[] r) {
        int i = 0, j = 0, k = 0;
        while (i < l.length && j < r.length) {
            steps++;
            a[k++] = l[i] <= r[j] ? l[i++] : r[j++];
        }
        while (i < l.length) a[k++] = l[i++];
        while (j < r.length) a[k++] = r[j++];
    }
    public static void main(String[] args) {
        int[] a = {5, 2, 8, 1, 9, 3, 7, 4};
        sort(a);
        System.out.println(Arrays.toString(a) + " steps=" + steps);
    }
}
