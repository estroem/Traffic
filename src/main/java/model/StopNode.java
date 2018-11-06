package model;

import java.util.Set;
import java.util.stream.Collectors;

public class StopNode extends Node {
    private Stop stop;
    private int time;

    public StopNode(Stop stop, int time) {
        this.stop = stop;
        this.time = time;
    }

    public Stop getStop() {
        return stop;
    }

    public int getTime() {
        return time;
    }

    public Set<Node> getNextNodes() {
        return stop.getRoutes().stream()
                .map(r -> r.findRouteStop(stop, time))
                .collect(Collectors.toSet());
    }
}
