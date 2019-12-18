class Doll implements Comparable<Doll> {
    int width;
    int height;

    Doll(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /*
     * Dolls will be sorted primarily by descending width and secondarily be
     * ascending height.
     */
    @Override
    public int compareTo(Doll doll) {
        if (width == doll.width) {
            return height - doll.height;
        } else {
            return doll.width - width;
        }
    }
}