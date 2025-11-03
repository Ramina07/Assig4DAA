# Smart City / Smart Campus Scheduling - Assignment 4

## Goal
This project consolidates two course topics in one practical case:

1. Strongly Connected Components (SCC) & Topological Ordering  
2. Shortest and Longest Paths in Directed Acyclic Graphs (DAGs)  

The project uses a dataset of city-service and internal analytics tasks with dependencies, some of which are cyclic (SCC detection) and others acyclic (DAG scheduling).  

---

## 1. Dataset

- **File used:** `src/main/resources/tasks_small_1.json`  
- **Number of nodes:** 8  
- **Number of edges:** 8 (mixed cyclic and acyclic dependencies)  
- **Cyclic components:** present (SCCs)  
- **Acyclic components:** DAG after SCC condensation  

---

## 2. Strongly Connected Components (SCC)

**Algorithm used:** Tarjan's algorithm  

**Resulting SCCs:**

| SCC ID | Nodes         | Size |
|--------|---------------|------|
| 0      | [3, 2, 1]     | 3    |
| 1      | [0]           | 1    |
| 2      | [7]           | 1    |
| 3      | [6]           | 1    |
| 4      | [5]           | 1    |
| 5      | [4]           | 1    |

**Metrics:**

Tarjan Metrics: DFS visits=8, DFS edges=7, Kahn pops=0, relaxations=0, elapsedTime=0 ms



**Explanation:**  
Tarjan’s algorithm successfully identified all SCCs, compressing cyclic dependencies into single components for the subsequent DAG analysis.

---

## 3. Condensation Graph (DAG of Components)

**Condensed graph:**

| Component | Adjacent Components |
|-----------|-------------------|
| 0         | []                |
| 1         | [0]               |
| 2         | []                |
| 3         | [2]               |
| 4         | [3]               |
| 5         | [4]               |

This DAG is acyclic, representing the task dependencies at the component level.

---

## 4. Topological Sorting

**Algorithm used:** Kahn’s algorithm  

**Topological order of components:**  
[1, 5, 0, 4, 3, 2]



**Derived order of original tasks (after SCC compression):**  
[0, 4, 3, 2, 1, 5, 6, 7]



**Metrics:**

Kahn Metrics: DFS visits=0, DFS edges=0, Kahn pops=6, relaxations=0, elapsedTime=0 ms



**Explanation:**  
Topological sort allows scheduling of tasks respecting dependencies. The derived order lists the original tasks in a valid sequence after collapsing cyclic dependencies.

---

## 5. Shortest and Longest Paths in DAG

**Single-source shortest paths** (source: component 1):

| Component | Distance |
|-----------|---------|
| 0         | 1       |
| 1         | 0       |
| 2         | ∞       |
| 3         | ∞       |
| 4         | ∞       |
| 5         | ∞       |

**Longest paths**:

| Component | Distance |
|-----------|---------|
| 0         | 1       |
| 1         | 0       |
| 2         | -∞      |
| 3         | -∞      |
| 4         | -∞      |
| 5         | -∞      |

**Metrics:**

PathFinder Metrics: DFS visits=0, DFS edges=0, Kahn pops=0, relaxations=3, elapsedTime=17 ms


**Explanation:**  
- Shortest paths were computed using topological order traversal and edge relaxation.  
- Longest paths were computed using similar DP over topological order.  
- Relaxations count matches the number of updates performed.

---

## 6. Critical Path

**Component-level critical path:**  
[1, 0]




**Original task-level critical path:**  
[0, 3, 2, 1]



**Explanation:**  
The critical path represents the longest path in the DAG, identifying tasks that define the minimum project duration.  

---

## 7. Analysis

1. **SCC/Condensation:**  
   - Detects cyclic dependencies effectively.  
   - SCC compression ensures DAG-based scheduling is feasible.  

2. **Topological Sort:**  
   - Kahn’s algorithm provides a valid execution order.  
   - Metrics show 6 pops, matching the number of components with zero indegree during execution.  

3. **DAG Shortest/Longest Paths:**  
   - Shortest paths highlight tasks reachable from the source.  
   - Longest paths identify potential bottlenecks.  
   - Critical path reconstruction confirms the sequence of dependent tasks that limit total completion time.

4. **Performance:**  
   - All metrics (DFS visits/edges, Kahn pops, relaxations) match expectations.  
   - Execution time is minimal for small datasets.  

---

## 8. Conclusion

- **SCC detection** is essential for handling cyclic dependencies before DAG scheduling.  
- **Topological ordering** provides a valid execution plan.  
- **Shortest and longest path analysis** helps identify bottlenecks and optimize scheduling.  
- Metrics allow evaluation of algorithmic performance and can be used for larger datasets.  

---

## 9. Files

- `/src/main/java/graph/scc/` → `TarjanSCC.java`  
- `/src/main/java/graph/topo/` → `TopologicalSort.java`  
- `/src/main/java/graph/dagsp/` → `PathFinder.java`  
- `/src/main/java/utils/metrics/` → `Metrics.java`  
- `/src/main/resources/` → `tasks_small_1.json` (dataset)  
- `/src/main/java/Main.java` → main execution file
