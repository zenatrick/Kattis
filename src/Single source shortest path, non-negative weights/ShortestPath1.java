import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

class ShortestPath1 {
    public static void main(String[] args) throws Exception {
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            String[] data = in.readLine().split("\\s");
            int nodes = Integer.parseInt(data[0]);
            int edges = Integer.parseInt(data[1]);
            int queries = Integer.parseInt(data[2]);
            int source = Integer.parseInt(data[3]);

            // Check for termination condition
            if (nodes == 0) {
                break;
            }

            // Initialise SSSPGraph
            SSSPGraph graph = SSSPGraph.initGraphForSSSPDist(nodes, edges);

            // Read and add edges to graph
            for (int i = 0; i < edges; i++) {
                String[] edgeData = in.readLine().split("\\s");
                int u = Integer.parseInt(edgeData[0]);
                int v = Integer.parseInt(edgeData[1]);
                int w = Integer.parseInt(edgeData[2]);
                graph.addEdge(u, v, w);
            }

            // Perform Dijkstra's algorithm
            graph.dijkstraWithoutPath(source);

            // Print result of queries
            for (int i = 0; i < queries; i++) {
                int qnode = Integer.parseInt(in.readLine());
                double distance = graph.getDistOf(qnode);
                out.println(distance == SSSPGraph.INF ? "Impossible" : (int) distance);
            }
            out.println();
        }

        in.close();
        out.close();
    }
}