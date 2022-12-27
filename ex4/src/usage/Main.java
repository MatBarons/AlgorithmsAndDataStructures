package usage;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Set;

import dijkstra.Dijkstra;
import graph.Edge;
import graph.Graph;
import graph.GraphException;
import graph.GraphType;
import graph.Vertex;

public class Main {

    private static final Charset ENCODING = StandardCharsets.UTF_8;

    private static void loadGraph(String filepath, Graph<String, String> graph)
            throws IOException, GraphException {
        System.out.format("\n%sLoading data from file...%s\n", ConsoleColors.YELLOW, ConsoleColors.RESET);

        Path inputFilePath = Paths.get(filepath);

        /* ADD ALL VERTICES */
        try (BufferedReader fileInputReader = Files.newBufferedReader(inputFilePath, ENCODING)) {
            String line = null;
            while ((line = fileInputReader.readLine()) != null) {
                String[] lineElements = line.split(",");

                graph.addVertex(lineElements[0]);
                graph.addVertex(lineElements[1]);
            }
        }

        /* ADD ALL EDGES + WEIGHTS */
        try (BufferedReader fileInputReader = Files.newBufferedReader(inputFilePath,
                ENCODING)) {
            String line = null;
            while ((line = fileInputReader.readLine()) != null) {
                String[] lineElements = line.split(",");

                try {
                    graph.addEdge(lineElements[0], lineElements[1], lineElements[0] + "-" +
                            lineElements[1]);
                    graph.addEdge(lineElements[1], lineElements[0], lineElements[1] + "-" +
                            lineElements[0]);

                    graph.getEdge(lineElements[0],
                            lineElements[1]).setWeight(Double.parseDouble(lineElements[2]));

                    graph.getEdge(lineElements[1],
                            lineElements[0]).setWeight(Double.parseDouble(lineElements[2]));
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }
        System.out.format("%sData loaded%s\n\n", ConsoleColors.YELLOW, ConsoleColors.RESET);
    }

    public static void main(String[] args) {
        Graph<String, String> graph = new Graph<>();
        try {
            loadGraph(args[0], graph);

            String srcLabel = "torino";
            String dstLabel = "catania";

            long nanoTime1 = System.nanoTime();
            Set<Vertex<String, String>> ris = Dijkstra.dijkstra(graph, srcLabel);
            long nanoTime2 = System.nanoTime();

            long runTimeInNanoSeconds = (nanoTime2 - nanoTime1);
            System.out
                    .format("%sTime taken by Dijkstra:%s %.2f milliseconds%s\n\n", ConsoleColors.CYAN,
                            ConsoleColors.GREEN_BOLD_BRIGHT, (double) (runTimeInNanoSeconds) / 1000000,
                            ConsoleColors.RESET);

            findDistance(ris, srcLabel, dstLabel);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    /**
     * From the given {@code Set} of vertices print the path and the distance from
     * {@code srcLabel} to {@code dstLabel}
     * 
     * @param ris      {@code Set} of vertices resulting from the call to Dijkstra
     * @param srcLabel
     * @param dstLabel
     */
    private static void findDistance(Set<Vertex<String, String>> ris, String srcLabel, String dstLabel) {
        Iterator<Vertex<String, String>> i = ris.iterator();
        while (i.hasNext()) {
            Vertex<String, String> v = i.next();

            if (v.getLabel().equals(dstLabel)) {
                System.out.format("%sCities crossed:%s\n", ConsoleColors.CYAN, ConsoleColors.RESET);
                int cities = path(v);
                System.out.format("%sNumber of cities crossed:%s %d%s\n", ConsoleColors.CYAN,
                        ConsoleColors.GREEN_BOLD_BRIGHT,
                        cities, ConsoleColors.RESET);

                System.out.format("%sDistance between %s%s%s and %s%s%s :%s ~%.2f Km%s\n\n", ConsoleColors.CYAN,
                        ConsoleColors.PURPLE_BACKGROUND_BRIGHT, srcLabel, ConsoleColors.CYAN,
                        ConsoleColors.PURPLE_BACKGROUND_BRIGHT, dstLabel, ConsoleColors.CYAN,
                        ConsoleColors.GREEN_BOLD_BRIGHT, (v.getDistance() / 1000), ConsoleColors.RESET);

                return;
            }
        }

        System.out.format("%sDistance between %s and %s :%s %f%s\n\n", ConsoleColors.CYAN, srcLabel, dstLabel,
                ConsoleColors.RED_BOLD, Double.POSITIVE_INFINITY, ConsoleColors.RESET);
    }

    /**
     * Print the path from the destination {@code Vertex} up to the source
     * 
     * @param v {@code Vertex} destination
     * @return the number of cities crossed
     */
    private static int path_aux(Vertex<String, String> v) {
        if (v.getPi() == null) {
            System.out.format("\t%s%s%s\n", ConsoleColors.PURPLE_BACKGROUND_BRIGHT, v.getLabel(), ConsoleColors.RESET);
            return 1;
        }

        int c = path_aux(v.getPi());
        System.out.println("\t" + v.getLabel());
        return c + 1;
    }

    private static int path(Vertex<String, String> v) {
        int c = path_aux(v.getPi());
        System.out.format("\t%s%s%s\n", ConsoleColors.PURPLE_BACKGROUND_BRIGHT, v.getLabel(), ConsoleColors.RESET);
        return c + 1;
    }
}
