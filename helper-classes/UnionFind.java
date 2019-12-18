/**
 * The UnionFind class is used for the union find data structure. The UnionFind
 * class uses union by rank and path compression.
 * 
 * Created by Teng Le. Last updated on 19 December 2019.
 */
public class UnionFind {
    private final int[] rank;
    private final int[] parent;
    private final int size;

    /**
     * Constructs a union find data stucture of a given size.
     * 
     * @param size The given size.
     */
    UnionFind(final int size) {
        this.size = size;
        rank = new int[size];
        parent = new int[size];
        reset();
    }

    /**
     * Get the size if the union find data structure.
     * 
     * @return The size if the union find data structure.
     */
    int getSize() {
        return size;
    }

    /**
     * Reset the union find data structure such that each node belongs to a
     * different set.
     */
    void reset() {
        for (int i = 0; i < size; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    /**
     * Get the root of the requested node.
     * 
     * @param x The requested node.
     * @return The root of the requested node.
     */
    int getRoot(final int x) {
        if (parent[x] != x) {
            parent[x] = getRoot(parent[x]);
        }
        return parent[x];
    }

    /**
     * Union the set that contains the 2 given node. Return true if successful and
     * return false if the 2 given nodes already belong to the same set.
     * 
     * @param x The first given node.
     * @param y The second given node.
     * @return True if successful and false if the 2 given nodes already belong to
     *         the same set.
     */
    boolean union(final int x, final int y) {
        final int xRoot = getRoot(x);
        final int yRoot = getRoot(y);

        /* Return false if the 2 given nodes already belong to the same set. */
        if (xRoot == yRoot) {
            return false;
        }

        /*
         * If rank of xRoot is less than rank of yRoot, make xRoot's parent as yRoot.
         * Else if rank of yRoot is less than rank of xRoot, make yRoot's parent as
         * xRoot. Else, the rank of xRoot and yRoot are the same and make xRoot's parent
         * as yRoot and increase rank of yRoot.
         */
        if (rank[xRoot] < rank[yRoot]) {
            parent[xRoot] = yRoot;
        } else if (rank[yRoot] < rank[xRoot]) {
            parent[yRoot] = xRoot;
        } else {
            parent[xRoot] = yRoot;
            rank[yRoot]++;
        }

        return true;
    }
}