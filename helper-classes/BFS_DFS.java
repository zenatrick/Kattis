import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * The BFS_DFS class is used for the breadth first search (BFS) and depth first
 * search (DFS) algorithms. The graph is represented using an adjacency list.
 * 
 * Created by Teng Le. Last updated on 18 December 2019.
 */
class BFS_DFS {
    /**
     * Edge class used to represent an edge in the graph.
     */
    class Edge {
        /** The node in which the edge is coming from. */
        final int u;

        /** The node in which the edge is going towards. */
        final int v;

        /**
         * Constructs an edge.
         * 
         * @param u The node in which the edge is coming from.
         * @param v The node in which the edge is going towards.
         */
        Edge(final int u, final int v) {
            this.u = u;
            this.v = v;
        }

        /**
         * Returns the string representation of the edge.
         * 
         * @return The string representation of the edge.
         */
        @Override
        public String toString() {
            return String.format("Edge - (u: %d | v: %d)", u, v);
        }
    }

    /** Number of nodes in the graph. */
    private final int nodes;

    /** Number of edges in the graph. */
    private final int edges;

    /** Array that keeps track of visited nodes. */
    private final boolean[] isVisited;

    /** Adjacency List representation of the graph. */
    private final ArrayList<ArrayList<Edge>> adjList;

    /**
     * Private Constructor to create new a new Graph.
     * 
     * @param nodes   The number of nodes in the graph.
     * @param edges   The number of edges in the graph.
     * @param adjList The adjacency list representation of the graph.
     */
    private BFS_DFS(final int nodes, final int edges) {
        this.nodes = nodes;
        this.edges = edges;
        this.isVisited = new boolean[nodes];
        this.adjList = new ArrayList<>();
        for (int i = 0; i < nodes; i++) {
            adjList.add(new ArrayList<>());
        }
    }

    /**
     * Initialise graph for graph traversal.
     * 
     * @param nodes The number of nodes in the graph.
     * @param edges The number of edges in the graph.
     * @return The graph with given number of nodes and edges.
     */
    static BFS_DFS initGraph(final int nodes, final int edges) {
        return new BFS_DFS(nodes, edges);
    }

    /**
     * Get the number of nodes in the graph.
     * 
     * @return The number of nodes in the graph.
     */
    int getNumNodes() {
        return nodes;
    }

    /**
     * Get the number of edges in the graph.
     * 
     * @return The number of edges in the graph.
     */
    int getNumEdges() {
        return edges;
    }

    /**
     * Get the visited array of the graph.
     * 
     * @return The visited array of the graph.
     */
    boolean[] getIsVisited() {
        return isVisited;
    }

    /**
     * Add an edge to the graph.
     * 
     * @param u The node in which the edge is coming from.
     * @param v The node in which the edge is going towards.
     * @param w The weight of the edge.
     * @return The edge that is added to the graph.
     */
    Edge addEdge(final int u, final int v) {
        final Edge e = new Edge(u, v);
        adjList.get(u).add(e);
        return e;
    }

    /**
     * Reset graph for graph traversal.
     */
    void resetGraph() {
        Arrays.fill(isVisited, false);
    }

    /**
     * BFS algorithm to traverse the whole graph.
     * 
     * @param source The source node of BFS.
     */
    void bfs(final int source) {
        bfs(source, -1);
    }

    /**
     * BFS algorithm to search for a target node.
     * 
     * @param source The source node of BFS.
     * @param target The target node of BFS.
     * @return True if the target node is found, else return false.
     */
    boolean bfs(final int source, final int target) {
        return traversal(source, target, true);
    }

    /**
     * DFS algorithm to traverse the whole graph.
     * 
     * @param source The source node of DFS.
     */
    void dfs(final int source) {
        dfs(source, -1);
    }

    /**
     * DFS algorithm to search for a target node.
     * 
     * @param source The source node of DFS.
     * @param target The target node of DFS.
     * @return True if the target node is found, else return false.
     */
    boolean dfs(final int source, final int target) {
        return traversal(source, target, false);
    }

    /**
     * Private traversal algorithm (BFS and DFS) to search for a target node.
     * 
     * @param source The source node of the algorithm.
     * @param target The target node of the algorithm.
     * @param isBFS  Set to true for BFS and false for DFS
     * @return True if the target node is found, else return false.
     */
    private boolean traversal(final int source, final int target, final boolean isBFS) {
        // Initialise data structure for traversal algorithm.
        final LinkedList<Integer> dataStruct = new LinkedList<>();

        // Set source as visited and add source into data structure.
        isVisited[source] = true;
        if (isBFS) {
            dataStruct.offer(source);
        } else {
            dataStruct.push(source);
        }

        // Continue traversal algorithm while the data structure is not empty.
        while (!dataStruct.isEmpty()) {
            final int u = dataStruct.removeFirst();
            final ArrayList<Edge> neighbours = adjList.get(u);

            // Add each unvisited neighbour of u into data structure and mark it as visited.
            for (final Edge neighbour : neighbours) {
                final int v = neighbour.v;
                if (!isVisited[v]) {
                    if (v == target) {
                        return true;
                    }
                    isVisited[v] = true;
                    if (isBFS) {
                        dataStruct.offer(v);
                    } else {
                        dataStruct.push(v);
                    }
                }
            }
        }
        return false;
    }
}
