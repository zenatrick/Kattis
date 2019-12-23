import java.util.PriorityQueue;

class MedianHeap {
    private PriorityQueue<Integer> maxHeap;
    private PriorityQueue<Integer> minHeap;

    MedianHeap() {
        maxHeap = new PriorityQueue<>((x, y) -> y - x);
        minHeap = new PriorityQueue<>((x, y) -> x - y);
    }

    public void add(Integer x) {
        Integer median = peekMedian();
        if (median == null || x >= median) {
            minHeap.add(x);
        } else {
            maxHeap.add(x);
        }
        rebalance();
    }

    public Integer pollMedian() {
        if (maxHeap.size() - minHeap.size() > 0) {
            return maxHeap.poll();
        } else {
            return minHeap.poll();
        }
    }

    private void rebalance() {
        if (maxHeap.size() - minHeap.size() > 1) {
            minHeap.add(maxHeap.poll());
        } else if (minHeap.size() - maxHeap.size() > 1) {
            maxHeap.add(minHeap.poll());
        }
    }

    Integer peekMedian() {
        if (maxHeap.size() - minHeap.size() > 0) {
            return maxHeap.peek();
        } else {
            return minHeap.peek();
        }
    }
}