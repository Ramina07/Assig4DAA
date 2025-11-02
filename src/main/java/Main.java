import graph.scc.*;
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

        // 3. Поиск SCC
        TarjanSCC tarjan = new TarjanSCC(graph, data.n);
        var sccs = tarjan.findSCCs();

        System.out.println("Strongly Connected Components:");
        for (int i = 0; i < sccs.size(); i++) {
            System.out.println("SCC " + i + ": " + sccs.get(i));
        }

        // 4. Конденсация
        var condensed = CondensationGraph.buildCondensation(graph, sccs);
        System.out.println("\nCondensation graph:");
        for (var entry : condensed.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}

