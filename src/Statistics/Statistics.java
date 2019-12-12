import java.util.Arrays;

class Statistics {
    public static void main(String[] args) {
        Kattio io = new Kattio(System.in);
        int caseNumber = 0;

        while (io.hasMoreTokens()) {
            caseNumber++;
            int numIntegers = io.getInt();
            int[] integers = new int[numIntegers];
            for (int i = 0; i < numIntegers; i++) {
                integers[i] = io.getInt();
            }
            Arrays.sort(integers);
            int min = integers[0];
            int max = integers[numIntegers - 1];
            int range = max - min;
            io.printf("Case %d: %d %d %d\n", caseNumber, min, max, range);
        }
        io.close();
    }
}