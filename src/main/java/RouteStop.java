import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RouteStop implements Node {
    private Stop stop;
    private int time;
    private RouteStop next;

    public RouteStop(Stop stop, int time) {
        this.stop = stop;
        this.time = time;
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
        ret.add(next);
        ret.add(new StopNode(stop, time));
        return ret;
    }
}
