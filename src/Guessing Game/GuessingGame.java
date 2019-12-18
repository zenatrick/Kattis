import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

class GuessingGame {
    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

        int lower = 0;
        int upper = 11;
        while (true) {
            int guess = Integer.parseInt(in.readLine());

            /* Check for end condition. */
            if (guess == 0) {
                break;
            }

            String response = in.readLine();
            /*
             * If response is "too low", update the lower bound. If response if "too high",
             * updated the upper bound. If response is "right on", make sure the guess is
             * strictly more than the lower bound and strictly less than the upper bound.
             */
            if (response.equals("too low")) {
                lower = Math.max(lower, guess);
            } else if (response.equals("too high")) {
                upper = Math.min(upper, guess);
            } else {
                if (guess > lower && guess < upper) {
                    out.println("Stan may be honest");
                } else {
                    out.println("Stan is dishonest");
                }
                /* Reset the lower and upper bound at the end of each game. */
                lower = 0;
                upper = 11;
            }

        }
        in.close();
        out.close();

    }
}