import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;

class CookieSelection {
    public static void main(String[] args) throws Exception {
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        MedianHeap cookies = new MedianHeap();

        String line;
        while ((line = in.readLine()) != null) {
            if (line.equals("#")) {
                out.println(cookies.pollMedian());
            } else {
                cookies.add(Integer.parseInt(line));
            }
        }

        out.close();
        in.close();
    }
}