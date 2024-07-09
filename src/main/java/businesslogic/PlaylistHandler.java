package businesslogic;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

public class PlaylistHandler extends Handler {

    private void renderChoices() {
        System.out.println("0: go back");
        System.out.println("1: add playlist to the bottom of the queue");
        System.out.println("2: add playlist to the top of the queue");
	    System.out.println("3: add song to the playlist");
	    System.out.println("4: remove song from the playlist");
        System.out.println("5: close Swetify");
    }

    @Override
    public State update(State state) {
	    clearScreen();
        renderChoices();
        int navigationOption = -1;
        boolean validNavigationOption = false;
        Scanner input = new Scanner(System.in);
        while (!validNavigationOption) {
            try {
                navigationOption = Integer.parseInt(input.nextLine());
                validNavigationOption = true;
            } catch (NumberFormatException ignored) {
                continue;
            }

            if (ConfigOptions.TEST_MODE) {
                String nextInput = getRestOfInput(input);
                System.setIn(new ByteArrayInputStream(nextInput.getBytes()));
            }

            switch (navigationOption) {
                case 0:
                    navigationManager.previousState();
                    break;
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
		            navigationManager.switchToController(NavigationManager.HandlerId.LOGIN);
                    break;
                case 5:
                    navigationManager.stop();
                    break;
                default:
                    printError("inserted option not valid");
                    validNavigationOption=false;
            }
        }
        return state;
    }
}
