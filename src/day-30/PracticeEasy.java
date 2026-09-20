import java.util.*;

/**
 * Day 30 — Easy capstone solutions.
 * Two Sum / Valid Parentheses / Best Time to Buy and Sell Stock.
 */
public class PracticeEasy {

    // Two sum (unsorted) using HashMap complement lookup
    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> seen = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int need = target - nums[i];
            if (seen.containsKey(need)) return new int[]{seen.get(need), i};
            seen.put(nums[i], i);
        }
        return new int[]{-1, -1};
    }

    // Valid Parentheses
    public static boolean isValid(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            if (c == '(' || c == '{' || c == '[') stack.push(c);
            else {
                if (stack.isEmpty()) return false;
                char o = stack.pop();
                if (c == ')' && o != '(') return false;
                if (c == '}' && o != '{') return false;
                if (c == ']' && o != '[') return false;
            }
        }
        return stack.isEmpty();
    }

    // Best Time to Buy and Sell Stock
    public static int maxProfit(int[] prices) {
        int min = Integer.MAX_VALUE, profit = 0;
        for (int p : prices) {
            if (p < min) min = p;
            else if (p - min > profit) profit = p - min;
        }
        return profit;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(twoSum(new int[]{2,7,11,15}, 9)));
        System.out.println(isValid("()[]{}") + " / " + isValid("([)]"));
        System.out.println(maxProfit(new int[]{7,1,5,3,6,4}));
    }
}
