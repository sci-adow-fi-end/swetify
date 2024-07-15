package businesslogic;

import java.util.Scanner;

public class PlaylistHandler extends Handler {

    private void renderChoices() {

        System.out.println("1: play a song from the playlist");
        System.out.println("2: play entire playlist");
        System.out.println("3: modify the playlist");
        System.out.println("4: go back");
        System.out.println("5: close Swetify");
    }

    @Override
    public State update(State state) {
        clearScreen();
        renderChoices();
        int navigationOption = askNumberInRange(1, 5);

        switch (navigationOption) {
            case 1:
                navigationManager.switchToController(NavigationManager.HandlerId.VIEW_PLAYLIST);
                break;
            case 2:
                navigationManager.switchToController(NavigationManager.HandlerId.VIEW_SUGGESTIONS);
                break;
            case 3:
                navigationManager.switchToController(NavigationManager.HandlerId.LOGIN);
                break;
            case 4:
                navigationManager.previousState();
                break;
            case 5:
                navigationManager.stop();
                break;
            default:
                printError("inserted option not valid");

        }

        return state;
    }
}
