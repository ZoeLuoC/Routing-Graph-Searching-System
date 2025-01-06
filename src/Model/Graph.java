package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Graph {
    HashMap<Integer, List<Edge>> adjacencyList = new HashMap<>();

    public void addEdge(int source, Edge edge) {
        adjacencyList.putIfAbsent(source, new ArrayList<>());
        adjacencyList.get(source).add(edge);
    }

    public List<Edge> getEdge(int source) {
        return adjacencyList.getOrDefault(source, new ArrayList<>());
    }
}
