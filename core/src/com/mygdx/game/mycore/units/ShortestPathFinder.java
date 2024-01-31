package com.mygdx.game.mycore.units;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

public class ShortestPathFinder {

    public static List<Coordinates> findShortestPath(List<List<Integer>> grid,
                                                     Coordinates start, Coordinates end) {
        grid.get(start.x).set(start.y, 0);
        grid.get(end.x).set(end.y, 0);

        PriorityQueue<Node> openSet =
                new PriorityQueue<>(Comparator.comparingInt(Node::getTotalCost));
        Map<Coordinates, Integer> gScores = new HashMap<>();
        Map<Coordinates, Node> cameFrom = new HashMap<>();

        gScores.put(start, 0);
        openSet.add(new Node(start, null, 0, getManhattanDistance(start, end)));

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            Coordinates currentPos = current.position;

            if (currentPos.equals(end)) {
                List<Coordinates> path = reconstructPath(current);
                List<Coordinates> way = path.stream()
                        .map(coord -> new Coordinates(coord.x + 1, coord.y + 1))
                        .collect(Collectors.toList());
                return way;
            }

            List<Coordinates> neighbors = getNeighbors(currentPos, grid);
            for (Coordinates neighbor : neighbors) {
                int tentativeGScore = gScores.getOrDefault(currentPos, Integer.MAX_VALUE) + 1;

                if (tentativeGScore < gScores.getOrDefault(neighbor, Integer.MAX_VALUE)
                        && grid.get(neighbor.x).get(neighbor.y) == 0) {
                    Node neighborNode = new Node(neighbor, current, tentativeGScore,
                            getManhattanDistance(neighbor, end));
                    openSet.add(neighborNode);
                    gScores.put(neighbor, tentativeGScore);
                    cameFrom.put(neighbor, current);
                }
            }
        }
        return Collections.emptyList();
    }

    private static List<Coordinates> getNeighbors(Coordinates pos, List<List<Integer>> grid) {
        List<Coordinates> neighbors = new ArrayList<>();
        int numRows = grid.size();
        int numCols = grid.get(0).size();

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] dir : directions) {
            int newRow = pos.x + dir[0];
            int newCol = pos.y + dir[1];

            if (newRow >= 0 && newRow < numRows && newCol >= 0 && newCol < numCols) {
                neighbors.add(new Coordinates(newRow, newCol));
            }
        }

        return neighbors;
    }

    private static List<Coordinates> reconstructPath(Node endNode) {
        List<Coordinates> path = new ArrayList<>();
        Node current = endNode;

        while (current != null) {
            path.add(current.position);
            current = current.parent;
        }

        Collections.reverse(path);
        return path;
    }

    private static int getManhattanDistance(Coordinates a, Coordinates b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    static class Node {
        Coordinates position;
        Node parent;
        int gScore;
        int hScore;

        public Node(Coordinates position, Node parent, int gScore, int hScore) {
            this.position = position;
            this.parent = parent;
            this.gScore = gScore;
            this.hScore = hScore;
        }

        public int getTotalCost() {
            return gScore + hScore;
        }
    }

}