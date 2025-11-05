package graph;

import java.util.*;

public class Graph {
    public final int n;
    public final boolean directed;
    private final List<Edge>[] adj;

    @SuppressWarnings("unchecked")
    public Graph(int n, boolean directed) {
        this.n = n;
        this.directed = directed;
        adj = new ArrayList[n];
        for (int i = 0; i < n; i++) adj[i] = new ArrayList<>();
    }

    public void addEdge(int u, int v, int w) {
        adj[u].add(new Edge(v, w));
        if (!directed) adj[v].add(new Edge(u, w));
    }

    public List<Edge> getEdges(int u) {
        return adj[u];
    }

    public static class Edge {
        public final int to;
        public final int weight;
        public Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }
}
