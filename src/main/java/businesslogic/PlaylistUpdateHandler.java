package businesslogic;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import java.util.ArrayList;

import dao.ArtistDao;
import dao.PodcastDao;
import dao.SongDao;
import domainmodel.*;

public class PlaylistUpdateHandler extends Handler {


    SongDao songData;
    PodcastDao podcastData;

    private List<Song> songs = new ArrayList<>();
    private List<Podcast> podcasts = new ArrayList<>();
    private List<Artist> artists = new ArrayList<>();
    private final SongDao songsDatabase;
    private final PodcastDao podcastsDatabase;
    private final ArtistDao artistsDatabase;


    private void renderChoices() {

        System.out.println("1: add a track");
        System.out.println("2: remove a track");
        System.out.println("3: go back");
        System.out.println("4: close Swetify");
    }

    public PlaylistUpdateHandler(SongDao songsDatabase, PodcastDao podcastsDatabase, ArtistDao artistsDatabase) {
        this.songsDatabase = songsDatabase;
        this.podcastsDatabase = podcastsDatabase;
        this.artistsDatabase = artistsDatabase;
    }


    private Song askNewSong(State s){
        Scanner input = new Scanner(System.in);
        System.out.println("Insert song name");
        System.out.println("\n");
        List<Song> songs = songData.getAllByName(input.nextLine());
        if (ConfigOptions.TEST_MODE) {
            String nextInput = getRestOfInput(input);
            System.setIn(new ByteArrayInputStream(nextInput.getBytes()));
        }
        int i=1;
        for (Song song : songs){
            System.out.println(i+" "+song.getTitle());
            System.out.println("\n");
            i++;
        }
        if (songs.isEmpty()) {
            printError("Keyword not found");
        }
        int selectedSong = askNumberInRange(1,i);
        return songs.get(selectedSong-1);
    }

    private Podcast askNewPodcast(State s){
        Scanner input = new Scanner(System.in);
        System.out.println("Insert podcast name");
        System.out.println("\n");
        List<Podcast> podcasts = podcastData.getAllByName(input.nextLine());
            if (ConfigOptions.TEST_MODE) {
                String nextInput = getRestOfInput(input);
                System.setIn(new ByteArrayInputStream(nextInput.getBytes()));
            }
        int i=1;
        for (Podcast podcast : podcasts){
            System.out.println(i+" "+podcast.getTitle());
            System.out.println("\n");
            i++;
        }
        if (podcasts.isEmpty()) {
            printError("Keyword not found");
        }
        int selectedPodcast = askNumberInRange(1,i);
        return podcasts.get(selectedPodcast-1);
    }


    @Override
    public State update(State state) {
        clearScreen();
        int size = state.getSelectedPlaylist().print();
        renderChoices();
        int option= askNumberInRange(1,4);


        if (state.getSelectedPlaylist() instanceof  SongPlaylist){

            switch (option) {
                case 1:
                    System.out.println("Type the name of the song you want to add");
                    System.out.println("\n");

                    ((SongPlaylist)(state.getSelectedPlaylist())).addSong(askNewSong(state));


                    break;
                case 2:
                    System.out.println("Type the number of the song you want to remove");
                    System.out.println("\n");
                    int removedSong = askNumberInRange(1,size)-1;
                    state.getSelectedPlaylist().removeTrack(removedSong);
                    break;
                case 3:
                    navigationManager.previousState();
                    break;
                case 4:
                    navigationManager.stop();
                    break;
            }
        }
        else {
            switch (option) {
                case 1:
                    System.out.println("Type the name of the song you want to add");
                    System.out.println("\n");

                    ((PodcastPlaylist)(state.getSelectedPlaylist())).addPodcast(askNewPodcast(state));

                    break;
                case 2:
                    System.out.println("Type the number of the song you want to remove");
                    System.out.println("\n");
                    int removedSong = askNumberInRange(1,size)-1;
                    state.getSelectedPlaylist().removeTrack(removedSong);
                    break;
                case 3:
                    navigationManager.previousState();
                    break;
                case 4:
                    navigationManager.stop();
                    break;
            }

        }
        return state;
    }
}
