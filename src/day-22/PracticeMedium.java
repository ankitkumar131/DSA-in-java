/**
 * Day 22 — Medium practice.
 *
 * Compile: javac src/day-22/PracticeMedium.java
 * Run    : java -cp src/day-22 PracticeMedium
 */
import java.util.*;

public class PracticeMedium {

    // Q6: can jump
    static boolean canJump(int[] a) {
        int reach = 0;
        for (int i = 0; i < a.length; i++) {
            if (i > reach) return false;
            reach = Math.max(reach, i + a[i]);
        }
        return true;
    }

    // Q7: jump game II (BFS)
    static int jump(int[] a) {
        int jumps = 0, end = 0, farthest = 0;
        for (int i = 0; i < a.length - 1; i++) {
            farthest = Math.max(farthest, i + a[i]);
            if (i == end) { jumps++; end = farthest; }
        }
        return jumps;
    }

    // Q8: gas station
    static int gasStation(int[] gas, int[] cost) {
        int total = 0, tank = 0, start = 0;
        for (int i = 0; i < gas.length; i++) {
            int diff = gas[i] - cost[i]; total += diff; tank += diff;
            if (tank < 0) { start = i + 1; tank = 0; }
        }
        return total >= 0 ? start : -1;
    }

    // Q9: min platforms
    static int minPlatforms(int[] arr, int[] dep) {
        Arrays.sort(arr); Arrays.sort(dep);
        int plat = 1, max = 1, i = 1, j = 0;
        while (i < arr.length && j < dep.length) {
            if (arr[i] <= dep[j]) { plat++; i++; max = Math.max(max, plat); }
            else { plat--; j++; }
        }
        return max;
    }

    // Q10: partition labels
    static List<Integer> partitionLabels(String s) {
        int[] last = new int[26];
        for (int i = 0; i < s.length(); i++) last[s.charAt(i) - 'a'] = i;
        List<Integer> out = new ArrayList<>();
        int start = 0, end = 0;
        for (int i = 0; i < s.length(); i++) {
            end = Math.max(end, last[s.charAt(i) - 'a']);
            if (i == end) { out.add(end - start + 1); start = i + 1; }
        }
        return out;
    }

    public static void main(String[] args) {
        System.out.println("Q6  canJump     = " + canJump(new int[]{2,3,1,1,4}));
        System.out.println("Q7  jump        = " + jump(new int[]{2,3,1,1,4}));
        System.out.println("Q8  gasStation  = " + gasStation(new int[]{1,2,3,4,5}, new int[]{3,4,5,1,2}));
        System.out.println("Q9  platforms   = " + minPlatforms(new int[]{900,940,950,1100,1500,1800}, new int[]{910,1200,1120,1130,1900,2000}));
        System.out.println("Q10 partitions  = " + partitionLabels("ababcbacadefegdehijhklij"));
    }
}
