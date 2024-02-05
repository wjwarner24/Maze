
import java.util.*;

public class BigGraph {
    private Map<GraphState, GraphState> parentMap;
    private Map<Integer, GraphState> states;


    public BigGraph() {
        this.parentMap = new HashMap<>();
        states = new HashMap<Integer, GraphState>();
    }

    public void initializeGraph(Node[] nodes, int rocketStartIndex, int luckyStartIndex, Edge[] edges) {
        for (int i = 0; i < nodes.length; i++) { // i = rocket
            for (int j = 0; j < nodes.length; j++) { // j = lucky
                GraphState temp = new GraphState(i, j, (nodes[i].color.equals("GOAL") || nodes[j].color.equals("GOAL")));
                states.put(key(i, j, nodes.length), temp);
            }
        }
        for (Edge e : edges) {
            for (int i = 0; i < nodes.length; i++) {
                GraphState state = states.get(key(i, e.fromIndex, nodes.length));
                if (nodes[i].color.equals(e.color)) {
                    state.addAdj(states.get(key(i, e.toIndex, nodes.length)));
                }
            }
            for (int i = 0; i < nodes.length; i++) {
                GraphState state = states.get(key(e.fromIndex, i, nodes.length));
                if (nodes[i].color.equals(e.color)) {
                    state.addAdj(states.get(key(e.toIndex, i, nodes.length)));
                }
            }
        }
    }

    // BFS algorithm
    public GraphState getSolution(Node[] nodes, int rocketIndex, int luckyIndex) {
        Queue<GraphState> queue = new LinkedList<>();
        Set<GraphState> visited = new HashSet<>();
        GraphState startState = states.get(key(rocketIndex, luckyIndex, nodes.length));
        queue.add(startState);
        visited.add(startState);
        parentMap.put(startState, null);

        while (!queue.isEmpty()) {
            GraphState currentState = queue.poll();

            if (currentState.win) {
                return currentState;
            }

            List<GraphState> childStates = currentState.childStates;
            sort(childStates, currentState); // lexographically sorts the moves
            for (GraphState childState : childStates) {
                if (!visited.contains(childState)) {
                    visited.add(childState);
                    queue.add(childState);
                    parentMap.put(childState, currentState);
                }
            }
        }
        return null;
    }

    //prints the path
    public void printPath(GraphState solution) {
        ArrayList<GraphState> path = new ArrayList<>();
        while (solution != null) {
            path.add(solution);
            solution = parentMap.get(solution);
        }
        Collections.reverse(path);
        for (int i = 1; i < path.size(); i++) {
            System.out.print(path.get(i-1).getMoveString(path.get(i)));
        }
        System.out.println();
    }

    // lexographically sorts a list GraphStates
    public static void sort(List<GraphState> children, GraphState current) {
        Collections.sort(children, new Comparator<GraphState>() {
            @Override
            public int compare(GraphState child1, GraphState child2) {
                String moveString1 = current.getMoveString(child1);
                String moveString2 = current.getMoveString(child2);
                return moveString1.compareTo(moveString2);
            }
        });
    }

    // custom hash function, a is index of Rocket, b is index of Lucky
    private int key(int a, int b, int numNodes) {
        return ((a * numNodes) + b);
    }
}
