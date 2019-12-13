import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

class Svada {
    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

        int totalTime = Integer.parseInt(in.readLine());

        Monkey[] monkeys1 = readMonkeys(in);
        Monkey[] monkeys2 = readMonkeys(in);

        out.println(findMonkeys2ArrivalTime(0, totalTime, totalTime, monkeys1, monkeys2));
        in.close();
        out.close();
    }

    /**
     * Read the monkeys' details from stdin.
     * 
     * @param in BufferedReader to read from stdin
     * @return array of monkeys
     * @throws Exception no exception will be thrown
     */
    public static Monkey[] readMonkeys(BufferedReader in) throws Exception {
        int numMonkeys = Integer.parseInt(in.readLine());
        Monkey[] monkeys = new Monkey[numMonkeys];
        for (int i = 0; i < numMonkeys; i++) {
            String[] values = in.readLine().split("\\s");
            monkeys[i] = new Monkey(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
        }
        return monkeys;
    }

    /**
     * Use binary search to find the arrival time.
     * 
     * @param lo        lower bound of the binary search
     * @param hi        upper bound of the binary search
     * @param totalTime total time that monkeys spent in the garden
     * @param monekys1  array of first type of monekys
     * @param monkeys2  array of second type of monkeys
     * @return second type of monkeys' arrival time
     */
    public static int findMonkeys2ArrivalTime(int lo, int hi, int totalTime, Monkey[] monekys1, Monkey[] monkeys2) {
        int lastestTime = -1;
        if (lo <= hi) {
            int mid = lo + (hi - lo) / 2;

            int monkeys1CoconutCount = calcNumCoconuts(mid, monekys1);
            int monkeys2CoconutCount = calcNumCoconuts(totalTime - mid, monkeys2);

            if (monkeys1CoconutCount > monkeys2CoconutCount) {
                return findMonkeys2ArrivalTime(lo, mid - 1, totalTime, monekys1, monkeys2);
            }

            if (monkeys1CoconutCount <= monkeys2CoconutCount) {
                lastestTime = Math.max(mid, findMonkeys2ArrivalTime(mid + 1, hi, totalTime, monekys1, monkeys2));
            }
        }

        return lastestTime;
    }

    /**
     * Calculates the number of coconuts processed by a given array of monkeys in a
     * given time.
     * 
     * @param time    time given to process the coconuts
     * @param monkeys array of monkeys
     * @return number of coconuts processed by the given array of monkeys in a given
     *         time
     */
    public static int calcNumCoconuts(int time, Monkey[] monkeys) {
        int numCoconuts = 0;
        for (Monkey monkey : monkeys) {
            int count = monkey.getNumCoconuts(time);
            numCoconuts += count;
        }

        return numCoconuts;
    }
}
