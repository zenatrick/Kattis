import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;

class TenKindsOfPeople {
    static final int[] HOR = { -1, 0, 1, 0 };
    static final int[] VER = { 0, 1, 0, -1 };

    /* Counter to keep track of the different connected components. */
    static int counter = 1;

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

        String[] size = in.readLine().split("\\s");
        int rows = Integer.parseInt(size[0]);
        int cols = Integer.parseInt(size[1]);
        String[] grid = new String[rows];
        for (int i = 0; i < rows; i++) {
            grid[i] = in.readLine();
        }
        int[][] visited = new int[rows][cols];

        int numQueries = Integer.parseInt(in.readLine());

        /*
         * For each query, call bfs() to determine if the 2 nodes are within the same
         * connected components.
         */
        for (int i = 0; i < numQueries; i++) {
            String[] query = in.readLine().split("\\s");
            int x1 = Integer.parseInt(query[0]) - 1;
            int y1 = Integer.parseInt(query[1]) - 1;
            int x2 = Integer.parseInt(query[2]) - 1;
            int y2 = Integer.parseInt(query[3]) - 1;

            if (bfs(grid, visited, x1, y1, x2, y2)) {
                out.println(grid[x1].charAt(y1) == '0' ? "binary" : "decimal");
            } else {
                out.println("neither");
            }
        }
        in.close();
        out.close();
    }

    public static boolean bfs(String[] grid, int[][] visited, int x1, int y1, int x2, int y2) {
        /*
         * Return false when the node differ in the original given grid. (one is binary
         * and the other is decimal)
         */
        if (grid[x1].charAt(y1) != grid[x2].charAt(y2)) {
            return false;
        }

        /*
         * Return true when both nodes are visited (node != '0') and they in the same
         * connected components.
         */
        if (visited[x1][y1] != 0 && visited[x1][y1] == visited[x2][y2]) {
            return true;
        }

        int rows = visited.length;
        int cols = visited[0].length;

        /* Initialise a queue for BFS */
        Queue<int[]> queue = new LinkedList<>();

        /* Set source node to counter value (visited) and offer it to the BFS queue. */
        visited[x1][y1] = counter;
        queue.offer(new int[] { x1, y1 });

        /* Keep track of the type (binary or decimal) for BFS traversal restriction */
        char type = grid[x1].charAt(y1);

        /*
         * Use a boolean variable to keep track if target node is found as the BFS
         * algorithm needs to continue till the end to map out the whole connected
         * component for futuer queries.
         */
        boolean found = false;

        while (!queue.isEmpty()) {
            int[] point = queue.poll();
            int x = point[0];
            int y = point[1];

            /* Set found to true when target node is being processed. */
            if (x == x2 && y == y2) {
                found = true;
            }

            for (int i = 0; i < 4; i++) {
                int adjX = x + HOR[i];
                int adjY = y + VER[i];

                /*
                 * Add nodes to queue if neighbour nodes are of the same type, within
                 * restriction and unvisited. Set these nodes to counter value (visited).
                 */
                if (adjX >= 0 && adjX < rows && adjY >= 0 && adjY < cols && visited[adjX][adjY] == 0
                        && grid[adjX].charAt(adjY) == type) {
                    visited[adjX][adjY] = counter;
                    queue.offer(new int[] { adjX, adjY });
                }
            }
        }

        /*
         * As the current connected component has been mapped out, increment the counter
         * for the next bfs call.
         */
        counter++;

        return found;
    }
}