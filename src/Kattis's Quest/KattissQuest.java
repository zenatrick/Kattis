import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.TreeSet;

class KattissQuest {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

        int numCommands = Integer.parseInt(in.readLine());

        /*
         * Use a TreeSet so that insertion, removal and search takes O(logn) time. Keep
         * track of quest index at there could be repeated quest with same energy and
         * gold.
         */
        TreeSet<Quest> quests = new TreeSet<>();

        for (int i = 0; i < numCommands; i++) {
            String[] input = in.readLine().split("\\s");
            String command = input[0];
            /* Do action according to the command. */
            if (command.equals("add")) {
                quests.add(new Quest(Integer.parseInt(input[1]), Integer.parseInt(input[2])));
            } else {
                out.println(query(quests, Integer.parseInt(input[1])));
            }
        }

        in.close();
        out.close();
    }

    public static long query(TreeSet<Quest> quests, int energySum) {

        Quest quest;
        long goldSum = 0;
        /*
         * Create a quest with highest index value and highest gold value for querying
         * using the floor() method of TreeSet until null is returned.
         */
        while ((quest = quests.floor(new Quest(Integer.MAX_VALUE, energySum, Integer.MAX_VALUE))) != null) {
            /*
             * Add gold to goldSum, deduct energy from energySum and remove quest from the
             * TreeSet.
             */
            goldSum += quest.gold;
            energySum -= quest.energy;
            quests.remove(quest);
        }
        return goldSum;
    }
}