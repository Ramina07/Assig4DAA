package graph.scc;

import java.util.*;

public class CondensationGraph {

    public static Map<Integer, List<Integer>> buildCondensation(
            Map<Integer, List<Integer>> graph, List<List<Integer>> sccs) {

        Map<Integer, Integer> nodeToComp = new HashMap<>();
        for (int i = 0; i < sccs.size(); i++) {
            for (int node : sccs.get(i)) nodeToComp.put(node, i);
        }

        Map<Integer, Set<Integer>> dag = new HashMap<>();
        for (int i = 0; i < sccs.size(); i++) dag.put(i, new HashSet<>());

        for (var entry : graph.entrySet()) {
            int u = entry.getKey();
            for (int v : entry.getValue()) {
                int cu = nodeToComp.get(u);
                int cv = nodeToComp.get(v);
                if (cu != cv) dag.get(cu).add(cv);
            }
        }

        Map<Integer, List<Integer>> condensed = new HashMap<>();
        for (var e : dag.entrySet()) {
            condensed.put(e.getKey(), new ArrayList<>(e.getValue()));
        }

        return condensed;
    }
}

