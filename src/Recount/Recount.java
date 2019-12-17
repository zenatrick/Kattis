import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;

class Recount {
    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        HashMap<String, Integer> voteCounts = new HashMap<>();
        String name = "";
        int maxCount = 0;
        boolean runoff = false;
        while (true) {
            String candidateName = in.readLine();
            if (candidateName.equals("***")) {
                break;
            }
            Integer count = voteCounts.get(candidateName);
            if (count == null) {
                voteCounts.put(candidateName, 1);
                if (maxCount == 0) {
                    maxCount = 1;
                } else if (maxCount == 1) {
                    runoff = true;
                }
            } else {
                voteCounts.put(candidateName, count + 1);
                if (maxCount < count + 1) {
                    maxCount = count + 1;
                    name = candidateName;
                    runoff = false;
                } else if (maxCount == count + 1) {
                    runoff = true;
                }
            }
        }

        out.println(runoff ? "Runoff!" : name);
        in.close();
        out.close();
    }
}