package graph;

public class Edge<S> {

    private S label = null;
    private double weight = 0;

    /**
     * Init an {@code Edge} with the given {@code label}
     * 
     * @param label name of the edge
     */
    public Edge(S label) {
        this.label = label;
    }

    /**
     * Set the {@code weight} of the {@code edge}
     * 
     * @param weight
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * @return the {@code weight} of the {@code edge}
     */
    public double getWeight() {
        return this.weight;
    }

    /**
     * @return the {@code label} of the {@code edge}
     */
    public S getLabel() {
        return this.label;
    }

    @Override
    public String toString() {
        return "" + this.weight;
    }
}
