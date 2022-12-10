package src;

public class Octopus {
  final private static int MAX_ENERGY = 10;
  private int energy;

  public Octopus(int energy) {
    this.energy = energy;
  }

  public boolean activate() {
    energy += 1;
    return energy == MAX_ENERGY;
  }

  public boolean reset() {
    if (energy >= MAX_ENERGY) {
      energy = 0;
      return true;
    }
    return false;
  }
}
