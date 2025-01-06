import FibonacciHeapImplementation.FibonacciHeap;
import FibonacciHeapImplementation.FibonacciHeapNode;
import Model.Edge;
import Model.Graph;
import Trie.BinaryTrie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sun.management.HotSpotDiagnosticMXBean.ThreadDumpFormat.JSON;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.addEdge(0, new Edge(1, 1));
        graph.addEdge(0, new Edge(2, 5));
        graph.addEdge(0, new Edge(3, 3));
        graph.addEdge(1, new Edge(4, 1));
        graph.addEdge(4, new Edge(2, 1));
        BinaryTrie res = dijkstra(graph, 0);
        int destinationIP = 4;
        System.out.println(res.search(2));
    }

    public static BinaryTrie dijkstra(Graph graph, int startNode) {
        Map<Integer, FibonacciHeapNode> nodeMap = new HashMap<>();
        BinaryTrie trie = new BinaryTrie();
        FibonacciHeap heap = new FibonacciHeap();

        /*
        for(int i = 0; i < 1_000_000; i++) {
            FibonacciHeapNode node = new FibonacciHeapNode(i, Double.POSITIVE_INFINITY);
            nodeMap.put(i, node);
            heap.insert(node);
        }*/
        List<Integer> curNodeValList = new ArrayList<>();
        curNodeValList.add(startNode);
        while (!curNodeValList.isEmpty()) {
            List<Integer> nextCurNodeValList = new ArrayList<>();
            for (int curNodeVal : curNodeValList) {
                FibonacciHeapNode node = new FibonacciHeapNode(curNodeVal, Double.POSITIVE_INFINITY);
                nodeMap.put(curNodeVal, node);
                heap.insert(node);
                List<Edge> edges = graph.getEdge(curNodeVal);
                for (Edge edge : edges) {
                    nextCurNodeValList.add(edge.target);
                }
            }
            curNodeValList = nextCurNodeValList;
        }

        heap.decreaseKey(nodeMap.get(startNode), 0);
        while(heap.minNode != null) {
            FibonacciHeapNode min = heap.removeMin();
            trie.insert(min.key, min.value);
            List<Edge> edges = graph.getEdge(min.key);
            for(Edge edge : edges) {
                if(nodeMap.get(edge.target).value > min.value + edge.weight) {
                    heap.decreaseKey(nodeMap.get(edge.target), min.value + edge.weight);
                }
            }
        }
        return trie;
    }
}