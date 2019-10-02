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

  public String toString() {
    String str = "";
    switch(value){
      case 1: //Ace
        str += "A";
        break;
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
      case 10: //Numbers 2 to 10
        str += value;
        break;
      case 11: //Jack
        str += "J";
        break;
      case 12: //Queen
        str += "Q";
        break;
      case 13: //King
        str += "K";
        break;
      default: //Error
        str += "?";
    }
    switch(suit) {
      case 1: //Clubs
        str += "C";
        break;
      case 2: //Spades
        str += "S";
        break;
      case 3: //Hearts
        str += "H";
        break;
      case 4: //Diamonds
        str += "D";
        break;
      default: //Error
        str += "?";
    }
    
    return str;
  }
}
