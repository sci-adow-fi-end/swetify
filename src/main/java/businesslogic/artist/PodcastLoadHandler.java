package businesslogic.artist;

import businesslogic.utility.Handler;
import businesslogic.utility.State;
import dao.tracks.PodcastDAO;
import dao.users.ArtistDAO;
import domainmodel.entities.tracks.Podcast;
import domainmodel.entities.users.Artist;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PodcastLoadHandler extends Handler {


    public PodcastLoadHandler(ArtistDAO artistData, PodcastDAO podcastData) {
        this.artistData = artistData;
        this.podcastData = podcastData;
    }

    ArtistDAO artistData;
    PodcastDAO podcastData;

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
            artists.addAll(artistData.getByStageName(input.nextLine()));
        }
        return artists;
    }

    private Podcast createPodcast(State s){
        Podcast np = new Podcast(askPodcastName(),askPodcastTheme(),askNumberInRange(0, Integer.MAX_VALUE),
                askNumberInRange(0, Integer.MAX_VALUE),askAuthors(s));
        podcastData.save(np);
        return np;
    }

    @Override
    public State update(State s) {

        clearScreen();
        renderChoices();
        int navOpt = askNumberInRange(1,2);
        switch (navOpt){
            case 1:
                s.getLoggedArtist().addPodcast(createPodcast(s));
                artistData.update(s.getLoggedArtist());
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
