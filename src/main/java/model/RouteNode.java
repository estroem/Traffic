package model;

public class RouteNode {
    private RouteStop routeStop;

    public RouteNode(RouteStop routeStop) {
        this.routeStop = routeStop;
    }

    public RouteStop getRouteStop() {
        return routeStop;
    }
}
