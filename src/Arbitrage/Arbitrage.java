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
            int numCurrencies = Integer.parseInt(in.readLine());

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

            /* Represent the exchange rate using a graph with adjacency matrix. */
            double[][] adjMtx = new double[numCurrencies][numCurrencies];
            int numEXCs = Integer.parseInt(in.readLine());
            for (int i = 0; i < numEXCs; i++) {
                String[] input = in.readLine().split("\\s");
                String[] rate = input[2].split(":");
                adjMtx[currenciesMap.get(input[0])][currenciesMap.get(input[1])] = Double.parseDouble(rate[1])
                        / Double.parseDouble(rate[0]);
            }

            /*
             * Use modified Floyd-Warshall algorithm. Terminate when arbitrage is detected
             * (when currency exchange rate of itself is more than 1).
             */
            boolean isArbitrage = false;
            for (int k = 0; k < numCurrencies && !isArbitrage; k++) {
                for (int i = 0; i < numCurrencies && !isArbitrage; i++) {
                    for (int j = 0; j < numCurrencies && !isArbitrage; j++) {
                        adjMtx[i][j] = Math.max(adjMtx[i][j], adjMtx[i][k] * adjMtx[k][j]);
                        if (i == j && adjMtx[i][j] > 1) {
                            isArbitrage = true;
                        }
                    }
                }
            }
            out.println(isArbitrage ? "Arbitrage" : "Ok");
        }

        in.close();
        out.close();
    }
}