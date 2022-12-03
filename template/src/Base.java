package src;

import java.io.IOException;
import java.util.List;

abstract public class Base {

  private List<String> input;
  private String output1;
  private String output2;

  public Base() throws IOException {
    this.input = FileHandler.read("../input.txt");
  }

  public void writeOutput() throws IOException {
    FileHandler.write("../output.txt", "Output 1 : " + getOutput1() + "\nOutput 2 : " + getOutput2());
  }

  public void printOutput() {
    System.out.println("Output 1 : " + getOutput1() + "\nOutput 2 : " + getOutput2());
  }

  protected List<String> getInput() {
    return this.input;
  }

  private String getOutput1() {
    if (this.output1 != null) {
      return this.output1;
    }
    this.output1 = generateOutput1();
    return this.output1;
  }

  private String getOutput2() {
    if (this.output2 != null) {
      return this.output2;
    }
    this.output2 = generateOutput2();
    return this.output2;
  }

  abstract public String generateOutput1();
  abstract public String generateOutput2();

}
