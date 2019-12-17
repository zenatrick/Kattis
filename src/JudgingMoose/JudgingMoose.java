class JudgingMoose {
    public static void main(String[] args) {
        Kattio io = new Kattio(System.in);
        int left = io.getInt();
        int right = io.getInt();

        if (left == 0 && right == 0) {
            io.println("Not a moose");
        } else if (left == right) {
            io.println("Even " + 2 * left);
        } else {
            io.println("Odd " + 2 * Math.max(left, right));
        }

        io.close();
    }
}