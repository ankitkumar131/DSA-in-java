/** Day 12 tiny project: count unique paths in an n×m grid. */
public class GridPaths {
    static long paths(int n, int m) {
        if (n == 1 || m == 1) return 1;
        return paths(n - 1, m) + paths(n, m - 1);
    }
    public static void main(String[] args) {
        System.out.println("3x3 paths = " + paths(3, 3));
        System.out.println("5x5 paths = " + paths(5, 5));
    }
}
