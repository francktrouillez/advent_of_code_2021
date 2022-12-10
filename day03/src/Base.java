package src;

import java.io.IOException;
import java.util.List;

abstract public class Base {

  private List<String> input;
  private String output1;
  private String output2;

  public Base(boolean isTest) throws IOException {
    this.input = FileHandler.read(isTest ? "../test/input.txt" : "../input.txt");
  }

  public void writeOutput() throws IOException {
    FileHandler.write("../output.txt", getOutput());
  }

  public void printOutput() {
    System.out.println(getOutput());
  }

  public String getOutput() {
    return "Output 1 :\n" + getOutput1() + "\nOutput 2 :\n" + getOutput2();
  }

  protected List<String> getInput() {
    return this.input;
  }

  private String getOutput1() {
    if (this.output1 == null) {
      this.output1 = generateOutput1();
    }
    return this.output1;
  }

  private String getOutput2() {
    if (this.output2 == null) {
      this.output2 = generateOutput2();
    }
    return this.output2;
  }

  abstract public String generateOutput1();
  abstract public String generateOutput2();

}
