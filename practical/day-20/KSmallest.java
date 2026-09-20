/** Day 20 tiny project: K smallest using max-heap. */
import java.util.*;
public class KSmallest {
    public static void main(String[] args) {
        int[] stream = {4, 1, 7, 3, 9, 2, 8, 5};
        int k = 3;
        PriorityQueue<Integer> max = new PriorityQueue<>(Comparator.reverseOrder());
        for (int x : stream) {
            max.offer(x);
            if (max.size() > k) max.poll();
        }
        List<Integer> out = new ArrayList<>(max);
        Collections.sort(out);
        System.out.println("k=" + k + " smallest = " + out);
    }
}
