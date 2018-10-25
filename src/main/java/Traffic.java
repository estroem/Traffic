import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Traffic {
    private List<Route> routes = new ArrayList<>();

    public Traffic(List<Route> routes) {
        this.routes = routes;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public List<Node> calculate(Stop from, Stop to, int time) {


        return new ArrayList<>(routes.get(1).getLegs().get(0).getRouteStops());
    }
}
