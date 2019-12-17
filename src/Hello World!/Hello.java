import java.io.BufferedOutputStream;
import java.io.PrintWriter;

class Hello {
    public static void main(String[] args) {
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        out.println("Hello World!");
        out.close();
    }
}