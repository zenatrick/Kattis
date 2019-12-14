import java.util.ArrayList;

class SidewaysSorting {
    public static void main(String[] args) throws Exception {
        FastIO io = new FastIO();
        while (true) {
            int rows = io.nextPositiveInt();
            int cols = io.nextPositiveInt();
            if (rows + cols == 0) {
                break;
            }

            // Read the input in a 2D char array such that each row is a string
            char[][] words = new char[cols][rows];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    words[j][i] = io.nextChar();
                }
            }

            // Convert the 2D char array into an ArrayList of strings
            ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i < cols; i++) {
                list.add(String.valueOf(words[i]));
            }

            // Use Java's built-in sort function to sort the list of strings
            list.sort((x, y) -> x.compareToIgnoreCase(y));

            // Print the strings sideway
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    io.write(list.get(j).charAt(i));
                }
                io.write("\n");
            }
            io.write("\n");
        }
        io.close();
    }
}