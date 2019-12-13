import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.io.InputStreamReader;

class Terraces {
    static final int[] HOR = { -1, 0, 1, 0 };
    static final int[] VER = { 0, 1, 0, -1 };

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

        String[] gridSize = in.readLine().split("\\s");
        int cols = Integer.parseInt(gridSize[0]);
        int rows = Integer.parseInt(gridSize[1]);

        int[][] grid = new int[rows][cols];
        boolean[][] visited = new boolean[rows][cols];

        for (int i = 0; i < rows; i++) {
            String[] row = in.readLine().split("\\s");
            for (int j = 0; j < cols; j++) {
                grid[i][j] = Integer.parseInt(row[j]);
            }
        }

        // Perform BFS on every vertices. Visited vertices will return immediately.
        int count = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                count += bfs(grid, visited, new Vertex(i, j));
            }
        }
        out.println(count);

        in.close();
        out.close();
    }

    /**
     * BFS algorithm that returns the number of valid vertices for growing crops.
     * Valid vertices are connected vertices whose neighbours all have higher
     * heights.
     * 
     * @param grid    given grid of vertices
     * @param visited visited grid to keep track of visited vertices
     * @param start   source vertex for BFS
     * @return return number of valid vertex for growing crops
     */
    static int bfs(int[][] grid, boolean[][] visited, Vertex start) {
        if (visited[start.x][start.y]) {
            return 0;
        }

        int rows = grid.length;
        int cols = grid[0].length;
        int count = 0;
        boolean isValid = true;

        LinkedList<Vertex> queue = new LinkedList<>();
        queue.offer(start);

        while (!queue.isEmpty()) {
            Vertex current = queue.poll();
            if (visited[current.x][current.y]) {
                continue;
            }
            count++;
            visited[current.x][current.y] = true;
            for (int i = 0; i < 4; i++) {
                Vertex adjVertex = new Vertex(current.x + HOR[i], current.y + VER[i]);
                if (adjVertex.x >= 0 && adjVertex.x < rows && adjVertex.y >= 0 && adjVertex.y < cols) {
                    if (grid[adjVertex.x][adjVertex.y] == grid[current.x][current.y]
                            && !visited[adjVertex.x][adjVertex.y]) {
                        queue.offer(adjVertex);
                    } else if (grid[adjVertex.x][adjVertex.y] < grid[current.x][current.y]) {
                        isValid = false;
                    }
                }
            }
        }
        return isValid ? count : 0;
    }
}