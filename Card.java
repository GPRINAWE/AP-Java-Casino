class Card {
  private int suit;
  private int value;

  public Card(int s,int v) {
    suit = s;
    value = v;
  }

  public int getSuit() {
    return suit;
  }

  public int getValue() {
    return value;
  }

  public String valueStr() {
    switch(value){
      case 1: //Ace
        return "A";
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
      case 10: //Numbers 2 to 10
        return Integer.toString(value);
      case 11: //Jack
        return "J";
      case 12: //Queen
        return "Q";
      case 13: //King
        return "K";
      default: //Error
        return "?";
    }
  }

  public String suitStr() {
    switch(suit) {
      case 1: //Clubs
        return "\u2663";
      case 2: //Spades
        return "\u2660";
      case 3: //Hearts
        return "\u2665";
      case 4: //Diamonds
        return "\u2666";
      default: //Error
        return "?";
    }
  }

  public String toString() {
    return valueStr() + suitStr();
  }
}
