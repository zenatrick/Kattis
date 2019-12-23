import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

class GetShorty {
    public static void main(String[] args) throws IOException {
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            String[] data = in.readLine().split("\\s");
            int nodes = Integer.parseInt(data[0]);
            int edges = Integer.parseInt(data[1]);

            /* Check for termination condition. */
            if (nodes == 0) {
                break;
            }

            /* Build graph from input. The weight of each edge will be -log(f). */
            SSSPGraph graph = SSSPGraph.initGraphForSSSPDist(nodes, edges);
            for (int i = 0; i < edges; i++) {
                String[] edgeData = in.readLine().split("\\s");
                int p = Integer.parseInt(edgeData[0]);
                int q = Integer.parseInt(edgeData[1]);
                double w = -Math.log(Double.parseDouble(edgeData[2]));
                graph.addEdge(p, q, w);
                graph.addEdge(q, p, w);
            }

            /* Perform Dijkstra's algorithm. */
            graph.dijkstraWithoutPath(0);

            /* Print result. */
            out.printf("%.4f\n", Math.exp(-graph.getDistOf(nodes - 1)));
        }

        in.close();
        out.close();
    }
}