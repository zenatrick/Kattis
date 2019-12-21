import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

class Guess {
    public static void main(String[] args) throws IOException {
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        int upper = 1000;
        int lower = 1;
        int mid;
        for (int i = 0; i < 10 && lower <= upper; i++) {
            mid = (upper + lower) / 2;
            out.println(mid);
            out.flush();

            String line;

            if ((line = in.readLine()) != null) {
                if (line.equals("lower")) {
                    upper = mid - 1;
                } else if (line.equals("higher")) {
                    lower = mid + 1;
                } else {
                    break;
                }
            }
        }

        in.close();
        out.close();
    }
}