import java.util.Set;

public interface Node {
    Stop getStop();
    int getTime();
    Set<Node> getNextNodes();
}
