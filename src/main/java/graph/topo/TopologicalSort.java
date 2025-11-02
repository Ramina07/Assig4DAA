package graph.topo;

import java.util.*;

public class TopologicalSort {

    // Реализация алгоритма Кана
    public static List<Integer> kahn(Map<Integer, List<Integer>> graph) {
        Map<Integer, Integer> indegree = new HashMap<>();

        // Подсчёт входящих рёбер
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
            order.add(u);

            for (int v : graph.getOrDefault(u, List.of())) {
                indegree.put(v, indegree.get(v) - 1);
                if (indegree.get(v) == 0) q.add(v);
            }
        }

        return order;
    }
}

