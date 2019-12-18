import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

class MusicYourWay {
    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

        /*
         * Read and store attributes in a HashMap such that each attribute is mapped to
         * an index.
         */
        String[] attributes = in.readLine().split("\\s");
        HashMap<String, Integer> attributeIndex = new HashMap<>();
        for (int i = 0; i < attributes.length; i++) {
            attributeIndex.put(attributes[i], i);
        }

        /* Read and store the list of songs in an ArrayList. */
        ArrayList<String[]> songs = new ArrayList<>();
        int numSongs = Integer.parseInt(in.readLine());
        for (int i = 0; i < numSongs; i++) {
            songs.add(in.readLine().split("\\s"));
        }

        /*
         * For each sorting command, get the attribute index to be sorted by and perform
         * sorting. Then, print the sorted list of songs.
         */
        int numSortings = Integer.parseInt(in.readLine());
        for (int i = 0; i < numSortings; i++) {
            int sortingIndex = attributeIndex.get(in.readLine());
            songs.sort((x, y) -> x[sortingIndex].compareTo(y[sortingIndex]));
            out.println(String.join(" ", attributes));
            for (String[] song : songs) {
                out.println(String.join(" ", song));
            }
            out.println();
        }
        in.close();
        out.close();
    }
}