package graph;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class SCCFinderTest {
    @Test
    void testSCC() {
        GraphUtils g = new GraphUtils(3);
        g.addEdge(0, 1, 1);
        g.addEdge(1, 2, 1);
        g.addEdge(2, 0, 1);

        SCCFinder sccFinder = new SCCFinder(g);
        List<List<Integer>> sccs = sccFinder.findSCCs();

        assertEquals(1, sccs.size());
        assertEquals(3, sccs.get(0).size());
    }
}
