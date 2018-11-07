package main;

import model.*;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

class Surface extends JPanel implements ActionListener {
    private static final int DELAY = 150;
    private static final int OFFSET_X = 80;
    private static final int OFFSET_Y = 40;
    private static final double SCALE = 40;
    private static final int DIAMETER = (int) (30 * SCALE/100);
    private static final int LINE_WIDTH = (int) (15 * SCALE/100);

    private Timer timer;
    private List<Stop> stops;
    private List<Route> routes;
    private HashMap<Integer, List<Node>> paths = new HashMap<>();
    private Color pathColor;
    private Color pathColorLighter;

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
        Set<Stop> dontDraw = new HashSet<>();

        if(routes != null && stops != null) {
            g2d.setPaint(Color.LIGHT_GRAY);
            g2d.setStroke(new BasicStroke(LINE_WIDTH));
            for(Route route : routes) {
                for(Leg leg : route.getLegs()) {
                    for (int i = 0; i < leg.getStops().size() - 1; i++) {
                        Coords from = leg.getStops().get(i).getStop().getCoords();
                        Coords to = leg.getStops().get(i + 1).getStop().getCoords();
                        g2d.drawLine((int) (from.getX() * SCALE) + OFFSET_X, (int) (from.getY() * SCALE) + OFFSET_Y, (int) (to.getX() * SCALE) + OFFSET_X, (int) (to.getY() * SCALE) + OFFSET_Y);
                    }
                }
            }
            for(int pathId : paths.keySet()) {
                List<Node> path = paths.get(pathId);
                g2d.setPaint(getLighterColor(pathId));
                if (path != null) {
                    for (int i = 0; i < path.size() - 1; i++) {
                        Coords from = path.get(i).getStop().getCoords();
                        Coords to = path.get(i + 1).getStop().getCoords();
                        g2d.drawLine((int) (from.getX() * SCALE) + OFFSET_X, (int) (from.getY() * SCALE) + OFFSET_Y, (int) (to.getX() * SCALE) + OFFSET_X, (int) (to.getY() * SCALE) + OFFSET_Y);
                    }
                    g2d.setPaint(getColor(pathId));
                    for (Node node : path) {
                        g2d.fillOval((int) (node.getStop().getCoords().getX() * SCALE) + OFFSET_X - DIAMETER/2, (int) (node.getStop().getCoords().getY() * SCALE) + OFFSET_Y - DIAMETER/2, DIAMETER, DIAMETER);
                        dontDraw.add(node.getStop());
                    }
                }
            }
            g2d.setPaint(Color.BLACK);
            for(Stop stop : stops) {
                if(!dontDraw.contains(stop)) {
                    g2d.fillOval((int) (stop.getCoords().getX() * SCALE) + OFFSET_X - DIAMETER/2, (int) (stop.getCoords().getY()* SCALE) + OFFSET_Y - DIAMETER/2, DIAMETER, DIAMETER);
                }
            }
        }
    }

    public void drawMap(List<Stop> stops, List<Route> routes) {
        this.stops = stops;
        this.routes = routes;
        repaint();
    }

    public void drawPath(List<Node> path, int threadId) {
        this.paths.put(threadId, path);
        repaint();
    }

    private Color getColor(int threadId) {
        switch (threadId) {
            case 1: return Color.RED;
            case 2: return Color.BLUE;
            case 3: return Color.ORANGE;
            default: return Color.MAGENTA;
        }
    }

    private Color getLighterColor(int threadId) {
        switch (threadId) {
            case 1: return Color.PINK;
            case 2: return Color.CYAN;
            case 3: return Color.YELLOW;
            default: return Color.MAGENTA;
        }
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