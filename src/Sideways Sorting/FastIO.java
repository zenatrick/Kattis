import java.io.DataInputStream;
import java.io.DataOutputStream;

public class FastIO {
    final private static int BUFFER_SIZE = 1 << 16;

    private DataInputStream in;
    private DataOutputStream out;
    private byte[] inBuffer;
    private byte[] outBuffer;
    private int inPointer, bytesRead, outPointer;

    public FastIO() {
        in = new DataInputStream(System.in);
        out = new DataOutputStream(System.out);
        inBuffer = new byte[BUFFER_SIZE];
        outBuffer = new byte[BUFFER_SIZE];
        inPointer = bytesRead = outPointer = 0;
    }

    public int nextPositiveInt() throws Exception {
        int value = 0;
        byte c = read();
        while (Character.isWhitespace(c)) {
            c = read();
        }
        value = c - '0';
        while ((c = read()) >= '0' && c <= '9') {
            value = value * 10 + c - '0';
        }
        return value;
    }

    public char nextChar() throws Exception {
        byte c = read();
        while (Character.isWhitespace(c)) {
            c = read();
        }
        return (char) c;
    }

    private byte read() throws Exception {
        if (inPointer == bytesRead) {
            inPointer = 0;
            bytesRead = in.read(inBuffer, inPointer, BUFFER_SIZE);
        }
        return inBuffer[inPointer++];
    }

    private void writeBytes(byte arr[]) throws Exception {
        int bytesToWrite = arr.length;
        if (outPointer + bytesToWrite >= BUFFER_SIZE) {
            flush();
        }
        for (int i = 0; i < bytesToWrite; i++, outPointer++) {
            outBuffer[outPointer] = arr[i];
        }
    }

    public void write(String str) throws Exception {
        writeBytes(str.getBytes());
    }

    public void write(char c) throws Exception {
        writeBytes(String.valueOf(c).getBytes());
    }

    public void flush() throws Exception {
        out.write(outBuffer, 0, outPointer);
        out.flush();
        outPointer = 0;
    }

    public void close() throws Exception {
        if (in != null) {
            in.close();
        }
        if (out != null) {
            flush();
            out.close();
        }
    }
}