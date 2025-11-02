package graph.scc;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class CondensationGraphTest {

    @Test
    public void testBuildCondensation() {
        // Исходный граф
        Map<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(0, List.of(1));
        graph.put(1, List.of(0, 2));
        graph.put(2, List.of());

        // SCC: 0 и 1 в одной компоненте, 2 отдельно
        List<List<Integer>> sccs = List.of(List.of(0, 1), List.of(2));

        Map<Integer, List<Integer>> condensed = CondensationGraph.buildCondensation(graph, sccs);

        assertEquals(2, condensed.size(), "There should be 2 components");

        // Компонента 0 (0,1) должна иметь ребро к компоненте 1 (2)
        assertEquals(List.of(1), condensed.get(0), "Component 0 should point to Component 1");

        // Компонента 1 (2) не должна иметь исходящих рёбер
        assertTrue(condensed.get(1).isEmpty(), "Component 1 should have no outgoing edges");
    }
}
