import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * The SSSGraph class is used for SSSP algorithms such as Dijkstra's and
 * Bellman-Ford. The graph is represented using an adjacency list.
 * 
 * Created by Teng Le. Last updated on 18 December 2019.
 */
class SSSPGraph {
    /** Infinity value. */
    static final double INF = 1e9;

    /**
     * Edge class used to represent an edge in the graph.
     */
    class Edge {
        /** The node in which the edge is coming from. */
        final int u;

        /** The node in which the edge is going towards. */
        final int v;

        /** The weight of the edge. */
        final double w;

        /**
         * Constructs an edge.
         * 
         * @param u The node in which the edge is coming from.
         * @param v The node in which the edge is going towards.
         * @param w The weight of the edge.
         */
        Edge(final int u, final int v, final double w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }

        /**
         * Returns the string representation of the edge.
         * 
         * @return The string representation of the edge.
         */
        @Override
        public String toString() {
            return String.format("Edge - (u: %d | v: %d | w: %f)", u, v, w);
        }
    }

    /**
     * NodeDist class used in Dijkstra's Algorithm to store the node and its
     * distance from the source.
     */
    private class NodeDist {
        /** Represents the node. */
        final int node;

        /** Represents the distance of the node from the source. */
        final double distFromSource;

        /**
         * Constructs a pair.
         * 
         * @param node The node.
         * @param dist The distance of the node from the source.
         */
        NodeDist(final int node, final double dist) {
            this.node = node;
            this.distFromSource = dist;
        }

        /**
         * Returns the string representation of the node and its distance from the
         * source.
         * 
         * @return The string representation of the node and its distance from the
         *         source.
         */
        @Override
        public String toString() {
            return String.format("NodeDist - (%d, %.4f)", node, distFromSource);
        }
    }

    /** Number of nodes in the graph. */
    private final int nodes;

    /** Number of edges in the graph. */
    private final int edges;

    /** Array that stores the shortest distance. */
    private final double[] dist;

    /** Array that stores the parent node for shortest distance. */
    private final int[] parent;

    /** Adjacency List representation of the graph. */
    private final ArrayList<ArrayList<Edge>> adjList;

    /** Check if a shortest path algorithm is performed. */
    private boolean isSPDone = false;

    /**
     * Private Constructor to create new a new Graph.
     * 
     * @param nodes   The number of nodes in the graph.
     * @param edges   The number of edges in the graph.
     * @param dist    The distance array to store the shortest distance for SSSP.
     * @param parent  The parent pointer array to store the shortest path for SSSP.
     * @param adjList The adjacency list representation of the graph.
     */
    private SSSPGraph(final int nodes, final int edges, final double[] dist, final int[] parent,
            final ArrayList<ArrayList<Edge>> adjList) {
        this.nodes = nodes;
        this.edges = edges;
        this.dist = dist;
        this.parent = parent;
        resetGraphForSSSP();
        this.adjList = adjList;
        if (adjList != null) {
            for (int i = 0; i < nodes; i++) {
                adjList.add(new ArrayList<>());
            }
        }
    }

    /**
     * Initialise graph to find shortest path with distance.
     * 
     * @param nodes The number of nodes in the graph.
     * @param edges The number of edges in the graph.
     * @return The graph with given number of nodes and edges.
     */
    static SSSPGraph initGraphForSSSP(final int nodes, final int edges) {
        return new SSSPGraph(nodes, edges, new double[nodes], new int[nodes], new ArrayList<>());
    }

