import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

class MissingGnomes {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

        String[] numGnomes = in.readLine().split("\\s");
        int totalGnomes = Integer.parseInt(numGnomes[0]);
        int remainingGnomes = Integer.parseInt(numGnomes[1]);

        /*
         * Initialise both a boolean and int array to keep track of the remaining
         * gnomes. Note that index 0 of gnomesArr is not used as the gnome index starts
         * from 1.
         */
        boolean[] gnomesArr = new boolean[totalGnomes + 1];
        int[] remainingGnomesArr = new int[remainingGnomes];

        /* Read the input gnomes and update the 2 arrays accordingly */
        for (int i = 0; i < remainingGnomes; i++) {
            remainingGnomesArr[i] = Integer.parseInt(in.readLine());
            gnomesArr[remainingGnomesArr[i]] = true;
        }

        /*
         * For each remaining gnome, insert non-remaining gnomes at the front, from the
         * smallest index till the current gnome index. Initialise a pointer to keep
         * track of the inserted gnomes.
         */
        int pointer = 1;
        for (int gnome : remainingGnomesArr) {
            while (pointer < gnome) {
                if (!gnomesArr[pointer]) {
                    out.println(pointer);
                }
                pointer++;
            }
            out.println(gnome);
        }

        /* Print the remaining gnomes if there is any. */
        for (pointer++; pointer < totalGnomes + 1; pointer++) {
            out.println(pointer);
        }

        in.close();
        out.close();
    }
}