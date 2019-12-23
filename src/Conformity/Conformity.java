import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;

class Conformity {
    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

        /* Read the number of frosh. */
        int numFrosh = Integer.parseInt(in.readLine());

        /* Initialise a HashMap to keep track of the number of frosh for each combi. */
        HashMap<Combi, Integer> combiCounts = new HashMap<>();

        /*
         * Initialise globalMax to keep track of the number of frosh for the most
         * popular combi.
         */
        int globalMax = 0;

        /*
         * Initialise globalCount to keep track of the total number of frosh for the
         * most popular combis.
         */
        int globalCount = 0;

        /* For each combi, update combiCounts, globalMax and globalCount. */
        for (int i = 0; i < numFrosh; i++) {
            Combi combi = new Combi(in.readLine());
            Integer count = combiCounts.get(combi);

            /*
             * If combi is not added to combiCounts, set count = 1. Else, update count by
             * incrementing it.
             */
            if (count == null) {
                count = 1;
            } else {
                count++;
            }

            /* Update combiCounts. */
            combiCounts.put(combi, count);

            /*
             * Update globalMax if necessary.
             * 
             * If current combi's count is equal to globalMax, add current combi's count to
             * globalCount as this combi is now also considered as the most popular.
             */
            if (globalMax < count) {
                globalMax = count;
                globalCount = count;
            } else if (globalMax == count) {
                globalCount += count;
            }

        }

        /* Print result. */
        out.println(globalCount);

        in.close();
        out.close();
    }
}