import java.util.Scanner;

public class Hearts {
  private static CommandLine actionCmdLn = new CommandLine(
    "Action (): ",
    new String[] {""}
  );

  private Scanner scanner;

  private Deck deck;
  private Hand playerHand;
  private Hand[] computerHands = new Hand[3];

  public Hearts(Scanner s) {
    scanner = s;

    deck = new Deck();
    deck.shuffle();

    playerHand = new Hand("hearts");
    for (int i = 0; i < computerHands.length; i++) {
      computerHands[i] = new Hand("hearts");
    }

    for (int i = 0; i < 13; i++) {
      playerHand.addCard(deck.draw());
      for (Hand compHand : computerHands) {
        compHand.addCard(deck.draw());
      }
    }
  }

  public double play(double bet) {
    System.out.println("Your hand: " + playerHand.toString());
    return 0.0;
  }
}
