package src;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Solver extends Base {

  int lineSize;
  int inputSize;

  public Solver(boolean isTest) throws IOException{
    super(isTest);
  }

  public String generateOutput1() {
    this.lineSize = getInput().get(0).length();
    this.inputSize = getInput().size();

    int epsilon = generateRate1('1');
    int gamma = generateRate1('0');

    return Integer.toString(gamma * epsilon);
  }

  public String generateOutput2() {
    this.lineSize = getInput().get(0).length();
    this.inputSize = getInput().size();

    int o2 = generateRate2('1');
    int co2 = generateRate2('0');

    return Integer.toString(o2 * co2);
  }

  private int generateRate1(char value) {
    List<String> representation = new ArrayList<String>();
    for (int i = 0; i < lineSize; i++) {
      final int index = i;
      if (getInput().stream().filter(e -> e.charAt(index) == value).collect(Collectors.toList()).size() >= (double)(inputSize) / 2) {
        representation.add("1");
      } else {
        representation.add("0");
      }
    }
    return Integer.parseInt(String.join("", representation), 2);
  }

  private int generateRate2(char preference) {
    List<String> remainingRows = getInput();
    int modifier = Character.getNumericValue(preference) * 2 - 1;
    double conditionalExpression;
    char choice;
    for (int i = 0; i < lineSize; i++) {
      if (remainingRows.size() == 1) {
        break;
      }
      final int finalI = i;
      conditionalExpression = remainingRows.stream().filter(e -> e.charAt(finalI) == '1').collect(Collectors.toList()).size() * modifier - (double)(remainingRows.size()) / 2 * modifier;
      if (conditionalExpression > 0) {
        choice = '1';
      } else if (conditionalExpression == 0) {
        choice = preference;
      } else {
        choice = '0';
      }
      final char finalChoice = choice;
      remainingRows = remainingRows.stream().filter(e -> e.charAt(finalI) == finalChoice).collect(Collectors.toList());
    }
    String representation = remainingRows.get(0);
    return Integer.parseInt(representation, 2);
  }
}
