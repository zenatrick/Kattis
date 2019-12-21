import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

class GrandpaBernie {
    public static void main(String[] args) throws Exception {
        FastIO io = new FastIO();

        /* Use a HashMap to map a country to an ArrayList of years. */
        HashMap<String, ArrayList<Integer>> trips = new HashMap<>();

        /*
         * Use a HashSet to quickly check if the list of years for a country has been
         * sorted.
         */
        HashSet<String> sortedCountries = new HashSet<>();

        /* Read the inputs and store them in the HashMap. */
        int numTrips = io.nextPositiveInt();
        for (int i = 0; i < numTrips; i++) {
            String country = io.nextWord();
            int year = io.nextPositiveInt();

            /*
             * Create a new ArrayList for first time encounter of a country, else append the
             * year to the back of the list.
             */
            ArrayList<Integer> years = trips.get(country);
            if (years == null) {
                years = new ArrayList<>();
                trips.put(country, years);
            }
            years.add(year);
        }

        int numQueries = io.nextPositiveInt();
        for (int i = 0; i < numQueries; i++) {
            String country = io.nextWord();
            int k = io.nextPositiveInt();

            /*
             * If a queried list of years is not sorted, sort before querying. Else, can
             * just query immediately.
             */
            if (!sortedCountries.contains(country)) {
                trips.get(country).sort((x, y) -> x - y);
                sortedCountries.add(country);
            }
            io.println(trips.get(country).get(k - 1));
        }

        io.close();
    }
}