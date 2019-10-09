import java.util.Scanner;

public class Input {

  public static void waitForEnter(Scanner scanner) {
    scanner.nextLine();
  }

  private static String getUserInput(Scanner scanner, String prompt, String regex) {
    String input;
    do {
        System.out.print(prompt);
        input = scanner.nextLine().replaceAll(regex, "");
    } while (input.isBlank());
    return input;
  }

  public static String getNormal(Scanner scanner, String prompt)   {
    return Input.getUserInput(scanner, prompt, "[^A-Za-z0-9]");
  }

  public static String getLetters(Scanner scanner, String prompt) {
    return Input.getUserInput(scanner, prompt, "[^A-Za-z]");
  }

  public static int getDigits(Scanner scanner, String prompt) {
    return Integer.parseInt(Input.getUserInput(scanner, prompt, "[^0-9]"));
  }
}
