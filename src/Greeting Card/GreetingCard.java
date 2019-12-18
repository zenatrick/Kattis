import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashSet;

class GreetingCard {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

        int numPoint = Integer.parseInt(in.readLine());

        /* Iitialise a HashSet to keep track of the added points. */
        HashSet<Point> allPoints = new HashSet<>();

        /* Initialise count to keep track of the number of pairs. */
        int count = 0;

        /*
         * For each input point, add it into the HashSet allPoints and check the
         * reachable points that are 2018 units away have already been added into
         * allPoints. If so, the 2 points will form a pair, thus increment count.
         */
        for (int i = 0; i < numPoint; i++) {
            String[] pointStr = in.readLine().split("\\s");
            Point point = new Point(Integer.parseInt(pointStr[0]), Integer.parseInt(pointStr[1]));
            allPoints.add(point);
            Point[] reachablePoints = point.getReachablePoints();
            for (Point p : reachablePoints) {
                if (allPoints.contains(p)) {
                    count++;
                }
            }
        }

        /* Print the result. */
        out.println(count);
        in.close();
        out.close();
    }
}