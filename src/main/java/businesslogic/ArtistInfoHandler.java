package businesslogic;

import domainmodel.Artist;

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

        clearScreen();
        renderChoices();

        int navigationOption = -1;
        boolean validNavigationOption = false;
        Scanner input = new Scanner(System.in);

        while (!validNavigationOption) {
            try {
                navigationOption = input.nextInt();
                validNavigationOption = true;
            } catch (NumberFormatException ignored) {
                System.out.println("Input is not a number");
                continue;
            }

            //TODO: decide what to do after viewing artist's info

            switch (navigationOption) {
                case 0:

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
