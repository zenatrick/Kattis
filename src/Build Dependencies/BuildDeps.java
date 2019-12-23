import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

class BuildDeps {
    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

        int numRules = Integer.parseInt(in.readLine());

        /*
         * Initialise a graph whereby node A has to be complied before node B if node A
         * points to node B.
         */
        HashMap<String, ArrayList<String>> graph = new HashMap<>();
        for (int i = 0; i < numRules; i++) {
            /* Parse the current node which the rule is refering to. */
            String[] rule = in.readLine().split(":");
            String currNode = rule[0];

            /*
             * Add node to the graph and initialise a new ArrayList if currNode is not yet
             * added.
             */
            graph.putIfAbsent(currNode, new ArrayList<>());

            /*
             * Check if currNode has any dependencies. If so, construct the graph such that
             * the dependencies points towards the current node. (Dependencies needs to be
             * compiled before the current file)
             */
            if (rule.length > 1) {
                String[] nodesToCurr = rule[1].trim().split("\\s");
                for (String node : nodesToCurr) {
                    graph.putIfAbsent(node, new ArrayList<>());
                    graph.get(node).add(currNode);
                }
            }
        }

        /* Initialise a LinkedList to store the result sorted in topological order. */
        LinkedList<String> resultList = new LinkedList<>();

        /* Initialise a HashSet to keep track of the visited nodes. */
        HashSet<String> visitedVertices = new HashSet<>();

        /* Perform topological sorting. */
        String startNode = in.readLine();
        toposort(graph, visitedVertices, startNode, resultList);

        /* Print result. */
        while (!resultList.isEmpty()) {
            out.println(resultList.pop());
        }

        in.close();
        out.close();
    }

    /* Topological sorting using the DFS algorithm. */
    static void toposort(HashMap<String, ArrayList<String>> graph, HashSet<String> visitedVertices, String vertex,
            LinkedList<String> resultList) {
        if (visitedVertices.contains(vertex)) {
            return;
        }
        ArrayList<String> neighbours = graph.get(vertex);
        for (String neighbour : neighbours) {
            toposort(graph, visitedVertices, neighbour, resultList);
        }
        visitedVertices.add(vertex);
        resultList.push(vertex);
    }
}