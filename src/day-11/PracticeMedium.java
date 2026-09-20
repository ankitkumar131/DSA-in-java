/**
 * Day 11 — Medium practice.
 *
 * Compile: javac src/day-11/PracticeMedium.java
 * Run    : java -cp src/day-11 PracticeMedium
 */
import java.util.*;

public class PracticeMedium {

    static int[][] mergeIntervals(int[][] iv) {
        Arrays.sort(iv, (a, b) -> Integer.compare(a[0], b[0]));
        List<int[]> out = new ArrayList<>();
        for (int[] x : iv) {
            if (out.isEmpty() || out.get(out.size() - 1)[1] < x[0]) {
                out.add(x);
            } else {
                out.get(out.size() - 1)[1] = Math.max(out.get(out.size() - 1)[1], x[1]);
            }
        }
        return out.toArray(new int[0][]);
    }

    static void sortColors(int[] a) {
        int[] count = new int[3];
        for (int x : a) count[x]++;
        int idx = 0;
        for (int c = 0; c < 3; c++) while (count[c]-- > 0) a[idx++] = c;
    }

    static int[] sortByParity(int[] a) {
        int lo = 0, hi = a.length - 1;
        while (lo < hi) {
            if (a[lo] % 2 == 0) lo++;
            else if (a[hi] % 2 == 1) hi--;
            else { int t = a[lo]; a[lo] = a[hi]; a[hi] = t; lo++; hi--; }
        }
        return a;
    }

    static int[] topKFrequent(int[] a, int k) {
        Map<Integer, Integer> m = new HashMap<>();
        for (int x : a) m.merge(x, 1, Integer::sum);
        List<Integer>[] bucket = new List[a.length + 1];
        for (var e : m.entrySet()) {
            int freq = e.getValue();
            if (bucket[freq] == null) bucket[freq] = new ArrayList<>();
            bucket[freq].add(e.getKey());
        }
        int[] out = new int[k];
        int idx = 0;
        for (int f = a.length; f >= 0 && idx < k; f--)
            if (bucket[f] != null) for (int x : bucket[f]) out[idx++] = x;
        return Arrays.copyOf(out, idx);
    }

    static int kthLargest(int[] a, int k) {
        // quickselect
        return quickselect(a, 0, a.length - 1, a.length - k);
    }
    static int quickselect(int[] a, int lo, int hi, int idx) {
        if (lo == hi) return a[lo];
        int p = partition(a, lo, hi);
        if (p == idx) return a[p];
        if (idx < p) return quickselect(a, lo, p - 1, idx);
        return quickselect(a, p + 1, hi, idx);
    }
    static int partition(int[] a, int lo, int hi) {
        int pivot = a[hi], i = lo - 1;
        for (int j = lo; j < hi; j++)
            if (a[j] <= pivot) { i++; int t = a[i]; a[i] = a[j]; a[j] = t; }
        int t = a[i + 1]; a[i + 1] = a[hi]; a[hi] = t;
        return i + 1;
    }

    public static void main(String[] args) {
        System.out.println("Q6 mergeIntervals = " + Arrays.deepToString(mergeIntervals(new int[][]{{1,3},{2,6},{8,10},{15,18}})));
        int[] colors = {2,0,2,1,1,0}; sortColors(colors);
        System.out.println("Q7 sortColors     = " + Arrays.toString(colors));
        System.out.println("Q8 sortParity     = " + Arrays.toString(sortByParity(new int[]{3,1,2,4})));
        System.out.println("Q9 topKFreq       = " + Arrays.toString(topKFrequent(new int[]{1,1,1,2,2,3}, 2)));
        System.out.println("Q10 kthLargest    = " + kthLargest(new int[]{3,2,1,5,6,4}, 2));
    }
}
