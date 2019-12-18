import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

class NestedDolls {
    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

        int numTestCases = Integer.parseInt(in.readLine());
        for (int c = 0; c < numTestCases; c++) {
            int numDolls = Integer.parseInt(in.readLine());
            String[] dollsData = in.readLine().split("\\s");

            /* Initialise a Doll array. Read and store the dolls in the array. */
            Doll[] dolls = new Doll[numDolls];
            for (int j = 0; j < numDolls * 2; j = j + 2) {
                dolls[j / 2] = new Doll(Integer.parseInt(dollsData[j]), Integer.parseInt(dollsData[j + 1]));
            }

            /*
             * Sort the dolls primarily by descending width then secondarily by ascending
             * height.
             */
            Arrays.sort(dolls);

            /* Initialise a list of dolls that are nested. */
            ArrayList<Doll> nestedDolls = new ArrayList<>();

            /* Call nestDoll() for each doll. */
            for (Doll doll : dolls) {
                nestDoll(doll, nestedDolls);
            }
            out.println(nestedDolls.size());
        }

        in.close();
        out.close();
    }

    public static void nestDoll(Doll currentDoll, ArrayList<Doll> nestedDolls) {
        /*
         * Add currentDoll to nestedDolls list if we are sure that it cannot be nested
         * in any dolls in nestedDolls.
         * 
         * Since the dolls have been sorted by desending width, currentDoll is
         * gaurenteed to have equal or smaller width as compared to all the nestedDolls.
         * 
         * If currentDoll has equal width to one or more of the doll in nestedDolls,
         * then it can only fit in a doll to the right those dolls of equal width
         * because it has greater height then them. Thus, if the last doll in
         * nestedDolls has equal width to currentDoll, we can be sure that currentDoll
         * will not be able to be nested in any dolls in nestedDolls.
         * 
         * Else, we need to search for the smallest height doll among nestedDolls in
         * which currentDoll can be nested in.
         */
        if (nestedDolls.isEmpty() || nestedDolls.get(nestedDolls.size() - 1).width == currentDoll.width) {
            nestedDolls.add(currentDoll);
            return;
        } else {
            int lo = 0;
            int hi = nestedDolls.size() - 1;
            int toBeNested = -1;
            int mid;

            /*
             * Binary search for the smallest height doll among nestedDolls in which
             * currentDoll can be nested in.
             */
            while (lo <= hi) {
                mid = lo + (hi - lo) / 2;
                if (nestedDolls.get(mid).height <= currentDoll.height) {
                    lo = mid + 1;
                } else {
                    toBeNested = mid;
                    hi = mid - 1;
                }
            }

            /*
             * If no doll is found, add currentDoll into the nestedDolls. Else, replace the
             * found doll with currentDoll to update the restriction.
             */
            if (toBeNested == -1) {
                nestedDolls.add(currentDoll);
            } else {
                nestedDolls.set(toBeNested, currentDoll);
            }
        }
    }
}