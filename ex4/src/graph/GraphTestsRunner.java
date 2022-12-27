package graph;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class GraphTestsRunner {
    /**
     * @param args Command line arguments (unused).
     */
    public static void main(String[] args) {
        testDirectedGraph();
    }

    public static void testDirectedGraph() {
        Result result = JUnitCore.runClasses(GraphTestsDirected.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        System.out.println(result.wasSuccessful());
    }
}
