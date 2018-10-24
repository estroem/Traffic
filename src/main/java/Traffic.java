import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Traffic {
    public static List<Route> routes = new ArrayList<Route>();

    public static void main(String args[]) {
        Stop stop1 = new Stop("stop1", new Coords(0, 0));
        Stop stop2 = new Stop("stop2", new Coords(0, 1));
        Stop stop3 = new Stop("stop3", new Coords(1, 0));
        Stop stop4 = new Stop("stop4", new Coords(1, 1));

        List<RouteStop> routeStops1 = new ArrayList<RouteStop>();
        routeStops1.add(new RouteStop(stop1, 0));
        routeStops1.add(new RouteStop(stop2, 1));
        routeStops1.add(new RouteStop(stop4, 2));

        Route route1 = new Route(Collections.singletonList(new Leg(routeStops1, null, "dest: stop4 via stop2")));

        List<RouteStop> routeStops2 = new ArrayList<RouteStop>();
        routeStops2.add(new RouteStop(stop1, 0));
        routeStops2.add(new RouteStop(stop3, 1));
        routeStops2.add(new RouteStop(stop4, 2));

        Route route2 = new Route(Collections.singletonList(new Leg(routeStops2, null, "dest: stop4 via stop3")));

        routes.add(route1);
        routes.add(route2);
    }

    public static void calculate() {

    }
}
