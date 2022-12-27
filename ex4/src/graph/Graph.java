package graph;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import java.util.Map.Entry;

public class Graph<T, S> {
    private Hashtable<T, Vertex<T, S>> vertices = null;
    private GraphType type = GraphType.DIRECTED;

    /**
     * Init a {@code Directed} {@code Graph}
     */
    public Graph() {
        this.vertices = new Hashtable<>();
    }

    /**
     * Init a {@code Graph} of given {@code type}
     * 
     * @param type of the new {@code Graph}
     * @see GraphType
     */
    public Graph(GraphType type) {
        this.vertices = new Hashtable<>();
        this.type = type;
    }

    /**
     * @return the {@code type} of the {@code Graph}
     */
    public GraphType getType() {
        return this.type;
    }

    /**
     * @return {@code TRUE} iff the {@code Graph} is {@code  Directed},
     *         {@code FALSE} otherwise
     */
    public boolean isDirected() {
        return this.type == GraphType.DIRECTED;
    }

    /**
     * @return the number of vertices
     */
    public int getNumberVertices() {
        return this.vertices.size();
    }

    /**
     * @return the number of edges
     */
    public int getNumberEdges() {
        int sum = 0;

        for (T a : vertices.keySet()) {
            sum += vertices.get(a).getOutDegree();
        }

        return (isDirected() ? sum : sum / 2);
    }

    /**
     * Add a {@code Vertex} with the given {@code vertexLabel} into the
     * {@code Graph}
     * 
     * @param vertexLabel
     * @return {@code TRUE} iff added successfully, {@code FALSE} if already exists
     *         a vertex with the same {@code vertexLabel}
     * @throws NullPointerException iff {@code vertexLabel} is {@code null}
     * @see Vertex
     */
    public boolean addVertex(T vertexLabel) throws NullPointerException {
        if (vertexLabel == null)
            throw new NullPointerException("addVertex(vertexLabel): vertexLabel must not be null");

        Vertex<T, S> newVertex = new Vertex<>(vertexLabel);

        return (vertices.putIfAbsent(vertexLabel, newVertex) == null);
    }

    /**
     * @param vertexLabel
     * @return the {@code Vertex} with the given {@code vertexLabel} on success,
     *         {@code null} if there are no vertex with vertex {@code label}
     * @throws NullPointerException iff {@code vertexLabel} is {@code null}
     * @see Vertex
     */
    public Vertex<T, S> getVertex(T vertexLabel) throws NullPointerException {
        if (vertexLabel == null)
            throw new NullPointerException("getVertex(vertexLabel): vertexLabel must not be null");

        return vertices.get(vertexLabel);
    }

    /**
     * Check if the vertex {@code label} is contained in the {@code Graph}
     * 
     * @param vertexLabel
     * @return {@code TRUE} iff {@code vertexLabel} is contained in the
     *         {@code Graph}, {@code FALSE} otherwise
     * @throws NullPointerException iff {@code vertexLabel} is {@code null}
     */
    public boolean containsVertex(T vertexLabel) throws NullPointerException {
        if (vertexLabel == null)
            throw new NullPointerException("containsVertex(vertexLabel): vertexLabel must not be null");

        return vertices.containsKey(vertexLabel);
    }

    /**
     * @param vertexA
     * @param vertexB
     * @return {@code TRUE} iff there is an {@code edge} between {@code vertexA} and
     *         {@code vertexB}, {@code FALSE} otherwise
     * @throws NullPointerException
     *                              <ul>
     *                              <li>if {@code vertexA} is {@code null}</li>
     *                              <li>if {@code vertexB} is {@code null}</li>
     *                              <li>if there are no {@code Vertex} with
     *                              {@code vertexA} label or {@code vertexB}
     *                              label to search on</li>
     *                              </ul>
     */
    public boolean containsEdge(T vertexA, T vertexB) throws NullPointerException {
        if (vertexA == null)
            throw new NullPointerException("containsEdge(vertexA, vertexB): vertexA must not be null");

        if (vertexB == null)
            throw new NullPointerException("containsEdge(vertexA, vertexB): vertexB must not be null");

        Vertex<T, S> firstVertex = vertices.get(vertexA);
        Vertex<T, S> secondVertex = vertices.get(vertexB);

        return firstVertex.hasAdjacent(vertexB) || secondVertex.hasAdjacent(vertexB);
    }

    /**
     * Get all the vertices label in the {@code Graph}
     * 
     * @return a new {@link Set} of vertices label
     */
    public Set<T> getVerticesLabel() {
        Set<T> verts = new HashSet<>();
        for (T a : vertices.keySet()) {
            verts.add(a);
        }
        return verts;
    }

