package model;

import java.util.Set;

public abstract class Node {
    protected Double dist;
    protected boolean visited = false;

    abstract public Stop getStop();
    abstract public int getTime();
    abstract public Set<Node> getNextNodes();
}
