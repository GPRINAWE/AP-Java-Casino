import java.util.Objects;
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
    System.out.println();

    playerHand.sort();
    System.out.println("Your hand: " + playerHand.toString());

    Card card;
    boolean validPlay = false;
    do {

      System.out.println();
      int value = Hearts.valueCmdLn.getUserInput(scanner);
      int suit = Hearts.suitCmdLn.getUserInput(scanner);
      card = playerHand.playCard(suit, value);

      if (card == null) {
        System.out.println("Card not found.");
      } else if (!heartsCanLead && card.getSuit() == 3 && trick.getCards().size() == 0) {
        System.out.println("Hearts cannot lead until a point card is discarded.");
      } else {
        validPlay = true;
      }
    } while(!validPlay);

    return card;
  }

  private Card computerPlayCard(Hand hand) {
    if (hand.getCards().size() == 0) {
      return null;
    } else if (trick.getCards().size() == 0) {
      int handMin = hand.getMinValue(2);
      Hand handWithMin = new Hand("hearts",hand.getCardsFromValue(handMin));
      return hand.playCard(handWithMin.getCard(0).getSuit(),handMin);
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
          Integer playMax = handWithSuit.getMaxValue(trickMax-1);

          //otherwise accept fate and get rid of highest value
          if (Objects.isNull(playMax)) {
            playMax = handWithSuit.getMaxValue(13);
          }
          return hand.playCard(trickSuit,playMax);
        }
      } else { //If hand doesn't have cards in trick's suit
        if (hand.findCard(2,12) != -1) {
          //play queen of spades at first opportunity
          return hand.playCard(2,12);
        } else if (hand.findCard(3,1) != -1) {
          //check for ace of hearts
          return hand.playCard(3,1);
        } else if (hand.getCardsFromSuit(3).size() > 0) {
          //play highest heart by default
          Hand handWithHearts = new Hand("hearts",hand.getCardsFromSuit(3));
          return hand.playCard(3,handWithHearts.getMaxValue(13));
        } else {
          //othewise play highest general card
          if (hand.getCardsFromValue(1).size() > 0) {
            //check for ace
            Hand handWithAce = new Hand("hearts",hand.getCardsFromValue(1));
            return hand.playCard(handWithAce.getCard(0).getSuit(),1);
          } else {
            Hand handWithMax = new Hand("hearts",hand.getCardsFromValue(hand.getMaxValue(13)));
            return hand.playCard(handWithMax.getCard(0).getSuit(),hand.getMaxValue(13));
          }
        }
      }
    }
  }

  public double play(double bet) {
    trick = new Hand("hearts");

    //computers have same turn number as their hand index
    //player's turn number is -1
    //Whoever has 2 of clubs starts the game
    Integer turn = null;
    if (playerHand.findCard(1,2) != -1) {
      turn = -1;
    } else {
      for (int i = 0; i < computerHands.length; i++) {
        if (computerHands[i].findCard(1,2) != -1) {
          turn = i;
        }
      }
    }

    if (turn == -1) {
      System.out.println("Player's has 2 of clubs.");
      trick.addCard(playerHand.playCard(1,2));
    } else {
      System.out.println("Computer #" + (turn+1) + " has 2 of clubs.");
      trick.addCard(computerHands[turn].playCard(1,2));
    }
    System.out.println("Trick: " + trick.toString());
    Input.waitForEnter(scanner);

    for (int round = 0; round < 13; round++) {
      while (trick.getCards().size() < 1 + computerHands.length) {
        turn++;
        if (turn > computerHands.length-1) {
          turn = -1;
        }

        if (turn == -1) {
          Card card = promptPlayerCard();
          trick.addCard(card);
        } else {
          Hand hand = computerHands[turn];
          //System.out.println("Comp"+(turn+1)+": " + hand.toString());
          trick.addCard(computerPlayCard(hand));
        }
        System.out.println("Trick: " + trick.toString());
        Input.waitForEnter(scanner);
      }
      trick = new Hand("hearts");
      System.out.println("-");
      System.out.println("New Round.");
    }
    return 0.0;
  }
}
