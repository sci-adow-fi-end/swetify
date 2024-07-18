package businesslogic.customer;

import businesslogic.utility.ConfigOptions;
import businesslogic.utility.Handler;
import businesslogic.utility.NavigationManager;
import businesslogic.utility.Session;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

public class HomeHandler extends Handler {


    private void renderChoices(Session session) {
        printGreen(session.getLoggedUser().getUsername() + " welcome to swetify, the engineer's music streaming service!");
        System.out.println("\n");
        System.out.println("1: Search for a track, artist or playlist");
        System.out.println("2: View your playlists");
        System.out.println("3: View suggested songs and podcasts");
        System.out.println("4: View followed artists");
        System.out.println("5: Log out");
        System.out.println("6: Close Swetify");
    }


    @Override
    public Session update(Session session) {
        clearScreen();
        renderChoices(session);
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
                    navigationManager.switchToController(NavigationManager.HandlerId.VIEW_ALL_PLAYLISTS);
                    break;
                case 3:
                    navigationManager.switchToController(NavigationManager.HandlerId.VIEW_SUGGESTIONS);
                    break;
                case 4:
                    navigationManager.switchToController(NavigationManager.HandlerId.VIEW_FOLLOWED_ARTISTS);
                    break;
                case 5:
                    navigationManager.switchToController(NavigationManager.HandlerId.LOGIN);
                    break;
                case 6:
                    navigationManager.stop();
                    break;
                default:
                    printError("inserted option not valid");
                    validNavigationOption=false;
            }
        }
        return session;
    }
}
