import java.util.Scanner;

class CommandLine {
  private commandList;
  private commandPrompt;

  public CommandLine(String[] cmdList) {
    commandList = cmdList;
    commandPrompt = "INPUT";
  }

  public CommandLine(String[] cmdList, String cmdPrompt) {
    commandList = cmdList;
    commandPrompt = cmdPrompt;
  }

  /**
   * Returns an integer,
   * which is the index of the target element in an array
   * or -1 if the element is not found
   *
   * @param  array  Any string array
   * @param  target  Any string
   * @return  the index of target in array or -1
   */
  public static int stringArrayIndexOfIgnoreCase(String[] array,String target) {
    for (int i = 0; i < array.length; i++) {
      if (array[i].equalsIgnoreCase(target)) {
        return i;
      }
    }
    return -1;
  }

  public int getUserInput(String prompt) {
    int commandInput;
    do {
        System.out.print("[" + commandPrompt + "]: ");
        String input = scanner.nextLine().replaceAll("[^A-Za-z]","");
        commandInput = CommandLine.stringArrayIndexOfIgnoreCase(commandList, input);
    } while (commandInput == -1);
    return commandInput;
  }
}
