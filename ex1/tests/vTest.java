package ex1.tests;
import ex1.src.*;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class vTest {

    @Test
    void zero_V_and_or_E() {
        weighted_graph g0 = new WGraph_DS(connectedGraphMaker(0, 0));
        weighted_graph_algorithms graph_algo = new WGraph_Algo();
        graph_algo.init(g0);
        assertTrue(graph_algo.isConnected());
        assertEquals(0, graph_algo.shortestPathDist(0, 0));
        assertNull(graph_algo.shortestPath(0, 0));
        weighted_graph g1 = new WGraph_DS(connectedGraphMaker(1, 0));
        graph_algo.init(g1);
        assertTrue(graph_algo.isConnected());
        assertEquals(-1, graph_algo.shortestPathDist(0, 1));
        assertNull(graph_algo.shortestPath(0, 1));
        weighted_graph g2 = new WGraph_DS(connectedGraphMaker(2, 0));
        graph_algo.init(g2);
        assertFalse(graph_algo.isConnected());
        assertEquals(-1, graph_algo.shortestPathDist(0, 2));
        assertNull(graph_algo.shortestPath(0, 2));
        weighted_graph g3 = new WGraph_DS(connectedGraphMaker(3, 0));
        graph_algo.init(g3);
        assertFalse(graph_algo.isConnected());
        assertEquals(-1, graph_algo.shortestPathDist(0, 3));
        assertNull(graph_algo.shortestPath(0, 3));
        weighted_graph g4 = new WGraph_DS(connectedGraphMaker(4, 0));
        graph_algo.init(g4);
        assertFalse(graph_algo.isConnected());
        assertEquals(-1, graph_algo.shortestPathDist(0, 4));
        assertNull(graph_algo.shortestPath(0, 4));
        g0 = connectedGraphMaker(0, 0);
        graph_algo.init(g0);
        System.out.print(g0); /*nothing should be printed*/
        assertTrue(graph_algo.isConnected());
        assertEquals(0, graph_algo.shortestPathDist(0, 0));
        assertNull(graph_algo.shortestPath(0, 0));
        g1 = connectedGraphMaker(0, 1);
        graph_algo.init(g1);
        System.out.print(g1); /*nothing should be printed*/
        assertTrue(graph_algo.isConnected());
        assertEquals(-1, graph_algo.shortestPathDist(0, 1));
        assertNull(graph_algo.shortestPath(0, 1));
        g2 = connectedGraphMaker(0, 2);
        graph_algo.init(g2);
        System.out.print(g2); /*nothing should be printed*/
        assertTrue(graph_algo.isConnected());
        assertEquals(-1, graph_algo.shortestPathDist(0, 2));
        assertNull(graph_algo.shortestPath(0, 2));
        g3 = connectedGraphMaker(0, 3);
        graph_algo.init(g3);
        System.out.print(g3); /*nothing should be printed*/
        assertTrue(graph_algo.isConnected());
        assertEquals(-1, graph_algo.shortestPathDist(0, 2));
        assertNull(graph_algo.shortestPath(0, 2));
        g4 = connectedGraphMaker(0, 4);
        graph_algo.init(g4);
        System.out.print(g4);//nothing should be printed
        assertTrue(graph_algo.isConnected());
        assertEquals(-1, graph_algo.shortestPathDist(0, 2));
        assertNull(graph_algo.shortestPath(0, 2));
    }

    @Test
    void ModeCount() {
        weighted_graph graph = new WGraph_DS();
        for (int i = 0; i < 26; i++) {
            graph.addNode(i);
        }
        assertEquals(graph.getMC(), graph.nodeSize());
        graph = connectedGraphMaker(80, 80);
        int nodesize = graph.nodeSize();
        assertEquals(graph.getMC(), 6400);
        int mode = graph.getMC();
        for (int m = 0; m < 5; m++) {
            graph.removeNode(m);
        }
        assertNotEquals(mode, graph.getMC());
        assertEquals(mode + 5, graph.getMC());
    }

    @Test
    void save_load() {
        weighted_graph graph = new WGraph_DS(connectedGraphMaker(5, 5));
        weighted_graph_algorithms graph_algorithms = new WGraph_Algo();
        graph_algorithms.init(graph);
        String string = "graph.obj";
        graph_algorithms.save(string);
        weighted_graph graph1 = graph_algorithms.copy();
        graph_algorithms.load(string);
        assertEquals(graph, graph1);
        graph.removeNode(0);
        assertNotEquals(graph, graph1);
    }

    @Test
    void SaveLoad() {
        weighted_graph_algorithms Algo = new WGraph_Algo();
        weighted_graph name = connectedGraphMaker(3, 3 * 2);
        Algo.init(name);
        String str = "Graph1";
        Algo.save(str);
        str = "Graph2";
        name.addNode(8);
        name.addNode(9);
        name.connect(8, 9, 5.0);
        Algo.init(name);
        Algo.save(str);

        Algo.load("Graph1");
        weighted_graph firstCheck = new WGraph_DS(Algo.getGraph());
        Algo.load("Graph2");
        weighted_graph secondCheck = new WGraph_DS(Algo.getGraph());
        assertNotEquals(secondCheck, firstCheck);
        name.removeEdge(8, 9);
        name.removeNode(9);
        name.removeNode(8);
        Algo.init(name);
        String ss = "DoubleCheck";
        Algo.save(ss);
        Algo.load(ss);
        weighted_graph TripleCheck = new WGraph_DS(Algo.getGraph());
        assertEquals(firstCheck, TripleCheck);
    }

    @Test
    void ShortDouble_and_Path() {
        weighted_graph Graph = new WGraph_DS();
        for (int i = 0; i <= 10; i++) {
            Graph.addNode(i);
        }
        weighted_graph_algorithms ag = new WGraph_Algo();
        ag.init(Graph);
        Graph.connect(0, 1, 3.0);
        Graph.connect(0, 2, 2.0);
        Graph.connect(0, 3, 1.5);
        Graph.connect(0, 4, 1.0);
        assertEquals(1.0, ag.shortestPathDist(0, 4));
        List<node_info> cN1 = new LinkedList<node_info>();
        cN1.add(Graph.getNode(0));
        cN1.add(Graph.getNode(4));
        assertEquals(cN1, ag.shortestPath(0, 4));
        Graph.connect(4, 5, 4.0);
        Graph.connect(1, 6, 2.5);
        Graph.connect(2, 7, 1.0);
        Graph.connect(2, 8, 6.0);
        Graph.connect(5, 7, 3.0);
        assertEquals(3.0, ag.shortestPathDist(0, 7));
        List<node_info> cN2 = new LinkedList<node_info>();
        cN2.add(Graph.getNode(0));
        cN2.add(Graph.getNode(2));
        cN2.add(Graph.getNode(7));
        assertEquals(cN2, ag.shortestPath(0, 7));
        Graph.connect(6, 9, 3.0);
        Graph.connect(9, 10, 3.0);
        Graph.connect(0, 10, 18.0);
        Graph.connect(1, 10, 8.0);
        Graph.connect(3, 10, 8.5);
        Graph.connect(7, 10, 5.5);
        assertEquals(8.5, ag.shortestPathDist(0, 10));
        List<node_info> cN3 = new LinkedList<node_info>();
        cN3.add(Graph.getNode(0));
        cN3.add(Graph.getNode(2));
        cN3.add(Graph.getNode(7));
        cN3.add(Graph.getNode(10));
        assertEquals(cN3, ag.shortestPath(0, 10));
    }
    @Test
    void IsConnected_DoubleCheck() {
        weighted_graph Graph = new WGraph_DS(connectedGraphMaker(15,15));
        weighted_graph_algorithms ag = new WGraph_Algo();
        ag.init(Graph);
        assertTrue(ag.isConnected());
        for (int i = 0; i < 15; i++){
            Graph.removeEdge(3,i);
        }
        assertFalse(ag.isConnected());
        for(int i = 0; i < 15; i++){
            Graph.connect(3,i,0.0);
        }
        assertTrue(ag.isConnected());
    }

    weighted_graph connectedGraphMaker(int SizeN, int SizeE) {
        weighted_graph gr = new WGraph_DS();
        for (int i = 0; i < SizeN; i++) {
            gr.addNode(i);
        }
        for (int i = 0; i < SizeE; i++) {
            for (int j = 0; j < SizeE; j++) {
                gr.connect(i, j, Math.random() * 10);
            }
        }
        // System.out.println(gr);
        return gr;
    }
}
