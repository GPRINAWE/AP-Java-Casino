import java.util.ArrayList;
import java.util.Collections;

public class Hand {
  private ArrayList<Card> cards;
  private String gameType;

  public Hand(String type) {
    cards = new ArrayList();
    gameType = type.toLowerCase();
  }

  public Hand(String type, ArrayList c) {
    cards = new ArrayList(c);
    gameType = type.toLowerCase();
  }

  public void addCard(Card cardToAdd) {
    cards.add(cardToAdd);
  }

  public void addCards(ArrayList cardsToAdd) {
    cards.addAll(cardsToAdd);
  }

  public void sort() {
    Collections.sort(cards);
  }

  public ArrayList<Card> getCards() {
    return cards;
  }

  public Card getCard(int index) {
    return cards.get(index);
  }

  public Card removeCard(int index) {
    Card card = cards.get(index);
    cards.remove(index);
    return card;
  }

  public int findCard(int suit, int value) {
    for (int i = 0; i < cards.size(); i++) {
      Card card = cards.get(i);
      if (card.suit == suit && card.value == value) {
        return i;
      }
    }
    return -1;
  }

  public Card playCard(int suit, int value) {
    int index = findCard(suit, value)
    return removeCard(index);
  }

  public ArrayList<Card> getCardsFromSuit(int suit) {
    ArrayList result = new ArrayList();
    for (Card card : cards) {
      if (card.suit = suit) {
        result.add(card);
      }
    }
    return result;
  }

  public ArrayList<Card> getCardsFromValue(int value) {
    ArrayList result = new ArrayList();
    for (Card card : cards) {
      if (card.value = value) {
        result.add(card);
      }
    }
    return result;
  }

  public int getHandValue() {
    switch (gameType) {
      case "blackjack":
        int[] pipValMap = new int[] {11,2,3,4,5,6,7,8,9,10,10,10,10};
        
        int totalVal = 0;
        int aceCount = 0;
        //Caclulate hand value without aces
        for (Object obj : cards) {
          Card card = (Card)obj;
          if (card.getValue() == 1) {
            aceCount++;
          } else {
            totalVal += pipValMap[card.getValue()-1];
          }
        }
        //Add ace values depending of hand value
        int acesLeft = aceCount;
        for (int i = 0; i < aceCount; i++) {
          if (totalVal + acesLeft * pipValMap[0] <= 21) {
            totalVal += pipValMap[0];
          } else {
            totalVal += 1;
          }
          acesLeft--;
        }
        return totalVal;
      default: //gameType not recognized
        return -1;
    }
  }

  public String toString() {
    return cards.toString();
  }
}
