/** Day 25 tiny project: count islands in a grid via DFS. */
public class Islands {
    static int count(char[][] g) {
        int n = g.length, m = g[0].length, c = 0;
        for (int i = 0; i < n; i++) for (int j = 0; j < m; j++)
            if (g[i][j] == '1') { dfs(g, i, j); c++; }
        return c;
    }
    static void dfs(char[][] g, int i, int j) {
        if (i < 0 || j < 0 || i >= g.length || j >= g[0].length || g[i][j] != '1') return;
        g[i][j] = '0';
        dfs(g, i + 1, j); dfs(g, i - 1, j); dfs(g, i, j + 1); dfs(g, i, j - 1);
    }
    public static void main(String[] args) {
        char[][] g = {
            {'1','1','0','0','0'},
            {'1','1','0','0','0'},
            {'0','0','1','0','0'},
            {'0','0','0','1','1'}
        };
        System.out.println("islands = " + count(g));
    }
}
