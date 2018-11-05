package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Route {
    private int id;
    private List<Leg> legs = new ArrayList<>();

    public Route() {}

    public Route(int id, List<Leg> legs) {
        this.id = id;
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

    public RouteStop findRouteStop(Stop stop, int time) {
        return legs.stream()
                .map(Leg::getRouteStops)
                .flatMap(Collection::stream)
                .filter(rs -> rs.getStop().equals(stop) && rs.getTime() >= time)
                .findFirst()
                .orElse(null);
    }
}
