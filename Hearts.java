import java.util.Objects;
import java.util.ArrayList;
import java.util.Scanner;

public class Hearts {
  private static int pointThreshold = 50;

  private static int computers = 3;
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
  private Hand[] computerHands = new Hand[computers];

  private Card playerLastPlay;
  private Card[] computerLastPlays = new Card[computers];

  private int playerPoints = 0;
  private int[] computerPoints = new int[computers];
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
    playerHand.sort();
    System.out.println("Hand: " + playerHand.toString());

    Integer trickSuit = null;
    ArrayList<Card> cardsWithTrickSuit = new ArrayList();
    boolean hasTrickSuit = false;
    if (trick.getCards().size() != 0) {
      trickSuit = trick.getCard(0).getSuit();
      cardsWithTrickSuit = playerHand.getCardsFromSuit(trickSuit);
      hasTrickSuit = (cardsWithTrickSuit.size() > 0);

      if (hasTrickSuit && playerHand.getCards().size() > 0) {
        System.out.println("Playable: " + playerHand.getCardsFromSuit(trickSuit).toString());
      }
    }

    if (playerHand.getCards().size() == 1) {
      return playerHand.removeCard(0);
    } else if (cardsWithTrickSuit.size() == 1) {
      return playerHand.playCard(trickSuit,cardsWithTrickSuit.get(0).getValue());
    } else {
      Card card;
      boolean validPlay = false;
      do {
        int value = Hearts.valueCmdLn.getUserInput(scanner);

        int suit;
        if (hasTrickSuit) {
          suit = trickSuit;
        } else if (playerHand.getCardsFromValue(value).size() == 1) {
          suit = playerHand.getCardsFromValue(value).get(0).getSuit();
        } else {
          suit = Hearts.suitCmdLn.getUserInput(scanner);
        }
        card = playerHand.playCard(suit, value);

        if (card == null) {
          System.out.println("Card not found.");
        } else if (!heartsCanLead && card.getSuit() == 3 && trick.getCards().size() == 0) {
          System.out.println("Hearts cannot lead until a point card is discarded.");
          playerHand.addCard(card);
        } else {
          validPlay = true;
        }
      } while(!validPlay);

      return card;
    }
  }

  private Card computerPlayCard(Hand hand) {
    if (hand.getCards().size() == 0) {
      return null;
    } else if (trick.getCards().size() == 0) {
      Integer handMin;
      Hand handWithMin;
      if (heartsCanLead) {
        handMin = hand.getMinValue(2);
        if (Objects.isNull(handMin)) {
          handMin = 1;
        }
        handWithMin = new Hand("hearts",hand.getCardsFromValue(handMin));
      } else {
        Hand handNoHearts = new Hand("hearts");
        handNoHearts.addCards(hand.getCardsFromSuit(1));
        handNoHearts.addCards(hand.getCardsFromSuit(2));
        handNoHearts.addCards(hand.getCardsFromSuit(4));

        handMin = handNoHearts.getMinValue(2);
        if (Objects.isNull(handMin)) {
          handMin = 1;
        }
        handWithMin = new Hand("hearts",handNoHearts.getCardsFromValue(handMin));
      }
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

          //otherwise...
          if (Objects.isNull(playMax) || playMax == 1) {
            if (trickSuit == 1 || trickSuit == 4 || trick.getCards().size() == 3) {
              //get rid of highest value when safe or inevitable
              playMax = handWithSuit.getMaxValue(13);
            } else {
              //or play lowest value in hope
              playMax = handWithSuit.getMinValue(2);
              if (Objects.isNull(playMax)) {
                playMax = 1;
              }
            }
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
      System.out.println("Player has 2 of clubs.");
      trick.addCard(playerHand.playCard(1,2));
      playerLastPlay = new Card(1,2);
    } else {
      System.out.println("Computer #" + (turn+1) + " has 2 of clubs.");
      trick.addCard(computerHands[turn].playCard(1,2));
      computerLastPlays[turn] = new Card(1,2);
    }
    System.out.println("Trick: " + trick.toString());
    System.out.println();

    turn++;
    if (turn > computerHands.length-1) {
      turn = -1;
    }

    for (int round = 1; round <= 13; round++) {
      while (trick.getCards().size() < 1 + computers) {
        Card card;
        if (turn == -1) {
          System.out.print("Your play...");
          Input.waitForEnter(scanner);
          card = promptPlayerCard();
          playerLastPlay = card;
        } else {
          System.out.print("Comp"+(turn+1)+"'s play...");
          Input.waitForEnter(scanner);
          //System.out.println(computerHands[turn].toString());

          Hand hand = computerHands[turn];
          card = computerPlayCard(hand);
          computerLastPlays[turn] = card;
        }

        if (card.getSuit() == 3 || (card.getSuit() == 2 && card.getValue() == 12 )) {
            heartsCanLead = true;
        }
        trick.addCard(card);

        System.out.println(trick.toString());
        System.out.println();

        turn++;
        if (turn > computerHands.length-1) {
          turn = -1;
        }
      }

      int points = 0;
      Card winCard = null;
      for (Card card : trick.getCards()) {
        if (card.getSuit() == 3) {
          points += 1;
        } else if (card.getSuit() == 2 && card.getValue() == 12) {
          points += 13;
        }
        if (Objects.isNull(winCard)) {
          winCard = card;
        } else if (card.getSuit() == trick.getCard(0).getSuit() && winCard.getValue() != 1){
          if (winCard.compareTo(card) < 0 || card.getValue() == 1) {
            winCard = card;
          }
        }
      }

      if (winCard.compareTo(playerLastPlay) == 0) {
        turn = -1;
        playerPoints += points;
        System.out.print("Player won.");
      } else {
        for (int i = 0; i < computerLastPlays.length; i++) {
          if (winCard.compareTo(computerLastPlays[i]) == 0) {
            turn = i;
            computerPoints[turn] += points;
            System.out.print("Comp"+(turn+1)+" won.");
          }
        }
      }
      System.out.println(" (+"+points+"pt)");

      Input.waitForEnter(scanner);

      System.out.println("Player - " + playerPoints);
      for (int i = 0; i < computerPoints.length; i++) {
        System.out.println("Comp"+(i+1)+"  - " + computerPoints[i]);
      }

      trick = new Hand("hearts");

      System.out.println("-");

      if (round != 13) {
        System.out.println("New round.");
        Input.waitForEnter(scanner);
      }
    }

    int minPoints = playerPoints;
    int winner = -1; //Player
    for (int i = 0; i < computerPoints.length; i++) {
      int points = computerPoints[i];
      if (points < minPoints) {
        minPoints = points;
        winner = i;
      } else if (points == minPoints) {
        winner = -999; //none
        break;
      }
    }

    if (winner == -999) {
      System.out.println("Tie.");
    } else if (winner == -1) {
      System.out.println("Player wins!");
    } else {
      System.out.println("Comp"+(winner+1)+" wins.");
    }

    return 0.0;
  }
}
