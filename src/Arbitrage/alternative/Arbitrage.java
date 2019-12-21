import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;

class Arbitrage {
    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

        while (true) {
            int numCurrencies = Integer.parseInt(in.readLine()); // number of nodes

            /* Check for termination condition. */
            if (numCurrencies == 0) {
                break;
            }

            /* Store currencies in a HashMap to translate them into indices. */
            String[] currencies = in.readLine().split("\\s");
            HashMap<String, Integer> currenciesMap = new HashMap<>(numCurrencies);
            for (int i = 0; i < numCurrencies; i++) {
                currenciesMap.put(currencies[i], i);
            }

            int numEXCs = Integer.parseInt(in.readLine()); // number of edges

            /* Initialise APSPGraph for Floyd-Warshall algorithm. */
            APSPGraph graph = APSPGraph.initGraphForAPSPDist(numCurrencies, numEXCs);

            /* Add each exchange rate as an edge using the formula, weight = -log(rate). */
            for (int i = 0; i < numEXCs; i++) {
                String[] input = in.readLine().split("\\s");
                String[] rateInfo = input[2].split(":");
                int u = currenciesMap.get(input[0]);
                int v = currenciesMap.get(input[1]);
                double rate = Double.parseDouble(rateInfo[1]) / Double.parseDouble(rateInfo[0]);
                graph.addEdge(u, v, -Math.log(rate));
            }

            /*
             * Perform standard Floyd-Warshall algorithm and check for any negative cycles.
             * If there is any negative cycles, output "Arbitrage". Else, output "Ok".
             */
            out.println(graph.floydWarshallWithoutPath() ? "Ok" : "Arbitrage");
        }

        in.close();
        out.close();
    }
}