/**
 * Day 11 — Sorting demos.
 *
 * Compile: javac src/day-11/SortingDemos.java
 * Run    : java -cp src/day-11 SortingDemos
 */
import java.util.Arrays;

public class SortingDemos {

    static void bubble(int[] a) {
        int n = a.length;
        for (int i = 0; i < n - 1; i++)
            for (int j = 0; j < n - i - 1; j++)
                if (a[j] > a[j + 1]) { int t = a[j]; a[j] = a[j + 1]; a[j + 1] = t; }
    }

    static void selection(int[] a) {
        int n = a.length;
        for (int i = 0; i < n - 1; i++) {
            int min = i;
            for (int j = i + 1; j < n; j++) if (a[j] < a[min]) min = j;
            int t = a[i]; a[i] = a[min]; a[min] = t;
        }
    }

    static void insertion(int[] a) {
        for (int i = 1; i < a.length; i++) {
            int key = a[i], j = i - 1;
            while (j >= 0 && a[j] > key) { a[j + 1] = a[j]; j--; }
            a[j + 1] = key;
        }
    }

    static void mergeSort(int[] a, int lo, int hi) {
        if (lo >= hi) return;
        int mid = lo + (hi - lo) / 2;
        mergeSort(a, lo, mid);
        mergeSort(a, mid + 1, hi);
        merge(a, lo, mid, hi);
    }
    static void merge(int[] a, int lo, int mid, int hi) {
        int[] left  = Arrays.copyOfRange(a, lo, mid + 1);
        int[] right = Arrays.copyOfRange(a, mid + 1, hi + 1);
        int i = 0, j = 0, k = lo;
        while (i < left.length && j < right.length)
            a[k++] = (left[i] <= right[j]) ? left[i++] : right[j++];
        while (i < left.length)  a[k++] = left[i++];
        while (j < right.length) a[k++] = right[j++];
    }

    static void quickSort(int[] a, int lo, int hi) {
        if (lo < hi) {
            int p = partition(a, lo, hi);
            quickSort(a, lo, p - 1);
            quickSort(a, p + 1, hi);
        }
    }
    static int partition(int[] a, int lo, int hi) {
        int pivot = a[hi], i = lo - 1;
        for (int j = lo; j < hi; j++)
            if (a[j] <= pivot) { i++; int t = a[i]; a[i] = a[j]; a[j] = t; }
        int t = a[i + 1]; a[i + 1] = a[hi]; a[hi] = t;
        return i + 1;
    }

    static void countingSort(int[] a, int k) {
        int[] count = new int[k + 1];
        for (int x : a) count[x]++;
        for (int i = 1; i <= k; i++) count[i] += count[i - 1];
        int[] out = new int[a.length];
        for (int i = a.length - 1; i >= 0; i--) out[--count[a[i]]] = a[i];
        System.arraycopy(out, 0, a, 0, a.length);
    }

    static void radixSort(int[] a) {
        int max = Arrays.stream(a).max().orElse(0);
        for (int exp = 1; max / exp > 0; exp *= 10) countingByDigit(a, exp);
    }
    static void countingByDigit(int[] a, int exp) {
        int n = a.length, out[] = new int[n], count[] = new int[10];
        for (int x : a) count[(x / exp) % 10]++;
        for (int i = 1; i < 10; i++) count[i] += count[i - 1];
        for (int i = n - 1; i >= 0; i--) { int d = (a[i] / exp) % 10; out[--count[d]] = a[i]; }
        System.arraycopy(out, 0, a, 0, n);
    }

    public static void main(String[] args) {
        int[] a = {5, 2, 8, 1, 9, 3, 7, 4, 6};
        int[] b = a.clone(); bubble(b); System.out.println("bubble     " + Arrays.toString(b));
        int[] s = a.clone(); selection(s); System.out.println("selection  " + Arrays.toString(s));
        int[] i = a.clone(); insertion(i); System.out.println("insertion  " + Arrays.toString(i));
        int[] m = a.clone(); mergeSort(m, 0, m.length - 1); System.out.println("merge      " + Arrays.toString(m));
        int[] q = a.clone(); quickSort(q, 0, q.length - 1); System.out.println("quick      " + Arrays.toString(q));
        int[] c = {1,4,1,2,7,5,2}; countingSort(c, 7); System.out.println("counting   " + Arrays.toString(c));
        int[] r = {170,45,75,90,802,24,2,66}; radixSort(r); System.out.println("radix      " + Arrays.toString(r));
    }
}
