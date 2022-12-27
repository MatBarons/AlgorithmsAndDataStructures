package graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Before;
import org.junit.Test;

public class GraphTestsDirected {
    private Graph<Integer, Character> graph = null;
    private int lb1, lb2, lb3;
    private char c1, c2, c3;

    @Before
    public void createGraph() {
        graph = new Graph<>(GraphType.DIRECTED);
        lb1 = 5;
        lb2 = 14;
        lb3 = -7;
        c1 = 'A';
        c2 = 'B';
        c3 = 'C';
    }

    @Test
    public void testAddOneVertex() throws NullPointerException {
        assertTrue(graph.addVertex(lb1));
    }

    @Test(expected = NullPointerException.class)
    public void testAddNullVertex() throws NullPointerException {
        graph.addVertex(null);
    }

    @Test
    public void testAddTwoVertex_True() throws NullPointerException {
        boolean ris = graph.addVertex(lb1);
        ris = ris && graph.addVertex(lb2);
        assertTrue(ris);
    }

    @Test
    public void testAddTwoVertex_False() throws NullPointerException {
        boolean ris = graph.addVertex(lb1);
        ris = ris && graph.addVertex(lb1);
        assertFalse(ris);
    }

    @Test
    public void testIsDirected() {
        assertTrue(graph.isDirected());
    }

    @Test
    public void testGetType() {
        assertThat(graph.getType(), is(GraphType.DIRECTED));
    }

    @Test
    public void testGetNumberVertices_Empty() {
        assertEquals(graph.getNumberVertices(), 0);
    }

    @Test
    public void testGetNumberVertices_OneVertex() throws NullPointerException {
        graph.addVertex(lb1);
        assertEquals(graph.getNumberVertices(), 1);
    }

    @Test
    public void testGetNumberVertices_TwoVertices() throws NullPointerException {
        graph.addVertex(lb1);
        graph.addVertex(lb2);
        assertEquals(graph.getNumberVertices(), 2);
    }

    @Test
    public void testContains_True() throws NullPointerException {
        graph.addVertex(lb1);
        assertTrue(graph.containsVertex(lb1));
    }

    @Test
    public void testContains_False() throws NullPointerException {
        assertFalse(graph.containsVertex(lb1));
    }

    @Test
    public void testGetVerticesLabel_Empty() {
        Set<Integer> ris = graph.getVerticesLabel();
        assertThat(ris, is(new HashSet<>()));
    }

    @Test
    public void testGetVerticesLabel_OneVertex() throws NullPointerException {
        graph.addVertex(lb1);
        Set<Integer> exp = new HashSet<>();
        exp.add(lb1);
        assertThat(graph.getVerticesLabel(), is(exp));
    }

    @Test
    public void testGetVertices_TwoVertices() throws NullPointerException {
        graph.addVertex(lb1);
        graph.addVertex(lb2);
        Set<Integer> exp = new HashSet<>();
        exp.add(lb1);
        exp.add(lb2);
        assertThat(graph.getVerticesLabel(), is(exp));
    }

    @Test(expected = NullPointerException.class)
    public void testRemoveVertex_Null() throws NullPointerException {
        graph.removeVertex(null);
    }

    @Test
    public void testRemoveVertex_NotInGraph() throws NullPointerException {
        assertNull(graph.removeVertex(lb1));
    }

    @Test
    public void testRemoveVertex_True() throws NullPointerException {
        graph.addVertex(lb1);
        assertThat(graph.removeVertex(lb1).getLabel(), is(lb1));
    }

    @Test(expected = NullPointerException.class)
    public void testGetAdjacentVertices_Null() throws NullPointerException, GraphException {
        graph.getAdjacentVertices(null);
    }

    @Test
    public void testGetAdjacentVertices_Empty() throws NullPointerException, GraphException {
        assertNull(graph.getAdjacentVertices(lb1));
    }

    @Test(expected = GraphException.class)
    public void testAddEdge_MissingVertex1() throws NullPointerException, GraphException {
        graph.addVertex(lb2);
        graph.addEdge(lb1, lb2, c1);
    }

    @Test(expected = GraphException.class)
    public void testAddEdge_MissingVertex2() throws NullPointerException, GraphException {
        graph.addVertex(lb1);
        graph.addEdge(lb1, lb2, c1);
    }

    @Test(expected = NullPointerException.class)
    public void testAddEdge_LabelNull() throws NullPointerException, GraphException {
        graph.addVertex(lb1);
        graph.addEdge(lb1, lb2, null);
    }

    @Test
    public void testAddEdge_True() throws NullPointerException, GraphException {
        graph.addVertex(lb1);
        graph.addVertex(lb2);
        graph.addEdge(lb1, lb2, 'A');
    }

    @Test
    public void testGetEdge_MissingVertex1() throws NullPointerException, GraphException {
        graph.addVertex(lb1);
        graph.addVertex(lb2);
        graph.addEdge(lb1, lb2, 'A');
        assertNull(graph.getEdge(lb3, lb2));
    }

    @Test
    public void testGetEdge_MissingVertex2() throws NullPointerException, GraphException {
        graph.addVertex(lb1);
        graph.addVertex(lb2);
        graph.addEdge(lb1, lb2, 'A');
        assertNull(graph.getEdge(lb1, lb3));
    }

    @Test
    public void testGetEdge_True() throws NullPointerException, GraphException {
        graph.addVertex(lb1);
        graph.addVertex(lb2);
        graph.addEdge(lb1, lb2, 'A');
        assertThat(graph.getEdge(lb1, lb2).getLabel(), is('A'));
    }

    // @Test
    // public void testAddThreeVerticesDirected() {
    // directedGraph.addVertex(lb1);
    // directedGraph.addVertex(lb2);
    // directedGraph.addVertex(lb3);

    // System.out.println(directedGraph);

    // directedGraph.addEdge(lb1, lb2, 'B');
    // directedGraph.addEdge(lb2, lb1, 'A');
    // directedGraph.addEdge(lb3, lb1, 'C');
    // directedGraph.addEdge(lb1, lb3, 'D');

    // System.out.println(directedGraph);

    // System.out.println(directedGraph.getNumberVertices());

    // System.out.println("DIRECTED DEGREE: " + directedGraph.getVertexDegree(lb1));

    // directedGraph.removeVertex(lb2);

    // System.out.println(directedGraph.getNumberVertices());

    // System.out.println(directedGraph);

    // Character label = directedGraph.getEdgeLabel(lb3, lb1);

    // System.out.println(label);

    // assertThat(directedGraph.getVertex(lb3), not((Vertex<Integer, Double>)
    // null));
    // }

    // @Test
    // public void testAddThreeVerticesUndirected() {
    // undirectedGraph.addVertex(lb1);
    // undirectedGraph.addVertex(lb2);
    // undirectedGraph.addVertex(lb3);

    // System.out.println(undirectedGraph);

    // undirectedGraph.addEdge(lb1, lb2, 'B');
    // undirectedGraph.addEdge(lb3, lb1, 'C');

    // System.out.println(undirectedGraph);

    // System.out.println(undirectedGraph.getNumberVertices());

    // System.out.println("UNDIRECTED DEGREE: " +
    // undirectedGraph.getVertexDegree(lb1));

    // undirectedGraph.removeVertex(lb2);

    // System.out.println(undirectedGraph.getNumberVertices());

    // System.out.println(undirectedGraph);

    // Character label = undirectedGraph.getEdgeLabel(lb3, lb1);

    // System.out.println(label);

    // assertThat(undirectedGraph.getVertex(lb3), not((Vertex<Integer, Double>)
    // null));
    // }
}
