/**
 * Day 20 — Medium practice.
 *
 * Compile: javac src/day-20/PracticeMedium.java
 * Run    : java -cp src/day-20 PracticeMedium
 */
import java.util.*;

public class PracticeMedium {

    static int kthSmallest(int[] a, int k) {
        PriorityQueue<Integer> max = new PriorityQueue<>(Comparator.reverseOrder());
        for (int x : a) { max.offer(x); if (max.size() > k) max.poll(); }
        return max.peek();
    }

    static int[] sortAlmostSorted(int[] a, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        int idx = 0;
        for (int i = 0; i < a.length; i++) {
            pq.offer(a[i]);
            if (pq.size() > k) a[idx++] = pq.poll();
        }
        while (!pq.isEmpty()) a[idx++] = pq.poll();
        return a;
    }

    static int minMeetingRooms(int[][] iv) {
        Arrays.sort(iv, (x, y) -> Integer.compare(x[0], y[0]));
        PriorityQueue<Integer> ends = new PriorityQueue<>();
        for (int[] i : iv) {
            if (!ends.isEmpty() && ends.peek() <= i[0]) ends.poll();
            ends.offer(i[1]);
        }
        return ends.size();
    }

    static String reorganiseString(String s) {
        int[] cnt = new int[26];
        for (char c : s.toCharArray()) cnt[c - 'a']++;
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> b[1] - a[1]);
        for (int i = 0; i < 26; i++) if (cnt[i] > 0) pq.offer(new int[]{i, cnt[i]});
        StringBuilder sb = new StringBuilder();
        while (!pq.isEmpty()) {
            int[] first = pq.poll();
            if (sb.length() == 0 || sb.charAt(sb.length() - 1) != (char) ('a' + first[0])) {
                sb.append((char) ('a' + first[0]));
                first[1]--;
                if (first[1] > 0) pq.offer(first);
            } else {
                if (pq.isEmpty()) return "";
                int[] second = pq.poll();
                sb.append((char) ('a' + second[0]));
                second[1]--;
                if (second[1] > 0) pq.offer(second);
                pq.offer(first);
            }
        }
        return sb.toString();
    }

    static int nthUgly(int n) {
        Set<Long> seen = new HashSet<>();
        PriorityQueue<Long> pq = new PriorityQueue<>();
        pq.offer(1L); seen.add(1L);
        long[] factors = {2, 3, 5};
        long cur = 1;
        for (int i = 0; i < n; i++) {
            cur = pq.poll();
            for (long f : factors) {
                long nxt = cur * f;
                if (seen.add(nxt)) pq.offer(nxt);
            }
        }
        return (int) cur;
    }

    public static void main(String[] args) {
        System.out.println("Q6  kthSmallest = " + kthSmallest(new int[]{7,10,4,3,20,15}, 3));
        System.out.println("Q7  almostSort  = " + Arrays.toString(sortAlmostSorted(new int[]{6,5,3,2,8,10,9}, 2)));
        System.out.println("Q8  minRooms    = " + minMeetingRooms(new int[][]{{0,30},{5,10},{15,20}}));
        System.out.println("Q9  reorganise  = " + reorganiseString("aab"));
        System.out.println("Q10 nthUgly     = " + nthUgly(10));
    }
}
