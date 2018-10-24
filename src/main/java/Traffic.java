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

        Route route1 = new Route();
        Leg leg1 = route1.newLeg("leg1, via stop2");

        leg1.addStop(stop1, 0);
        leg1.addStop(stop2, 1);
        leg1.addStop(stop4, 2);

        Route route2 = new Route();
        Leg leg2 = route2.newLeg("leg2, via stop3");

        leg2.addStop(stop1, 0);
        leg2.addStop(stop3, 1);
        leg2.addStop(stop4, 2);

        routes.add(route1);
        routes.add(route2);
    }

    public static void calculate() {

    }
}
