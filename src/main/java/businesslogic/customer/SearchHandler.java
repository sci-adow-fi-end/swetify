package businesslogic.customer;

import businesslogic.utility.ConfigOptions;
import businesslogic.utility.Handler;
import businesslogic.utility.NavigationManager;
import businesslogic.utility.State;
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

    @Override
    public State update(State state) {
        clearScreen();
        renderChoices();

        Scanner scanner = new Scanner(System.in);
        input = scanner.nextLine();
        boolean songsFound = false;
        boolean podcastsFound = false;
        boolean artistsFound = false;

        if (ConfigOptions.TEST_MODE) {
            String nextInput = getRestOfInput(scanner);
            System.setIn(new ByteArrayInputStream(nextInput.getBytes()));
        }

        if (!input.equals("-")){

            songs = songsDatabase.getAllByName(input);
            if (songs.isEmpty()){
                System.out.println(ANSI_RED +"No song matches found"+ ANSI_RESET);
            }
            else{
                songsFound = true;
            }

            podcasts = podcastsDatabase.getAllByName(input);
            if (podcasts.isEmpty()){
                System.out.println(ANSI_RED +"No podcast matches found"+ ANSI_RESET);
            }
            else{
                podcastsFound = true;
            }

            artists = artistsDatabase.getAllByName(input);
            if (artists.isEmpty()){
                System.out.println(ANSI_RED +"No artist matches found"+ ANSI_RESET);
            }
            else{
                artistsFound = true;
            }

            int navigationChoice;
            boolean validNavigationChoice = false;

            while (!validNavigationChoice) {

                validNavigationChoice = true;

                if (songsFound){
                    int i = 0;
                    for (Song song : songs){
                        System.out.println(i+") "+ song.getTitle());
                    }
                    System.out.println("Enter the number corresponding to the song you want to play:");
                    try{
                        navigationChoice = scanner.nextInt();
                        state.setSelectedTrack(songs.get(navigationChoice));
                        navigationManager.switchToController(NavigationManager.HandlerId.PLAY_TRACK);
                    }
                    catch (NumberFormatException e){
                        System.out.println("Input is not a number");
                        validNavigationChoice = false;
                    }
                    catch (IndexOutOfBoundsException e){
                        System.out.println("Please enter a valid number");
                        validNavigationChoice = false;
                    }
                }

                if (podcastsFound){
                    int i = 0;
                    for (Podcast podcast : podcasts){
                        System.out.println(i+") "+ podcast.getTitle());
                    }
                    System.out.println("Enter the number corresponding to the podcast you want to play:");
                    try{
                        navigationChoice = scanner.nextInt();
                        state.setSelectedTrack(podcasts.get(navigationChoice));
                        navigationManager.switchToController(NavigationManager.HandlerId.PLAY_TRACK);
                    }
                    catch (NumberFormatException e){
                        System.out.println("Input is not a number");
                        validNavigationChoice = false;
                    }
                    catch (IndexOutOfBoundsException e){
                        System.out.println("Please enter a valid number");
                        validNavigationChoice = false;
                    }
                }

                if (artistsFound){
                    int i = 0;
                    for (Artist artist : artists){
                        System.out.println(i+") "+ artist.getStageName());
                    }

                    System.out.println("Enter the number corresponding to the artist you want to view:");
                    try{
                        navigationChoice = scanner.nextInt();
                        state.setViewingArtist(artists.get(navigationChoice));
                        navigationManager.switchToController(NavigationManager.HandlerId.VIEW_ARTIST);
                    }
                    catch (NumberFormatException e){
                        System.out.println("Input is not a number");
                        validNavigationChoice = false;
                    }
                    catch (IndexOutOfBoundsException e){
                        System.out.println("Please enter a valid number");
                        validNavigationChoice = false;
                    }
                }
            }
        }
        else
            navigationManager.previousState();

        return state;
    }
}
