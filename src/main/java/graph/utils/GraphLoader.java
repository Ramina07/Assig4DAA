package graph.utils;

import com.google.gson.Gson;
import java.io.FileReader;
import java.util.List;

// Класс для загрузки графа из JSON
public class GraphLoader {

    // Метод для загрузки данных из файла
    public static GraphData loadFromJson(String path) throws Exception {
        try (FileReader reader = new FileReader(path)) {
            Gson gson = new Gson();
            return gson.fromJson(reader, GraphData.class);
        }
    }

    // Класс, описывающий граф
    public static class GraphData {
        public int n; // количество вершин
        public List<Edge> edges; // список рёбер
    }

    // Класс для одного ребра
    public static class Edge {
        public int u; // откуда
        public int v; // куда
    }
}
