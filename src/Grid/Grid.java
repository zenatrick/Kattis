import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.io.InputStreamReader;

class Grid {
    static final int[] HOR = { -1, 0, 1, 0 };
    static final int[] VER = { 0, -1, 0, 1 };

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

        /* Read the nubmer of rows and columns of the grid. */
        String[] gridInfo = in.readLine().split("\\s");
        int rows = Integer.parseInt(gridInfo[0]);
        int cols = Integer.parseInt(gridInfo[1]);

        /* Read and store input grid in a 2D Node array. */
        Node[][] grid = new Node[rows][cols];
        for (int i = 0; i < rows; i++) {
            String[] row = in.readLine().split("");
            for (int j = 0; j < cols; j++) {
                grid[i][j] = new Node(i, j, Integer.parseInt(row[j]));
            }
        }

        /*
         * Initialise a queue for BFS. Add the source node with the steps count to the
         * queue. Format: [i, j, numSteps]
         */
        LinkedList<int[]> queue = new LinkedList<>();
        queue.offer(new int[] { 0, 0, 0 });

        /*
         * Initalise the result as -1. If target node is reached, result will be updated
         * to the number of steps taken.
         */
        int result = -1;

        /* Initialise the isVisted array. */
        boolean[][] isVisited = new boolean[rows][cols];

        /* Perform BFS algorithm and keep track of the number of steps taken. */
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int i = current[0];
            int j = current[1];
            int numSteps = current[2];
            if (i == rows - 1 && j == cols - 1) {
                result = numSteps;
                break;
            }

            if (!isVisited[i][j]) {
                isVisited[i][j] = true;
                int scale = grid[i][j].value;
                if (scale > 0) {
                    for (int k = 0; k < 4; k++) {
                        int adjI = i + HOR[k] * scale;
                        int adjJ = j + VER[k] * scale;
                        if (adjI >= 0 && adjI < rows && adjJ >= 0 && adjJ < cols) {
                            queue.offer(new int[] { adjI, adjJ, numSteps + 1 });
                        }
                    }
                }
            }
        }

        out.println(result);

        in.close();
        out.close();
    }
}