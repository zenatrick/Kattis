import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.stream.Stream;

class BlockCrusher {
    static final int[] HOR = { -1, -1, -1, 0, 0, 1, 1, 1 };
    static final int[] VER = { -1, 0, 1, -1, 1, -1, 0, 1 };

    public static void main(String[] args) throws IOException {
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            String[] blockInfo = in.readLine().split("\\s");
            int rows = Integer.parseInt(blockInfo[0]);
            int cols = Integer.parseInt(blockInfo[1]);

            /* Check for end condition */
            if (rows == 0) {
                break;
            }

            /* Read the input block structure and store it in a 2D String array. */
            int[][] block = new int[rows][cols];
            for (int i = 0; i < rows; i++) {
                block[i] = Stream.of(in.readLine().split("")).mapToInt(Integer::parseInt).toArray();
            }

            /*
             * Initialise graph with (rows * cols + 2) nodes. The first node is a super node
             * that is pointing to all first row nodes with a weight of 0. The last node is
             * a super node that all last row nodes point to. Number of edges is set to 0 as
             * it is unnecessary in this algorithm.
             */
            SSSPGraph graph = SSSPGraph.initGraphForSSSP(rows * cols + 2, 0);

            /* Add all edges for firstSuperNode to all first row nodes. */
            final int firstSuperNode = 0;
            for (int i = 0; i < cols; i++) {
                int firstRowNode = i + 1;
                graph.addEdge(firstSuperNode, firstRowNode, 0);
            }

            /*
             * Add all normal nodes. Each node points to its surrounding 8 neighbours,
             * except for those on the edges.
             */
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    int node = i * cols + j + 1; // Formula for node number
                    for (int k = 0; k < 8; k++) {
                        int adjI = i + HOR[k];
                        int adjJ = j + VER[k];

                        /* Check for valid neighbours that is within boundary. */
                        if (adjI >= 0 && adjI < rows && adjJ >= 0 && adjJ < cols) {
                            int adjNode = adjI * cols + adjJ + 1;
                            graph.addEdge(node, adjNode, block[i][j]);
                        }
                    }
                }
            }

            /* Add all edges from last row nodes to lastSuperNode. */
            final int lastSuperNode = rows * cols + 1;
            for (int i = 0; i < cols; i++) {
                int lastRowNode = (rows - 1) * cols + i + 1;
                graph.addEdge(lastRowNode, lastSuperNode, block[rows - 1][i]);
            }

            /* Perform Dijkstra's Algorithm to find the shortest path. */
            graph.dijkstraWithPath(firstSuperNode);

            /* Gets the shortest path representation. */
            int[] path = graph.getPathArray();

            /* Set all nodes in the shortest path to a value of 0. */
            int currNode = path[lastSuperNode];
            while (currNode != 0) {
                int i = (currNode - 1) / cols;
                int j = (currNode - 1) % cols;
                block[i][j] = 0;
                currNode = path[currNode];
            }

            /* Print out the result */
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    int weight = block[i][j];
                    out.print(weight == 0 ? " " : weight);
                }
                out.println();
            }
            out.println();
        }

        in.close();
        out.close();
    }
}