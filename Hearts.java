import java.util.Scanner;

public class Hearts {
  private static CommandLine suitCmdLn = new CommandLine(
    "Enter card suit: ",
    new String[] {"","clubs","spades","hearts","diamonds"}
  );

  private static CommandLine valueCmdLn = new CommandLine(
    "Enter card value: ",
    new String[] {"","ace","two","three","four","five","six","seven","eight","nine","ten","jack","queen","king"}
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
    playerHand.sort();
    System.out.println("Your hand: " + playerHand.toString());

    int suit = Hearts.suitCmdLn.getUserInput(scanner);
    int value = Hearts.valueCmdLn.getUserInput(scanner);

    System.out.println("suit: " + suit + "\nvalue: " + value);
    int index = playerHand.findCard(suit, value);
    Card card = playerHand.getCard(index);
    System.out.println(card.toString());
    return 0.0;
  }
}
