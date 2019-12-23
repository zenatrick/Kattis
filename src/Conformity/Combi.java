import java.util.Arrays;

class Combi {
    String[] combi;

    Combi(String str) {
        combi = str.split("\\s");
        Arrays.sort(combi);
    }

    @Override
    public boolean equals(Object obj) {
        String[] other = ((Combi) obj).combi;
        return Arrays.equals(combi, other);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(combi);
    }
}