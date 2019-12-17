import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;

class ReachableRoads {
    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

        int numCities = Integer.parseInt(in.readLine());
        for (int city = 0; city < numCities; city++) {
            int nodes = Integer.parseInt(in.readLine());
            int edges = Integer.parseInt(in.readLine());

            BFS_DFS graph = BFS_DFS.initGraph(nodes, edges);

            for (int i = 0; i < edges; i++) {
                String[] pair = in.readLine().split("\\s");
                int first = Integer.parseInt(pair[0]);
                int second = Integer.parseInt(pair[1]);
                graph.addEdge(first, second);
                graph.addEdge(second, first);
            }

            boolean[] isVisited = graph.getIsVisited();

            int numConnectedComponents = 0;
            for (int i = 0; i < nodes; i++) {
                if (!isVisited[i]) {
                    numConnectedComponents++;
                    graph.dfs(i);
                }
            }

            out.println(numConnectedComponents - 1);
        }

        in.close();
        out.close();
    }
}