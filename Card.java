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

  public char valueChar() {
    switch(value){
      case 1: //Ace
        return 'A';
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
      case 10: //Numbers 2 to 10
        return Character.forDigit(value, 10);
      case 11: //Jack
        return 'J';
      case 12: //Queen
        return 'Q';
      case 13: //King
        return 'K';
      default: //Error
        return '?';
    }
  }

  public char suitChar() {
    switch(suit) {
      case 1: //Clubs
        return 'C';
      case 2: //Spades
        return 'S';
      case 3: //Hearts
        return 'H';
      case 4: //Diamonds
        return 'D';
      default: //Error
        return '?';
    }
  }

  public String toString() {
    return Character.toString(valueChar()) + Character.toString(suitChar());
  }
}
