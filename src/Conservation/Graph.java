import java.util.ArrayList;
import java.util.LinkedList;

class Graph {
    /*
     * The a node in the graph represents a conservation stage. Each node contains
     * the variable inDegree to keep track of the total number of edges pointing
     * towards itself.
     */
    class Node {
        int inDegree;
        int removedDegree;

        Node() {
            inDegree = 0;
            removedDegree = 0;
        }

        void resetRemovedDeg() {
            removedDegree = 0;
        }
    }

    /*
     * The pair class is used as the return type for the private method
     * generateLists() so that the method can return a pair of lists.
     */
    class Pair<T> {
        T first;
        T second;

        Pair(T first, T second) {
            this.first = first;
            this.second = second;
        }
    }

    /* The number of nodes in the graph. */
    int nodes;

    /* The number of edges in the graph. */
    int edges;

    /* The list of nodes in the graph. */
    ArrayList<Node> nodeList;

    /* The adjacency list representation of the graph. */
    ArrayList<ArrayList<Integer>> adjList;

    /* The array to keep track of the lab in which the node belongs to. */
    int[] nodeType;

    Graph(int nodes, int edges, int[] nodeType) {
        this.nodes = nodes;
        this.edges = edges;
        this.nodeType = nodeType;
        nodeList = new ArrayList<>();
        adjList = new ArrayList<>();
        for (int i = 0; i < nodes; i++) {
            nodeList.add(new Node());
            adjList.add(new ArrayList<>());
        }
    }

    /* To add an edge to the graph. */
    void addEdge(int from, int to) {
        adjList.get(from).add(to);
        nodeList.get(to).inDegree++;
    }

    /*
     * Compute the minimum number of transfers required between the 2 labs. Can
     * increase effificency by checking if either of the list is empty so that only
     * one execution of getCount() is needed.
     */
    int computeMinTransfers() {
        /* Get the number of transfers starting from lab1. */
        Pair<LinkedList<Integer>> listPair = generateLists();
        LinkedList<Integer> list1 = listPair.first;
        LinkedList<Integer> list2 = listPair.second;
        int count1 = getCount(list1, list2, true, -1);

        /* Get the number of transfers starting from lab2. */
        Pair<LinkedList<Integer>> listPair2 = generateLists();
        list1 = listPair2.first;
        list2 = listPair2.second;
        int count2 = getCount(list1, list2, false, -1);

        /* Result will be the minimum of the 2. */
        return Math.min(count1, count2);
    }

    /*
     * Generate 2 lists of nodes with incoming degree equal to 0 (List of nodes
     * where no other nodes points to them). The first list contains nodes for lab1
     * processing while the second list contains nodes for lab2 processing.
     */
    private Pair<LinkedList<Integer>> generateLists() {
        LinkedList<Integer> list1 = new LinkedList<>();
        LinkedList<Integer> list2 = new LinkedList<>();

        for (int i = 0; i < nodes; i++) {
            Node node = nodeList.get(i);
            node.resetRemovedDeg();
            if (node.inDegree == 0) {
                if (nodeType[i] == 1) {
                    list1.offer(i);
                } else {
                    list2.offer(i);
                }
            }
        }

        return new Pair<>(list1, list2);
    }

    /*
     * Modified Kahn's algorithm for topological sorting which keep tracks of how
     * many transfers are needed between the 2 labs.
     */
    private int getCount(LinkedList<Integer> list1, LinkedList<Integer> list2, boolean isList1, int count) {
        if (list1.isEmpty() && list2.isEmpty()) {
            return count;
        }

        LinkedList<Integer> currList = isList1 ? list1 : list2;
        while (!currList.isEmpty()) {
            int node = currList.poll();
            ArrayList<Integer> neighbours = adjList.get(node);
            for (int neighbour : neighbours) {
                Node neighbourNode = nodeList.get(neighbour);
                neighbourNode.removedDegree++;
                if (neighbourNode.inDegree == neighbourNode.removedDegree) {
                    if (nodeType[neighbour] == 1) {
                        list1.offer(neighbour);
                    } else {
                        list2.offer(neighbour);
                    }
                }
            }
        }
        return getCount(list1, list2, !isList1, count + 1);
    }
}