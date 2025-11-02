package graph.scc;

import com.google.gson.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class GraphLoader {

    public static class Edge {
        public int u;
        public int v;
        public int w;
    }

    public static class GraphData {
        public boolean directed;
        public int n;
        public List<Edge> edges;
        public int source;
        public String weight_model;
    }

    public static GraphData loadFromJson(String filePath) throws IOException {
        String json = Files.readString(Paths.get(filePath));
        Gson gson = new Gson();
        return gson.fromJson(json, GraphData.class);
    }
}

