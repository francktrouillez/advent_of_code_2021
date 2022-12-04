package src;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solver extends Base {

  List<Board> boards = new ArrayList<>();

  public Solver(boolean isTest) throws IOException {
    super(isTest);
  }

  public String generateOutput1() {
    List<String> numbers = Arrays.asList(getInput().get(0).split(","));
    generateBoards();
    for (String numberString: numbers) {
      for (Board board: this.boards) {
        board.playTurn(Integer.parseInt(numberString));
        if (board.getHasWon()) {
          return Integer.toString(board.getWinningNumber() * board.getSumUnmarkedNumbers());
        }
      }
    }
    return "";
  }

  public String generateOutput2() {
    List<String> numbers = Arrays.asList(getInput().get(0).split(","));
    generateBoards();
    Board winningBoard = null;
    for (String numberString: numbers) {
      for (Board board: this.boards) {
        if (board.getHasWon()) {
          continue;
        }
        board.playTurn(Integer.parseInt(numberString));
        if (board.getHasWon()) {
          winningBoard = board;
        }
      }
    }
    return Integer.toString(winningBoard.getWinningNumber() * winningBoard.getSumUnmarkedNumbers());
  }

  private void generateBoards() {
    for (int i = 2; i < getInput().size(); i += 6) {
      this.boards.add(new Board(getInput().subList(i, i + 5)));
    }
  }
}
