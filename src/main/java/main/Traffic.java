package main;

import model.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Traffic {
    private List<Stop> stops = new ArrayList<>();
    private List<Route> routes = new ArrayList<>();
    private Surface surface;
    private Stop from;
    private Stop to;
    private int time;

    private final Set<Node> potentialNodes = ConcurrentHashMap.newKeySet();
    private final Map<Stop, Double> visitedStops = new ConcurrentHashMap<>();
    private final Set<Node> visitedNodes = ConcurrentHashMap.newKeySet();
    private final Map<Node, Node> prev = new ConcurrentHashMap<>();

    private boolean stop = false;

    public Traffic(List<Stop> stops, List<Route> routes, Surface surface) {
        this.stops = stops;
        this.routes = routes;
        this.surface = surface;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setParams(Stop from, Stop to, int time) {
        this.from = from;
        this.to = to;
        this.time = time;
    }

    public List<Node> calculate() {
        Map<Node, Node> prev = new HashMap<>();

        Set<Node> visitedNodes = new HashSet<>();

        Map<Stop, Integer> nonVisitedStops = new HashMap<>();
        for(Stop s : stops) nonVisitedStops.put(s, null);

        Set<Node> potentialNodes = new HashSet<>();

        Node startNode = new StopNode(from, time);
        nonVisitedStops.put(startNode.getStop(), time);
        potentialNodes.add(startNode);

        while(!nonVisitedStops.isEmpty()) {
            Node currentNode = potentialNodes.stream()
                    .min(Comparator.comparing(Node::getTime)).orElse(null);

            surface.drawPath(buildPath(prev, currentNode), 0);

            System.out.println(currentNode.getStop().getName());

            try{
                Thread.sleep(300);
            } catch (Exception e) {
                System.out.println("some error");
            }

            visitedNodes.add(currentNode);
            potentialNodes.remove(currentNode);
            nonVisitedStops.remove(currentNode.getStop());

            if(currentNode.getStop().equals(to)) {
                return buildPath(prev, currentNode);
            }

            for (Node n : currentNode.getNextNodes()) {
                if (!visitedNodes.contains(n)) {
                    if(n.getStop().equals(currentNode.getStop()) || nonVisitedStops.containsKey(n.getStop()) && (nonVisitedStops.get(n.getStop()) == null || n.getTime() < nonVisitedStops.get(n.getStop()))) {
                        nonVisitedStops.computeIfPresent(n.getStop(), (s, t) -> Math.min(n.getTime(), t));
                        potentialNodes.add(n);
                        prev.put(n, currentNode);
                    }
                }
            }
        }

        return null;
    }

    public List<Node> calculate2(int threadId) {
        StopNode startNode = new StopNode(from, time);
        potentialNodes.add(startNode);

        while(!potentialNodes.isEmpty() && !stop) {
            Node currentNode;
            synchronized (this) {
                currentNode = potentialNodes.stream().min(Comparator.comparing(this::getScore)).orElse(null);

                if (currentNode == null) {
                    return null;
                }

                potentialNodes.remove(currentNode);
            }

            visitedStops.put(currentNode.getStop(), getScore(currentNode));
            visitedNodes.add(currentNode);

            surface.drawPath(buildPath(prev, currentNode), threadId);

            if(currentNode.getStop().equals(to)) {
                stop = true;
                return buildPath(prev, currentNode);
            }

            for(Node neighbor : currentNode.getNextNodes()) {
                synchronized (this) {
                    if (!visitedNodes.contains(neighbor) && !potentialNodes.contains(neighbor) && (!visitedStops.containsKey(neighbor.getStop()) || neighbor.getTime() < visitedStops.get(neighbor.getStop()))) {
                        prev.put(neighbor, currentNode);
                        potentialNodes.add(neighbor);
                        visitedStops.remove(neighbor.getStop());
                    }
                }
            }
        }
        return null;
    }

    private List<Node> buildPath(Map<Node, Node> prev, Node end) {
        List<Node> path = new ArrayList<>();
        do {
            path.add(end);
            end = prev.get(end);
        } while(end != null);
        return path;
    }

    private double getScore(Node node) {
        return node.getTime() + getDist(node.getStop(), to);
    }

    private double getDist(Stop n1, Stop n2) {
        Coords c1 = n1.getCoords();
        Coords c2 = n2.getCoords();
        return Math.sqrt(Math.pow(c1.getX()-c2.getX(), 2) + Math.pow(c1.getY() - c2.getY(), 2));
    }
}
