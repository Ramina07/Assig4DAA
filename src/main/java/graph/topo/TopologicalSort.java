package graph.topo;

import utils.metrics.Metrics;
import java.util.*;

public class TopologicalSort {

    public static List<Integer> kahn(Map<Integer, List<Integer>> graph, Metrics metrics) {
        Map<Integer, Integer> indegree = new HashMap<>();

        for (var u : graph.keySet()) {
            indegree.putIfAbsent(u, 0);
            for (int v : graph.get(u)) {
                indegree.put(v, indegree.getOrDefault(v, 0) + 1);
            }
        }

        Queue<Integer> q = new ArrayDeque<>();
        for (var e : indegree.entrySet()) {
            if (e.getValue() == 0) q.add(e.getKey());
        }

        List<Integer> order = new ArrayList<>();
        while (!q.isEmpty()) {
            int u = q.poll();
            metrics.incrementKahnPop();
            order.add(u);

            for (int v : graph.getOrDefault(u, List.of())) {
                indegree.put(v, indegree.get(v) - 1);
                if (indegree.get(v) == 0) q.add(v);
            }
        }

        return order;
    }
}
