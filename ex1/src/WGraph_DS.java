package ex1.src;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

/**
 * Represent a Weighted Undirected Graph - Each edge contain a weight value.
 *
 * @author eyalHadad
 */
public class WGraph_DS implements weighted_graph, java.io.Serializable {
    private int mc;
    private HashMap<Integer, node_info> vertexes;
    private HashMap<Integer, HashMap<node_info, Double>> graphEdges;
    private int nodeCount, edgeCount;

    /**
     * Empty Constructor to create an empty Graph
     */
    public WGraph_DS() {
        this.mc = 0;
        this.nodeCount = 0;
        this.edgeCount = 0;
        this.vertexes = new HashMap<Integer, node_info>();
        this.graphEdges = new HashMap<Integer, HashMap<node_info, Double>>();
    }

    /**
     * Equal Method - Object Graph
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WGraph_DS wGraph_ds = (WGraph_DS) o;
        return nodeCount == wGraph_ds.nodeCount &&
                edgeCount == wGraph_ds.edgeCount &&
                vertexes.equals(wGraph_ds.vertexes) &&
                graphEdges.equals(wGraph_ds.graphEdges);
    }

    /**
     * deepCopy Constructor
     */
    public WGraph_DS(weighted_graph other) {
        this.vertexes = new HashMap<Integer, node_info>();
        this.graphEdges = new HashMap<Integer, HashMap<node_info, Double>>();
        for (node_info vertex : other.getV()) {
            node_info vertexC = new NodeData(vertex.getKey());
            this.vertexes.put(vertex.getKey(), vertexC);
            this.graphEdges.put(vertex.getKey(), new HashMap<node_info, Double>());
        }
        for (node_info vertex : other.getV()) {
            Iterator<node_info> iterator = other.getV(vertex.getKey()).iterator();
            while (iterator.hasNext()) {
                node_info copyV = new NodeData(iterator.next());
                double w = other.getEdge(vertex.getKey(), copyV.getKey());
                this.connect(vertex.getKey(), copyV.getKey(), w);
            }
        }
        this.mc = other.getMC();
        this.nodeCount = other.nodeSize();
        this.edgeCount = other.edgeSize();
    }

    /**
     * get a node_info from key
     *
     * @param key
     * @return -> node_info
     */
    @Override
    public node_info getNode(int key) {
        if (!vertexes.containsKey(key)) return null;
        return vertexes.get(key);
    }

    /**
     * Boolean Method for checking if exists an Edge between vertices
     *
     * @param node1
     * @param node2
     * @return -> True : False
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
        if (vertexes.containsKey(node1) && vertexes.containsKey(node2)) {
            return graphEdges.get(node1).containsKey(getNode(node2));
        }
        return false;
    }

    /**
     * For receive the weight between vertices
     *
     * @param node1
     * @param node2
     * @return -> weight
     */
    @Override
    public double getEdge(int node1, int node2) {
        if (!hasEdge(node1, node2)) return -1;
        return graphEdges.get(node1).get(getNode(node2));
    }

    /**
     * Add a Vertex (node_info) to the Graph Using a KEY value
     *
     * @param key
     */
    @Override
    public void addNode(int key) {
        if (vertexes.containsKey(key)) return;
        node_info vertex = new NodeData(key);
        vertexes.put(vertex.getKey(), vertex);
        HashMap<node_info, Double> neighbors = new HashMap<node_info, Double>();
        graphEdges.put(vertex.getKey(), neighbors);
        nodeCount++;
        mc++;
    }

    /**
     * Connect between vertices and add a Weight value.
     * if there is already an Edge -> Update Weight Value
     *
     * @param node2
     * @param node1
     * @param w     -> Weight Value
     */
    @Override
    public void connect(int node1, int node2, double w) {
        if (node1 == node2) return;
        if (vertexes.containsKey(node1) && vertexes.containsKey(node2)) {
            if (hasEdge(node1, node2)) {
                graphEdges.get(node1).put(getNode(node2), w);
                graphEdges.get(node2).put(getNode(node1), w);
                mc++;
            } else {
                graphEdges.get(node1).put(getNode(node2), w);
                graphEdges.get(node2).put(getNode(node1), w);
                edgeCount++;
                mc++;
            }
        }
    }

    /**
     * This method return a pointer (shallow copy) for a
     * Collection representing all the nodes in the graph.
     * Note: this method should run in O(1) tim
     *
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_info> getV() {
        return vertexes.values();
    }

    /**
     * This method returns a Collection containing all the
     * nodes connected to node_id
     * Note: this method should run in O(1) time.
     *
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_info> getV(int node_id) {
        return graphEdges.get(node_id).keySet();
    }

    /**
     * Delete the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * This method should run in O(n), |V|=n, as all the edges should be removed.
     *
     * @param key
     * @return the data of the removed node (null if none).
     */
    @Override
    public node_info removeNode(int key) {
        if (!vertexes.containsKey(key)) return null;
        Iterator<node_info> infoIterator = this.getV(key).iterator();
        while (infoIterator.hasNext()) {
            node_info niRemove = infoIterator.next();
            graphEdges.get(niRemove.getKey()).remove(getNode(key));
        }
        graphEdges.remove(key);
        nodeCount--;
        mc++;
        return vertexes.remove(key);
    }

    /**
     * Delete the edge from the graph,
     * Note: this method should run in O(1) time.
     *
     * @param node1
     * @param node2
     */
    @Override
    public void removeEdge(int node1, int node2) {
        if (!hasEdge(node1, node2)) {
            return;
        }
        graphEdges.get(node1).remove(getNode(node2));
        graphEdges.get(node2).remove(getNode(node1));
        edgeCount--;
        mc++;
    }

    @Override
    public int nodeSize() {
        return nodeCount;
    }

    @Override
    public int edgeSize() {
        return edgeCount;
    }

    @Override
    public int getMC() {
        return mc;
    }

    private class NodeData implements node_info, java.io.Serializable {
        private int key;
        private String meta_data;
        private double tag = -1;

        /**
         * Constructor Using Key Value
         *
         * @param key
         */
        public NodeData(int key) {
            this.key = key;
            this.meta_data = "";
        }

        /**
         * deepCopy Constructor
         */
        public NodeData(node_info vertex) {
            this.key = vertex.getKey();
            this.meta_data = vertex.getInfo();
            this.tag = vertex.getTag();
        }

        @Override
        public int getKey() {
            return key;
        }

        @Override
        public String getInfo() {
            return meta_data;
        }

        @Override
        public void setInfo(String s) {
            this.meta_data = s;
        }

        @Override
        public double getTag() {
            return tag;
        }

        @Override
        public void setTag(double t) {
            this.tag = t;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            NodeData nodeData = (NodeData) o;
            return key == nodeData.key;
        }

        @Override
        public int hashCode() {
            return Objects.hash(key);
        }

        @Override
        public String toString() {
            return "K:(" + key + ") T:[" + tag + "] I:{" + meta_data+"}";
        }

    }

    @Override
    public String toString() {
        String print = "";
        for (Integer value : graphEdges.keySet()) {
            print = print + "Vertex: " + value + "[" + vertexes.get(value).getTag() + "] <~> [";
            for (node_info neighborValue : graphEdges.get(value).keySet()) {
                print += neighborValue.toString() + " <" + graphEdges.get(value).get(neighborValue) + "> ; ";
            }
            print = print + "] \n";
        }
        return print + " ";
    }
}
