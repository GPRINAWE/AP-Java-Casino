import java.util.ArrayList;

class Hand {
  private ArrayList cards;

  public Hand() {
    cards = new ArrayList();
  }

  public Hand(ArrayList c) {
    cards = new ArrayList(c);
  }

  public Card getCard(int index) {
    return (Card)cards.get(index);
  }

  public ArrayList getCards() {
    return cards;
  }

  public void addCard(Card cardToAdd) {
    cards.add(cardToAdd);
  }

  public void addCards(ArrayList cardsToAdd) {
    cards.addAll(cardsToAdd);
  }

  public int getHandValue(String gameType) {
    int[] pipValMap;
    switch (gameType.toLowerCase()) {
      case 'blackjack':
        pipValMap = new int[] {11,2,3,4,5,6,7,8,9,10,10,10,10};
        
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
          if (totalVal + acesLeft * 11 <= 21) {
            totalVal += pipValMap[0];
          } else {
            totalVal += 1;
          }
          acesLeft--;
        }
        return totalVal;
  }

  public String toString() {
    return cards.toString();
  }
}

