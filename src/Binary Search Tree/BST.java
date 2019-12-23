import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Map;
import java.util.TreeMap;

class BST {
    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

        int numSequence = Integer.parseInt(in.readLine());

        /*
         * Initialise a TreeMap to store the nodes as keys and the depths of the node as
         * the values.
         */
        TreeMap<Integer, Integer> bst = new TreeMap<>();

        /* Add the root to the BST and print "0". */
        bst.put(Integer.parseInt(in.readLine()), 0);
        out.println(0);

        /* Initialise a counter to keep track of the result to be printed. */
        long counter = 0;

        for (int i = 0; i < numSequence - 1; i++) {
            int key = Integer.parseInt(in.readLine());

            /* Get the successor and predecessor. */
            Map.Entry<Integer, Integer> successor = bst.ceilingEntry(key);
            Map.Entry<Integer, Integer> predecessor = bst.floorEntry(key);

            /*
             * Find the depth of the current node.
             * 
             * Depth = Max(depth of successor, depth of predecessor) + 1.
             */
            int depth = 0;
            if (successor != null) {
                depth = successor.getValue();
            }
            if (predecessor != null) {
                depth = Math.max(depth, predecessor.getValue());
            }
            depth++;

            /* Add the node to the BST. */
            bst.put(key, depth);

            /* Add depth to counter and print result. */
            counter += depth;
            out.println(counter);
        }

        in.close();
        out.close();
    }
}