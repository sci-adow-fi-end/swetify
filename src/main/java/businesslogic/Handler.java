package businesslogic;

public abstract class Handler {

    private static NavigationManager navigationManager = new NavigationManager();

    protected abstract void pullData();
    protected abstract void render();
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
        pullData();
        render();
        boolean askForInput = true;
        while (askForInput) {
            askForInput = handleInput();
            if (askForInput){
                printError();
            }
        }
    }
}
