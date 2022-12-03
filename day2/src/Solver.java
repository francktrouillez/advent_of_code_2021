package src;

import java.io.IOException;

public class Solver extends Base {

  int position;
  int depth;
  int aim;

  public Solver() throws IOException{
    super();
  }

  public String generateOutput1() {
    this.position = 0;
    this.depth = 0;
    for (String line: getInput()) {
      applyCommand1(line);
    }
    return Integer.toString(this.position * this.depth);
  }

  public String generateOutput2() {
    this.position = 0;
    this.depth = 0;
    this.aim = 0;
    for (String line: getInput()) {
      applyCommand2(line);
    }
    return Integer.toString(this.position * this.depth);
  }

  private void applyCommand1(String command) {
    String[] move = command.split(" ");
    applyMove1(move[0], move[1]);
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

  private void applyCommand2(String command) {
    String[] move = command.split(" ");
    applyMove2(move[0], move[1]);
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
