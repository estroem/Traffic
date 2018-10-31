import java.util.Set;

public abstract class Node {
    protected Double dist;
    protected boolean visited = false;

    abstract public Stop getStop();
    abstract int getTime();
    abstract Set<Node> getNextNodes();

    public void setDist(double dist) {
        this.dist = dist;
    }

    public Double getDist() {
        return dist;
    }

    public boolean isVisited() {
        return visited;
    }

    public void visit() {
        visited = true;
    }
}
