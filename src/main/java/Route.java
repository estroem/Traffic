import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Route {
    private List<Leg> legs;

    public Route() {}

    public Route(List<Leg> legs) {
        this.legs = legs;
    }
    public List<Leg> getLegs() {
        return legs;
    }

    public Leg newLeg(String destination) {
        Leg leg = new Leg(this, destination);
        legs.add(leg);
        return leg;
    }

    public Iterator<RouteStop> iterator(Stop stop, int time) {
        List<RouteStop> routeStops = legs.get(0).getRouteStops();
        for(int i = 0; i < routeStops.size(); i++) {
            if(routeStops.get(i).getStop().equals(stop)) {
                return routeStops.listIterator();
            }
        }
        return null;
    }
}
