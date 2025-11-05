package graph;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class DAGShortestLongestPathsTest {
    @Test
    void testShortestPath() {
        GraphUtils g = new GraphUtils(3);
        g.addEdge(0, 1, 5);
        g.addEdge(1, 2, 3);

        DAGShortestLongestPaths sp = new DAGShortestLongestPaths(g, false);
        sp.compute(0);
        int[] dist = sp.getDistances();

        assertEquals(0, dist[0]);
        assertEquals(5, dist[1]);
        assertEquals(8, dist[2]);

        List<Integer> path = sp.reconstructPath(2);
        assertArrayEquals(new Integer[]{0, 1, 2}, path.toArray(new Integer[0]));
    }
}
