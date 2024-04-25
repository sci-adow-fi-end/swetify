package businesslogic;
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

    protected static NavigationManager navigationManager = new NavigationManager();

    protected abstract void renderChoices();
    protected abstract boolean handleInput();

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    protected static void printError(){
        System.out.println(" ");
        System.out.println("the inserted input is not valid, please type it again");
        System.out.println(" ");
    }

    public void update(){
        renderChoices();
        boolean askForInput = true;
        while (askForInput) {
            askForInput = handleInput();
            if (!askForInput){
                printError();
            }
        }
    }
}
