class Quest implements Comparable<Quest> {
    private static int counter = 0;
    int index;
    int energy;
    int gold;

    Quest(int energy, int gold) {
        this(counter++, energy, gold);
    }

    Quest(int index, int energy, int gold) {
        this.index = index;
        this.energy = energy;
        this.gold = gold;
    }

    @Override
    public int compareTo(Quest o) {
        return energy == o.energy ? gold == o.gold ? index - o.index : gold - o.gold : energy - o.energy;
    }
}