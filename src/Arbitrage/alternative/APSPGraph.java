import java.util.Arrays;

/**
 * The APSGraph class is used for the Floyd-Warshall algorithm. The graph is
 * represented using an adjacency matrix.
 * 
 * Created by Teng Le. Last updated on 21 December 2019.
 */
class APSPGraph {
    /** Infinity value. */
    static final double INF = 1e9;

    /** Number of nodes in the graph. */
    private final int nodes;

    /** Number of edges in the graph. */
    private final int edges;

    /** Adjacency Matrix representation of the graph. */
    private final double[][] adjMtx;

    /** 2D array that stores the parent node for shortest distance. */
    private final int[][] parent;

    /** Check if a shortest path algorithm is performed. */
    private boolean isSPDone;

    /** Check if the graph has negative cycles. */
    private boolean hasNegCycles;

    /**
     * Private Constructor to create new a new Graph.
     * 
     * @param nodes  The number of nodes in the graph.
     * @param edges  The number of edges in the graph.
     * @param parent The parent pointer array to store the shortest path for SSSP.
     */
    private APSPGraph(final int nodes, final int edges, final boolean storePath) {
        this.nodes = nodes;
        this.edges = edges;
        adjMtx = new double[nodes][nodes];
        for (final double[] row : adjMtx)
            Arrays.fill(row, INF);
        for (int i = 0; i < nodes; i++)
            adjMtx[i][i] = 0;
        if (storePath) {
            parent = new int[nodes][nodes];
            for (final int[] row : parent)
                Arrays.fill(row, -1);
            for (int i = 0; i < nodes; i++)
                parent[i][i] = i;
        } else {
            parent = null;
        }
        isSPDone = false;
        hasNegCycles = false;
    }

    /**
     * Initialise graph to find shortest path with distance.
     * 
     * @param nodes The number of nodes in the graph.
     * @param edges The number of edges in the graph.
     * @return The graph with given number of nodes and edges.
     */
    static APSPGraph initGraphForAPSP(final int nodes, final int edges) {
        return new APSPGraph(nodes, edges, true);
    }

    /**
     * Initialise graph to find shortest path distance only. No path pointer array
     * will be initialised.
     * 
     * @param nodes The number of nodes in the graph.
     * @param edges The number of edges in the graph.
     * @return The graph with given number of nodes and edges.
     */
    static APSPGraph initGraphForAPSPDist(final int nodes, final int edges) {
        return new APSPGraph(nodes, edges, false);
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
     * Add an edge to the graph.
     * 
     * @param u The node in which the edge is coming from.
     * @param v The node in which the edge is going towards.
     * @param w The weight of the edge.
     */
    void addEdge(final int u, final int v, final double w) {
        if (!isSPDone) {
            adjMtx[u][v] = w;
            if (parent != null)
                parent[u][v] = u;
        } else {
            throw new UnsupportedOperationException(
                    "Cannot add edges to graph after performing Floyd-Warshall algorithm.");
        }
    }

    /**
     * Get the shortest distance from a source node to a target node after the
     * shortest path algorithm.
     * 
     * @param source The source node.
     * @param target The target node.
     * 
     * @return The shortest distance from the source node to the target node.
     */
    double getDistOf(final int source, final int target) {
        if (!isSPDone) {
            throw new UnsupportedOperationException("Cannot get distance before performing Floyd-Warshall algorithm.");
        } else if (hasNegCycles) {
            throw new UnsupportedOperationException("Cannot get distance as the graph contains negative cycles.");
        } else {
            return adjMtx[source][target];
        }
    }

    /**
     * Get the matrix representation of all pair shortest path distance after
     * Floyd-Warshall algorithm.
     * 
     * @return The matrix representation fo all pair shortest path distance.
     */
    double[][] getAllDist() {
        if (!isSPDone) {
            throw new UnsupportedOperationException("Cannot get distance before performing Floyd-Warshall algorithm.");
        } else if (hasNegCycles) {
            throw new UnsupportedOperationException("Cannot get distance as the graph contains negative cycles.");
        } else {
            return adjMtx;
        }

    }

    /**
     * Get the shortest path matrix of all nodes after Floyd-Warshall algorithm. The
     * format is in the form of matrix[source][target] = parentOfTarget.
     * 
     * @return The shortest path matrix of all nodes.
     */
    int[][] getPathMatrix() {
        if (parent == null) {
            throw new UnsupportedOperationException(
                    "Cannot get path of nodes as graph is not initialised with initGraphForAPSP(nodes, edges).");
        } else if (!isSPDone) {
            throw new UnsupportedOperationException(
                    "Cannot get path of nodes before performing Floyd-Warshall algorithm.");
        } else if (hasNegCycles) {
            throw new UnsupportedOperationException("Cannot get path of nodes as the graph contains negative cycles.");
        } else {
            return parent;
        }
    }

    /**
     * Floyd-Warshall algorithm that does not stores path.
     * 
     * @return True if there is no negative cycles in the graph, else false.
     */
    boolean floydWarshallWithoutPath() {
        return floydWarshall(false);
    }

    /**
     * Floyd-Warshall algorithm that stores path.
     * 
     * @return True if there is no negative cycles in the graph, else false.
     */
    boolean floydWarshallWithPath() {
        if (parent == null) {
            throw new UnsupportedOperationException(
                    "Cannot perform floydWarshallWithPath as graph is not initialised with initGraphForAPSP(nodes, edges).");
        } else {
            return floydWarshall(true);
        }
    }

    /**
     * Private Floyd-Warshall algorithm.
     * 
     * @param withPath True if path is needed, else false to get only the shortest
     *                 distances.
     * @return True if there is no negative cycles in the graph, else false.
     */
    private boolean floydWarshall(final boolean withPath) {
        if (isSPDone) {
            throw new UnsupportedOperationException("Floyd-Warshall has already been performed.");
        }
        for (int k = 0; k < nodes; k++) {
            for (int i = 0; i < nodes; i++) {
                for (int j = 0; j < nodes; j++) {
                    final double distThroughK = adjMtx[i][k] + adjMtx[k][j];
                    if (distThroughK < adjMtx[i][j]) {
                        adjMtx[i][j] = distThroughK;
                        if (withPath)
                            parent[i][j] = parent[k][j];
                    }
                    if (i == j && adjMtx[i][j] < 0) {
                        hasNegCycles = true;
                    }
                }
            }
        }
        isSPDone = true;
        return !hasNegCycles;
    }
}