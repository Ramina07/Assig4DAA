package graph;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        String[] filenames = {
                "tasks_small_1.json",
                "tasks_small_2.json",
                "tasks_small_3.json",
                "tasks_medium_1.json",
                "tasks_medium_2.json",
                "tasks_medium_3.json",
                "tasks_large_1.json",
                "tasks_large_2.json",
                "tasks_large_3.json"
        };

        ObjectMapper mapper = new ObjectMapper();

        for (String filename : filenames) {
            File file = new File("src/main/data/" + filename);
            GraphInput input = mapper.readValue(file, GraphInput.class);

            System.out.println("\n=== Loaded file: " + filename + " ===");
            System.out.println("Vertices: " + input.n);
            System.out.println("Weight model: " + input.weight_model);
            System.out.println("Source: " + input.source);

            // Build graph
            Graph graph = new Graph(input.n, input.directed);
            for (GraphInput.Edge e : input.edges) {
                graph.addEdge(e.u, e.v, e.w);
            }

            // 1. SCC + condensation
            SCCFinder sccFinder = new SCCFinder(graph);
            List<List<Integer>> sccs = sccFinder.findSCCs();
            System.out.println("SCCs: " + sccs);

            Graph dagCondensation = sccFinder.getCondensationGraph();

            // 2. Topological sort
            TopologicalSorter topoSorter = new TopologicalSorter(dagCondensation);
            List<Integer> topoOrder = topoSorter.sort();
            System.out.println("Topological order (components): " + topoOrder);

            // 3. Shortest and Longest paths on DAG
            DAGShortestLongestPaths sp = new DAGShortestLongestPaths(dagCondensation);
            sp.computeShortest(input.source);
            sp.computeLongest(input.source);

            System.out.println("Shortest distances from " + input.source + ":");
            sp.printShortestDistances();

            System.out.println("Longest distances from " + input.source + ":");
            sp.printLongestDistances();
        }

        System.out.println("\nAll JSON files processed successfully.");
    }

    public static class GraphInput {
        public boolean directed;
        public int n;
        public List<Edge> edges;
        public int source;
        public String weight_model;

        public static class Edge {
            public int u, v, w;
        }
    }
}
