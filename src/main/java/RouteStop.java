public class RouteStop {
    private Stop stop;
    private int time;

    public RouteStop(Stop stop, int time) {
        this.stop = stop;
        this.time = time;
    }

    public Stop getStop() {
        return stop;
    }

    public int getTime() {
        return time;
    }
}
