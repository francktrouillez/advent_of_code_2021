package src;

import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;
import java.util.List;

public class FileHandler {
  public static List<String> read(String fileName) throws IOException {
    String data = new String(Files.readAllBytes(Paths.get(fileName)));
    List<String> lines = Arrays.asList(data.split("\\r?\\n|\\r"));
    return lines;
  }

  public static void write(String fileName, String content) throws IOException {
    Path path = Paths.get(fileName);
    byte[] strToBytes = content.getBytes();
    Files.write(path, strToBytes);
  }
}
