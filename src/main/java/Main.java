import graph.scc.*;
import graph.topo.*;
import graph.dagsp.*;
import utils.metrics.Metrics;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {

        // 1. Загрузка графа
        var data = GraphLoader.loadFromJson("src/main/resources/tasks_small_1.json");

        // 2. Преобразование в Map
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (var e : data.edges) {
            graph.computeIfAbsent(e.u, k -> new ArrayList<>()).add(e.v);
        }

        // ================== Tarjan ==================
        Metrics tarjanMetrics = new Metrics();
        tarjanMetrics.startTimer();
        TarjanSCC tarjan = new TarjanSCC(graph, data.n, tarjanMetrics);
        var sccs = tarjan.findSCCs();
        tarjanMetrics.stopTimer();

        System.out.println("=== Strongly Connected Components ===");
        for (int i = 0; i < sccs.size(); i++) System.out.println("SCC " + i + ": " + sccs.get(i));
        System.out.println("Tarjan Metrics: " + tarjanMetrics);

        // ================== Condensation ==================
        var condensed = CondensationGraph.buildCondensation(graph, sccs);
        System.out.println("\n=== Condensation graph ===");
        for (var entry : condensed.entrySet()) System.out.println(entry.getKey() + " -> " + entry.getValue());

        // ================== Topological Sort ==================
        Metrics kahnMetrics = new Metrics();
        kahnMetrics.startTimer();
        var topoOrder = TopologicalSort.kahn(condensed, kahnMetrics);
        kahnMetrics.stopTimer();
        System.out.println("\n=== Topological Order of Components ===");
        System.out.println(topoOrder);
        System.out.println("Kahn Metrics: " + kahnMetrics);

        // ================== Derived Task Order ==================
        List<Integer> taskOrder = new ArrayList<>();
        for (int comp : topoOrder) taskOrder.addAll(sccs.get(comp));
        System.out.println("\n=== Derived Task Order ===");
        System.out.println(taskOrder);

        // ================== PathFinder ==================
        Metrics pathMetrics = new Metrics();
        pathMetrics.startTimer();

// Shortest paths
        var shortestResult = PathFinder.shortestPaths(condensed, topoOrder, pathMetrics);
        var shortest = shortestResult.distances;
        var prev = shortestResult.prev;

// Longest paths & critical path
        var longest = PathFinder.longestPaths(condensed, topoOrder, pathMetrics);
        var critical = PathFinder.findCriticalPath(condensed, topoOrder, pathMetrics);

        pathMetrics.stopTimer();

        System.out.println("\n=== Shortest paths in DAG ===");
        shortest.forEach((v, d) -> System.out.println("Comp " + v + ": " + d));

// Reconstruct one shortest path example (source -> target)
        int source = topoOrder.get(0);
        int target = topoOrder.get(topoOrder.size() - 1); // можно последний в topoOrder
        List<Integer> shortestPath = PathFinder.reconstructShortestPath(source, target, prev);
        System.out.println("Shortest path from " + source + " to " + target + ": " + shortestPath);

        System.out.println("\n=== Longest paths in DAG ===");
        longest.forEach((v, d) -> System.out.println("Comp " + v + ": " + d));

        System.out.println("\n=== Critical Path (component-level) ===");
        System.out.println(critical);

        System.out.println("\n=== Critical Path (tasks) ===");
        List<Integer> criticalTasks = new ArrayList<>();
        for (int comp : critical) criticalTasks.addAll(sccs.get(comp));
        System.out.println(criticalTasks);

        System.out.println("PathFinder Metrics: " + pathMetrics);

    }
}
