package src;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Solver extends Base {

  private Map<Integer, Map<Integer, Octopus>> grid;
  private int activationCounter;
  private boolean isFullyActivated;

  public Solver(boolean isTest) throws IOException {
    super(isTest);
  }

  public String generateOutput1() {
    grid = new HashMap<>();

    for (int i = 0; i < getInput().size(); i++) {
      for (int j = 0; j < getInput().get(i).length(); j ++) {
        addOctopus(i, j, new Octopus(Character.getNumericValue(getInput().get(i).charAt(j))));
      }
    }
    for (int t = 0; t < 100; t++) {
      for (int i = 0; i < getInput().size(); i++) {
        for (int j = 0; j < getInput().get(i).length(); j ++) {
          activateOctopus(i, j);
        }
      }
      updateGrid();
    }

    return Integer.toString(activationCounter);
  }

  public String generateOutput2() {
    grid = new HashMap<>();

    for (int i = 0; i < getInput().size(); i++) {
      for (int j = 0; j < getInput().get(i).length(); j ++) {
        addOctopus(i, j, new Octopus(Character.getNumericValue(getInput().get(i).charAt(j))));
      }
    }
    int step = 0;
    do {
      step++;
      for (int i = 0; i < getInput().size(); i++) {
        for (int j = 0; j < getInput().get(i).length(); j ++) {
          activateOctopus(i, j);
        }
      }
      updateGrid();
    } while (!isFullyActivated);

    return Integer.toString(step);
  }

  private void addOctopus(int x, int y, Octopus octopus) {
    if (grid.get(x) == null) {
      grid.put(x, new HashMap<>());
    }
    grid.get(x).put(y, octopus);
  }

  private Octopus getOctopus(int x, int y) {
    if (x < 0 || x >= getInput().size() || y < 0 || y >= getInput().get(x).length()) {
      return null;
    }
    if (grid.get(x) == null) {
      grid.put(x, new HashMap<>());
    }
    return grid.get(x).get(y);
  }

  private void activateOctopus(int x, int y) {
    Octopus octopus = getOctopus(x, y);
    if (octopus == null) {
      return;
    }
    if (octopus.activate()) {
      for (int i = -1; i <= 1; i++) {
        for (int j = -1; j <= 1; j++) {
          activateOctopus(x + i, y + j);
        }
      }
    }
  }

  private void updateGrid() {
    isFullyActivated = true;
    for (int i = 0; i < getInput().size(); i++) {
      for (int j = 0; j < getInput().get(i).length(); j ++) {
        if (getOctopus(i, j).reset()) {
          activationCounter++;
        } else {
          isFullyActivated = false;
        }
      }
    }
  }
}
