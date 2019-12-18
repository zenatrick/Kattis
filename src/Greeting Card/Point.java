import java.util.Arrays;

class Point {
    /*
     * By Pythagoras theorem, 1118^2 + 1680^2 = 2108^2. Use this information to
     * determine points 2018 units away from a given point.
     */
    static final int A = 1118;
    static final int B = 1680;
    static final int C = 2018;
    int x;
    int y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the array of reachable points from this point. Reachable points are 2018
     * units away and there are only 12 reachable points per point.
     * 
     * @return The array of reachable points from this point.
     */
    Point[] getReachablePoints() {
        return new Point[] { new Point(x - C, y), new Point(x, y + C), new Point(x + C, y), new Point(x, y - C),
                new Point(x + A, y + B), new Point(x + B, y + A), new Point(x + B, y - A), new Point(x + A, y - B),
                new Point(x - A, y - B), new Point(x - B, y - A), new Point(x - B, y + A), new Point(x - A, y + B) };
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        Point other = (Point) obj;
        return other.x == x && other.y == y;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new int[] { x, y });
    }
}