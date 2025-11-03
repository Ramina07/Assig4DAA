package graph.scc;

import utils.metrics.Metrics;
import java.util.*;

public class TarjanSCC {

    private final Map<Integer, List<Integer>> graph;
    private final int n;
    private final Metrics metrics;

    private int time = 0;
    private final int[] disc;
    private final int[] low;
    private final boolean[] onStack;
    private final Deque<Integer> stack = new ArrayDeque<>();
    private final List<List<Integer>> sccList = new ArrayList<>();

    public TarjanSCC(Map<Integer, List<Integer>> graph, int n, Metrics metrics) {
        this.graph = graph;
        this.n = n;
        this.metrics = metrics;
        this.disc = new int[n];
        this.low = new int[n];
        this.onStack = new boolean[n];
        Arrays.fill(disc, -1);
    }

    public List<List<Integer>> findSCCs() {
        for (int i = 0; i < n; i++) {
            if (disc[i] == -1) {
                dfs(i);
            }
        }
        return sccList;
    }

    private void dfs(int u) {
        metrics.incrementDFSVisit();

        disc[u] = low[u] = ++time;
        stack.push(u);
        onStack[u] = true;

        for (int v : graph.getOrDefault(u, List.of())) {
            metrics.incrementDFSEdge();
            if (disc[v] == -1) {
                dfs(v);
                low[u] = Math.min(low[u], low[v]);
            } else if (onStack[v]) {
                low[u] = Math.min(low[u], disc[v]);
            }
        }

        if (low[u] == disc[u]) {
            List<Integer> component = new ArrayList<>();
            int v;
            do {
                v = stack.pop();
                onStack[v] = false;
                component.add(v);
            } while (v != u);
            sccList.add(component);
        }
    }
}
