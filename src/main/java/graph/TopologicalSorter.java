package graph;

import java.util.*;

public class TopologicalSorter {
    private final Graph g;
    public TopologicalSorter(Graph g) {
        this.g = g;
    }

    public List<Integer> sort() {
        int n = g.n;
        int[] inDegree = new int[n];
        for (int u = 0; u < n; u++) {
            for (Graph.Edge e : g.getEdges(u)) {
                inDegree[e.to]++;
            }
        }

        Deque<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < n; i++) if (inDegree[i] == 0) queue.add(i);

        List<Integer> order = new ArrayList<>();
        while (!queue.isEmpty()) {
            int u = queue.poll();
            order.add(u);
            for (Graph.Edge e : g.getEdges(u)) {
                inDegree[e.to]--;
                if (inDegree[e.to] == 0) queue.add(e.to);
            }
        }

        return order;
    }
}
