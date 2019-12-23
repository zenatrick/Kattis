import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

class BasicInterpreter {
    final static int[] vars = new int[26];

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

        ArrayList<Statement> program = new ArrayList<>();
        HashMap<Integer, Integer> labelMap = new HashMap<>();
        String line;
        while ((line = in.readLine()) != null) {
            String[] input = line.split("\\s");
            int l = Integer.parseInt(input[0]);
            program.add(new Statement(l, input));
        }
        program.sort((x, y) -> x.label - y.label);
        for (int i = 0, n = program.size(); i < n; i++) {
            Statement s = program.get(i);
            labelMap.put(s.label, i);
        }

        for (int i = 0, n = program.size(); i < n; i++) {
            Statement s = program.get(i);
            if (s.type == 0) {
                int varIdx = getVarIdx(s.command[2]);
                if (s.command.length == 5) {
                    vars[varIdx] = getValue(s.command[4]);
                } else {
                    int x = getValue(s.command[4]);
                    int y = getValue(s.command[6]);
                    vars[varIdx] = compute(x, y, s.command[5]);
                }
            } else if (s.type == 1) {
                i = getLabel(i, labelMap, s.command) - 1;
            } else if (s.type == 2) {
                out.print(getStringToPrint(s.command));
            } else {
                out.println(getStringToPrint(s.command));
            }
        }
        in.close();
        out.close();
    }

    private static int getLabel(int i, HashMap<Integer, Integer> labelMap, String[] command) {
        int x = getValue(command[2]);
        int y = getValue(command[4]);
        boolean isTrue = false;
        if ((command[3].equals("=") && x == y) || (command[3].equals("<") && x < y) || (command[3].equals(">") && x > y)
                || (command[3].equals("<=") && x <= y) || (command[3].equals(">=") && x >= y)
                || (command[3].equals("<>") && x != y)) {
            isTrue = true;
        }
        return isTrue ? labelMap.get(Integer.parseInt(command[7])) : i + 1;
    }

    private static String getStringToPrint(String[] command) {
        int x;
        if ((x = command[2].charAt(0)) >= 'A' && x <= 'Z') {
            return String.valueOf(vars[x - 'A']);
        } else {
            StringBuilder builder = new StringBuilder();
            for (int i = 2; i < command.length; i++) {
                builder.append(command[i]);
                builder.append(" ");
            }
            return builder.substring(1, builder.length() - 2);
        }
    }

    private static int compute(int x, int y, String operator) {
        if (operator.equals("+")) {
            return x + y;
        } else if (operator.equals("-")) {
            return x - y;
        } else if (operator.equals("*")) {
            return x * y;
        } else {
            return x / y;
        }
    }

    private static int getValue(String s) {
        int x;
        if ((x = s.charAt(0)) >= 'A' && x <= 'Z') {
            x = vars[x - 'A'];
        } else {
            x = Integer.parseInt(s);
        }
        return x;
    }

    private static int getVarIdx(String s) {
        return s.charAt(0) - 'A';
    }
}