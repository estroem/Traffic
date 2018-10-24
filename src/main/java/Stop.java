import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Stop {
    private static int nextId = 0;

    private int id;
    private String name;
    private Coords coords;
    private Set<Route> routes = new HashSet<Route>();

    public Stop(String name, Coords coords) {
        this.name = name;
        this.coords = coords;
        this.id = nextId++;
    }

    public void addRoute(Route route) {
        routes.add(route);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coords getCoords() {
        return coords;
    }
}
