import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

class MinSpanTree {
    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

        while (true) {
            String[] input = in.readLine().split("\\s");
            int nodes = Integer.parseInt(input[0]);
            int edges = Integer.parseInt(input[1]);

            /* Check for terminating condition. */
            if (nodes == 0) {
                break;
            }

            /*
             * If the number of edges is less than number of nodes - 1, read all the edges
             * but output "Impossible". Then, move on to the next case.
             */
            if (edges < nodes - 1) {
                for (int i = 0; i < edges; i++) {
                    in.readLine();
                }
                out.println("Impossible");
                continue;
            }

            /* Read and store the list of edges in an ArrayList. */
            ArrayList<int[]> edgeList = new ArrayList<>(edges);
            for (int i = 0; i < edges; i++) {
                String[] edgeInfo = in.readLine().split("\\s");
                int node1 = Integer.parseInt(edgeInfo[0]);
                int node2 = Integer.parseInt(edgeInfo[1]);
                int weight = Integer.parseInt(edgeInfo[2]);
                edgeList.add(new int[] { Math.min(node1, node2), Math.max(node1, node2), weight });
            }

            /* Sort edgeList for Kruskal's algorithm. */
            edgeList.sort((x, y) -> x[2] - y[2]);

            /* Keep track of the cost of the MST. */
            int mstCost = 0;

            /* Initialise the union find data structure for Kruskal's algorithm. */
            UnionFind unionFind = new UnionFind(nodes);

            /* Initialise an ArrayList to keep track of the edges in the MST. */
            ArrayList<int[]> resultList = new ArrayList<>();

            /* Apply Kruskal's algorithm. */
            for (int i = 0; i < edges; i++) {
                int[] edge = edgeList.get(i);
                if (unionFind.union(edge[0], edge[1])) {
                    mstCost += edge[2];
                    resultList.add(edge);
                }
            }

            /*
             * If the number of edges is less than number of nodes - 1, read all the edges
             * but output "Impossible". Then, move on to the next case.
             */
            if (resultList.size() < nodes - 1) {
                out.println("Impossible");
                continue;
            }

            /* Sort and print resultList. */
            resultList.sort((x, y) -> x[0] == y[0] ? x[1] - y[1] : x[0] - y[0]);
            out.println(mstCost);
            for (int i = 0, n = resultList.size(); i < n; i++) {
                int[] edge = resultList.get(i);
                out.println(edge[0] + " " + edge[1]);
            }
        }

        in.close();
        out.close();
    }
}