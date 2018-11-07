package main;

import builders.RoutesJSONBuilder;
import builders.StopsJSONBuilder;
import model.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.*;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MainWindow extends JFrame {
    private final Surface surface = new Surface();

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainWindow tr = new MainWindow();
                tr.setVisible(true);
                tr.start();
            }
        });
    }

    private void start() {
        Demo demo = new Demo(surface);
        demo.loadData();
        demo.runSimulation();
    }

    private MainWindow() {
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
}

class Demo {
    private Surface surface;
    private List<Stop> stops = new ArrayList<>();
    private List<Route> routes = new ArrayList<>();
    private List<Thread> threads = new ArrayList<>();

    public Demo(Surface surface) {
        this.surface = surface;
    }

    public void loadData() {
        //stops = StopsJSONBuilder.fromJson(readFile("stops.json"));
        //routes = RoutesJSONBuilder.fromJson(readFile("routes.json"));
    }

    public void runSimulation() {
        Stop stop1 = new Stop(0, "stop1", new Coords(0, 0));
        Stop stop2 = new Stop(1, "stop2", new Coords(0, 1));
        Stop stop3 = new Stop(2, "stop3", new Coords(1, 0));
        Stop stop4 = new Stop(3, "stop4", new Coords(1, 1));
        Stop stop5 = new Stop(4, "stop5", new Coords(-1, 0));

        Route route1 = new Route();
        Leg leg1 = route1.newLeg("leg1, via stop2");

        leg1.addStop(stop1, 0);
        leg1.addStop(stop2, 1);
        leg1.addStop(stop4, 4);

        Route route2 = new Route();
        Leg leg2 = route2.newLeg("leg2, via stop3");

        leg2.addStop(stop1, 0);
        leg2.addStop(stop3, 2);
        leg2.addStop(stop4, 3);

        Route route3 = new Route();
        Leg leg3 = route3.newLeg("leg3, dont go here");

        leg3.addStop(stop1, 0);
        leg3.addStop(stop5, 1);

        routes.add(route1);
        routes.add(route2);
        routes.add(route3);

        stops.add(stop1);
        stops.add(stop2);
        stops.add(stop3);
        stops.add(stop4);
        stops.add(stop5);

        surface.drawMap(stops, routes);

        Traffic traffic = new Traffic(stops, routes, surface);
        traffic.setParams(stop1, stop4, 0);

        threads.add(new Thread(new Runnable() {
            @Override
            public void run() {
                traffic.calculate2(1);
            }
        }));
        threads.add(new Thread(new Runnable() {
            @Override
            public void run() {
                traffic.calculate2(2);
            }
        }));

        threads.forEach(Thread::start);
    }

    private String readFile(String filename) {
        StringBuilder result = new StringBuilder("");

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(filename).getFile());

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.append(line).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result.toString();
    }
}