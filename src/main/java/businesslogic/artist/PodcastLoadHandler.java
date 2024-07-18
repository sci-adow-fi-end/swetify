package businesslogic.artist;

import businesslogic.utility.ConfigOptions;
import businesslogic.utility.Handler;
import businesslogic.utility.Session;
import dao.tracks.PodcastDAO;
import dao.users.ArtistDAO;
import domainmodel.entities.tracks.Podcast;
import domainmodel.entities.users.Artist;

import java.io.ByteArrayInputStream;
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
        System.out.println("3: Close Swetify");
        System.out.println("\n");
    }

    private String askPodcastName(){
        Scanner input = new Scanner(System.in);
        System.out.println("Insert podcast name");
        System.out.println("\n");
        String s = input.nextLine();
        if (ConfigOptions.TEST_MODE) {
            String nextInput = getRestOfInput(input);
            System.setIn(new ByteArrayInputStream(nextInput.getBytes()));
        }
        return s;
    }

    private String askPodcastTheme(){
        Scanner input = new Scanner(System.in);
        System.out.println("Insert podcast theme");
        System.out.println("\n");
        String s = input.nextLine();
        if (ConfigOptions.TEST_MODE) {
            String nextInput = getRestOfInput(input);
            System.setIn(new ByteArrayInputStream(nextInput.getBytes()));
        }
        return s;
    }

    private List<Artist> askAuthors(Session s) {
        List<Artist> artists= new ArrayList<>();
        Scanner input = new Scanner(System.in);
        artists.add(s.getLoggedArtist());
        boolean insertionEnded = false;
        while(!insertionEnded){
            System.out.println("1: Add Collaboration");
            System.out.println("2: exit");
            insertionEnded = askNumberInRange(1,2)==2;
            if(!insertionEnded) {
                System.out.println("\n");
                System.out.println("Insert artist name");
                artists.addAll(artistData.getByStageName(input.nextLine()));
            }
        }
        return artists;
    }

    private void createPodcast(Session s) {
        String title = askPodcastName();
        String theme = askPodcastTheme();
        System.out.println("Insert duration (minutes)");
        int minutes = askNumberInRange(0, Integer.MAX_VALUE);
        System.out.println("Insert duration (seconds)");
        int seconds = askNumberInRange(0, Integer.MAX_VALUE);
        List<Artist> authors = askAuthors(s);

        Podcast np = new Podcast(title,theme,minutes,seconds,authors);

        podcastData.save(np);
    }

    @Override
    public Session update(Session s) {

        clearScreen();
        renderChoices();
        int navOpt = askNumberInRange(1,2);
        switch (navOpt){
            case 1:
                createPodcast(s);
                break;
            case 2:
                navigationManager.previousState();
                break;
            case 3:
                navigationManager.stop();
                break;
            default:
                printError("option not valid");
        }
        return s;
    }
}