    /**
     * Get all the vertices in the {@code Graph}
     * 
     * @return a new {@link Set} of {@code Vertex}
     * @see Vertex
     */
    public Set<Vertex<T, S>> getVertices() {
        Set<Vertex<T, S>> vertices = new HashSet<>();
        for (T vLabel : getVerticesLabel()) {
            vertices.add(getVertex(vLabel));
        }
        return vertices;
    }

    /**
     * Get all the edges in the {@code Graph}
     * 
     * @return a new {@code Set} of {@code Edge}
     * @see Edge
     */
    public Set<Edge<S>> getEdges() {
        Set<Edge<S>> edges = new HashSet<>();

        for (T vLabel : this.vertices.keySet()) {
            for (Edge<S> b : getVertex(vLabel).getEdges()) {
                edges.add(b);
            }
        }

        return edges;
    }

    /**
     * @param vertexLabel to remove
     * @return the {@code Vertex} removed with the given label
     * @throws NullPointerException iff {@code vertexLabel} is {@code null}
     */
    public Vertex<T, S> removeVertex(T vertexLabel) throws NullPointerException {
        if (vertexLabel == null)
            throw new NullPointerException("removeVertex(vertexLabel): vertexLabel must not be null");

        Vertex<T, S> ris = vertices.remove(vertexLabel);

        if (ris != null) {
            Set<T> verticesLabel = getVerticesLabel();

            for (T vLabel : verticesLabel) {
                Vertex<T, S> curr = this.vertices.get(vLabel);
                if (curr.hasAdjacent(vertexLabel)) {
                    curr.removeEdge(vertexLabel);
                }
            }
        }

        return ris;
    }

    /**
     * @param vertexLabel vertex whose adjacents label you want to know
     * @return a {@link Set} of adjacents label of the given {@code vertexLabel}
     * @throws NullPointerException iff {@code vertexLabel} is {@code null}
     */
    public Set<T> getAdjacentVerticesLabel(T vertexLabel) throws NullPointerException, GraphException {
        if (vertexLabel == null)
            throw new NullPointerException("getAdjacentVerticesLabel(vertexLabel): vertexLabel must not be null");

        if (!vertices.containsKey(vertexLabel))
            return null;

        Vertex<T, S> vertex = vertices.get(vertexLabel);

        return vertex.getAdjacentVerticesLabel();
    }

    /**
     * @param vertexLabel
     * @return a new {@link Set} of {@link Vertex} adjacents with the {@code vertex}
     *         with the given label
     * @throws NullPointerException iff {@code vertexLabel} is {@code null}
     */
    public Set<Vertex<T, S>> getAdjacentVertices(T vertexLabel) throws NullPointerException, GraphException {
        if (vertexLabel == null)
            throw new NullPointerException("getAdjacentVertices(vertexLabel): vertexLabel must not be null");

        if (!vertices.containsKey(vertexLabel))
            return null;

        Set<T> adjVerticesLabel = getAdjacentVerticesLabel(vertexLabel);

        Set<Vertex<T, S>> adjVertices = new HashSet<>();
        for (T adjLabel : adjVerticesLabel) {
            adjVertices.add(getVertex(adjLabel));
        }
        return adjVertices;
    }

    /**
     * Check if the two vertices are adjacents
     * 
     * @param vertexFrom
     * @param vertexTo
     * @return {@code TRUE} iff the two vertices are adjacents, {@code FALSE}
     *         otherwise
     * @throws NullPointerException iff {@code vertexFrom} OR {@code vertexTo} are
     *                              {@code null}
     */
    public boolean areAdjacents(T vertexFrom, T vertexTo) throws NullPointerException {
        if (vertexFrom == null)
            throw new NullPointerException("areAdjacents(vertexFrom, vertexTo): vertexFrom must not be null");

        if (vertexTo == null)
            throw new NullPointerException("areAdjacents(vertexFrom, vertexTo): vertexTo must not be null");

        Vertex<T, S> firstVertex = vertices.get(vertexFrom);

        return firstVertex.hasAdjacent(vertexTo);
    }

    /**
     * Check if the two vertices are complete adjacents:
     * <ul>
     * <li>{@code vertexA} is adjacent of {@code vertexB}</li>
     * <li>{@code vertexB} is adjacent of {@code vertexA}</li>
     * </ul>
     * For {@code UNDIRECTED} {@code Graph} {@link #areAdjacents(Object, Object)}
     * and {@link #areCompleteAdjacents(Object, Object)} return the same result
     * 
     * @param vertexA
     * @param vertexB
     * @return {@code TRUE} iff the two vertices are complete adjacents,
     *         {@code FALSE}
     *         otherwise
     * @throws NullPointerException iff {@code vertexA} OR {@code vertexB} are
     *                              {@code null}
     */
    public boolean areCompleteAdjacents(T vertexA, T vertexB) {
        return areAdjacents(vertexA, vertexB) && areAdjacents(vertexB, vertexA);
    }

