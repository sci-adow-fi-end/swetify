package businesslogic.customer;

import businesslogic.utility.ConfigOptions;
import businesslogic.utility.Handler;
import businesslogic.utility.NavigationManager;
import businesslogic.utility.State;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

public class HomeHandler extends Handler {


    private void renderChoices() {
        System.out.println("Welcome to swetify, the engineer's music streaming service!");
        System.out.println("\n");
        System.out.println("1: Search for a track, artist or playlist");
        System.out.println("2: View your playlists");
        System.out.println("3: View suggested songs and podcasts");
        System.out.println("4: Log out");
        System.out.println("5: Close Swetify");
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
                case 1:
                    navigationManager.switchToController(NavigationManager.HandlerId.SEARCH);
                    break;
                case 2:
                    navigationManager.switchToController(NavigationManager.HandlerId.VIEW_PLAYLIST);
                    break;
                case 3:
                    navigationManager.switchToController(NavigationManager.HandlerId.VIEW_SUGGESTIONS);
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
