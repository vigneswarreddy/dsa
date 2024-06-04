package dsagui;

import java.util.*;

public class DijkstrasShortestPath {
    private final int V;
    private final List<List<Node>> adj;

    public DijkstrasShortestPath(int V) {
        this.V = V;
        adj = new ArrayList<>(V);
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }
    }

    public void addEdge(int u, int v, int weight) {
        adj.get(u).add(new Node(v, weight));
        adj.get(v).add(new Node(u, weight));
    }

    public int[] dijkstra(int src) {
        PriorityQueue<Node> pq = new PriorityQueue<>(V, new Node());
        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);
        pq.add(new Node(src, 0));
        dist[src] = 0;

        while (!pq.isEmpty()) {
            int u = pq.poll().node;

            for (Node neighbor : adj.get(u)) {
                int v = neighbor.node;
                int weight = neighbor.cost;

                if (dist[v] > dist[u] + weight) {
                    dist[v] = dist[u] + weight;
                    pq.add(new Node(v, dist[v]));
                }
            }
        }
        return dist;
    }

    public int getDistance(int src, int dest) {
        int[] distances = dijkstra(src);
        return distances[dest];
    }

    public void displayGraph() {
        for (int i = 0; i < adj.size(); i++) {
            System.out.print(i + " -> ");
            for (Node node : adj.get(i)) {
                System.out.print(node.node + "(" + node.cost + ") ");
            }
            System.out.println();
        }
    }

    static class Node implements Comparator<Node> {
        public int node;
        public int cost;

        public Node() {}

        public Node(int node, int cost) {
            this.node = node;
            this.cost = cost;
        }

        @Override
        public int compare(Node node1, Node node2) {
            return Integer.compare(node1.cost, node2.cost);
        }
    }
}
