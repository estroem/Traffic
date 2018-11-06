package model;

import java.util.ArrayList;
import java.util.List;

public class Leg {
    private List<RouteStop> stops = new ArrayList<RouteStop>();
    private int id;
    private int nextLeg;
    private Leg next;
    private String destination;
    private Route route;

    public Leg() {}

    public Leg(int id, String destination, int nextLeg, List<RouteStop> stops) {
        this.id = id;
        this.destination = destination;
        this.nextLeg = nextLeg;
        this.stops = stops;
    }

    public Leg(List<RouteStop> stops, Leg next, String destination) {
        this.stops = stops;
        this.next = next;
        this.destination = destination;
    }

    public Leg(Route route, String destination) {
        this.route = route;
        this.destination = destination;
    }

    public RouteStop addStop(Stop stop, int time) {
        RouteStop routeStop = new RouteStop(stop, time);
        if(stops.size() > 0) {
            stops.get(stops.size()-1).setNext(routeStop);
        }
        stops.add(routeStop);
        stop.addRoute(route);
        return routeStop;
    }

    public int getId() {
        return id;
    }

    public int getNextLeg() {
        return nextLeg;
    }

    public void setNext(Leg next) {
        this.next = next;
    }

    public List<RouteStop> getStops() {
        return stops;
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
