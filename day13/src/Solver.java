package src;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Solver extends Base {

  private Map<Integer, Set<Integer>> paper;
  private List<String> foldInstructions;

  public Solver(boolean isTest) throws IOException {
    super(isTest);
  }

  public String generateOutput1() {
    initializePaper();
    String[] splitFoldInstruction = foldInstructions.get(0).split(" ")[2].split("=");
    fold(splitFoldInstruction[0], Integer.parseInt(splitFoldInstruction[1]));
    return Integer.toString(countDots());
  }

  public String generateOutput2() {
    initializePaper();
    String[] splitFoldInstruction;
    for (String foldInstruction : foldInstructions) {
      splitFoldInstruction = foldInstruction.split(" ")[2].split("=");
      fold(splitFoldInstruction[0], Integer.parseInt(splitFoldInstruction[1]));
    }
    return displayPaper();
  }

  private void initializePaper() {
    paper = new HashMap<>();
    foldInstructions = new ArrayList<>();
    String[] coordinatesString;
    int splitIndex = -1;
    for (int i = 0; i < getInput().size(); i++) {
      if (getInput().get(i).equals("")) {
        splitIndex = i;
        break;
      }
      coordinatesString = getInput().get(i).split(",");
      addDot(paper, Integer.parseInt(coordinatesString[0]), Integer.parseInt(coordinatesString[1]));
    }

    for (int i = splitIndex + 1; i < getInput().size(); i++) {
      foldInstructions.add(getInput().get(i));
    }
  }

  private void addDot(Map<Integer, Set<Integer>> paper, int x, int y) {
    if (paper.get(x) == null) {
      paper.put(x, new HashSet<>());
    }
    paper.get(x).add(y);
  }

  private void fold(String axis, int coordinate) {
    if (axis.equals("x")) {
      foldAlongX(coordinate);
    } else {
      foldAlongY(coordinate);
    }
  }

  private void foldAlongX(int coordinate) {
    Map<Integer, Set<Integer>> resultPaper = new HashMap<>();
    int offsetX = Math.max(((maxX() + 1) / 2) - coordinate, 0);
    for (int x : paper.keySet()) {
      for (int y : paper.get(x)) {
        if (x < coordinate) {
          addDot(resultPaper, x + offsetX, y);
        } else if (x > coordinate) {
          addDot(resultPaper, 2 * coordinate - x + offsetX, y);
        }
      }
    }
    paper = resultPaper;
  }

  private void foldAlongY(int coordinate) {
    Map<Integer, Set<Integer>> resultPaper = new HashMap<>();
    int offsetY = Math.max(((maxY() + 1) / 2) - coordinate, 0);
    for (int x : paper.keySet()) {
      for (int y : paper.get(x)) {
        if (y < coordinate) {
          addDot(resultPaper, x, y + offsetY);
        } else if (y > coordinate) {
          addDot(resultPaper, x, 2 * coordinate - y + offsetY);
        }
      }
    }
    paper = resultPaper;
  }

  private int maxX() {
    int max = 0;
    for (int x : paper.keySet()) {
      if (max < x) {
        max = x;
      }
    }
    return max;
  }

  private int maxY() {
    int max = 0;
    for (Set<Integer> ySet : paper.values()) {
      for (int y : ySet) {
        if (max < y) {
          max = y;
        }
      }
    }
    return max;
  }

  private int countDots() {
    int dotCounter = 0;
    for (Set<Integer> ySet : paper.values()) {
      dotCounter += ySet.size();
    }
    return dotCounter;
  }

  private String displayPaper() {
    String result = "";
    for (int y = 0; y <= maxY(); y++) {
      for (int x = 0; x <= maxX(); x++) {
        if (paper.get(x) == null || !paper.get(x).contains(y)) {
          result += ".";
        } else {
          result += "#";
        }
      }
      result += "\n";
    }
    return result;
  }
}
