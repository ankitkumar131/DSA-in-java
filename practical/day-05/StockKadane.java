/** Day 5 tiny project: Kadane applied to daily stock-price changes. */
public class StockKadane {
    static int bestPeriod(int[] prices) {
        int maxEnd = prices[1] - prices[0];
        int best = maxEnd;
        for (int i = 1; i < prices.length - 1; i++) {
            int diff = prices[i + 1] - prices[i];
            maxEnd = Math.max(diff, maxEnd + diff);
            best = Math.max(best, maxEnd);
        }
        return best;
    }
    public static void main(String[] args) {
        int[] p = {7, 1, 5, 3, 6, 4};
        System.out.println("best sub-period gain = " + bestPeriod(p));
    }
}
