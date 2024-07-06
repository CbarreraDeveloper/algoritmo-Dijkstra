import java.util.*;

class Edge {
    int destination, weight;

    public Edge(int destination, int weight) {
        this.destination = destination;
        this.weight = weight;
    }
}

class Node implements Comparable<Node> {
    int node, cost;

    public Node(int node, int cost) {
        this.node = node;
        this.cost = cost;
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.cost, other.cost);
    }
}

class Graph {
    private final Map<Integer, List<Edge>> adjList = new HashMap<>();

    public void addEdge(int source, int destination, int weight) {
        adjList.computeIfAbsent(source, k -> new ArrayList<>()).add(new Edge(destination, weight));
        adjList.computeIfAbsent(destination, k -> new ArrayList<>()).add(new Edge(source, weight));
    }

    public int[] dijkstra(int n, int start) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        int[] distances = new int[n + 1];  // Usamos n + 1 para acomodar índices basados en 1
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[start] = 0;
        pq.add(new Node(start, 0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();

            for (Edge edge : adjList.getOrDefault(current.node, new ArrayList<>())) {
                int neighbor = edge.destination;
                int newDist = distances[current.node] + edge.weight;

                if (newDist < distances[neighbor]) {
                    distances[neighbor] = newDist;
                    pq.add(new Node(neighbor, newDist));
                }
            }
        }

        return Arrays.copyOfRange(distances, 1, distances.length);  // Omitir el índice 0
    }
}

public class Main {
    public static void main(String[] args) {
        int n = 5;  // Número de nodos
        int[][] edges = {
                {1, 2, 1},
                {1, 3, 4},
                {2, 3, 2},
                {2, 4, 5},
                {3, 4, 1},
                {4, 5, 3}
        };
        int startNode = 1;

        Graph graph = new Graph();

        for (int[] edge : edges) {
            graph.addEdge(edge[0], edge[1], edge[2]);
        }

        int[] distances = graph.dijkstra(n, startNode);

        // Imprimir las distancias desde el nodo inicial
        System.out.println("Distancias desde el nodo " + startNode + ": " + Arrays.toString(distances));
    }
}