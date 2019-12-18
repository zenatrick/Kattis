import java.util.Arrays;

class APrizeNoOneCanWin {
    public static void main(String[] args) {
        Kattio io = new Kattio(System.in);
        int numItems = io.getInt();
        int priceLimit = io.getInt();
        int[] prices = new int[numItems];
        for (int i = 0; i < numItems; i++) {
            prices[i] = io.getInt();
        }
        io.println(getMaxItems(prices, priceLimit));
        io.close();
    }

    public static int getMaxItems(int[] prices, int priceLimit) {
        int max = 1;
        Arrays.sort(prices);
        for (int i = 0; i < prices.length - 1; i++) {
            if (prices[i] + prices[i + 1] <= priceLimit) {
                max++;
            } else {
                break;
            }
        }
        return max;
    }
}