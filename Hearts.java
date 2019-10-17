import java.util.Scanner;

public class Hearts {
  private Scanner scanner;

  private Hand playerHand;
  private Hand[] computerHands = new Hand[3];

  public Hearts(scanner s) {
    scanner = s;
    playerHand = new Hand("hearts");
    for (int i = 0; i < computerHands.length(); i++) {
      computerHands[i] = new Hand("hearts");
    }
  }
}
