package model;

public class Coords {
    private final double x;
    private final double y;

    public Coords() {
        this.x = 0;
        this.y = 0;
    }

    public Coords(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
