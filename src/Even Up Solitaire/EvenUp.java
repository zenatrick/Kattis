import java.util.LinkedList;

class EvenUp {
    public static void main(String[] args) throws Exception {
        FastIO io = new FastIO();
        int numCard = io.nextPositiveInt();
        LinkedList<Integer> stack = new LinkedList<>();
        for (int i = 0; i < numCard; i++) {
            Integer prev = stack.peek();
            int curr = io.nextPositiveInt();

            /*
             * If prev exist and the sum of prev and curr is even, pop the previous element
             * from the stack. Else, push the current element into the stack.
             */
            if (prev != null && (prev + curr) % 2 == 0) {
                stack.pop();
            } else {
                stack.push(curr);
            }
        }

        /* The size of the stack will be the number of cards remaining. */
        io.write(String.format("%d\n", stack.size()));
        io.close();
    }
}