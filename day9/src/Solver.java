package src;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Solver extends Base {
  final private static int MAX_HEIGHT = 9;

  private int heightMap[][];
  private boolean visitedPositions[][];

  public Solver(boolean isTest) throws IOException {
    super(isTest);
  }

  public String generateOutput1() {
    generateHeightMap();
    List<int[]> minima = findMinima();
    return Integer.toString(getSumHeight(minima) + minima.size());
  }

  public String generateOutput2() {
    generateHeightMap();
    List<Integer> basinSizes = findBasinSizes();
    basinSizes.sort(Comparator.reverseOrder());
    return Integer.toString(getProductBasinSizes(basinSizes.subList(0, 3)));
  }

  private void generateHeightMap() {
    heightMap = new int[getInput().size()][getInput().get(0).length()];
    for (int i = 0; i < getInput().size(); i++) {
      for (int j = 0; j < getInput().get(i).length(); j++) {
        heightMap[i][j] = Character.getNumericValue(getInput().get(i).charAt(j));
      }
    }
  }

  private int getHeight(int i, int j) {
    if (i < 0 || i >= getInput().size() || j < 0 || j >= getInput().get(0).length()) {
      return MAX_HEIGHT;
    }
    return heightMap[i][j];
  }

  private int getHeight(int[] position) {
    return getHeight(position[0], position[1]);
  }

  private List<int[]> findMinima() {
    List<int[]> minima = new ArrayList<>();
    int currentHeight;
    int[] currentPosition;
    boolean isMinimum;
    for (int i = 0; i < getInput().size(); i++) {
      for (int j = 0; j < getInput().get(i).length(); j++) {
        currentHeight = getHeight(i, j);
        isMinimum = true;
        for (int[] neighbour : getNeighbours(i, j)) {
          if (currentHeight >= getHeight(neighbour)) {
            isMinimum = false;
            break;
          }
        }
        if (isMinimum) {
          currentPosition = new int[2];
          currentPosition[0] = i;
          currentPosition[1] = j;
          minima.add(currentPosition);
        }
      }
    }
    return minima;
  }

  private int getSumHeight(List<int[]> list) {
    int sum = 0;
    for (int[] position : list) {
      sum += getHeight(position);
    }
    return sum;
  }

  private int getProductBasinSizes(List<Integer> list) {
    int sum = 1;
    for (int elem : list) {
      sum *= elem;
    }
    return sum;
  }

  private List<Integer> findBasinSizes() {
    List<Integer> basinSizes = new ArrayList<>();
    List<int[]> minima = findMinima();
    visitedPositions = new boolean[getInput().size()][getInput().get(0).length()];
    for (int[] minimum : minima) {
      basinSizes.add(getBasinSize(minimum));
    }
    return basinSizes;
  }

  private int getBasinSize(int[] position) {
    ArrayList<int[]> candidates = new ArrayList<>();
    int[] candidate;
    int basinSize = 0;
    candidates.add(position);
    basinSize = 0;
    while (candidates.size() > 0) {
      candidate = candidates.get(0);
      candidates.remove(0);
      if (isVisited(candidate) || getHeight(candidate) == MAX_HEIGHT) {
        continue;
      }
      addVisited(candidate);
      basinSize += 1;
      for (int[] neighbour : getNeighbours(candidate)) {
        candidates.add(neighbour);
      }
    }
    return basinSize;
  }

  private boolean isVisited(int[] position) {
    return visitedPositions[position[0]][position[1]];
  }

  private void addVisited(int[] position) {
    visitedPositions[position[0]][position[1]] = true;
  }

  private List<int[]> getNeighbours(int[] position) {
    return getNeighbours(position[0], position[1]);
  }

  private List<int[]> getNeighbours(int x, int y) {
    List<int[]> neighbours = new ArrayList<>();
    int[] currentNeighbour;
    if (x - 1 >= 0) {
      currentNeighbour = new int[2];
      currentNeighbour[0] = x - 1;
      currentNeighbour[1] = y;
      neighbours.add(currentNeighbour);
    }
    if (x + 1 < getInput().size()) {
      currentNeighbour = new int[2];
      currentNeighbour[0] = x + 1;
      currentNeighbour[1] = y;
      neighbours.add(currentNeighbour);
    }
    if (y - 1 >= 0) {
      currentNeighbour = new int[2];
      currentNeighbour[0] = x;
      currentNeighbour[1] = y - 1;
      neighbours.add(currentNeighbour);
    }
    if (y + 1 < getInput().get(0).length()) {
      currentNeighbour = new int[2];
      currentNeighbour[0] = x;
      currentNeighbour[1] = y + 1;
      neighbours.add(currentNeighbour);
    }
    return neighbours;
  }
}
