import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

class DrivingRange {
    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

        String[] input = in.readLine().split("\\s");
        int nodes = Integer.parseInt(input[0]);
        int edges = Integer.parseInt(input[1]);

        /* Read the list of edges and store it in an ArrayList. */
        ArrayList<int[]> edgeList = new ArrayList<>(edges);
        for (int i = 0; i < edges; i++) {
            String[] edgeInfo = in.readLine().split("\\s");
            int node1 = Integer.parseInt(edgeInfo[0]);
            int node2 = Integer.parseInt(edgeInfo[1]);
            int weight = Integer.parseInt(edgeInfo[2]);
            edgeList.add(new int[] { node1, node2, weight });
        }

        /*
         * Sort edgeList in the order of increasing weight to use Kruskal's algorithm to
         * find the minimum spanning tree (MST).
         */
        edgeList.sort((x, y) -> x[2] - y[2]);

        /* Keep track of the cost of the MST. */
        int mstCost = 0;

        /* Kepp track of the number of edges in the MST. */
        int mstEdgeCount = 0;

        /* Initialise a union find data structure for Kruskal's algorithm. */
        UnionFind unionFind = new UnionFind(nodes);
        for (int i = 0; i < edges; i++) {
            int[] edge = edgeList.get(i);
            if (unionFind.union(edge[0], edge[1])) {
                mstCost = Math.max(mstCost, edge[2]);
                mstEdgeCount++;
            }
        }

        /*
         * If the number of edges in the MST is the less than number of nodes - 1,
         * output "IMPOSSIBLE". Else, output the cost of the MST.
         */
        out.println(mstEdgeCount == nodes - 1 ? mstCost : "IMPOSSIBLE");

        in.close();
        out.close();
    }
}