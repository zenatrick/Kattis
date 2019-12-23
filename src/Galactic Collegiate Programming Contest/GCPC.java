import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.TreeSet;

class GCPC {
    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

        String[] data = in.readLine().split("\\s");
        int numTeams = Integer.parseInt(data[0]);
        int numEvents = Integer.parseInt(data[1]);

        /* Initialise an array for easy reference to all teams. */
        Team[] teams = new Team[numTeams];

        /*
         * Initialise a TreeSet so that searching, insertion and removal takes O(logn)
         * time.
         */
        TreeSet<Team> teamsTree = new TreeSet<>();
        for (int i = 0; i < numTeams; i++) {
            teams[i] = new Team(i, 0, 0);
            teamsTree.add(teams[i]);
        }

        int rank = 1;
        for (int i = 0; i < numEvents; i++) {
            String[] message = in.readLine().split("\\s");
            int teamIdx = Integer.parseInt(message[0]) - 1;
            long penalty = Long.parseLong(message[1]);

            /* Keep track of team before the event. */
            Team teamBefore = teams[teamIdx];

            /* Keep track of the team after the event. */
            Team teamAfter = new Team(teamIdx, teamBefore.solved + 1, teamBefore.penalty + penalty);

            /* Update teams to keep track of the most recent score. */
            teams[teamIdx] = teamAfter;

            /* Add teamAfter into tree. */
            teamsTree.add(teamAfter);

            /* Check if this team is my favourite team or not. */
            if (teamIdx == 0) {
                /*
                 * If this team is my favourite team, get the number of teams which my favourite
                 * team has surpassed and update the rank.
                 */
                int surpassed = teamsTree.subSet(teamBefore, false, teamAfter, false).size();
                rank -= surpassed;
            } else {
                /*
                 * If this team is not my favourite team, check if this team overtakes my
                 * favourite team. If so, increase rank by 1.
                 */
                if (teamBefore.isWorseThan(teams[0]) && teamAfter.isBetterThan(teams[0])) {
                    rank++;
                }
            }

            /* Remove teamBefore from tree. */
            teamsTree.remove(teamBefore);

            /* Print rank. */
            out.println(rank);
            out.flush();
        }

        in.close();
        out.close();
    }
}