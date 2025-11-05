# Assignment 4: Smart City / Smart Campus Scheduling

## Goal
The goal of this assignment is to consolidate two course topics in one practical case:

1. **Strongly Connected Components (SCC) & Topological Ordering**
2. **Shortest Paths in Directed Acyclic Graphs (DAGs)**

We apply these techniques to a "Smart City / Smart Campus Scheduling" scenario, where datasets represent city-service tasks (street cleaning, repairs, camera/sensor maintenance) and internal analytics subtasks.

---

## Scenario
- Each dataset contains directed task dependencies.
- Some dependencies are cyclic → detect & compress using SCC.
- Others are acyclic → plan optimally using DAG shortest/longest paths.
- Tasks may have edge weights representing duration or priority.
- Separate subtasks follow dynamic programming patterns.

---

## Implementation Overview

### 1. Graph Structure
- Classes under `graph` package:
  - `Graph` – adjacency list representation of a directed graph.
  - `GraphUtils` – utility methods for graph construction and edge addition.
  - `SCCFinder` – finds strongly connected components using Tarjan’s algorithm.
  - `TopologicalSorter` – computes topological order of the condensation DAG.
  - `DAGShortestLongestPaths` – computes single-source shortest and longest paths in DAG.
  - `Metrics` – tracks operation counts and execution time.

- Input format: JSON files stored in `/data/`
  - Fields: `n` (vertices), `edges` (u,v,w), `directed`, `source`, `weight_model`.

---

### 2. Algorithms

#### 2.1 Strongly Connected Components (SCC)
- **Algorithm**: Tarjan’s DFS-based algorithm
- **Input**: directed dependency graph
- **Output**:
  - List of SCCs
  - Condensation graph (DAG of components)
- **Instrumentation**:
  - DFS visits and edges counted

#### 2.2 Topological Sort
- **Algorithm**: Kahn’s algorithm variant
- **Input**: condensation DAG
- **Output**:
  - Topological order of components
  - Derived order of original tasks

#### 2.3 Shortest/Longest Paths in DAG
- **Algorithm**: Single-source shortest paths via relaxation along topological order
- **Longest Path**: computed using negative weight inversion or max-DP
- **Output**:
  - Shortest distances from source
  - Longest path (critical path) and its length
  - One optimal path reconstruction

---

## Dataset Summary

| Dataset | Nodes | Edges | Cyclic / DAG | Description |
|---------|-------|-------|-------------|-------------|
| tasks_small_1.json | 8 | 10 | Mixed | Small, 1 cycle |
| tasks_small_2.json | 6 | 8  | Mixed | Small, simple cycle |
| tasks_small_3.json | 9 | 12 | Mixed | Small, multiple SCCs |
| tasks_medium_1.json | 12 | 18 | Mixed | Medium, several SCCs |
| tasks_medium_2.json | 15 | 25 | Mixed | Medium, larger SCCs |
| tasks_medium_3.json | 18 | 30 | Mixed | Medium, multiple DAG branches |
| tasks_large_1.json | 18 | 30 | Mixed | Large, performance test |
| tasks_large_2.json | 35 | 60 | DAG   | Large, dense, mostly acyclic |
| tasks_large_3.json | 50 | 80 | DAG   | Large, performance / stress test |

---

## Results Summary

### Example: tasks_small_1.json
- Vertices: 8
- Source: 4
- SCCs: [[3,2,1],[0],[7],[6],[5],[4]]
- Topological order: [1,5,0,4,3,2]
- Shortest distances from 4: [∞,∞,6,5,0,∞,...]
- Longest distances from 4: [-∞,-∞,6,5,0,-∞,...]


---

## Metrics and Performance
- SCC DFS visits / edges tracked
- Kahn’s queue operations tracked
- DAG shortest/longest path relaxations counted
- Execution times measured using `System.nanoTime()`
- Observations:
  - Sparse graphs → faster SCC and topological sorting
  - Dense graphs → more relaxations, longer critical path computation
  - Number and size of SCCs significantly affect condensation graph construction

---

## Code Quality
- Modular packages: `graph`, `graph.scc`, `graph.topo`, `graph.dagsp`
- Key algorithms documented with Javadoc
- Unit tests cover small deterministic and edge cases (`src/test/java`)
- JSON input files stored in `/data/`

---

## Analysis & Conclusions

### 1. SCC + Topological Sorting
- Efficient for compressing cyclic dependencies.  
- Provides a DAG suitable for scheduling and DP-based path computations.

### 2. DAG Shortest / Longest Paths
- Single-source shortest path works for planning minimal completion times.  
- Longest path is critical for scheduling tasks with maximum duration.

### 3. Practical Recommendations
- Use SCC to simplify cyclic dependencies.  
- Use topological sorting to linearize tasks.  
- Apply DAG-SP algorithms for time/resource optimization.  
- Dense graphs require careful tracking of operation counts for performance.
