package model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Stop {
    private int id;
    private String name;
    private Coords coords;
    private Set<Route> routes = new HashSet<Route>();

    public Stop() {}

    public Stop(int id, String name, Coords coords) {
        this.id = id;
        this.name = name;
        this.coords = coords;
    }

    public void addRoute(Route route) {
        routes.add(route);
    }

    public Set<Route> getRoutes() {
        return routes;
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

    @Override
    public boolean equals(Object obj) {
        return this == obj || obj instanceof Stop && ((Stop)obj).name.equals(name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
