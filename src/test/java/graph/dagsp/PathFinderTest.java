package graph.dagsp;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class PathFinderTest {

    @Test
    public void testShortestAndLongestPath() {
        // Простейший DAG
        Map<Integer, List<Integer>> dag = new HashMap<>();
        dag.put(0, List.of(1, 2));
        dag.put(1, List.of(3));
        dag.put(2, List.of(3));
        dag.put(3, List.of());

        // Топологический порядок
        List<Integer> topoOrder = List.of(0, 1, 2, 3);

        Map<Integer, Integer> shortest = PathFinder.shortestPaths(dag, topoOrder);
        Map<Integer, Integer> longest = PathFinder.longestPaths(dag, topoOrder);

        // Проверка кратчайших расстояний
        assertEquals(0, shortest.get(0));
        assertEquals(1, shortest.get(1));
        assertEquals(1, shortest.get(2));
        assertEquals(2, shortest.get(3));

        // Проверка длиннейших расстояний
        assertEquals(0, longest.get(0));
        assertEquals(1, longest.get(1));
        assertEquals(1, longest.get(2));
        assertEquals(2, longest.get(3));
    }
}
