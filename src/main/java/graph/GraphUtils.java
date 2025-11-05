package graph;

import java.util.ArrayList;
import java.util.List;

public class GraphUtils {
    private int n;
    private List<List<Edge>> adj;

    public GraphUtils(int n) {
        this.n = n;
        adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
    }

    public void addEdge(int u, int v, int w) {
        adj.get(u).add(new Edge(v, w));
    }

    public List<Edge> getNeighbors(int u) {
        return adj.get(u);
    }

    public int size() {
        return n;
    }

    public static class Edge {
        public int to;
        public int weight;

        public Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }
}
