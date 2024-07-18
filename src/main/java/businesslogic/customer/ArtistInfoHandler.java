package businesslogic.customer;

import businesslogic.utility.Handler;
import businesslogic.utility.NavigationManager;
import businesslogic.utility.Session;
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
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("Stage name: "+artist.getStageName());
        System.out.println();
        System.out.println("Biography: "+artist.getBiography());
        System.out.println();
        System.out.println("Followers: "+artist.getFollowers());
        System.out.println("-------------------------------------------------------------------------");
    }

    private void renderChoices(){
        System.out.println("0: view artist's albums");
        System.out.println("1: follow artist");
        System.out.println("2: unfollow artist");
        System.out.println("3: go back");
        System.out.println("4: close Swetify");
    }

    @Override
    public Session update(Session session) {

        boolean validNavigationOption = false;

        while (!validNavigationOption) { //render again choices when the input is not valid
            clearScreen();
            renderArtistInfo(session.getViewingArtist());
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
                    if (!session.getLoggedUser().getFollowedArtists().contains(session.getViewingArtist())) {
                        session.getViewingArtist().setFollowers(session.getViewingArtist().getFollowers() + 1);
                        session.getLoggedUser().addFollowedArtists(session.getViewingArtist());
                        artistDAO.update(session.getViewingArtist());
                        customerDAO.update(session.getLoggedUser());
                        System.out.println("Now you follow " + session.getViewingArtist().getStageName());
                    } else {
                        System.out.println("You have already followed " + session.getViewingArtist().getStageName());
                    }
                    break;
                case 2:
                    if (session.getLoggedUser().getFollowedArtists().contains(session.getViewingArtist())) {
                        session.getViewingArtist().setFollowers(session.getViewingArtist().getFollowers() - 1);
                        session.getLoggedUser().removeFollowedArtists(session.getViewingArtist());
                        customerDAO.update(session.getLoggedUser());
                        System.out.println("You unfollowed " + session.getViewingArtist().getStageName());
                    } else {
                        System.out.println("You don't follow " + session.getViewingArtist().getStageName());
                    }
                    break;

                case 3:
                    navigationManager.previousState();
                    break;
                case 4:
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
