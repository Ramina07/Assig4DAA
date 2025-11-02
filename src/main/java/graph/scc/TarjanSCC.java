package graph.scc;

import java.util.*;

public class TarjanSCC {

    private final Map<Integer, List<Integer>> graph;
    private final int n;

    private int time = 0;
    private final int[] disc;
    private final int[] low;
    private final boolean[] onStack;
    private final Deque<Integer> stack = new ArrayDeque<>();
    private final List<List<Integer>> sccList = new ArrayList<>();

    // Конструктор принимает граф в виде Map и количество вершин
    public TarjanSCC(Map<Integer, List<Integer>> graph, int n) {
        this.graph = graph;
        this.n = n;
        this.disc = new int[n];
        this.low = new int[n];
        this.onStack = new boolean[n];
        Arrays.fill(disc, -1);  // Изначально все вершины не посещены
    }

    // Основной метод для нахождения всех SCC
    public List<List<Integer>> findSCCs() {
        for (int i = 0; i < n; i++) {
            if (disc[i] == -1) {
                dfs(i);
            }
        }
        return sccList;
    }

    // DFS с вычислением low-link значений
    private void dfs(int u) {
        disc[u] = low[u] = ++time;
        stack.push(u);
        onStack[u] = true;

        for (int v : graph.getOrDefault(u, List.of())) {
            if (disc[v] == -1) {
                dfs(v);
                low[u] = Math.min(low[u], low[v]);
            } else if (onStack[v]) {
                low[u] = Math.min(low[u], disc[v]);
            }
        }

        // Если вершина u является корнем SCC
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
