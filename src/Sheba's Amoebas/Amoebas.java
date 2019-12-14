import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStreamReader;

class Amoebas {
    static final int[] HOR = { -1, -1, -1, 0, 0, 1, 1, 1 };
    static final int[] VER = { -1, 0, 1, -1, 1, -1, 0, 1 };

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

        String[] gridSize = in.readLine().split("\\s");
        int rows = Integer.parseInt(gridSize[0]);
        int cols = Integer.parseInt(gridSize[1]);

        String[] grid = new String[rows];
        boolean[][] visited = new boolean[rows][cols];

        for (int i = 0; i < rows; i++) {
            grid[i] = in.readLine();
        }

        // Initialise count as 0.
        // For each unvisited grid that is '#', perform DFS and increment count.
        int count = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!visited[i][j] && grid[i].charAt(j) == '#') {
                    count++;
                    dfs(grid, visited, i, j);
                }
            }
        }
        out.println(count);

        in.close();
        out.close();
    }

    static void dfs(String[] grid, boolean[][] visited, int i, int j) {
        if (visited[i][j]) {
            return;
        }
        visited[i][j] = true;
        int rows = grid.length;
        int cols = grid[0].length();
        for (int k = 0; k < 8; k++) {
            int adjI = i + HOR[k];
            int adjJ = j + VER[k];
            if (adjI >= 0 && adjI < rows && adjJ >= 0 && adjJ < cols && grid[adjI].charAt(adjJ) == '#') {
                dfs(grid, visited, adjI, adjJ);
            }
        }
    }
}