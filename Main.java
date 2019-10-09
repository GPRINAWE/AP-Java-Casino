import java.util.Scanner;
import java.util.Locale;
import java.text.NumberFormat;

public class Main {
  public static Scanner scanner = new Scanner(System.in);

  public static String moneyFormat(double moneyNum) {
    Locale locale = new Locale("en", "US");      
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
    return currencyFormatter.format(moneyNum);
  }

  public static double getUserBet() {
    double input;
    do {
      input = (double)Input.getInt(scanner, "Bet: ");
    } while (input <= 0);
    return input;
  }

  public static void main(String[] args) {
    System.out.println("\n<Casino>\n");

    /*
     * 0 - START - Starts the game with default settings
     * 1 - EXIT - Closes the program
     */
    CommandLine cmdLn = new CommandLine(
      "[START/EXIT]: ",
      new String[] {"START","EXIT"}
    );

    int commandInput;
    do {

      commandInput = cmdLn.getUserInput(scanner);

      switch (commandInput) {
        case 0: //Game start
          System.out.println("----------");
          Blackjack blackjack = new Blackjack(scanner);
          double bet = getUserBet();
          System.out.println("You bet " + moneyFormat(bet));
          double winnings = blackjack.play(bet);
          System.out.println("Winnings: " + moneyFormat(winnings));
          System.out.println("----------");
          break;
        case 1: //System exit message
          System.out.println("\n</Casino>\n");
          break;
        default: //Error handling
          System.out.println("ERROR: Invalid Command Code");
          System.out.close();
      }

    } while(commandInput != 1);
    scanner.close();
  }
}
