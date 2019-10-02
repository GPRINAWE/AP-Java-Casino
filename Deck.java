import java.util.ArrayList;
import java.util.Random;

class Deck {
  private ArrayList cards;

  Deck() {
    cards = new ArrayList();
    for (int i = 1; i <= 4; i++) {
      for (int j = 1; j <= 13; j++) {
        cards.add(new Card(i,j));
      }
    }
  }

  //Knuth Fisher Yates Algorithm
  public void shuffle() {
    Random r = new Random();
    for (int i = cards.size()-1; i > 0; i--) {
      int swapIndex = r.nextInt(i);
      Card tempVal = (Card)cards.get(swapIndex);
      cards.set(swapIndex,cards.get(i));
      cards.set(i,tempVal);
    }
  }

  public Card draw() {
    return (Card)cards.remove(0);
  }

  public String toString() {
    return cards.toString();
  }
}
