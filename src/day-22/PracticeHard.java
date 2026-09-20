/**
 * Day 22 — Hard practice.
 *
 * Compile: javac src/day-22/PracticeHard.java
 * Run    : java -cp src/day-22 PracticeHard
 */
import java.util.*;

public class PracticeHard {

    // Q11: candy (two passes)
    static int candy(int[] r) {
        int n = r.length;
        int[] c = new int[n];
        Arrays.fill(c, 1);
        for (int i = 1; i < n; i++) if (r[i] > r[i - 1]) c[i] = c[i - 1] + 1;
        for (int i = n - 2; i >= 0; i--) if (r[i] > r[i + 1]) c[i] = Math.max(c[i], c[i + 1] + 1);
        long total = 0; for (int x : c) total += x; return (int) total;
    }

    // Q12: insert interval
    static int[][] insert(int[][] iv, int[] nw) {
        List<int[]> out = new ArrayList<>();
        int i = 0;
        while (i < iv.length && iv[i][1] < nw[0]) out.add(iv[i++]);
        while (i < iv.length && iv[i][0] <= nw[1]) {
            nw[0] = Math.min(nw[0], iv[i][0]);
            nw[1] = Math.max(nw[1], iv[i][1]);
            i++;
        }
        out.add(nw);
        while (i < iv.length) out.add(iv[i++]);
        return out.toArray(new int[0][]);
    }

    // Q13: merge intervals
    static int[][] merge(int[][] iv) {
        Arrays.sort(iv, (a, b) -> Integer.compare(a[0], b[0]));
        List<int[]> out = new ArrayList<>();
        for (int[] x : iv) {
            if (out.isEmpty() || out.get(out.size() - 1)[1] < x[0]) out.add(x);
            else out.get(out.size() - 1)[1] = Math.max(out.get(out.size() - 1)[1], x[1]);
        }
        return out.toArray(new int[0][]);
    }

    // Q14: min arrows for balloons
    static int minArrows(int[][] points) {
        if (points.length == 0) return 0;
        Arrays.sort(points, (a, b) -> Integer.compare(a[1], b[1]));
        int arrows = 1, end = points[0][1];
        for (int i = 1; i < points.length; i++)
            if (points[i][0] > end) { arrows++; end = points[i][1]; }
        return arrows;
    }

    // Q15: task scheduler
    static int taskScheduler(char[] tasks, int n) {
        int[] cnt = new int[26];
        for (char c : tasks) cnt[c - 'A']++;
        Arrays.sort(cnt);
        int max = cnt[25], idle = (max - 1) * n;
        for (int i = 24; i >= 0; i--) idle -= Math.min(max - 1, cnt[i]);
        return tasks.length + Math.max(0, idle);
    }

    public static void main(String[] args) {
        System.out.println("Q11 candy       = " + candy(new int[]{1,0,2}));
        System.out.println("Q12 insert      = " + Arrays.deepToString(insert(new int[][]{{1,3},{6,9}}, new int[]{2,5})));
        System.out.println("Q13 merge       = " + Arrays.deepToString(merge(new int[][]{{1,3},{2,6},{8,10},{15,18}})));
        System.out.println("Q14 arrows      = " + minArrows(new int[][]{{10,16},{2,8},{1,6},{7,12}}));
        System.out.println("Q15 taskSched   = " + taskScheduler(new char[]{'A','A','A','B','B','B'}, 2));
    }
}
