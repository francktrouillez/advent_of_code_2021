package src;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Solver extends Base {

  private Map<Integer,Map<Integer, Integer>> map;

  public Solver(boolean isTest) throws IOException {
    super(isTest);
  }

  public String generateOutput1() {
    this.map = new HashMap<>();
    fillMap(true);
    return Integer.toString(getOverlappingPoints());
  }

  public String generateOutput2() {
    this.map = new HashMap<>();
    fillMap(false);
    return Integer.toString(getOverlappingPoints());
  }

  private void fillMap(boolean onlyHorizontalAndVertical) {
    String[] lineString;
    String[] pos1String;
    String[] pos2String;
    int[] pos1 = new int[2];
    int[] pos2 = new int[2];
    int[] direction = new int[2];
    int lineLength;
    int x;
    int y;
    for (int i = 0; i < getInput().size(); i++) {
      lineString = getInput().get(i).split(" -> ");
      pos1String = lineString[0].split(",");
      pos2String = lineString[1].split(",");
      pos1[0] = Integer.parseInt(pos1String[0]);
      pos1[1] = Integer.parseInt(pos1String[1]);
      pos2[0] = Integer.parseInt(pos2String[0]);
      pos2[1] = Integer.parseInt(pos2String[1]);
      if (!onlyHorizontalAndVertical || isLineOnlyHorizontalOrVertical(pos1, pos2)) {
        lineLength = Math.max(Math.abs(pos1[0] - pos2[0]), Math.abs(pos1[1] - pos2[1]));
        direction[0] = pos1[0] == pos2[0] ? 0 : (pos1[0] < pos2[0] ? 1 : -1);
        direction[1] = pos1[1] == pos2[1] ? 0 : (pos1[1] < pos2[1] ? 1 : -1);
        for (int j = 0; j <= lineLength; j++) {
          x = pos1[0] + j * direction[0];
          y = pos1[1] + j * direction[1];
          if (this.map.get(x) == null) {
            this.map.put(x, new HashMap<>());
          }
          if (this.map.get(x).get(y) == null) {
            this.map.get(x).put(y, 0);
          }
          this.map.get(x).put(y, this.map.get(x).get(y) + 1);
        }
      }
    }
  }

  private boolean isLineOnlyHorizontalOrVertical(int[] pos1, int[] pos2) {
    return pos1[0] == pos2[0] || pos1[1] == pos2[1];
  }

  private int getOverlappingPoints() {
    int result = 0;
    for (Map.Entry<Integer, Map<Integer, Integer>> rowEntry : this.map.entrySet()) {
      for (Map.Entry<Integer, Integer> columnEntry : rowEntry.getValue().entrySet()) {
        if (columnEntry.getValue() > 1) {
          result += 1;
        }
      }
    }
    return result;
  }
}
