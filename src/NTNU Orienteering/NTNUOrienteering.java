import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;

class NTNUOrienteering {
    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

        int numCases = Integer.parseInt(in.readLine());
        for (int c = 0; c < numCases; c++) {
            int numCampuses = Integer.parseInt(in.readLine());
            int numLectures = Integer.parseInt(in.readLine());
            int numEdges = numCampuses * (numCampuses - 1) / 2;

            /* Make campus graph and add all the edges to the graph. */
            APSPGraph campusGraph = APSPGraph.initGraphForAPSPDist(numCampuses, numEdges);
            for (int i = 0; i < numEdges; i++) {
                String[] edgeInfo = in.readLine().split("\\s");
                int u = Integer.parseInt(edgeInfo[0]);
                int v = Integer.parseInt(edgeInfo[1]);
                int weight = Integer.parseInt(edgeInfo[2]);
                campusGraph.addEdge(u, v, weight);
                campusGraph.addEdge(v, u, weight);
            }

            /* Perform Floyd-Warshall algorithm. */
            campusGraph.floydWarshallWithoutPath();

            /*
             * Read lectures info and sort the lectures in a list. Initialise a graph for
             * the lectures.
             */
            ArrayList<Lecture> listOfLectures = new ArrayList<>();
            ArrayList<ArrayList<Integer>> lectureGraph = new ArrayList<>();
            for (int i = 0; i < numLectures; i++) {
                String[] edgeInfo = in.readLine().split("\\s");
                int campus = Integer.parseInt(edgeInfo[0]);
                int startTime = Integer.parseInt(edgeInfo[1]);
                int endTime = Integer.parseInt(edgeInfo[2]);
                listOfLectures.add(new Lecture(campus, startTime, endTime));
                lectureGraph.add(new ArrayList<>());
            }

            /*
             * Build the lecture graph. If current lecutre's end time + duration to get from
             * current campus to next campus <= next lecture's start time, connect the
             * current lecture to the next lecture with an edge.
             */
            for (int i = 0; i < numLectures; i++) {
                ArrayList<Integer> listOfEdges = lectureGraph.get(i);
                for (int j = 0; j < numLectures; j++) {
                    Lecture curr = listOfLectures.get(i);
                    Lecture next = listOfLectures.get(j);
                    if (curr.endTime + campusGraph.getDistOf(curr.campus, next.campus) <= next.startTime) {
                        listOfEdges.add(j);
                    }
                }
            }

            /* Topological sort on the lecture graph. */
            boolean[] visited = new boolean[numLectures];
            LinkedList<Integer> sortedGraph = new LinkedList<>();
            for (int i = 0; i < numLectures; i++) {
                if (!visited[i]) {
                    toposort(i, lectureGraph, visited, sortedGraph);
                }
            }

            /*
             * Find longest path of lecture graph by incrementing count in topological
             * order.
             */
            int maxCount = 0;
            int[] numLecturesArr = new int[numLectures];
            while (!sortedGraph.isEmpty()) {
                int current = sortedGraph.pop();
                ArrayList<Integer> neighbours = lectureGraph.get(current);
                for (int neighbour : neighbours) {
                    // Update neighbour if neighbour is less than current count + 1
                    if (numLecturesArr[neighbour] < numLecturesArr[current] + 1) {
                        numLecturesArr[neighbour] = numLecturesArr[current] + 1;
                    }
                    // Update maxCount if overtaken
                    maxCount = Math.max(maxCount, numLecturesArr[neighbour]);
                }
            }
            out.println(maxCount + 1);

        }

        in.close();
        out.close();
    }

    private static void toposort(int current, ArrayList<ArrayList<Integer>> lectureGraph, boolean[] visited,
            LinkedList<Integer> sortedGraph) {
        if (visited[current]) {
            return;
        }
        ArrayList<Integer> neighbours = lectureGraph.get(current);
        for (int neighbour : neighbours) {
            toposort(neighbour, lectureGraph, visited, sortedGraph);
        }
        visited[current] = true;
        sortedGraph.push(current);
    }
}