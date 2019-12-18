import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashSet;

class Everywhere {

    static HashSet<String> trips;

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

        int numCases = Integer.parseInt(in.readLine());

        for (int i = 0; i < numCases; i++) {
            int numTrips = Integer.parseInt(in.readLine());
            trips = new HashSet<>();
            int count = 0;
            for (int j = 0; j < numTrips; j++) {
                String city = in.readLine();
                if (trips.add(city)) {
                    count++;
                }
            }
            out.println(count);
        }
        out.close();
    }
}