package main;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Surface extends JPanel implements ActionListener {
    private final int DELAY = 150;
    private Timer timer;
    private List<Stop> stops;
    private List<Route> routes;
    private List<Node> path = new ArrayList<>();

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
            g2d.setStroke(new BasicStroke(15));
            for(Route route : routes) {
                for(Leg leg : route.getLegs()) {
                    for (int i = 0; i < leg.getStops().size() - 1; i++) {
                        Coords from = leg.getStops().get(i).getStop().getCoords();
                        Coords to = leg.getStops().get(i + 1).getStop().getCoords();
                        g2d.drawLine((int) from.getX() * 100 + 100, (int) from.getY() * 100 + 100, (int) to.getX() * 100 + 100, (int) to.getY() * 100 + 100);
                    }
                }
            }
            g2d.setPaint(Color.PINK);
            if(path != null) {
                for(int i = 0; i < path.size()-1; i++) {
                    Coords from = path.get(i).getStop().getCoords();
                    Coords to = path.get(i+1).getStop().getCoords();
                    g2d.drawLine((int)from.getX()*100+100, (int)from.getY()*100+100, (int)to.getX()*100+100, (int)to.getY()*100+100);
                }
                g2d.setPaint(Color.RED);
                for(Node node : path) {
                    g2d.fillOval((int) node.getStop().getCoords().getX() * 100 + 85, (int) node.getStop().getCoords().getY() * 100 + 85, 30, 30);
                    dontDraw.add(node.getStop());
                }
            }
            g2d.setPaint(Color.BLACK);
            for(Stop stop : stops) {
                if(!dontDraw.contains(stop)) {
                    g2d.fillOval((int) stop.getCoords().getX() * 100 + 85, (int) stop.getCoords().getY() * 100 + 85, 30, 30);
                }
            }
        }
    }

    public void drawMap(List<Stop> stops, List<Route> routes) {
        this.stops = stops;
        this.routes = routes;
        repaint();
    }

    public void drawPath(List<Node> path) {
        this.path = path;
        repaint();
    }

    public void addNode(Node node) {
        path.add(node);
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