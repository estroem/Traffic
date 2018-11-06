package model;

import java.util.HashSet;
import java.util.Set;

public class RouteStop extends Node {
    private int id;
    private Stop stop;
    private int time;
    private RouteStop next;

    public RouteStop() {}

    public RouteStop(Stop stop, int time) {
        this.stop = stop;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public Stop getStop() {
        return stop;
    }

    public int getTime() {
        return time;
    }

    public void setNext(RouteStop next) {
        this.next = next;
    }

    public Set<Node> getNextNodes() {
        Set<Node> ret = new HashSet<>();
        if(next != null) ret.add(next);
        ret.add(new StopNode(stop, time));
        return ret;
    }
}
