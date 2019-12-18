class Different {
    public static void main(String[] args) {
        Kattio io = new Kattio(System.in);
        while (io.hasMoreTokens()) {
            long first = io.getLong();
            long second = io.getLong();
            io.println(Math.abs(first - second));
        }
        io.close();
    }
}