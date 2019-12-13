class TreasureHunt {
    public static void main(String[] args) {
        Kattio io = new Kattio(System.in);
        int rows = io.getInt();
        int cols = io.getInt();
        char[][] playingArea = new char[rows][cols];
        boolean[][] checkingMtx = new boolean[rows][cols];

        for (int i = 0; i < playingArea.length; i++) {
            playingArea[i] = io.getWord().toCharArray();
        }

        int numMoves = getNumMoves(playingArea, checkingMtx, 0, 0, 0);

        switch (numMoves) {
        case -1:
            io.println("Out");
            break;
        case -2:
            io.println("Lost");
            break;
        default:
            io.println(numMoves);
        }
        io.close();
    }

    public static int getNumMoves(char[][] playingArea, boolean[][] checkingMtx, int currentRow, int currentCol,
            int numMovesTaken) {
        if (currentRow < 0 || currentRow >= playingArea.length || currentCol < 0
                || currentCol >= playingArea[0].length) {
            return -1;
        } else {
            if (checkingMtx[currentRow][currentCol]) {
                return -2;
            } else if (playingArea[currentRow][currentCol] == 'T') {
                return numMovesTaken;
            } else {
                checkingMtx[currentRow][currentCol] = true;
                int numMoves = numMovesTaken;
                switch (playingArea[currentRow][currentCol]) {
                case 'N':
                    numMoves = getNumMoves(playingArea, checkingMtx, currentRow - 1, currentCol, numMovesTaken + 1);
                    break;
                case 'S':
                    numMoves = getNumMoves(playingArea, checkingMtx, currentRow + 1, currentCol, numMovesTaken + 1);
                    break;
                case 'W':
                    numMoves = getNumMoves(playingArea, checkingMtx, currentRow, currentCol - 1, numMovesTaken + 1);
                    break;
                case 'E':
                    numMoves = getNumMoves(playingArea, checkingMtx, currentRow, currentCol + 1, numMovesTaken + 1);
                }
                return numMoves;
            }
        }
    }
}