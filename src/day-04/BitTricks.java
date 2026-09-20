/**
 * Day 4 — Bit manipulation tricks.
 *
 * Compile: javac src/day-04/BitTricks.java
 * Run    : java -cp src/day-04 BitTricks
 */
public class BitTricks {

    // Count set bits in n
    public static int countSetBits(int n) {
        int count = 0;
        while (n != 0) {
            n = n & (n - 1);   // strip the lowest set bit
            count++;
        }
        return count;
    }

    // True if n is a power of two
    public static boolean isPowerOfTwo(int n) {
        return n > 0 && (n & (n - 1)) == 0;
    }

    // Lowest set bit, e.g. 12 (1100) -> 4 (0100)
    public static int lowestSetBit(int n) {
        return n & -n;
    }

    // XOR-swap two array elements
    public static void swapXor(int[] a, int i, int j) {
        if (i == j) return;
        a[i] ^= a[j];
        a[j] ^= a[i];
        a[i] ^= a[j];
    }

    // Find the unique number in an array where all others appear twice
    public static int singleNumber(int[] a) {
        int xor = 0;
        for (int x : a) xor ^= x;
        return xor;
    }

    public static void main(String[] args) {
        System.out.println("setBits(11)      = " + countSetBits(11));           // 3
        System.out.println("isPow2(16)       = " + isPowerOfTwo(16));          // true
        System.out.println("lowestSetBit(12) = " + lowestSetBit(12));          // 4

        int[] arr = {4, 1, 2, 1, 2};
        System.out.println("singleNumber     = " + singleNumber(arr));        // 4

        int[] swap = {1, 2};
        swapXor(swap, 0, 1);
        System.out.println("after XOR swap   = " + swap[0] + "," + swap[1]); // 2,1
    }
}
