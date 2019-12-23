import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;

class Brexit {
    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

        String[] input = in.readLine().split("\\s");
        int numCountries = Integer.parseInt(input[0]);
        int numPartnerships = Integer.parseInt(input[1]);
        int homeCountry = Integer.parseInt(input[2]) - 1;
        int startCountry = Integer.parseInt(input[3]) - 1;

        /* Initialise boolean array to keep track of countries that have left. */
        boolean[] hasLeft = new boolean[numCountries];

        /*
         * Initialise Country array to keep track of all countries and the number of
         * partners they have and the number of partners that have left.
         */
        Country[] countries = new Country[numCountries];

        /* Initialise an adjacency list to build a graph. */
        ArrayList<ArrayList<Integer>> adjList = new ArrayList<>();

        for (int i = 0; i < numCountries; i++) {
            countries[i] = new Country();
            adjList.add(new ArrayList<>());
        }

        /*
         * Read the edges info to build the graph and update each country's number of
         * partners.
         */
        for (int i = 0; i < numPartnerships; i++) {
            String[] pair = in.readLine().split("\\s");
            int country1 = Integer.parseInt(pair[0]) - 1;
            int country2 = Integer.parseInt(pair[1]) - 1;
            countries[country1].incrementPartners();
            countries[country2].incrementPartners();
            adjList.get(country1).add(country2);
            adjList.get(country2).add(country1);
        }

        /* Initialise queue for modified BFS. */
        LinkedList<Integer> queue = new LinkedList<>();

        /*
         * Perform modified BFS whereby a country is added and processed in the queue if
         * it leaves.
         */
        queue.offer(startCountry);
        hasLeft[startCountry] = true;
        while (!queue.isEmpty()) {
            int country = queue.poll();
            ArrayList<Integer> neighbours = adjList.get(country);
            for (int neighbour : neighbours) {
                countries[neighbour].partnerLeaves();

                /*
                 * If the neighbour has not previously left and is leaving, add the neighbour to
                 * queue and update hasLeft array.
                 */
                if (!hasLeft[neighbour] && countries[neighbour].willLeave()) {
                    queue.offer(neighbour);
                    hasLeft[neighbour] = true;
                }
            }
        }

        /* Print the result. */
        out.println(hasLeft[homeCountry] ? "leave" : "stay");

        in.close();
        out.close();
    }
}