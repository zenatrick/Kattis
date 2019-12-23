import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Random;

class Esej {
    static final long SEED = 1;

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

        String[] input = in.readLine().split("\\s");
        int a = Integer.parseInt(input[0]);
        int b = Integer.parseInt(input[1]);

        /* Initialise a Random object for generating random numbers. */
        Random rng = new Random(SEED);

        /* Initialise a HashSet to keep track of used words. */
        HashSet<String> words = new HashSet<>();

        /* Calculate the number of words required. */
        int numWords = Math.max(a, b / 2);

        /* For each word required, generate a random word and print it. */
        for (int i = 0; i < numWords; i++) {
            int strLength = rng.nextInt(15) + 1;
            String word = generateRandomString(rng, strLength);

            /* Continue to generate a word until a unused word is generated. */
            while (!words.add(word)) {
                strLength = rng.nextInt(15) + 1;
                word = generateRandomString(rng, strLength);
            }

            /* Print the word. Omit the space for the last word. */
            if (i == numWords - 1) {
                out.print(word);
            } else {
                out.print(word + " ");
            }
        }
        in.close();
        out.close();
    }

    /* Generate a random word of given length using the given Random object. */
    public static String generateRandomString(Random rng, int length) {
        StringBuilder buffer = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            buffer.append((char) (97 + rng.nextInt(26)));
        }
        return buffer.toString();
    }
}