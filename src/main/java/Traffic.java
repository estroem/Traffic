import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

class Surface extends JPanel implements ActionListener {
    private final int DELAY = 150;
    private Timer timer;
    private List<Stop> stops;
    private List<Route> routes;

    public Surface() {
        initTimer();
    }

    private void initTimer() {
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public Timer getTimer() {
        return timer;
    }

    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        if(routes != null && stops != null) {
            g2d.setPaint(Color.LIGHT_GRAY);
            g2d.setStroke(new BasicStroke(15));
            for(Route route : routes) {
                for(Leg leg : route.getLegs())
                for(int i = 0; i < leg.getRouteStops().size()-1; i++) {
                    Coords from = leg.getRouteStops().get(i).getStop().getCoords();
                    Coords to = leg.getRouteStops().get(i+1).getStop().getCoords();
                    g2d.drawLine((int)from.getX()*100+20, (int)from.getY()*100+20, (int)to.getX()*100+20, (int)to.getY()*100+20);
                }
            }
            g2d.setPaint(Color.BLACK);
            for(Stop stop : stops) {
                g2d.fillOval((int)stop.getCoords().getX()*100+5, (int)stop.getCoords().getY()*100+5, 30, 30);
            }
        }
    }

    public void drawMap(List<Route> routes, List<Stop> stops) {
        this.routes = routes;
        this.stops = stops;
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}

public class Traffic extends JFrame {
    final Surface surface = new Surface();

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Traffic tr = new Traffic();
                tr.setVisible(true);
                tr.setup();
            }
        });
    }

    public Traffic() {
        initUI();
    }

    private void initUI() {
        add(surface);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Timer timer = surface.getTimer();
                timer.stop();
            }
        });

        setTitle("Points");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setup() {
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

        List<Route> routes = new ArrayList<Route>();

        routes.add(route1);
        routes.add(route2);

        List<Stop> stops = new ArrayList<>();
        stops.add(stop1);
        stops.add(stop2);
        stops.add(stop3);
        stops.add(stop4);

        surface.drawMap(routes, stops);

        calculate(routes, stop1, stop4, 0);
    }

    public void calculate(List<Route> routes, Stop from, Stop to, int startTime) {

    }


}