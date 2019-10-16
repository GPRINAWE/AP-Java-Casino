import java.util.Scanner;

public class Blackjack {
  //Blackjack pays 3 for 2
  private static double winPerWager = 3.0/2.0;

  private Scanner scanner;

  private Deck deck;
  private Hand dealerHand;
  private Hand playerHand;

  public Blackjack(Scanner s) {
    scanner = s;

    deck = new Deck();
    deck.shuffle();

    dealerHand = new Hand("blackjack");
    dealerHand.addCard(deck.draw());
    dealerHand.addCard(deck.draw());

    playerHand = new Hand("blackjack");
    playerHand.addCard(deck.draw());
    playerHand.addCard(deck.draw());
  }

  private static double winnings(double bet) {
    return bet * Blackjack.winPerWager;
  }

  private void displayWin(String msg) {
    System.out.println();
    System.out.println(msg);
    System.out.println("Player wins!");
  }

  private void displayLoss(String msg) {
    System.out.println();
    System.out.println(msg);
    System.out.println("Dealer wins.");
  }

  public static void printHand(String name, Hand hand) {
    System.out.println(name + " cards: " + hand.toString());
  }

  private void drawCard(String name, Hand hand) {
    Card newCard = deck.draw();
    System.out.println(name + " drew " + newCard.toString());

    hand.addCard(newCard);
    Blackjack.printHand(name, hand);
  }

  public double play(double bet) {
    System.out.println("Dealer cards: [" + dealerHand.getCard(0) + ",??]");
    System.out.println("Player cards: " + playerHand.toString());
    System.out.println("-");

    if (playerHand.getHandValue() == 21) {
      displayWin("Player gets a dealer blackjack.");
      return Blackjack.winnings(bet);
    }

    CommandLine actionCmdLn = new CommandLine(
      "Action (Hit,Stand): ",
      new String[] {"hit","stand"}
    );

    int actionInput;
    boolean busted = false;
    do {

      actionInput = actionCmdLn.getUserInput(scanner);

      switch (actionInput) {
        case 0: //Hit another card
          drawCard("Player", playerHand);
          if (playerHand.getHandValue() > 21) {
            busted = true;
          }
          break;
        case 1: //Stay on current hand
          System.out.println("You stand.");
          break;
        default: //Error handling
          System.out.println("ERROR: Invalid Action Code");
          System.out.close();
      }
    } while(actionInput != 1 && busted == false);

    //Display results
    if (busted) {
      displayLoss("Player's bust.");
      return -bet;
    }

    Input.waitForEnter(scanner);

    //Reveal hidden dealer card and hit until at hand value 17 or more
    System.out.println("Dealer reveals " + dealerHand.getCard(1));
    Blackjack.printHand("Dealer", dealerHand);
    while(dealerHand.getHandValue() < 17) {
      Input.waitForEnter(scanner);
      drawCard("Dealer", dealerHand);
    }

    if (dealerHand.getHandValue() > 21) {
      displayWin("Dealer busts.");
      return Blackjack.winnings(bet);
    }

    Input.waitForEnter(scanner);

    //Display results
    int dealerHandValue = dealerHand.getHandValue();
    int playerHandValue = playerHand.getHandValue();

    if (dealerHandValue == 21) {
      displayLoss("Dealer's blackjack.");
      return -bet;
    }
    if (playerHandValue == 21) {
      displayWin("Player's blackjack.");
      return Blackjack.winnings(bet);
    }

    System.out.println("Player's hand value: " + playerHandValue);
    System.out.println("Dealer's hand value: " + dealerHandValue);
    if (playerHandValue > dealerHandValue) {
      displayWin("Player has higher value.");
      return Blackjack.winnings(bet);
    } else if (playerHandValue < dealerHandValue) {
      displayLoss("Dealer has higher value.");
      return -bet;
    } else {
      System.out.println();
      System.out.println("Draw.");
      return 0.0;
    }
  }
}
