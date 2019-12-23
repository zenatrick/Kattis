class Statement {
    int label;
    String[] command;
    int type;

    Statement(int label, String[] command) {
        this.label = label;
        this.command = command;
        if (this.command[1].equals("LET")) {
            type = 0;
        } else if (this.command[1].equals("IF")) {
            type = 1;
        } else if (this.command[1].equals("PRINT")) {
            type = 2;
        } else {
            type = 3;
        }
    }
}