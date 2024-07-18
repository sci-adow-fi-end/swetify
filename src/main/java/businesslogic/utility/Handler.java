package businesslogic.utility;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

public abstract class Handler {


    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    protected static NavigationManager navigationManager;

    public abstract Session update(Session session);

    public void setNavigationManager(NavigationManager navigationManager) {
        Handler.navigationManager = navigationManager;
    }

    //TODO add clearscreen to all handlers
    protected static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    protected static void printError(String error){
        System.out.println(" ");
        System.out.println(ANSI_RED+error+ANSI_RESET);
        System.out.println(" ");
    }
    protected static void printGreen(String message){
        System.out.println(" ");
        System.out.println(ANSI_GREEN+message+ANSI_RESET);
        System.out.println(" ");
    }

    protected static int askNumberInRange(int min, int max){
        int navigationOption = -1;
        boolean validNavigationOption = false;
        Scanner input = new Scanner(System.in);
        while (!validNavigationOption) {
            try {
                navigationOption = Integer.parseInt(input.nextLine());
                if (ConfigOptions.TEST_MODE) {
                    String nextInput = getRestOfInput(input);
                    System.setIn(new ByteArrayInputStream(nextInput.getBytes()));
                }
                if (navigationOption>=min&&navigationOption<=max) {
                    validNavigationOption = true;
                }
                else{
                    printError("not in range");
                }
            } catch (NumberFormatException e) {
                printError("not a number");
            }
        }

        return navigationOption;
    }
    protected static String getRestOfInput(Scanner input){
        StringBuilder restOfInput = new StringBuilder();
        while (input.hasNextLine()) {
            String riga = input.nextLine();
            restOfInput.append(riga).append("\n");
        }
        return restOfInput.toString();
    }

}
