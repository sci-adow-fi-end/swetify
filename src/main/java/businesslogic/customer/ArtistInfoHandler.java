package businesslogic.customer;

import businesslogic.utility.Handler;
import businesslogic.utility.NavigationManager;
import businesslogic.utility.State;
import dao.users.ArtistDAO;
import dao.users.CustomerDAO;
import domainmodel.entities.users.Artist;

import java.util.Scanner;

public class ArtistInfoHandler extends Handler {

    private final ArtistDAO artistDAO;
    private final CustomerDAO customerDAO;

    public ArtistInfoHandler(ArtistDAO artistDAO, CustomerDAO customerDAO) {
        this.artistDAO = artistDAO;
        this.customerDAO = customerDAO;
    }

    private void renderArtistInfo(Artist artist) {
        System.out.println("Stage name: "+artist.getStageName());
        System.out.println("Biography: "+artist.getBiography());
        System.out.println("Followers: "+artist.getFollowers());
    }

    private void renderChoices(){
        System.out.println("0: view artist's albums");
        System.out.println("1: follow artist");
        System.out.println("2: go back");
        System.out.println("3: close Swetify");
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
                    state.getViewingArtist().setFollowers(state.getViewingArtist().getFollowers() + 1);
                    state.getLoggedUser().addFollowedArtists(state.getViewingArtist());
                    artistDAO.update(state.getViewingArtist());
                    customerDAO.update(state.getLoggedUser());
                    System.out.println("Now you follow " + state.getViewingArtist().getStageName());
                    break;
                case 2:
                    navigationManager.previousState();
                    break;
                case 3:
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
