import java.util.Arrays;

class SortOfSorting {
    public static void main(String[] args) {
        Kattio io = new Kattio(System.in);
        while (io.hasMoreTokens()) {
            int numNames = io.getInt();
            if (numNames == 0) {
                break;
            }

            String[] names = new String[numNames];
            for (int i = 0; i < numNames; i++) {
                names[i] = io.getWord();
            }

            Arrays.sort(names, (name1, name2) -> {
                if (name1.charAt(0) == name2.charAt(0)) {
                    return name1.charAt(1) - name2.charAt(1);
                } else {
                    return name1.charAt(0) - name2.charAt(0);
                }
            });

            for (String name : names) {
                io.println(name);
            }
            io.println();
        }
        io.close();
    }
}