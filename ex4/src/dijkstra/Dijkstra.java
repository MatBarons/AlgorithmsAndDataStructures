package dijkstra;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import graph.Graph;
import graph.Vertex;
import graph.Edge;
import graph.GraphException;
import minimumheap.MinimumHeap;
import minimumheap.MinimumHeapException;

public class Dijkstra {

    public static <T, S> Set<Vertex<T, S>> dijkstra(Graph<T, S> graph, T srcLabel)
            throws MinimumHeapException, GraphException, Exception {
        GenericComparator<T, S> comparator = new GenericComparator<>();

        MinimumHeap<Vertex<T, S>> Q = new MinimumHeap<>(comparator);
        Set<Vertex<T, S>> visited = new HashSet<>(); // new Set containing all the vertices already visited

        Vertex<T, S> src = graph.getVertex(srcLabel); // retrieve the Vertex

        init(graph, src, Q);

        visited.add(src); // add the source to the set of visited

        while (Q.size() != 0) {
            Vertex<T, S> u = Q.remove(); // extract the Vertex with min distance --> u

            visited.add(u); // add the Vertex (u) extracted into the set of visited

            for (Vertex<T, S> v : graph.getAdjacentVertices(u.getLabel())) { // foreach vertex v which is adjacent of u
                relax(u, v, Q);
            }
        }

        return visited;
    }

    private static <T, S> void relax(Vertex<T, S> u, Vertex<T, S> v, MinimumHeap<Vertex<T, S>> Q)
            throws MinimumHeapException {
        Double alt = u.getDistance() + u.getEdgeWeight(v.getLabel());

        if (v.getDistance() > alt && u.getDistance() != Double.POSITIVE_INFINITY) {
            Vertex<T, S> tmp = v; // save the vertex v into tmp

            v.setDistance(alt);
            v.setPi(u);

            Q.decrease(tmp, v); // decrease the MinimumHeap, change the old v (tmp) with the new v
        }
    }

    private static <T, S> void init(Graph<T, S> graph, Vertex<T, S> src, MinimumHeap<Vertex<T, S>> Q)
            throws MinimumHeapException {
        for (Vertex<T, S> v : graph.getVertices()) { // foreach vertex v in the graph
            if (!v.equals(src)) {
                v.setDistance(Double.POSITIVE_INFINITY);
                v.setPi(null);
            }
            Q.add(v); // add the vertex to the MinimumHeap
        }
        src.setDistance(0); // set the distance of the source (from the source) to 0
    }
}

class GenericComparator<T, S> implements Comparator<Vertex<T, S>> {
    @Override
    public int compare(Vertex<T, S> o1, Vertex<T, S> o2) {
        if (o1.getDistance() > o2.getDistance()) {
            return 1;
        } else if (o1.getDistance() < o2.getDistance()) {
            return -1;
        } else {
            return 0;
        }
    }
}