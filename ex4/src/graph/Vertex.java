package graph;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

public class Vertex<T, S> {
    private T label = null;
    private Hashtable<T, Edge<S>> adjacentList = null;
    private double distance = 0;
    private Vertex<T, S> pi = null;

    /**
     * Create a new {@code Vertex} with the given {@code label}
     */
    public Vertex(T label) {
        this.adjacentList = new Hashtable<>();
        this.label = label;
    }

    /**
     * @return the {@code label} of the {@code vertex}
     */
    public T getLabel() {
        return this.label;
    }

    /**
     * Set the {@code distance} from the {@code parent} vertex
     * 
     * @param distance
     */
    public void setDistance(double distance) {
        this.distance = distance;
    }

    /**
     * @return the {@code distance} from the {@code parent} vertex
     */
    public double getDistance() {
        return this.distance;
    }

    /**
     * Set the {@code parent} {@code Vertex}
     * 
     * @param pi {@code parent} {@code Vertex}
     */
    public void setPi(Vertex<T, S> pi) {
        this.pi = pi;
    }

    /**
     * @return the {@code parent} {@code Vertex}
     */
    public Vertex<T, S> getPi() {
        return pi;
    }

    /**
     * @return the total number of {@code edge} outgoing
     */
    public int getOutDegree() {
        return adjacentList.size();
    }

    /**
     * @return a new {@link Set} of vertex label containing all the vertices
     *         adjacents
     */
    public Set<T> getAdjacentVerticesLabel() {
        Set<T> adjacent = new HashSet<>();

        for (T a : adjacentList.keySet()) {
            adjacent.add(a);
        }
        return adjacent;
    }

    /**
     * @return a new {@link Set} of {@link Edge} containing all the edges
     */
    public Set<Edge<S>> getEdges() {
        Set<Edge<S>> ris = new HashSet<>();
        Enumeration<Edge<S>> edge = adjacentList.elements();

        while (edge.hasMoreElements()) {
            Edge<S> e = edge.nextElement();
            ris.add(e);
        }

        return ris;
    }

    /**
     * Check if the given {@code vertexLabel} is an adjacent of the current
     * {@code Vertex}
     * 
     * @param vertexLabel label of the vertex to check if is adjacent
     * @return {@code TRUE} iff {@code vertexLabel} is an adjacent of the current
     *         {@code Vertex}, {@code FALSE} otherwise
     * @throws NullPointerException iff {@code vertexLabel} is {@code null}
     */
    public boolean hasAdjacent(T vertexLabel) throws NullPointerException {
        if (vertexLabel == null)
            throw new NullPointerException("hasAdjacent(vertexLabel): vertexLabel must not be null");

        return adjacentList.containsKey(vertexLabel);
    }

    /**
     * @param edge
     * @return {@code TRUE} iff {@code edge} is an {@link Edge} of the current
     *         {@code Vertex}, {@code FALSE} otherwise
     * @throws NullPointerException iff {@code edge} is {@code null}
     */
    public boolean hasEdge(Edge<S> edge) throws NullPointerException {
        if (edge == null)
            throw new NullPointerException("hasEdge(edge): edge must not be null");

        return adjacentList.contains(edge);
    }

    /**
     * @param adjacentLabel of the vertex adjacent
     * @param edgeLabel     of the edge
     * @return {@code TRUE} iff added successfully, {@code FALSE} otherwise
     * @throws NullPointerException iff {@code adjacentLabel} OR {@code edgeLabel}
     *                              are {@code null}
     */
    public boolean addAdjacent(T adjacentLabel, S edgeLabel) throws NullPointerException {
        if (adjacentLabel == null)
            throw new NullPointerException("addAdjacent(adjacentLabel, edgeLabel): adjacentLabel must not be null");

        if (edgeLabel == null)
            throw new NullPointerException("addAdjacent(adjacentLabel, edgeLabel): edgeLabel must not be null");

        Edge<S> edge = new Edge<>(edgeLabel);

        return (adjacentList.putIfAbsent(adjacentLabel, edge) == null);
    }

    /**
     * @param adjacentLabel
     * @return the {@code Edge} on success, {@code null} otherwise
     * @throws NullPointerException iff {@code adjacentLabel} is {@code null}
     * @see Edge
     */
    public Edge<S> getEdge(T adjacentLabel) throws NullPointerException {
        if (adjacentLabel == null)
            throw new NullPointerException("getEdge(adjacentLabel): adjacentLabel must not be null");

        return adjacentList.get(adjacentLabel);
    }

    /**
     * Retrieve the {@code weight} of the {@code edge} with the adjacent vertex with
     * the given {@code adjacentLabel}
     * 
     * @param adjacentLabel label of the {@code adjacent} vertex
     * @return the {@code edge} {@code weight}
     * @throws NullPointerException
     *                              <ul>
     *                              <li>if {@code adjacentLabel} is
     *                              {@code null}</li>
     *                              <li>if there is no {@code edge} between the
     *                              vertex and his {@code adjacent}</li>
     *                              </ul>
     */
    public double getEdgeWeight(T adjacentLabel) throws NullPointerException {
        Edge<S> edge = getEdge(adjacentLabel);

        if (edge == null)
            throw new NullPointerException("getEdgeWeight(adjacentLabel): the edge between " + this.label + " and "
                    + adjacentLabel + " does not exists");

        return edge.getWeight();
    }

    /**
     * @param adjacentLabel the adjacent to remove
     * @return the edge of the adjacent removed on success, {@code null} otherwise
     * @throws NullPointerException iff {@code adjacentLabel} is {@code null}
     */
    public Edge<S> removeEdge(T adjacentLabel) throws NullPointerException {
        if (adjacentLabel == null)
            throw new NullPointerException("remove(adjacentLabel): adjacentLabel must not be null");

        return adjacentList.remove(adjacentLabel);
    }

    /**
     * Remove all the adjacents vertices
     */
    public void clearEdges() {
        adjacentList.clear();
    }

    @Override
    public String toString() {
        return "{" + this.label + "}";
    }

    public String adjacentsToString() {
        return adjacentList.toString();
    }
}
