package src;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Solver extends Base {

  private Map<String, Set<String>> graph;
  private int pathCounter;
  private boolean hasVisitedSmallCaveTwice;

  public Solver(boolean isTest) throws IOException {
    super(isTest);
  }

  public String generateOutput1() {
    generateGraph();
    generatePaths(false);
    return Integer.toString(pathCounter);
  }

  public String generateOutput2() {
    generateGraph();
    generatePaths(true);
    return Integer.toString(pathCounter);
  }

  private void generatePaths(boolean canVisitSmallCaveTwice) {
    List<String> path = new ArrayList<>();
    path.add("start");
    pathCounter = 0;
    hasVisitedSmallCaveTwice = !canVisitSmallCaveTwice;
    findPaths(path);
  }

  private void findPaths(List<String> path) {
    boolean memoryHasVisitedSmallCaveTwice = hasVisitedSmallCaveTwice;
    for (String neighbour : graph.get(path.get(path.size() - 1))) {
      if (neighbour.toLowerCase().equals(neighbour)) {
        if (neighbour.equals("end")) {
          pathCounter++;
          continue;
        } else if (neighbour.equals("start")) {
          continue;
        } else {
          if (path.contains(neighbour)) {
            if (!hasVisitedSmallCaveTwice) {
              hasVisitedSmallCaveTwice = true;
            } else {
              continue;
            }
          }
        }
      }
      path.add(neighbour);
      findPaths(path);
      path.remove(path.size() - 1);
      hasVisitedSmallCaveTwice = memoryHasVisitedSmallCaveTwice;
    }
  }

  private void generateGraph() {
    if (graph != null) {
      return;
    }
    graph = new HashMap<>();
    String[] splitInputLine;
    for (String inputLine: getInput()) {
      splitInputLine = inputLine.split("-");
      addEdge(splitInputLine[0], splitInputLine[1]);
    }
  }

  private void addEdge(String nodeA, String nodeB) {
    addDirectionalEdge(nodeA, nodeB);
    addDirectionalEdge(nodeB, nodeA);
  }

  private void addDirectionalEdge(String nodeA, String nodeB) {
    if (graph.get(nodeA) == null) {
      graph.put(nodeA, new HashSet<>());
    }
    graph.get(nodeA).add(nodeB);
  }
}