    /**
     * @param vertexFrom
     * @param vertexTo
     * @return the edge that connect {@code vertexFrom} with {@code vertexTo} on
     *         success, {@code null} otherwise
     * @throws NullPointerException iff {@code vertexFrom} OR {@code vertexTo} are
     *                              {@code null}
     */
    public Edge<S> getEdge(T vertexFrom, T vertexTo) throws NullPointerException {
        if (vertexFrom == null)
            throw new NullPointerException("getEdge(vertexFrom, vertexTo): vertexFrom must not be null");

        if (vertexTo == null)
            throw new NullPointerException("getEdge(vertexFrom, vertexTo): vertexTo must not be null");

        Vertex<T, S> vertex = vertices.get(vertexFrom);

        if (vertex == null)
            return null;

        return vertex.getEdge(vertexTo);
    }

    /**
     * @param vertexFrom
     * @param vertexTo
     * @param edgeLabel
     * @throws NullPointerException iff {@code vertexFrom} OR {@code vertexTo} OR
     *                              {@code edgeLabel} are {@code null}
     * @throws GraphException       if {@code vertexFrom} OR {@code vertexTo}
     *                              are not in the {@code Graph}
     */
    public void addEdge(T vertexFrom, T vertexTo, S edgeLabel) throws NullPointerException, GraphException {
        if (vertexFrom == null)
            throw new NullPointerException("addEdge(vertexFrom, vertexTo, edgeLabel): vertexFrom must not be null");

        if (vertexTo == null)
            throw new NullPointerException("addEdge(vertexFrom, vertexTo, edgeLabel): vertexTo must not be null");

        if (edgeLabel == null)
            throw new NullPointerException("addEdge(vertexFrom, vertexTo, edgeLabel): edgeLabel must not be null");

        if (!vertices.containsKey(vertexFrom))
            throw new GraphException("There is no vertexFrom:" + vertexFrom + " in the Graph");

        if (!vertices.containsKey(vertexTo))
            throw new GraphException("There is no vertexTo" + vertexTo + " in the Graph");

        Vertex<T, S> firstVertex = vertices.get(vertexFrom);

        firstVertex.addAdjacent(vertexTo, edgeLabel);

        if (!isDirected()) {
            Vertex<T, S> secondVertex = vertices.get(vertexTo);
            secondVertex.addAdjacent(vertexFrom, edgeLabel);
        }
    }

    /**
     * @param vertexA
     * @param vertexB
     * @return the {@code Edge} removed
     * @throws NullPointerException iff {@code vertexA} OR {@code vertexB} are
     *                              {@code null}
     * @throws GraphException
     *                              <ul>
     *                              <li>if {@code vertexA} OR {@code vertexB} are
     *                              not in the {@code Graph}</li>
     *                              <li>if there is no {@code edge} between
     *                              the two vertices</li>
     *                              </ul>
     */
    public Edge<S> removeEdge(T vertexA, T vertexB) throws NullPointerException, GraphException {
        if (vertexA == null)
            throw new NullPointerException("removeEdge(vertexA, vertexB): vertexA must not be null");

        if (vertexB == null)
            throw new NullPointerException("removeEdge(vertexA, vertexB): vertexB must not be null");

        if (!vertices.containsKey(vertexA))
            throw new GraphException("There is no vertexA:" + vertexA + " in the Graph");

        if (!vertices.containsKey(vertexB))
            throw new GraphException("There is no vertexB:" + vertexB + " in the Graph");

        Vertex<T, S> firstVertex = vertices.get(vertexA);

        Edge<S> edgeRemoved = firstVertex.removeEdge(vertexB);

        if (edgeRemoved == null)
            throw new GraphException(
                    "There is no edge between vertexA:" + vertexA + " and vertexB:" + vertexB + " in the Graph");

        if (!isDirected()) {
            Vertex<T, S> secondVertex = vertices.get(vertexB);

            if (secondVertex.removeEdge(vertexA) == null)
                throw new GraphException(
                        "There is no edge between vertexB:" + vertexB + " and vertexA:" + vertexA
                                + " in the Graph");
        }

        return edgeRemoved;
    }

    @Override
    public String toString() {
        String s = "{\n";
        for (Entry<T, Vertex<T, S>> e : vertices.entrySet()) {
            s += "  " + e.getKey() + ": " + e.getValue().adjacentsToString() + "\n";
        }
        s += "}";
        return s;
    }
}
