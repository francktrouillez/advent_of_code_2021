import java.io.IOException;

import src.Solver;
import src.FileHandler;

public class Main {
  public static void main(String[] args) throws IOException {
    printTest();
    System.out.println();
    printProcessing();
  }

  private static void printTest() throws IOException {
    Solver solver = new Solver(true);

    System.out.println("## Testing ##");
    System.out.println("- Expected -");
    String expectedOutput = String.join("\n", FileHandler.read("../test/output.txt"));
    System.out.println(expectedOutput);
    System.out.println("-- Actual --");
    System.out.println(solver.getOutput());
    System.out.println("-- Assert --");
    boolean isTestOk = expectedOutput.equals(solver.getOutput());
    System.out.println(isTestOk ? "   - OK -   " : " - NOT OK - ");
    System.out.println("#############");
    if (!isTestOk) {
      System.exit(0);
    }
  }

  private static void printProcessing() throws IOException {
    Solver solver = new Solver(false);

    System.out.println("# Processing #");
    solver.printOutput();
    solver.writeOutput();
    System.out.println("#############");
  }
}
