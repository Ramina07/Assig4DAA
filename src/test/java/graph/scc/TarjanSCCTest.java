package graph.scc;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class TarjanSCCTest {

    @Test
    public void testSCCDetection() {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(0, List.of(1));
        graph.put(1, List.of(2));
        graph.put(2, List.of(0));
        graph.put(3, List.of(1, 2));

        TarjanSCC tarjan = new TarjanSCC(graph, 4);
        List<List<Integer>> sccs = tarjan.findSCCs();

        // У нас должно быть 2 SCC: {0,1,2} и {3}
        assertEquals(2, sccs.size());
        assertTrue(sccs.stream().anyMatch(c -> c.containsAll(List.of(0,1,2))));
        assertTrue(sccs.stream().anyMatch(c -> c.contains(3)));
    }
}
