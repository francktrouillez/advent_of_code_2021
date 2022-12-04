package src;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Solver extends Base {

  public Solver(boolean isTest) throws IOException{
    super(isTest);
  }

  public String generateOutput1() {
    int previousValue;
    int currentValue;
    int increasedCounter = 0;
    List<Integer> integerInput = getIntegerInputList();
    for (int i = 1; i < integerInput.size(); i++) {
      previousValue = integerInput.get(i - 1);
      currentValue = integerInput.get(i);
      if (previousValue < currentValue) {
        increasedCounter++;
      }
    }
    return Integer.toString(increasedCounter);
  }

  public String generateOutput2() {
    int previousValue;
    int currentValue;
    int increasedCounter = 0;
    List<Integer> integerInput = getIntegerInputList();
    for (int i = 1; i < integerInput.size() - 2; i++) {
      previousValue = getSum(integerInput.subList(i - 1, i + 2));
      currentValue = getSum(integerInput.subList(i, i + 3));
      if (previousValue < currentValue) {
        increasedCounter++;
      }
    }
    return Integer.toString(increasedCounter);
  }

  private List<Integer> getIntegerInputList() {
    return getInput().stream().map(e -> Integer.parseInt(e)).collect(Collectors.toList());
  }

  private int getSum(List<Integer> values) {
    return values.stream().reduce(0, (acc, e) -> acc + e);
  }

}
