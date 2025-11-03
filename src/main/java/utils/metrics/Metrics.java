package utils.metrics;

public class Metrics {
    private int dfsVisitCount = 0;        // количество посещённых вершин DFS
    private int dfsEdgeCount = 0;         // количество пройденных рёбер DFS
    private int kahnPopCount = 0;         // количество операций pop в Kahn
    private int relaxations = 0;          // количество релаксаций (PathFinder)
    private long startTime = 0;
    private long endTime = 0;

    // Таймер
    public void startTimer() {
        startTime = System.nanoTime();
    }

    public void stopTimer() {
        endTime = System.nanoTime();
    }

    public long getElapsedTimeMillis() {
        return (endTime - startTime) / 1_000_000;
    }

    // Методы для Tarjan
    public void incrementDFSVisit() {
        dfsVisitCount++;
    }

    public void incrementDFSEdge() {
        dfsEdgeCount++;
    }

    // Методы для TopologicalSort
    public void incrementKahnPop() {
        kahnPopCount++;
    }

    // Методы для PathFinder
    public void incrementRelaxation() {
        relaxations++;
    }

    @Override
    public String toString() {
        return "Metrics{" +
                "DFS visits=" + dfsVisitCount +
                ", DFS edges=" + dfsEdgeCount +
                ", Kahn pops=" + kahnPopCount +
                ", relaxations=" + relaxations +
                ", elapsedTime(ms)=" + getElapsedTimeMillis() +
                '}';
    }
}
