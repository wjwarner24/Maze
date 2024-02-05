import java.util.ArrayList;

public class Node {
    String color;
    ArrayList<Edge> edges;

    public Node(String col) {
        this.color = col;
        this.edges = new ArrayList<Edge>();
    }

    public void addEdge(Edge e) {
        edges.add(e);
    }
 
}
