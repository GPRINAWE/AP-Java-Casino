import java.util.Scanner;

import java.util.Locale;
import java.text.NumberFormat;

public class Main {
  public static Locale locale = new Locale("en", "US");      
  public static NumberFormat moneyFormatter = NumberFormat.getCurrencyInstance(locale);

  public static String moneyFormat(double moneyNum) {
    return moneyFormatter.format(moneyNum);
  }

  public static double getUserBet(Scanner scanner) {
    int input;
    do {
      input = Input.getInt(scanner, "Bet: ");
    } while (input <= 0);
    return (double)input;
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    CommandLine cmdLn = new CommandLine(
      "[START/EXIT]: ",
      new String[] {"start","exit"}
    );

    CommandLine gameCmdLn = new CommandLine(
      "Casino (Blackjack,Leave): ",
      new String[] {"blackjack","leave"}
    );

    System.out.println("\n<Casino>\n");

    int commandInput;
    do {

      commandInput = cmdLn.getUserInput(scanner);

      switch (commandInput) {
        case 0: //Casino start
          System.out.println("----------");

          double money = 100.0;

          int gameInput;
          do {

            gameInput = gameCmdLn.getUserInput(scanner);

            switch(gameInput) {
              case 0: //Blackjack
                System.out.println("-----");

                System.out.println("Money: " + moneyFormat(money));
                System.out.println();

                double bet;
                do {
                  bet = getUserBet(scanner);
                  if (money-bet < 0) {
                    System.out.println("Not enough money.\n");
                  }
                } while((money-bet) < 0);
                System.out.println("You bet " + moneyFormat(bet));

                System.out.println();

                Blackjack blackjack = new Blackjack(scanner);
                double winnings = blackjack.play(bet);

                Input.waitForEnter(scanner);
                System.out.println("Winnings: " + moneyFormat(winnings));
                money += winnings;
                System.out.println("Money: " + moneyFormat(money));

                System.out.println("-----");
                break;
              case 1: //Leave
                break;
              default: //Error handling
                System.out.println("ERROR: Invalid Command Code");
                System.out.close();
            }

          } while (gameInput != 1);

          System.out.println("----------");
          break;
        case 1: //Exit
          break;
        default: //Error handling
          System.out.println("ERROR: Invalid Command Code");
          System.out.close();
      }

    } while(commandInput != 1);

    System.out.println("\n</Casino>\n");
    scanner.close();
  }
}
