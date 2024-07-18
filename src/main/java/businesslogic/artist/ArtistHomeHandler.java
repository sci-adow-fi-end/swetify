package businesslogic.artist;
import businesslogic.utility.ConfigOptions;
import businesslogic.utility.Handler;
import businesslogic.utility.NavigationManager;
import businesslogic.utility.State;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

public class ArtistHomeHandler extends Handler {


    private void renderChoices(State state) {
        System.out.println(state.getLoggedArtist().getUsername() + " welcome to swetify, the engineer's music streaming service!");
        System.out.println("\n");
        System.out.println("1: Upload an album");
        System.out.println("2: Upload a podcast");
        System.out.println("3: Log out");
        System.out.println("4: Close Swetify");
    }


    @Override
    public State update(State state) {
        clearScreen();
        renderChoices(state);
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
                case 1:
                    navigationManager.switchToController(NavigationManager.HandlerId.LOAD_ALBUM);
                    break;
                case 2:
                    navigationManager.switchToController(NavigationManager.HandlerId.LOAD_PODCAST);
                    break;
                case 3:
                    navigationManager.switchToController(NavigationManager.HandlerId.LOGIN);
                    break;
                case 4:
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
