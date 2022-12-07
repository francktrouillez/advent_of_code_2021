package src;

import java.io.IOException;

public class Solver extends Base {
  final static private int INITIAL_TIMER = 8;
  final static private int RESET_TIMER = 6;

  private int lengthPopulation;
  private long[] population;

  public Solver(boolean isTest) throws IOException {
    super(isTest);
  }

  public String generateOutput1() {
    generateInitialPopulation();
    for (int i = 0; i < 80; i++) {
      nextDay();
    }
    return Long.toString(getPopulationSize());
  }

  public String generateOutput2() {
    generateInitialPopulation();
    for (int i = 0; i < 256; i++) {
      nextDay();
    }
    return Long.toString(getPopulationSize());
  }

  private void generateInitialPopulation() {
    lengthPopulation = Math.max(getIndex(INITIAL_TIMER), getIndex(RESET_TIMER)) + 1;
    population = new long[lengthPopulation];
    for (String initialLife : getInput().get(0).split(",")) {
      population[getIndex(Integer.parseInt(initialLife))]++;
    }
  }

  private void decreaseTimer() {
    for (int i = getIndex(0); i < lengthPopulation; i++) {
      population[i - 1] = population[i];
    }
    population[lengthPopulation - 1] = 0;
  }

  private void nextDay() {
    decreaseTimer();
    population[getIndex(RESET_TIMER)] += population[0];
    population[getIndex(INITIAL_TIMER)] += population[0];
    population[0] = 0;
  }

  private long getPopulationSize() {
    long populationSize = 0;
    for (int i = getIndex(0); i < lengthPopulation; i++) {
      populationSize += population[i];
    }
    return populationSize;
  }

  private int getIndex(int index) {
    return index + 1;
  }
}
