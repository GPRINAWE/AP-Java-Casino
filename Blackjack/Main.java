import java.util.Scanner;

public class Main {
  
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    //Aces handled by special rule, numbers are normal, face cards are 10
    int[] pipValMap = new int[] {11,2,3,4,5,6,7,8,9,10,10,10,10};

    System.out.println("\n<Blackjack>\n");

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

          //Initialize and shuffle deck
          Deck deck = new Deck();
          deck.shuffle();

          //Deal first two for dealer
          Hand dealerHand = new Hand("blackjack");
          dealerHand.addCard(deck.draw());
          dealerHand.addCard(deck.draw());
          System.out.println("Dealer cards: [" + dealerHand.getCard(0) + ",??]");

          System.out.println();

          //Deal first two for player
          Hand playerHand = new Hand("blackjack");
          playerHand.addCard(deck.draw());
          playerHand.addCard(deck.draw());
          System.out.println("Player cards: " + playerHand.toString());
          System.out.println("Hand Value: " + playerHand.getHandValue());

          if (playerHand.getHandValue() == 21) {
            System.out.println();
            System.out.println("Player gets a dealer blackjack.");
            System.out.println("Player wins!");
          } else {
            /*
            * 0 - Hit
            * 1 - Stay
            */
            CommandLine actionCmdLn = new CommandLine(
              "Action (Hit,Stand): ",
              new String[] {"Hit","Stand"}
            );

            int actionInput;
            boolean busted = false;
            do {

              System.out.println("-");

              actionInput = actionCmdLn.getUserInput(scanner);

              switch (actionInput) {
                case 0: //Hit another card
                  Card newCard = deck.draw();
                  playerHand.addCard(newCard);
                  System.out.println("Drew " + newCard.toString());
                  System.out.println();
                  System.out.println("Player cards: " + playerHand.toString());
                  System.out.println("Hand value: " + playerHand.getHandValue());
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
            
            System.out.println();

            //Display results
            if (busted) {
              System.out.println("Player's bust.");
              System.out.println("Dealer wins.");
            } else {
              //Reveal hidden dealer card and hit until at hand value 17 or more
              System.out.println("Dealer reveals " + dealerHand.getCard(1));
              System.out.println();
              System.out.println("Dealer cards: " + dealerHand.getCards());
              System.out.println("Hand value: " + dealerHand.getHandValue());
              while(dealerHand.getHandValue() < 17) {
                System.out.println("-");
                Card newCard = deck.draw();
                dealerHand.addCard(newCard);
                System.out.println("Dealer draws " + newCard.toString());
                System.out.println();
                System.out.println("Dealer cards: " + dealerHand.getCards());
                System.out.println("Hand value: " + dealerHand.getHandValue());
              }

              System.out.println();

              //Display results
              int dealerHandValue = dealerHand.getHandValue();
              int playerHandValue = playerHand.getHandValue();
              if (dealerHandValue > 21) {
                System.out.println("Dealer busts.");
                System.out.println("Player wins!");
              } else if (dealerHandValue == 21) {
                System.out.println("Dealer's blackjack.");
                System.out.println("Dealer wins.");
              } else if (playerHandValue == 21) {
                System.out.println("Player's blackjack.");
                System.out.println("Player wins!");
              } else {
                System.out.println("Player's hand value: " + playerHandValue);
                System.out.println("Dealer's hand value: " + dealerHandValue);
                System.out.println();
                if (playerHandValue > dealerHandValue) {
                  System.out.println("Player wins!");
                } else if (playerHandValue == dealerHandValue) {
                  System.out.println("Draw.");
                } else {
                  System.out.println("Dealer wins.");
                }
              }
            }
          }

          System.out.println("----------");
          break;
        case 1: //System exit message
          System.out.println("\n</Blackjack>\n");
          break;
        default: //Error handling
          System.out.println("ERROR: Invalid Command Code");
          System.out.close();
      }

    } while(commandInput != 1);
    scanner.close();
  }
}
