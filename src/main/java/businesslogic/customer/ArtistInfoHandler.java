package businesslogic.customer;

import businesslogic.utility.NavigationManager;
import businesslogic.utility.Handler;
import businesslogic.utility.State;
import domainmodel.entities.Artist;

import java.util.Scanner;

public class ArtistInfoHandler extends Handler {

    private void renderArtistInfo(Artist artist) {
        System.out.println("Stage name: "+artist.getStageName());
        System.out.println("Biography: "+artist.getBiography());
        System.out.println("Followers: "+artist.getFollowers());
    }

    private void renderChoices(){
        System.out.println("0: view artist's albums");
        System.out.println("1: go back");
    }

    @Override
    public State update(State state) {

        boolean validNavigationOption = false;

        while (!validNavigationOption) { //render again choices when the input is not valid
            clearScreen();
            renderArtistInfo(state.getViewingArtist());
            renderChoices();

            int navigationOption = -1;
            Scanner input = new Scanner(System.in);

            try {
                navigationOption = input.nextInt();
                validNavigationOption = true;
            } catch (NumberFormatException ignored) {
                System.out.println("Input is not a number");
                continue;
            }

            switch (navigationOption) {
                case 0:
                    navigationManager.switchToController(NavigationManager.HandlerId.VIEW_ALBUMS);
                    break;
                case 1:
                    navigationManager.previousState();
                    break;
                default:
                    printError("inserted option not valid");
                    validNavigationOption=false;
            }
        }

        return state;
    }

}
