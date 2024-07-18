package businesslogic.customer;

import businesslogic.utility.ConfigOptions;
import businesslogic.utility.Handler;
import businesslogic.utility.NavigationManager;
import businesslogic.utility.Session;
import dao.tracks.PodcastDAO;
import dao.tracks.SongDAO;
import dao.users.ArtistDAO;
import domainmodel.entities.tracks.Podcast;
import domainmodel.entities.tracks.Song;
import domainmodel.entities.users.Artist;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SearchHandler extends Handler {

    private String input = "";
    private List<Song> songs = new ArrayList<>();
    private List<Podcast> podcasts = new ArrayList<>();
    private List<Artist> artists = new ArrayList<>();
    private final SongDAO songsDatabase;
    private final PodcastDAO podcastsDatabase;
    private final ArtistDAO artistsDatabase;

    public SearchHandler(SongDAO songsDatabase, PodcastDAO podcastsDatabase, ArtistDAO artistsDatabase) {
        this.songsDatabase = songsDatabase;
        this.podcastsDatabase = podcastsDatabase;
        this.artistsDatabase = artistsDatabase;
    }

    private void renderChoices() {
        System.out.println("enter a keyword to search or press - to go back\n");
    }

    private int printResults(Session session) {
        songs = songsDatabase.getByTitle(input);
        podcasts = podcastsDatabase.getByTitle(input);
        artists = artistsDatabase.getByStageName(input);

        int i = 1;
        for (Song song : songs) {
            System.out.println(i + ") " + song);
            i++;
        }
        System.out.println("\n\n");

        for (Podcast podcast : podcasts) {
            System.out.println(i + ") " + podcast);
            i++;
        }
        System.out.println("\n\n");

        for (Artist artist : artists) {
            System.out.println(i + ") " + artist.getStageName());
        }
        System.out.println("\n\n");

        if (i > 0) {
            System.out.println("insert the number of a track to play it or the number of an artist to view his info");
            int choice = askNumberInRange(1, i) - 1; //the minus 1 is to make it a valid index
            if (choice < songs.size()) {
                session.setSelectedTrack(songs.get(choice));
                return 1;
            } else if (choice < songs.size() + podcasts.size()) {
                session.setSelectedTrack(podcasts.get(choice - songs.size()));
                return 2;
            } else if (choice < songs.size() + podcasts.size() + artists.size()) {
                session.setViewingArtist(artists.get(choice - (songs.size() + podcasts.size())));
                return 3;
            }
            else{
                printError("value out of range");
                return -1;
            }

        } else {
            printError("no results found");
            return -1;
        }

    }


    @Override
    public Session update(Session session) {
        clearScreen();
        renderChoices();

        Scanner scanner = new Scanner(System.in);
        input = scanner.nextLine();
        boolean someResultsFound = false;

        if (ConfigOptions.TEST_MODE) {
            String nextInput = getRestOfInput(scanner);
            System.setIn(new ByteArrayInputStream(nextInput.getBytes()));
        }

        if (!input.equals("-")) {
            int userChoice = printResults(session);
            switch (userChoice) {
                case 1:
                case 2:
                    navigationManager.switchToController(NavigationManager.HandlerId.PLAY_TRACK);
                    break;
                case 3:
                    navigationManager.switchToController(NavigationManager.HandlerId.VIEW_ARTIST);
                    break;
            }

        } else {
            navigationManager.previousState();
        }

        return session;
    }
}
