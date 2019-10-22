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

  //Hearts can only lead a trick if a heart is discarded
  private boolean heartsCanLead = false;

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

  private Card promptPlayerCard() {
    int value = Hearts.valueCmdLn.getUserInput(scanner);
    int suit = Hearts.suitCmdLn.getUserInput(scanner);
    return playerHand.playCard(suit, value);
  }

  public double play(double bet) {
    Hand trick = new Hand("hearts");

    playerHand.sort();
    System.out.println("Your hand: " + playerHand.toString());

    Card card;
    boolean validPlay = false;
    do {

      System.out.println();
      card = promptPlayerCard();

      if (card == null) {
        System.out.println("Card not found.");
      } else if (card.getSuit() == 3 && trick.getCards().size() == 0 && !heartsCanLead) {
        System.out.println("Hearts cannot lead until a point card is discarded.");
      } else {
        validPlay = true;
      }
    } while(!validPlay);

    trick.addCard(card);

    System.out.println("Trick: " + trick.toString());
    System.out.println("Your hand: " + playerHand.toString());
    return 0.0;
  }
}
