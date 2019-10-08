import java.util.Scanner;

public class Main {
  
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

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
          double winnings = blackjack.play(10.0);
          System.out.println("Winnings: " + winnings);
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
