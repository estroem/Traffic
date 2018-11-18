package main;

import model.*;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.*;
import java.util.List;
import javax.swing.JFrame;
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
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

class Demo {
    private static final int NUM_THREADS = 3;

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
        Stop stop2 = new Stop(2, "stop2", new Coords(0.8, 0));
        Stop stop3 = new Stop(3, "stop3", new Coords(1.2, 0.3));
        Stop stop4 = new Stop(4, "stop4", new Coords(1.5, 0.2));
        Stop stop5 = new Stop(0, "stop5", new Coords(1.6, 1));
        Stop stop6 = new Stop(1, "stop6", new Coords(1.8, 0.9));
        Stop stop7 = new Stop(2, "stop7", new Coords(2.1, 1.4));
        Stop stop8 = new Stop(3, "stop8", new Coords(2.6, 1.7));
        Stop stop9 = new Stop(1, "stop9", new Coords(0.3, 1));
        Stop stop10 = new Stop(4, "stop10", new Coords(0.4, 1.4));
        Stop stop11 = new Stop(0, "stop11", new Coords(1, 1.5));
        Stop stop12 = new Stop(1, "stop12", new Coords(1.5, 1.6));
        Stop stop13 = new Stop(2, "stop13", new Coords(1.6, 1.9));
        Stop stop14 = new Stop(3, "stop14", new Coords(1.7, 2.4));
        Stop stop15 = new Stop(4, "stop15", new Coords(1.9, 2.2));
        Stop stop16 = new Stop(0, "stop16", new Coords(2.1, 2));
        Stop stop17 = new Stop(1, "stop17", new Coords(2.3, -0.6));
        Stop stop18 = new Stop(2, "stop18", new Coords(1.9, -0.4));
        Stop stop19 = new Stop(3, "stop19", new Coords(0.6, 2));
        Stop stop20 = new Stop(4, "stop20", new Coords(0.1, 2.5));
        Stop stop21 = new Stop(0, "stop21", new Coords(-0.1, 2.9));
        Stop stop22 = new Stop(1, "stop22", new Coords(-0.4, 3.2));/*
        Stop stop23 = new Stop(2, "stop23", new Coords(1, 0));
        Stop stop24 = new Stop(3, "stop24", new Coords(1, 1));
        Stop stop25 = new Stop(4, "stop25", new Coords(-1, 0));*/

        Route route1 = new Route();
        Leg leg1 = route1.newLeg("leg1, via stop2");

        leg1.addStop(stop1, 0);
        leg1.addStop(stop2, 1);
        leg1.addStop(stop3, 4);
        leg1.addStop(stop4, 5);
        leg1.addStop(stop5, 8);
        leg1.addStop(stop6, 9);
        leg1.addStop(stop7, 11);
        leg1.addStop(stop8, 18);

        Route route2 = new Route();
        Leg leg2 = route2.newLeg("leg2, via stop3");

        leg2.addStop(stop1, 0);
        leg2.addStop(stop9, 2);
        leg2.addStop(stop10, 3);
        leg2.addStop(stop11, 10);
        leg2.addStop(stop12, 11);
        leg2.addStop(stop13, 12);
        leg2.addStop(stop14, 13);
        leg2.addStop(stop15, 14);
        leg2.addStop(stop16, 15);
        leg2.addStop(stop8, 16);

        Route route3 = new Route();
        Leg leg3 = route3.newLeg("leg3, via stop3");

        leg3.addStop(stop17, 0);
        leg3.addStop(stop18, 2);
        leg3.addStop(stop4, 5);
        leg3.addStop(stop5, 8);
        leg3.addStop(stop11, 9);
        leg3.addStop(stop19, 10);
        leg3.addStop(stop20, 13);
        leg3.addStop(stop21, 16);
        leg3.addStop(stop22, 19);

        routes.add(route1);
        routes.add(route2);
        routes.add(route3);

        stops.add(stop1);
        stops.add(stop2);
        stops.add(stop3);
        stops.add(stop4);
        stops.add(stop5);
        stops.add(stop6);
        stops.add(stop7);
        stops.add(stop8);
        stops.add(stop9);
        stops.add(stop10);
        stops.add(stop11);
        stops.add(stop12);
        stops.add(stop13);
        stops.add(stop14);
        stops.add(stop15);
        stops.add(stop16);
        stops.add(stop17);
        stops.add(stop18);
        stops.add(stop19);
        stops.add(stop20);
        stops.add(stop21);
        stops.add(stop22);

        surface.drawMap(stops, routes);

        long startTime = System.nanoTime();

        Traffic traffic = new Traffic(stops, routes, surface);
        traffic.setParams(stop1, stop8, 0);

        List<Thread> threads = new ArrayList<>();

        for (int i = 1; i <= NUM_THREADS; i++) {
            final int id = i;
            Thread thread = new Thread(() -> traffic.calculate2(id, 100));
            threads.add(thread);
            thread.start();
        }

        long endTime = System.nanoTime();

        System.out.println("Time taken: " + (endTime - startTime) / 1000000);
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