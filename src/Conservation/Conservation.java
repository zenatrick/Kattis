import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.stream.Stream;

class Conservation {
    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

        int numCases = Integer.parseInt(in.readLine());

        for (int c = 0; c < numCases; c++) {
            String[] graphInfo = in.readLine().split("\\s");
            int nodes = Integer.parseInt(graphInfo[0]);
            int edges = Integer.parseInt(graphInfo[1]);
            int[] nodeType = Stream.of(in.readLine().split("\\s")).mapToInt(Integer::parseInt).toArray();

            /* Initialise and build the graph for topological sorting. */
            Graph graph = new Graph(nodes, edges, nodeType);
            for (int i = 0; i < edges; i++) {
                String[] edgeInfo = in.readLine().split("\\s");
                int from = Integer.parseInt(edgeInfo[0]) - 1;
                int to = Integer.parseInt(edgeInfo[1]) - 1;
                graph.addEdge(from, to);
            }

            /* Compute the minimum number of transfer required. */
            out.println(graph.computeMinTransfers());
        }
        in.close();
        out.close();
    }
}