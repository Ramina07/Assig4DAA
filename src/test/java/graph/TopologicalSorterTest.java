package graph;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class TopologicalSorterTest {
    @Test
    void testTopo() {
        GraphUtils g = new GraphUtils(3);
        g.addEdge(0, 1, 1);
        g.addEdge(1, 2, 1);

        TopologicalSorter topo = new TopologicalSorter(g);
        List<Integer> order = topo.sort();

        assertEquals(3, order.size());
        assertTrue(order.indexOf(0) < order.indexOf(1));
        assertTrue(order.indexOf(1) < order.indexOf(2));
    }
}
