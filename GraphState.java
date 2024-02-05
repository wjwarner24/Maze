import java.util.*;

public class GraphState {
    int rocketNodeIndex;
    int luckyNodeIndex;
    ArrayList<GraphState> childStates;
    boolean win;

    // Constructor
    public GraphState(int rocketNodeIndex, int luckyNodeIndex, boolean win) {
        this.rocketNodeIndex = rocketNodeIndex;
        this.luckyNodeIndex = luckyNodeIndex;
        this.win = win;
        childStates = new ArrayList<GraphState>();
    }

    // returns a string representing which player moved from this graphState to g, alse where. (eg. L1)
    public String getMoveString(GraphState g) {
        if (this.rocketNodeIndex == g.rocketNodeIndex && this.luckyNodeIndex == g.luckyNodeIndex) {
            return "";
        }
        else if (this.rocketNodeIndex == g.rocketNodeIndex) {
            return "L" + (g.luckyNodeIndex+1);
        }
        else if (this.luckyNodeIndex == g.luckyNodeIndex) {
            return "R" + (g.rocketNodeIndex+1);
        }

        return "";
    }

    // adds adjacent graphState
    public void addAdj(GraphState g) {
        childStates.add(g);

    }
}
