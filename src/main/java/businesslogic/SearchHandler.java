package businesslogic;
import java.util.NoSuchElementException;
import java.util.Scanner;

import java.util.ArrayList;

import dao.Dao;
import domainmodel.Artist;
import domainmodel.Podcast;
import domainmodel.Song;

public class SearchHandler extends Handler {

    private String input = "";
    private ArrayList<Song> songs;
    private ArrayList<Podcast> podcasts ;
    private ArrayList<Artist> artists;
    private Dao songsDatabase;
    private Dao podcastsDatabase;
    private Dao artistsDatabase;


    private void renderChoices() {
        System.out.println("enter a keyword to search or press - to go back\n");
    }
    //TODO remove casts when the dao class is ready

    @Override
    public State update(State state) {
        clearScreen();
        renderChoices();

        Scanner scanner = new Scanner(System.in);
        input = scanner.nextLine();
        boolean songsFound = false;
        boolean podcastsFound = false;
        boolean artistsFound = false;

        if (!input.equals("-")){

            try {
                songs = (ArrayList<Song>) songsDatabase.get(input).orElseThrow();
                songsFound = true;
            }catch (NoSuchElementException e){
                System.out.println(ANSI_RED +"No song matches found"+ ANSI_RESET);
            }
            try {
                podcasts = (ArrayList<Podcast>) podcastsDatabase.get(input).orElseThrow();
                podcastsFound = true;
            }catch (NoSuchElementException e){
                System.out.println(ANSI_RED +"No podcast matches found"+ ANSI_RESET);
            }
            try {
                artists = (ArrayList<Artist>) artistsDatabase.get(input).orElseThrow();
                artistsFound = true;
            }catch (NoSuchElementException e){
                System.out.println(ANSI_RED +"No artist matches found"+ ANSI_RESET);
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
        else{
            navigationManager.previousState();
        }

        return state;
    }
}
