import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.BufferedOutputStream;

class Autori {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

        String longVariation = in.readLine();
        String[] lastNames = longVariation.split("-");
        for (String name : lastNames) {
            out.print(name.charAt(0));
        }
        out.println();
        in.close();
        out.close();

    }
}