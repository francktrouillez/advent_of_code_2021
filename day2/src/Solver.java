package src;

import java.io.IOException;

public class Solver extends Base {

  int position;
  int depth;
  int aim;

  public Solver(boolean isTest) throws IOException{
    super(isTest);
  }

  public String generateOutput1() {
    this.position = 0;
    this.depth = 0;
    for (String line: getInput()) {
      applyCommand(line, 1);
    }
    return Integer.toString(this.position * this.depth);
  }

  public String generateOutput2() {
    this.position = 0;
    this.depth = 0;
    this.aim = 0;
    for (String line: getInput()) {
      applyCommand(line, 2);
    }
    return Integer.toString(this.position * this.depth);
  }

  private void applyCommand(String command, int number) {
    String[] move = command.split(" ");
    if (number == 1) {
      applyMove1(move[0], move[1]);
    } else {
      applyMove2(move[0], move[1]);
    }
  }

  private void applyMove1(String instruction, String stringValue) {
    int value = Integer.parseInt(stringValue);
    if (instruction.equals("forward")) {
      this.position += value;
    } else {
      if (instruction.equals("down")) {
        this.depth += value;
      } else {
        this.depth -= value;
      }
    }
  }

  private void applyMove2(String instruction, String stringValue) {
    int value = Integer.parseInt(stringValue);
    if (instruction.equals("forward")) {
      this.position += value;
      this.depth += this.aim * value;
    } else {
      if (instruction.equals("down")) {
        this.aim += value;
      } else {
        this.aim -= value;
      }
    }
  }

}
