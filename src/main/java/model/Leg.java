package model;

import java.util.ArrayList;
import java.util.List;

public class Leg {
    private List<RouteStop> routeStops = new ArrayList<RouteStop>();
    private int id;
    private int nextLeg;
    private Leg next;
    private String destination;
    private Route route;

    public Leg(List<RouteStop> routeStops, Leg next, String destination) {
        this.routeStops = routeStops;
        this.next = next;
        this.destination = destination;
    }

    public Leg(Route route, String destination) {
        this.route = route;
        this.destination = destination;
    }

    public RouteStop addStop(Stop stop, int time) {
        RouteStop routeStop = new RouteStop(stop, time);
        if(routeStops.size() > 0) {
            routeStops.get(routeStops.size()-1).setNext(routeStop);
        }
        routeStops.add(routeStop);
        stop.addRoute(route);
        return routeStop;
    }

    public void setNext(Leg next) {
        this.next = next;
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

    public Route getRoute() {
        return route;
    }
}
