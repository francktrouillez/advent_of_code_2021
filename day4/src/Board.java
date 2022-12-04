package src;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Board {
  final private int BOARD_SIZE = 5;

  private boolean boardCompletion[][] = new boolean[BOARD_SIZE][BOARD_SIZE];
  private Map<Integer, int[]> boardPositions = new HashMap<>();
  private boolean hasWon;
  private int winningNumber;

  public Board(List<String> boardStringList) {
    generateBoard(boardStringList);
    this.hasWon = false;
  }

  private void generateBoard(List<String> boardStringList) {
    String[] boardLineStringList;
    int[] boardPosition;
    for (int i = 0; i < boardStringList.size(); i ++) {
      boardLineStringList = boardStringList.get(i).strip().split("[ ]+");
      for (int j = 0; j < BOARD_SIZE; j++) {
        boardPosition = new int[2];
        boardPosition[0] = i;
        boardPosition[1] = j;
        this.boardPositions.put(Integer.parseInt(boardLineStringList[j]), boardPosition);
      }
    }
  }

  public void playTurn(int number) {
    if (this.hasWon) {
      return;
    }
    markNumber(number);
    this.hasWon = isWinningBoard();
    if (this.hasWon) {
      this.winningNumber = number;
    }
  }

  public boolean getHasWon() {
    return this.hasWon;
  }

  public int getWinningNumber() {
    return this.winningNumber;
  }

  public int getSumUnmarkedNumbers() {
    int result = 0;
    int[] boardPosition;
    for (Map.Entry<Integer, int[]> entry: this.boardPositions.entrySet()) {
      boardPosition = entry.getValue();
      if (!this.boardCompletion[boardPosition[0]][boardPosition[1]]) {
        result += entry.getKey();
      }
    }
    return result;
  }

  private void markNumber(int number) {
    int[] boardPosition = this.boardPositions.get(number);
    if (boardPosition != null) {
      this.boardCompletion[boardPosition[0]][boardPosition[1]] = true;
    }
  }

  private boolean isWinningBoard() {
    boolean result = false;
    for (int i = 0; i < BOARD_SIZE; i ++) {
      result = result || isWinningRow(i) || isWinningColumn(i);
    }
    return result;
  }

  private boolean isWinningRow(int rowIndex) {
    boolean result = true;
    for (int i = 0; i < BOARD_SIZE; i++) {
      result = result && boardCompletion[rowIndex][i];
    }
    return result;
  }

  private boolean isWinningColumn(int columnIndex) {
    boolean result = true;
    for (int i = 0; i < BOARD_SIZE; i++) {
      result = result && boardCompletion[i][columnIndex];
    }
    return result;
  }
}
