package graph;

public class Metrics {
    public long dfsCount = 0;
    public long relaxations = 0;
    private long startTime = 0;
    private long endTime = 0;

    public void startTimer() {
        startTime = System.nanoTime();
    }

    public void stopTimer() {
        endTime = System.nanoTime();
    }

    public long getElapsedMillis() {
        return (endTime - startTime) / 1_000_000;
    }
}
