package graph;

import java.util.*;

public class DAGShortestLongestPaths {
    private final Graph g;
    private int[] distShortest;
    private int[] distLongest;

    public DAGShortestLongestPaths(Graph g) {
        this.g = g;
        distShortest = new int[g.n];
        distLongest = new int[g.n];
    }

    public void computeShortest(int source) {
        Arrays.fill(distShortest, Integer.MAX_VALUE);
        distShortest[source] = 0;

        TopologicalSorter sorter = new TopologicalSorter(g);
        List<Integer> order = sorter.sort();

        for (int u : order) {
            if (distShortest[u] == Integer.MAX_VALUE) continue;
            for (Graph.Edge e : g.getEdges(u)) {
                int v = e.to;
                if (distShortest[v] > distShortest[u] + e.weight) {
                    distShortest[v] = distShortest[u] + e.weight;
                }
            }
        }
    }

    public void computeLongest(int source) {
        Arrays.fill(distLongest, Integer.MIN_VALUE);
        distLongest[source] = 0;

        TopologicalSorter sorter = new TopologicalSorter(g);
        List<Integer> order = sorter.sort();

        for (int u : order) {
            if (distLongest[u] == Integer.MIN_VALUE) continue;
            for (Graph.Edge e : g.getEdges(u)) {
                int v = e.to;
                if (distLongest[v] < distLongest[u] + e.weight) {
                    distLongest[v] = distLongest[u] + e.weight;
                }
            }
        }
    }

    public void printShortestDistances() {
        for (int i = 0; i < g.n; i++) {
            System.out.println(i + ": " + distShortest[i]);
        }
    }

    public void printLongestDistances() {
        for (int i = 0; i < g.n; i++) {
            System.out.println(i + ": " + distLongest[i]);
        }
    }
}
