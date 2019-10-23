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
  private Hand trick;
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

  private Card computerPlayCard(Hand hand) {
    if (trick.getCards().size() == 0) {
      int handMin = hand.getMinValue(2);
    } else {
      int trickSuit = trick.getCards().get(0).getSuit();
      Hand trickWithSuit = new Hand("hearts",trick.getCardsFromSuit(trickSuit));
      Hand handWithSuit = new Hand("hearts",hand.getCardsFromSuit(trickSuit));

      if (handWithSuit.getCards().size() > 0) { //If hand has cards in trick's suit...
        if (trickWithSuit.getCardsFromValue(1).size() > 0) {
          //if there is an ace, play highest value of hand
          return hand.playCard(trickSuit, handWithSuit.getMaxValue(13));
        } else {
          //if no ace, try to play highest value you can lose on
          int trickMax = trickWithSuit.getMaxValue(13);
          Card card = hand.playCard(trickSuit,handWithSuit.getMaxValue(trickMax-1));
          if (card == null) {
            //otherwise accept fate and get rid of highest value
            card = hand.playCard(trickSuit,handWithSuit.getMaxValue(13));
          }
          return card;
        }
      } else { //If hand doesn't have cards in trick's suit
        if (hand.findCard(2,12) != -1) {
          //play queen of spades at first opportunity
          return hand.playCard(2,12);
        } else if (hand.getCardsFromSuit(3).size() > 0) {
          //play highest heart by default
          Hand handWithHearts = new Hand("hearts",hand.getCardsFromSuit(3));
          return hand.playCard(3,handWithHearts.getMaxValue(13));
        } else {
          //othewise play highest general card
          Hand handWithMax = new Hand("hearts",getCardsFromValue(hand.getMaxValue(13)));
          return hand.playCard(handWithMax.getCard(0).getSuit(),hand.getMaxValue(13));
        }
      }
    }
  }

  public double play(double bet) {
    trick = new Hand("hearts");

    playerHand.sort();
    System.out.println("Your hand: " + playerHand.toString());

    Card card;
    boolean validPlay = false;
    do {

      System.out.println();
      card = promptPlayerCard();

      if (card == null) {
        System.out.println("Card not found.");
      } else if (!heartsCanLead && card.getSuit() == 3 && trick.getCards().size() == 0) {
        System.out.println("Hearts cannot lead until a point card is discarded.");
      } else {
        validPlay = true;
      }
    } while(!validPlay);

    System.out.println();

    trick.addCard(card);
    System.out.println("Trick: " + trick.toString());
    System.out.println("Your hand: " + playerHand.toString());
    return 0.0;
  }
}