    /**
     * Initialise graph to find shortest path distance only. No path pointer array
     * will be initialised.
     * 
     * @param nodes The number of nodes in the graph.
     * @param edges The number of edges in the graph.
     * @return The graph with given number of nodes and edges.
     */
    static SSSPGraph initGraphForSSSPDist(final int nodes, final int edges) {
        return new SSSPGraph(nodes, edges, new double[nodes], null, new ArrayList<>());
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
     * @return The edge that is added to the graph.
     */
    Edge addEdge(final int u, final int v, final double w) {
        final Edge e = new Edge(u, v, w);
        adjList.get(u).add(e);
        return e;
    }

    /**
     * Reset graph for single source shortest path.
     */
    void resetGraphForSSSP() {
        if (dist != null) {
            Arrays.fill(dist, INF);
        }
        if (parent != null) {
            Arrays.fill(parent, -1);
        }
        isSPDone = false;
    }

    /**
     * Get the shortest distance of given node after a shortest path algorithm.
     * 
     * @param node The node of interest to obtain the shortest distance.
     * @return The shortest distance of the given node.
     */
    double getDistOf(final int node) {
        if (isSPDone) {
            return dist[node];
        } else {
            throw new UnsupportedOperationException(
                    "Cannot get distance of a node before performing any shortest path algorithm.");
        }
    }

    /**
     * Get the shortest distance array of all nodes after a shortest path algorithm.
     * 
     * @return The shortest distance array of all nodes.
     */
    double[] getAllDist() {
        if (isSPDone) {
            return dist;
        } else {
            throw new UnsupportedOperationException(
                    "Cannot get distance of nodes before performing any shortest path algorithm.");
        }
    }

    /**
     * Get the shortest path array of all nodes after a shortest path algorithm. The
     * index represents the node and the element of that index is the parent node.
     * 
     * @return The shortest path array of all nodes.
     */
    int[] getPathArray() {
        if (isSPDone) {
            return parent;
        } else {
            throw new UnsupportedOperationException(
                    "Cannot get distance of nodes before performing any shortest path algorithm.");
        }
    }

    /**
     * Dijskstra's Algorithm that does not stores path.
     * 
     * @param source The source node of the algorithm.
     */
    void dijkstraWithoutPath(final int source) {
        dijkstra(source, false);
    }

    /**
     * Dijskstra's Algorithm that stores path. Use only if graph is initialised with
     * initGraphForSSSP(nodes, edges).
     * 
     * @param source The source node of the algorithm.
     */
    void dijkstraWithPath(final int source) {
        if (parent == null) {
            throw new UnsupportedOperationException(
                    "Cannot perform DijkstraWithPath as graph is not initialised with initGraphForSSSP(nodes, edges).");
        } else {
            dijkstra(source, true);
        }
    }

    /**
     * Private method for Dijskstra's Algorithm using PriorityQueue with lazy
     * deletion. Contains commented sysout lines for debugging purposes.
     * 
     * @param source   The source node of the algorithm.
     * @param withPath True if path is needed, else false to get only the shortest
     *                 distances.
     */
    private void dijkstra(final int source, final boolean withPath) {
        if (dist == null) {
            throw new NullPointerException("Distance array is not initialised.");
        }
        /* Set distance of source to 0 */
        dist[source] = 0;

        /*
         * Initialise priority queue where the node with smallest distance has the
         * highest priority.
         */
        final PriorityQueue<NodeDist> pq = new PriorityQueue<>(
                (p1, p2) -> (int) Math.signum(p1.distFromSource - p2.distFromSource));

        /* Add source node and its distance into the queue. */
        pq.add(new NodeDist(source, 0));

        /*
         * While not all nodes are processed, perform relaxation on the adjacent edges.
         */
        while (!pq.isEmpty()) {
            final NodeDist nodeDist = pq.poll();
            // System.out.printf("Processing %s\n", nodeDist);

            /* u is the current node to be processed. */
            final int u = nodeDist.node;

            /*
             * Check if distance of u from the priority queue is updated. Only process u if
             * it is updated as lazy deletion is used.
             */
            if (dist[u] == nodeDist.distFromSource) {
                // System.out.printf("** %s is updated\n", nodeDist);

                /* Get neighbours of u */
                final ArrayList<Edge> neighbours = adjList.get(u);

                /*
                 * For each neighbour of u, perform relaxation, update dist[] and parent[] and
                 * add neighbour with updated distance into priority queue.
                 */
                for (final Edge neighbour : neighbours) {
                    // System.out.printf("**** Performing relaxation on %s\n", neighbour);

                    /* v is the neighbour node, w is the weight to v. */
                    final int v = neighbour.v;
                    final double w = neighbour.w;

                    /*
                     * If dist[u] + w is shorter than current dist[v], relax dist[v], update path
                     * and insert new info into priority queue.
                     */
                    if (dist[u] + w < dist[v]) {
                        dist[v] = dist[u] + w;
                        if (withPath) {
                            parent[v] = u;
                        }
                        pq.offer(new NodeDist(neighbour.v, dist[neighbour.v]));

                        // System.out.printf("**** %s is relaxed\n", neighbour);
                    }
                }
            }
            // System.out.printf("Finish Processing %s\n\n\n", nodeDist);
        }

        /* Shortest path algorithm is done. */
        isSPDone = true;
    }

    /**
     * Bellman-Ford Algorithm that does not stores path.
     * 
     * @param source The source node of the algorithm.
     * @return True if there is no negative cycles in the graph, else false.
     */
    boolean bellmanFordWithoutPath(final int source) {
        return bellmanFord(source, false);
    }

    /**
     * Bellman-Ford Algorithm that stores path.
     * 
     * @param source The source node of the algorithm.
     * @return True if there is no negative cycles in the graph, else false.
     */
    boolean bellmanFordWithPath(final int source) {
        if (parent == null) {
            throw new UnsupportedOperationException(
                    "Cannot perform bellmanFordWithPath as graph is not initialised with initGraphForSSSP(nodes, edges).");
        } else {
            return bellmanFord(source, true);
        }
    }

    /**
     * Private Bellman-Ford Algorithm. Contains commented sysout lines for debugging
     * purposes.
     * 
     * @param source   The source node of the algorithm.
     * @param withPath True if path is needed, else false to get only the shortest
     *                 distances.
     * @return True if there is no negative cycles in the graph, else false.
     */
    private boolean bellmanFord(final int source, final boolean withPath) {
        dist[source] = 0;

        /*
         * Perform relaxation for alll edges for |V| - 1 times where |V| = number of
         * nodes.
         */
        for (int i = 0; i < nodes - 1; i++) {
            // System.out.printf("Iteration %d:\n", i);

            /* Relaxation for all edges. */
            for (int u = 0; u < nodes; u++) {
                // System.out.printf("Processing node %d, dist[u] = %.3f\n", u, dist[u]);

                /* Get neighbours of u */
                final ArrayList<Edge> neighbours = adjList.get(u);

                /*
                 * For each neighbour of u, perform relaxation and update dist[] and parent[].
                 */
                for (final Edge neighbour : neighbours) {
                    // System.out.printf("**** Performing relaxation on %s\n", neighbour);

                    /* v is the neighbour node, w is the weight to v. */
                    final int v = neighbour.v;
                    final double w = neighbour.w;

                    /*
                     * If dist[u] + w is shorter than current dist[v], relax dist[v] and update
                     * path. Skip for dist[u] = INF as adding INF with negative number will cause
                     * problems.
                     */
                    if (dist[u] != INF && dist[u] + w < dist[v]) {
                        dist[v] = dist[u] + w;
                        if (withPath) {
                            parent[v] = u;
                        }

                        // System.out.printf("**** %s is relaxed\n", neighbour);
                    }
                }

                // System.out.printf("Finish Processing %d, dist[u] = %.4f\n\n", u, dist[u]);
            }
            // System.out.printf("Iteration %d done\n\n\n", i);
        }

        /* Check for negative cycles */
        boolean hasNegCycles = false;

        /*
         * Continue to update dist[] if there is any changes to the distance of any
         * nodes.
         */
        boolean done = false;
        while (!done) {
            done = true;
            for (int u = 0; u < nodes; u++) {
                /* Get neighbours of u */
                final ArrayList<Edge> neighbours = adjList.get(u);

                /*
                 * For each neighbour of u, perform relaxation and update dist[]. If relaxation
                 * is necessary, it means that negative cycle exist.
                 */
                for (final Edge neighbour : neighbours) {
                    /* v is the neighbour node, w is the weight to v. */
                    final int v = neighbour.v;
                    final double w = neighbour.w;

                    /*
                     * If dist[u] + w is shorter than current dist[v], it means that negative cycle
                     * exist. Thus, set dist[v] = -INF. Skip for dist[u] = INF and dist[v] = -INF.
                     */
                    if (dist[v] != -INF && dist[u] != INF && dist[u] + w < dist[v]) {
                        dist[v] = -INF;
                        if (withPath) {
                            parent[v] = -1;
                        }
                        done = false;
                        hasNegCycles = true;
                    }
                }
            }
        }

        /* Shortest path algorithm is done. */
        isSPDone = true;
        return !hasNegCycles;
    }
}