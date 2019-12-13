class UnionFind {
    static int[] parent;
    static int[] rank;

    public static void main(String[] args) throws Exception {
        FastIO io = new FastIO();

        int numNodes = io.nextPositiveInt();
        int numOps = io.nextPositiveInt();
        parent = new int[numNodes];
        rank = new int[numNodes];
        for (int i = 0; i < numNodes; i++) {
            parent[i] = i;
            rank[i] = 0;
        }

        for (int i = 0; i < numOps; i++) {
            char operation = io.nextChar();
            int rootA = findParent(io.nextPositiveInt());
            int rootB = findParent(io.nextPositiveInt());
            if (operation == '?') {
                io.write(rootA == rootB ? "yes\n" : "no\n");
            } else {
                if (rank[rootA] < rank[rootB]) {
                    parent[rootA] = rootB;
                } else if (rank[rootB] < rank[rootA]) {
                    parent[rootB] = rootA;
                } else {
                    parent[rootA] = rootB;
                    rank[rootB]++;
                }
            }
        }

        io.close();
    }

    static int findParent(int a) {
        if (parent[a] != a) {
            parent[a] = findParent(parent[a]);
        }
        return parent[a];
    }
}