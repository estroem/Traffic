import java.util.ArrayList;
import java.util.List;

public class Leg {
    private List<RouteStop> routeStops = new ArrayList<RouteStop>();
    private Leg next;
    private String destination;

    public Leg(List<RouteStop> routeStops, Leg next, String destination) {
        this.routeStops = routeStops;
        this.next = next;
        this.destination = destination;
    }

    public List<RouteStop> getRouteStops() {
        return routeStops;
    }

    public Leg getNext() {
        return next;
    }

    public String getDestination() {
        return destination;
    }
}
