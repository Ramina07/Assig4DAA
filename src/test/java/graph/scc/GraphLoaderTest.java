package graph.scc;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class GraphLoaderTest {

    @Test
    public void testLoadFromJson() throws IOException {
        // Подставь путь к маленькому тестовому JSON в src/test/resources
        String path = "src/test/resources/test_graph.json";
        GraphLoader.GraphData data = GraphLoader.loadFromJson(path);

        assertNotNull(data, "GraphData should not be null");
        assertTrue(data.n > 0, "Number of vertices should be positive");
        assertNotNull(data.edges, "Edges list should not be null");
        assertFalse(data.edges.isEmpty(), "Edges list should not be empty");

        // Пример проверки первого ребра
        GraphLoader.Edge firstEdge = data.edges.get(0);
        assertTrue(firstEdge.u >= 0, "Edge source should be >= 0");
        assertTrue(firstEdge.v >= 0, "Edge target should be >= 0");
    }
}
