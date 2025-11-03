package graph.dagsp;

import utils.metrics.Metrics;
import java.util.*;

public class PathFinder {

    public static class Result {
        public final Map<Integer, Integer> distances;
        public final Map<Integer, Integer> prev;

        public Result(Map<Integer, Integer> distances, Map<Integer, Integer> prev) {
            this.distances = distances;
            this.prev = prev;
        }
    }

    // Shortest paths with reconstruction
    public static Result shortestPaths(Map<Integer, List<Integer>> graph, List<Integer> topoOrder, Metrics metrics) {
        Map<Integer, Integer> dist = new HashMap<>();
        Map<Integer, Integer> prev = new HashMap<>();
        for (int v : graph.keySet()) dist.put(v, Integer.MAX_VALUE);

        if (!topoOrder.isEmpty()) dist.put(topoOrder.get(0), 0);

        for (int u : topoOrder) {
            if (dist.get(u) == Integer.MAX_VALUE) continue;
            for (int v : graph.getOrDefault(u, List.of())) {
                int newDist = Math.min(dist.get(v), dist.get(u) + 1);
                if (newDist != dist.get(v)) {
                    dist.put(v, newDist);
                    prev.put(v, u);
                    metrics.incrementRelaxation();
                }
            }
        }
        return new Result(dist, prev);
    }

    // Reconstruct shortest path from source to target
    public static List<Integer> reconstructShortestPath(int source, int target, Map<Integer, Integer> prev) {
        List<Integer> path = new ArrayList<>();
        Integer at = target;
        while (at != null && at != source) {
            path.add(at);
            at = prev.get(at);
        }
        if (at == null) return Collections.emptyList(); // no path
        path.add(source);
        Collections.reverse(path);
        return path;
    }

    // Longest paths
    public static Map<Integer, Integer> longestPaths(Map<Integer, List<Integer>> graph, List<Integer> topoOrder, Metrics metrics) {
        Map<Integer, Integer> dist = new HashMap<>();
        for (int v : graph.keySet()) dist.put(v, Integer.MIN_VALUE);

        if (!topoOrder.isEmpty()) dist.put(topoOrder.get(0), 0);

        for (int u : topoOrder) {
            if (dist.get(u) == Integer.MIN_VALUE) continue;
            for (int v : graph.getOrDefault(u, List.of())) {
                int newDist = Math.max(dist.get(v), dist.get(u) + 1);
                if (newDist != dist.get(v)) metrics.incrementRelaxation();
                dist.put(v, newDist);
            }
        }
        return dist;
    }

    // Critical path (longest)
    public static List<Integer> findCriticalPath(Map<Integer, List<Integer>> graph, List<Integer> topoOrder, Metrics metrics) {
        Map<Integer, Integer> longest = longestPaths(graph, topoOrder, metrics);
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
