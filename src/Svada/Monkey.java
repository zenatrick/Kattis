/**
 * Represents a monkey with an initial and a subsequent timing for processing
 * coconuts.
 */
class Monkey {
    private int initial;
    private int subsequent;

    Monkey(int initial, int subsequent) {
        this.initial = initial;
        this.subsequent = subsequent;
    }

    /**
     * Get the number of coconuts processed in a given time.
     * 
     * @param time time for processing coconuts
     * @return number of coconuts processed
     */
    int getNumCoconuts(int time) {
        if (time < initial) {
            return 0;
        }

        return 1 + (time - initial) / subsequent;
    }
}