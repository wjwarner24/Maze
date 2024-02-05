import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    static GraphState firstGraph;
    static Node[] nodes;
    static Edge[] edges;
    static int rocketStartIndex;
    static int luckyStartIndex;

    public static void main(String[] args) {
        try {
            File file = new File(args[0]);
            Scanner scanner = new Scanner(file);

            // Get the number of Nodes and Edges
            String[] numNodesEdges = scanner.nextLine().split(" ");
            int numNodes = Integer.parseInt(numNodesEdges[0]);
            int numEdges = Integer.parseInt(numNodesEdges[1]);

            // Get the Colors of the Nodes
            String[] nodeColors = scanner.nextLine().split(" ");
            

            // Get the start indices of Rocket and Lucky
            String[] startIndices = scanner.nextLine().split(" ");
            rocketStartIndex = Integer.parseInt(startIndices[0]) - 1;
            luckyStartIndex = Integer.parseInt(startIndices[1]) - 1;

            // Initialize the Nodes
            nodes = new Node[numNodes];
            edges = new Edge[numEdges];
            for (int i = 0; i < numNodes - 1; i++) {
                nodes[i] = new Node(nodeColors[i]);
            }
            nodes[numNodes - 1] = new Node("GOAL");
            int index = 0;
            // Get the edges and add them to the list of edges in respective node
            while (scanner.hasNextLine()) {
                String[] edgeString = scanner.nextLine().split(" ");
                int fromIndex = Integer.parseInt(edgeString[0]) - 1;
                int toIndex = Integer.parseInt(edgeString[1]) - 1;
                String color = edgeString[2];
                Edge tempEdge = new Edge(color, fromIndex, toIndex);
                nodes[fromIndex].addEdge(tempEdge);
                edges[index] = tempEdge;
                index++;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Error with opening or reading file.");
            e.printStackTrace();
        }


        BigGraph g = new BigGraph();
        g.initializeGraph(nodes, rocketStartIndex, luckyStartIndex, edges);
        GraphState solution = g.getSolution(nodes, rocketStartIndex, luckyStartIndex);
        if (solution != null) {
            g.printPath(solution);
        }
        else {
            System.out.println("NO PATH");
        }
    }
}
