package ex1.src;

import ex0.Graph_Algo;

import java.io.*;
import java.util.*;

public class WGraph_Algo implements weighted_graph_algorithms, java.io.Serializable {
    private weighted_graph graph;
    /**
     * Init the graph on which this set of algorithms operates on.
     * @param g
     */
    @Override
    public void init(weighted_graph g) {
        this.graph = g;
    }
    /**
     * Return the underlying graph of which this class works.
     * @return
     */
    @Override
    public weighted_graph getGraph() {
        init(this.graph);
        return this.graph;
    }
    /** Compute a deepCopy to the Graph that has been initiated
     * */
    @Override
    public weighted_graph copy() {
        weighted_graph graph = new WGraph_DS(this.graph);
        return graph;
    }

    /**
     * First using Iterator to reach a random Vertex
     * Applying BFS Algorithms on the Graph
     * If Exist a Vertex with Tag = -1 Than the Graph is Not Connected
     */
    @Override
    public boolean isConnected() {
        if (graph.nodeSize() == 0 || graph.nodeSize() == 1) {
            return true;
        }
        if (graph.nodeSize() > graph.edgeSize() + 2) {
            return false;
        }
        Iterator<node_info> iterator = graph.getV().iterator();
        node_info nodeStart = iterator.next();
        this.BFS(nodeStart);
        for (node_info i : graph.getV()) {
            if (i.getTag() == -1) return false;
        }
        return true;
    }
    /** Using Dijkstra
     * @return double -> The Tag Value from the VertexKey(dest)
     *  */
    @Override
    public double shortestPathDist(int src, int dest) {
        if (src == dest) return 0;
        if (graph.getNode(src) == null | graph.getNode(dest) == null) return -1;
        Dijkstra(graph.getNode(src));
        if (graph.getNode(dest).getTag() == Integer.MAX_VALUE) return -1;
        return graph.getNode(dest).getTag();
    }
    /** Using Dijkstra
     *  Start from the back -> VertexKey(dest)
     *  each iteration on the VertexNeighbors
     *  checking if the Sum of the EdgeWeight+Neighbor.TagValue = Vertex.Tag
     *  Adding to the List if True ->
     *  When Reaching VertexKey(src) ->
     *  Break-While-Loop ->
     *  Reverse the List
     *
     * @return List -> Mentioned Above
     *  */
    @Override
    public List<node_info> shortestPath(int src, int dest) {
        List<node_info> pathList = new LinkedList<node_info>();
        if (graph.getNode(src) == null | graph.getNode(dest) == null) return null;
        pathList.add(graph.getNode(dest));
        if (src == dest) return pathList;
        int key = dest;
        Dijkstra(graph.getNode(src));
        if (graph.getNode(dest).getTag() == Integer.MAX_VALUE) return null;
        Iterator<node_info> iterator = graph.getV(key).iterator();
        while (iterator.hasNext()) {
            node_info vertex = iterator.next();
            if (vertex.getTag() + graph.getEdge(vertex.getKey(), key) == graph.getNode(key).getTag()) {
                key = vertex.getKey();
                iterator = graph.getV(key).iterator();
                pathList.add(vertex);
            }
            if (vertex.getKey() == src) break;
        }
        Collections.reverse(pathList);
        return pathList;
    }
    /** Save Method
     * Saving a Graph from the Class
     *  */
    @Override
    public boolean save(String file) {
        try {
            FileOutputStream toSave = new FileOutputStream(file);
            ObjectOutputStream obi = new ObjectOutputStream(toSave);
            obi.writeObject(graph);
            toSave.close();
            obi.close();
        } catch (IOException e) {
            System.out.println("Something went wrong :( ");
            return false;
        }
        return true;

    }
    /** Load Method
     * Loading a Graph into the Class
     *  */
    @Override
    public boolean load(String file) {
        try {
            FileInputStream toLoad = new FileInputStream(file);
            ObjectInputStream obi = new ObjectInputStream(toLoad);
            weighted_graph newGraph = (weighted_graph) obi.readObject();
            graph = newGraph;

            toLoad.close();
            obi.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Something went wrong :( ");
            return false;
        }
        return true;
    }

    /**
     * BFS Algorithm
     * Using a Queue to keep track of the Vertex that has been reached
     * Giving each Vertex a Integer Tag representing the distance (n.Vertex) from the src Vertex
     *
     * @param node
     */
    private void BFS(node_info node) {
        Queue<node_info> queue = new LinkedList<node_info>();
        for (node_info vertex : graph.getV()) {
            vertex.setTag(-1);
        }
        node.setTag(0);
        queue.add(node);
        while (!queue.isEmpty()) {
            node_info nodeStart = queue.poll();
            Iterator<node_info> iterator = graph.getV(nodeStart.getKey()).iterator();
            while (iterator.hasNext()) {
                node_info nodeNeighbor = iterator.next();
                if (nodeNeighbor.getTag() == -1) {
                    nodeNeighbor.setTag(nodeStart.getTag() + 1);
                    queue.add(nodeNeighbor);
                }
            }
        }
    }

    /**
     * Dijkstra Algorithm
     * similar to BFS using a Queue to keep track - 2 Queues this time
     * changing the Tag Value to the Vertex that has been reached from src
     * if the EdgeWeight + Vs.Tag < Vd.Tag => Vd.Tag = EdgeWeight + Vs.Tag
     * each Vertex get a Tag Value => (Min)Sum of the EdgeWeight from Source
     *
     * @param node -> source vertex
     */
    private void Dijkstra(node_info node) {
        for (node_info vertex : graph.getV()) {
            vertex.setTag(Integer.MAX_VALUE);
        }
        node.setTag(0);
        Queue<node_info> queue = new LinkedList<node_info>();
        List<node_info> pQueue = new LinkedList<node_info>();
        queue.add(node);
        pQueue.add(node);
        while (!queue.isEmpty()) {
            node_info nodeStart = queue.poll();
            Iterator<node_info> iterator = graph.getV(nodeStart.getKey()).iterator();
            while (iterator.hasNext()) {
                node_info nodeNeighbor = iterator.next();
                double weight = nodeStart.getTag() + graph.getEdge(nodeStart.getKey(), nodeNeighbor.getKey());
                if (weight < nodeNeighbor.getTag()) {
                    nodeNeighbor.setTag(weight);
                    if (!pQueue.contains(nodeNeighbor)) {
                        queue.add(nodeNeighbor);
                        pQueue.add(nodeNeighbor);
                    }
                }
            }
        }
    }

}
