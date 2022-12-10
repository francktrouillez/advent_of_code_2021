package src;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Solver extends Base {

  final private static Map<String, Integer> SCORE_WRONG_SYMBOL = Map.of(
    ")", 3,
    "]", 57,
    "}", 1197,
    ">", 25137
  );

  final private static Map<String, Integer> SCORE_CLOSING_SYMBOL = Map.of(
    ")", 1,
    "]", 2,
    "}", 3,
    ">", 4
  );

  final private static Map<String, String> OPEN_SYMBOLS = Map.of(
    ")", "(",
    "]", "[",
    "}", "{",
    ">", "<"
  );

  final private static Map<String, String> CLOSE_SYMBOLS = Map.of(
    "(", ")",
    "[", "]",
    "{", "}",
    "<", ">"
  );

  private ArrayList<String> wrongSymbols;
  private ArrayList<ArrayList<String>> completedSequences;

  public Solver(boolean isTest) throws IOException {
    super(isTest);
  }

  public String generateOutput1() {
    parse();

    int score = 0;
    for (String wrongSymbol : wrongSymbols) {
      if (wrongSymbol == null) {
        continue;
      }
      score += SCORE_WRONG_SYMBOL.get(wrongSymbol);
    }
    return Integer.toString(score);
  }

  public String generateOutput2() {
    parse();

    List<Long> scores = new ArrayList<>();
    Long[] scoresArray;
    int scoreIndex;
    ArrayList<String> completedSequence;

    for (int i = 0; i < completedSequences.size(); i++) {
      completedSequence = completedSequences.get(i);
      if (completedSequence.size() == 0) {
        continue;
      }
      scores.add((long) 0);
      scoreIndex = scores.size() - 1;
      for (String closingSymbol : completedSequence) {
        scores.set(scoreIndex, scores.get(scoreIndex) * 5);
        scores.set(scoreIndex, scores.get(scoreIndex) + SCORE_CLOSING_SYMBOL.get(closingSymbol));
      }
    }
    scoresArray = scores.toArray(new Long[scores.size()]);
    Arrays.sort(scoresArray);
    return Long.toString(scoresArray[scoresArray.length/2]);
  }

  private void parse() {
    if (wrongSymbols != null && completedSequences != null) {
      return;
    }
    wrongSymbols = new ArrayList<>();
    completedSequences = new ArrayList<>();
    for (String inputLine : getInput()) {
      completedSequences.add(new ArrayList<>());
      parseSymbols(inputLine.split(""));
    }
  }

  private int parseSymbols(String[] symbols) {
    return parseSymbols(symbols, 0, null);
  }

  private int parseSymbols(String[] symbols, int start, String openingSymbol) {
    String openSymbol;
    int indexParsedSymbol;
    for (int i = start; i <  symbols.length; i++) {
      openSymbol = OPEN_SYMBOLS.get(symbols[i]);
      if (openSymbol != null) {
        if (openingSymbol.equals(openSymbol)) {
          return i;
        } else {
          wrongSymbols.add(symbols[i]);
          return -1;
        }
      } else {
        indexParsedSymbol = parseSymbols(symbols, i + 1, symbols[i]);
        if (indexParsedSymbol == -1) {
          return -1;
        } else {
          i = indexParsedSymbol;
        }
      }
    }
    wrongSymbols.add(null);
    if (openingSymbol != null) {
      completedSequences.get(completedSequences.size() - 1).add(CLOSE_SYMBOLS.get(openingSymbol));
    }
    return symbols.length + 1;
  }
}
