package graph.dagsp;

import java.util.*;

public class PathFinder {

    public static Map<Integer, Integer> shortestPaths(Map<Integer, List<Integer>> graph, List<Integer> topoOrder) {
        Map<Integer, Integer> dist = new HashMap<>();
        for (int v : graph.keySet()) dist.put(v, Integer.MAX_VALUE);

        if (!topoOrder.isEmpty())
            dist.put(topoOrder.get(0), 0);

        for (int u : topoOrder) {
            if (dist.get(u) == Integer.MAX_VALUE) continue;
            for (int v : graph.getOrDefault(u, List.of())) {
                dist.put(v, Math.min(dist.get(v), dist.get(u) + 1));
            }
        }

        return dist;
    }

    public static Map<Integer, Integer> longestPaths(Map<Integer, List<Integer>> graph, List<Integer> topoOrder) {
        Map<Integer, Integer> dist = new HashMap<>();
        for (int v : graph.keySet()) dist.put(v, Integer.MIN_VALUE);

        if (!topoOrder.isEmpty())
            dist.put(topoOrder.get(0), 0);

        for (int u : topoOrder) {
            if (dist.get(u) == Integer.MIN_VALUE) continue;
            for (int v : graph.getOrDefault(u, List.of())) {
                dist.put(v, Math.max(dist.get(v), dist.get(u) + 1));
            }
        }

        return dist;
    }

    public static List<Integer> findCriticalPath(Map<Integer, List<Integer>> graph, List<Integer> topoOrder) {
        Map<Integer, Integer> longest = longestPaths(graph, topoOrder);
        int maxDist = Integer.MIN_VALUE;
        int end = -1;

        for (var e : longest.entrySet()) {
            if (e.getValue() > maxDist) {
                maxDist = e.getValue();
                end = e.getKey();
            }
        }

        List<Integer> path = new ArrayList<>();
        path.add(end);
        for (int i = topoOrder.indexOf(end) - 1; i >= 0; i--) {
            int prev = topoOrder.get(i);
            if (graph.getOrDefault(prev, List.of()).contains(end)
                    && longest.get(end) == longest.get(prev) + 1) {
                path.add(prev);
                end = prev;
            }
        }
        Collections.reverse(path);
        return path;
    }
}
