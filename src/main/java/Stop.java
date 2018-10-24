public class Stop {
    private static int nextId = 0;

    private int id;
    private String name;
    private Coords coords;

    public Stop(String name, Coords coords) {
        this.name = name;
        this.coords = coords;
        this.id = nextId++;
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
