package businesslogic;

import dao.ArtistDao;
import domainmodel.Artist;
import domainmodel.Podcast;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PodcastLoadHandler extends Handler{


    ArtistDao data;


    private void renderChoices() {
        System.out.println("1: Load a podcast");
        System.out.println("2: exit");
        System.out.println("\n");
    }

    private String askPodcastName(){
        Scanner input = new Scanner(System.in);
        System.out.println("Insert podcast name");
        System.out.println("\n");
        return input.nextLine();
    }

    private String askPodcastTheme(){
        Scanner input = new Scanner(System.in);
        System.out.println("Insert podcast theme");
        System.out.println("\n");
        return input.nextLine();
    }


    private List<Artist> askAuthors(State s){
        List<Artist> artists= new ArrayList<>();
        Scanner input = new Scanner(System.in);
        artists.add(s.getLoggedArtist());
        boolean insertionEnded = false;
        while(!insertionEnded){
            System.out.println("1: Add Collaboration");
            System.out.println("2: exit");
            insertionEnded = askNumberInRange(1,2)==2;
            System.out.println("\n");
            System.out.println("Insert artist name");
            artists.add(data.getByName(input.nextLine()));
        }
        return artists;
    }

    private Podcast createPodcast(State s){
        return new Podcast(askPodcastName(),askPodcastTheme(),askNumberInRange(0, Integer.MAX_VALUE),
                askNumberInRange(0, Integer.MAX_VALUE),askAuthors(s));
    }

    @Override
    public State update(State s) {

        clearScreen();
        renderChoices();
        int navOpt = askNumberInRange(1,2);
        switch (navOpt){
            case 1:
                s.getLoggedArtist().addPodcast(createPodcast(s));
                break;
            case 2:
                navigationManager.previousState();
                break;
            default:
                printError("option not valid");
        }
        return s;
    }
}
