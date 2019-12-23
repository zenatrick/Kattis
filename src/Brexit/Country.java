class Country {
    int numPartners;
    int partnersLeft;

    Country() {
        numPartners = 0;
        partnersLeft = 0;
    }

    void incrementPartners() {
        numPartners++;
    }

    void partnerLeaves() {
        partnersLeft++;
    }

    /* The country will leave if at least half of its partners leave. */
    boolean willLeave() {
        return (numPartners + 1) / 2 <= partnersLeft;
    }
}