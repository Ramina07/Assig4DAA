package graph;

import java.util.*;

public class SCCFinder {
    private final Graph g;
    private int index = 0;
    private final int[] indices, lowlink;
    private final boolean[] onStack;
    private final Deque<Integer> stack = new ArrayDeque<>();
    private final List<List<Integer>> sccs = new ArrayList<>();
    private Graph condensation;

    public SCCFinder(Graph g) {
        this.g = g;
        indices = new int[g.n];
        Arrays.fill(indices, -1);
        lowlink = new int[g.n];
        onStack = new boolean[g.n];
    }

    public List<List<Integer>> findSCCs() {
        for (int v = 0; v < g.n; v++) {
            if (indices[v] == -1) dfs(v);
        }
        buildCondensation();
        return sccs;
    }

    private void dfs(int v) {
        indices[v] = index;
        lowlink[v] = index++;
        stack.push(v);
        onStack[v] = true;

        for (Graph.Edge e : g.getEdges(v)) {
            int w = e.to;
            if (indices[w] == -1) {
                dfs(w);
                lowlink[v] = Math.min(lowlink[v], lowlink[w]);
            } else if (onStack[w]) {
                lowlink[v] = Math.min(lowlink[v], indices[w]);
            }
        }

        if (lowlink[v] == indices[v]) {
            List<Integer> component = new ArrayList<>();
            int w;
            do {
                w = stack.pop();
                onStack[w] = false;
                component.add(w);
            } while (w != v);
            sccs.add(component);
        }
    }

    private void buildCondensation() {
        int k = sccs.size();
        condensation = new Graph(k, true);
        Map<Integer, Integer> nodeToComponent = new HashMap<>();
        for (int i = 0; i < k; i++) {
            for (int v : sccs.get(i)) nodeToComponent.put(v, i);
        }

        for (int u = 0; u < g.n; u++) {
            for (Graph.Edge e : g.getEdges(u)) {
                int cu = nodeToComponent.get(u);
                int cv = nodeToComponent.get(e.to);
                if (cu != cv) condensation.addEdge(cu, cv, e.weight);
            }
        }
    }

    public Graph getCondensationGraph() {
        return condensation;
    }
}
