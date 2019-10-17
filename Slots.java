import java.util.Scanner;
import java.util.Random;

public class Slots {
  private Scanner scanner;
  private Random rng;

  private static CommandLine actionCmdLn = new CommandLine(
    "Action (Bet,Spin,Leave): ",
    new String[] {"bet","spin","leave"}
  );

  public Slots(Scanner s) {
    scanner = s;
    rng = new Random();
  }

  private int randomInt(int min, int max) {
    return min + rng.nextInt(max-min);
  }

  private int[] randomIntArray(int min, int max, int length) {
    int[] array = new int[length];
    for (int i = 0; i < length; i++) {
      array[i] = randomInt(min, max);
    }
    return array;
  }

  private static void printSlots(int[] slots) {
    String printStr = "|";
    for (int slot : slots) {
      printStr += (slot + "|");
    }
    System.out.println(printStr);
  }

  public double play(double bet) {
    int[] slots;

    int actionInput;
    do {

      actionInput = Slots.actionCmdLn.getUserInput(scanner);

      switch (actionInput) {
        case 0: //change bet
          break;
        case 1: //spin
          slots = randomIntArray(0,10,5);
          Slots.printSlots(slots);
          break;
        case 2: //leave
          break;
      }
    } while(actionInput != 2);

    return 0.0;
  }
}
