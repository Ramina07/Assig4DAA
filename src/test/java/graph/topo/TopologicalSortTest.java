package graph.topo;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class TopologicalSortTest {

    @Test
    public void testKahnSort() {
        Map<Integer, List<Integer>> dag = new HashMap<>();
        dag.put(0, List.of(1, 2));
        dag.put(1, List.of(3));
        dag.put(2, List.of(3));
        dag.put(3, List.of());

        List<Integer> topoOrder = TopologicalSort.kahn(dag);

        // Проверка: валидный топологический порядок
        assertEquals(dag.size(), topoOrder.size());
        assertTrue(topoOrder.containsAll(dag.keySet()));
        assertTrue(topoOrder.indexOf(0) < topoOrder.indexOf(1));
        assertTrue(topoOrder.indexOf(0) < topoOrder.indexOf(2));
        assertTrue(topoOrder.indexOf(1) < topoOrder.indexOf(3));
        assertTrue(topoOrder.indexOf(2) < topoOrder.indexOf(3));
    }
}
