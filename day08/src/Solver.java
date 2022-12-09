package src;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Solver extends Base {
  private Map<Integer, Set<String>> decodeMap;

  public Solver(boolean isTest) throws IOException {
    super(isTest);
  }

  public String generateOutput1() {
    int sum = 0;
    int decodedNumber;
    for (String inputLine : getInput()) {
      String[] inputMessage = inputLine.split("\\|");
      List<String> codeNumbers = Arrays.asList(inputMessage[0].strip().split(" "));
      generateDecodeMap(codeNumbers);
      List<String> outputNumbers = Arrays.asList(inputMessage[1].strip().split(" "));
      for (String outputNumber : outputNumbers) {
        decodedNumber = decode(outputNumber);
        if (decodedNumber == 1 || decodedNumber == 4 || decodedNumber == 7 || decodedNumber == 8) {
          sum += 1;
        }
      }
    }
    return Integer.toString(sum);
  }

  public String generateOutput2() {
    int sum = 0;
    List<String> decodedNumbers;
    for (String inputLine : getInput()) {
      String[] inputMessage = inputLine.split("\\|");
      List<String> codeNumbers = Arrays.asList(inputMessage[0].strip().split(" "));
      generateDecodeMap(codeNumbers);
      List<String> outputNumbers = Arrays.asList(inputMessage[1].strip().split(" "));
      decodedNumbers = new ArrayList<>();
      for (String outputNumber : outputNumbers) {
        decodedNumbers.add(Integer.toString(decode(outputNumber)));
      }
      sum += Integer.parseInt(String.join("", decodedNumbers));
    }
    return Integer.toString(sum);
  }

  /**
   * To generate the decode map, we first decompose
   * the different digits based on the segments
   *
   * +--------+-----------------+---------+
   * | Digits | Segments        | Length  |
   * +--------+-----------------+---------+
   * |   0    | {a,b,c,e,f,g}   |    6    |
   * |   1    | {c,f}           |    2    |
   * |   2    | {a,c,d,e,g}     |    5    |
   * |   3    | {a,c,d,f,g}     |    5    |
   * |   4    | {b,c,d,f}       |    4    |
   * |   5    | {a,b,d,f,g}     |    5    |
   * |   6    | {a,b,d,e,f,g}   |    6    |
   * |   7    | {a,c,f}         |    3    |
   * |   8    | {a,b,c,d,e,f,g} |    7    |
   * |   9    | {a,b,c,d,f,g}   |    6    |
   * +--------+-----------------+---------+
   *
   * The easy ones to identify are the ones with a unique length
   * such as 1, 4, 7 and 8
   *
   * We need now to identify the digits with 5 and 6 segments
   *
   * Let's start with the digits with 5 segments
   *
   * Knowing 1 and 4, one could deduce the set of segments {b,d}
   * by doing the difference between {b,c,d,f} and {c,f}
   *
   * We have now everything to find the digits with a length
   * of 5!
   *
   * We can see that 5 is the only 5-segments digit containing
   * the set of segments {b,d}
   *
   * We also know that 3 is the only 5-segments digit
   * containing the set of segments {c,f}, identified by 1
   *
   * Lastly, we know that 2 is the remaining 5-segments
   * digit since there are 3 5-segments digits.
   *
   * We apply the same logic with the 6-segments digits
   *
   * 0 is the only 6-segments digit that doesn't contain
   * the set of segments {b,d}
   *
   * 6 is the only 6-segments digit that doesn't contain
   * the set of segments {c,f}
   *
   * 9 is the remaining 6-segments digit
   */
  private void generateDecodeMap(List<String> codeNumbers) {
    decodeMap = new HashMap<>();
    codeNumbers.sort((a, b) -> a.length() - b.length());
    // Length = 2 -> "1"
    decodeMap.put(1, getSet(codeNumbers.get(0)));
    // Length = 3 -> "7"
    decodeMap.put(7, getSet(codeNumbers.get(1)));
    // Length = 4 -> "4"
    decodeMap.put(4, getSet(codeNumbers.get(2)));
    // Length = 5 -> "2", "3", "5"
    List<String> candidatesLength5 = codeNumbers.subList(3, 6);
    Set<String> candidateLength5;
    for (String candidateLength5String : candidatesLength5) {
      candidateLength5 = getSet(candidateLength5String);
      if (candidateLength5.containsAll(getDifference(decodeMap.get(4), decodeMap.get(1)))) {
        decodeMap.put(5, candidateLength5);
      } else if (candidateLength5.containsAll(decodeMap.get(1))) {
        decodeMap.put(3, candidateLength5);
      } else {
        decodeMap.put(2, candidateLength5);
      }
    }
    // Length = 6 -> "0", "6", "9"
    List<String> candidatesLength6 = codeNumbers.subList(6, 9);
    Set<String> candidateLength6;
    for (String candidateLength6String : candidatesLength6) {
      candidateLength6 = getSet(candidateLength6String);
      if (!candidateLength6.containsAll(getDifference(decodeMap.get(4), decodeMap.get(1)))) {
        decodeMap.put(0, candidateLength6);
      } else if (!candidateLength6.containsAll(decodeMap.get(1))) {
        decodeMap.put(6, candidateLength6);
      } else {
        decodeMap.put(9, candidateLength6);
      }
    }
    // Length = 7 -> "8"
    decodeMap.put(8, getSet(codeNumbers.get(9)));
  }

  private Set<String> getDifference(Set<String> a, Set<String> b) {
    Set<String> result = new HashSet<>();
    result.addAll(a);
    result.removeAll(b);
    return result;
  }

  private int decode(String code) {
    Set<String> codeSet = getSet(code);
    for (int i = 0; i <= 9; i++) {
      if (codeSet.equals(decodeMap.get(i))) {
        return i;
      }
    }
    return -1;
  }

  private Set<String> getSet(String a) {
    return new HashSet<String>(Arrays.asList(a.split("")));
  }
}
