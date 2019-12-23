class Team implements Comparable<Team> {
    int index;
    int solved;
    long penalty;

    Team(int index, int solved, long penalty) {
        this.index = index;
        this.solved = solved;
        this.penalty = penalty;
    }

    @Override
    public int compareTo(Team other) {
        if (solved != other.solved) {
            return solved - other.solved;
        } else {
            if (other.penalty - penalty < 0) {
                return -1;
            } else if (other.penalty - penalty > 0) {
                return 1;
            } else {
                return other.index - index;
            }
        }
    }

    boolean isBetterThan(Team other) {
        if (solved < other.solved || (solved == other.solved && penalty >= other.penalty)) {
            return false;
        }
        return true;
    }

    boolean isWorseThan(Team other) {
        if (solved > other.solved || (solved == other.solved && penalty < other.penalty)) {
            return false;
        }
        return true;
    }
}