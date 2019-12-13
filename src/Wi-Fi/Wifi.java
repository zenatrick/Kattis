import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;

class Wifi {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

        int numTestCases = Integer.parseInt(in.readLine());

        for (int i = 0; i < numTestCases; i++) {
            handleTestCase(in, out);
        }

        in.close();
        out.close();
    }

    public static void handleTestCase(BufferedReader in, PrintWriter out) throws IOException {
        String[] values = in.readLine().split("\\s");
        int numAccessPoints = Integer.parseInt(values[0]);
        int numHouses = Integer.parseInt(values[1]);

        int[] houseNumbers = new int[numHouses];
        for (int i = 0; i < numHouses; i++) {
            houseNumbers[i] = Integer.parseInt(in.readLine());
        }

        if (numAccessPoints >= numHouses) {
            out.println("0.0");
            return;
        }

        Arrays.sort(houseNumbers);

        int lo = 0;
        int hi = 10_000_000;
        int maxDistance = Integer.MAX_VALUE;

        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (canAccessWifi(houseNumbers, mid, numAccessPoints)) {
                hi = mid - 1;
                maxDistance = mid;
            } else {
                lo = mid + 1;
            }
        }

        out.printf("%.1f\n", maxDistance / 10.0);
    }

    public static boolean canAccessWifi(int[] houseNumbers, int maxDistance, int numAccessPoints) {
        int counter = 1;
        double range = maxDistance / 5.0;
        double totalCoveredRange = houseNumbers[0] + range;
        for (int i = 1; i < houseNumbers.length; i++) {
            if (houseNumbers[i] > totalCoveredRange) {
                totalCoveredRange = houseNumbers[i] + range;
                counter++;
            }
        }

        return counter <= numAccessPoints;
    }
}