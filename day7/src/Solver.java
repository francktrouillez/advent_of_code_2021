package src;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solver extends Base {

  private List<Integer> positions;

  public Solver(boolean isTest) throws IOException {
    super(isTest);
  }

  public String generateOutput1() {
    String[] positionsString = getInput().get(0).split(",");
    positions = new ArrayList<>();
    for (String position : positionsString) {
      positions.add(Integer.parseInt(position));
    }
    int minPosition = Collections.min(positions);
    int maxPosition = Collections.max(positions);
    int minCost = Integer.MAX_VALUE;
    int cost;
    for (int i = minPosition; i <= maxPosition; i++) {
      cost = getCost1(i);
      if (cost < minCost) {
        minCost = cost;
      }
    }
    return Integer.toString(minCost);
  }

  public String generateOutput2() {
    String[] positionsString = getInput().get(0).split(",");
    positions = new ArrayList<>();
    for (String position : positionsString) {
      positions.add(Integer.parseInt(position));
    }
    int minPosition = Collections.min(positions);
    int maxPosition = Collections.max(positions);
    int minCost = Integer.MAX_VALUE;
    int cost;
    for (int i = minPosition; i <= maxPosition; i++) {
      cost = getCost2(i);
      if (cost < minCost) {
        minCost = cost;
      }
    }
    return Integer.toString(minCost);
  }

  private int getCost1(int candidatePosition) {
    int cost = 0;
    for (Integer position: positions) {
      cost += Math.abs(position - candidatePosition);
    }
    return cost;
  }

  private int getCost2(int candidatePosition) {
    int cost = 0;
    for (Integer position: positions) {
      cost += getSumFirstNumbers(Math.abs(position - candidatePosition));
    }
    return cost;
  }

  private int getSumFirstNumbers(int n) {
    return n * (n + 1) / 2;
  }
}
