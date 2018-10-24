import java.util.ArrayList;
import java.util.List;

public class Route {
    private List<Leg> legs;

    public Route() {}

    public Route(List<Leg> legs) {
        this.legs = legs;
    }
    public List<Leg> getLegs() {
        return legs;
    }

    public Leg newLeg(String destination) {
        Leg leg = new Leg(this, destination);
        legs.add(leg);
        return leg;
    }
}
