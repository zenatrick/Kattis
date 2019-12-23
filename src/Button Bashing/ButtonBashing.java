import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;

class ButtonBashing {
    static final int MAXTIME = 3601;

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

        int numCases = Integer.parseInt(in.readLine());
        for (int c = 0; c < numCases; c++) {
            String[] inputs = in.readLine().split("\\s");
            int numButtons = Integer.parseInt(inputs[0]);
            int targetTime = Integer.parseInt(inputs[1]);

            /* Read and store the buttons value in an array. */
            int[] buttons = Arrays.stream(in.readLine().split("\\s")).mapToInt(Integer::parseInt).toArray();

            /*
             * Initialise an array to keep track of the number of presses needed for each
             * timing. The initial value of each timing is -1 (not processed).
             * 
             * i.e. at time t, the nubmer of presses is given by numPresses[t].
             */
            int[] numPresses = new int[MAXTIME];
            Arrays.fill(numPresses, -1);

            /*
             * Keep track of the closest time that hits the target time and the least number
             * of presses required for that time.
             */
            int bestTime = MAXTIME;
            int bestCount = Integer.MAX_VALUE;

            /* Initialise a queue for BFS. */
            LinkedList<Node> queue = new LinkedList<>();

            /* Perform the BFS algorithm. */
            queue.offer(new Node(0, 0));
            while (!queue.isEmpty()) {
                Node currNode = queue.poll();
                int currTime = currNode.time;
                int currCount = currNode.count;

                /*
                 * If node is not processed or has a better count as comapred to that in
                 * numPresses, update numPresses and continue to process the node. Else, do not
                 * process the node.
                 */
                if (numPresses[currTime] == -1 || numPresses[currTime] > currCount) {
                    numPresses[currTime] = currCount;

                    /*
                     * If currTime hits targetTime and is better than bestTime, update bestTime and
                     * bestCount.
                     */
                    if (currTime >= targetTime && currTime < bestTime) {
                        bestTime = currTime;
                        bestCount = currCount;
                    }

                    /* Add each neighbour into the BFS queue. */
                    for (int b = 0; b < numButtons; b++) {
                        int nextTime = currTime + buttons[b];
                        /* Make sure that the time is between 0 and 3600, inclusive. */
                        nextTime = Math.min(MAXTIME - 1, Math.max(0, nextTime));
                        queue.offer(new Node(nextTime, currCount + 1));
                    }
                }
            }
            out.println(bestCount + " " + (bestTime - targetTime));
        }
        in.close();
        out.close();
    }
}