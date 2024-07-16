package businesslogic.customer;

import businesslogic.utility.ConfigOptions;
import businesslogic.utility.Handler;
import businesslogic.utility.State;
import dao.PodcastDAO;
import dao.PodcastPlaylistDAO;
import dao.SongDAO;
import dao.SongPlaylistDAO;
import domainmodel.entities.playlist.PodcastPlaylist;
import domainmodel.entities.playlist.SongPlaylist;
import domainmodel.entities.track.Podcast;
import domainmodel.entities.track.Song;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Scanner;

public class PlaylistUpdateHandler extends Handler {


    public PlaylistUpdateHandler(SongDAO songData, PodcastDAO podcastData, SongPlaylistDAO soPlayData, PodcastPlaylistDAO poPlayData) {
        this.songData = songData;
        this.podcastData = podcastData;
        this.soPlayData = soPlayData;
        this.poPlayData = poPlayData;
    }

    SongDAO songData;
    PodcastDAO podcastData;
    SongPlaylistDAO soPlayData;
    PodcastPlaylistDAO poPlayData;



    private void renderChoices() {

        System.out.println("1: add a track");
        System.out.println("2: remove a track");
        System.out.println("3: go back");
        System.out.println("4: close Swetify");
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


        if (state.getSelectedPlaylist() instanceof SongPlaylist) {

            switch (option) {
                case 1:
                    System.out.println("Type the name of the song you want to add");
                    System.out.println("\n");

                    ((SongPlaylist)(state.getSelectedPlaylist())).addSong(askNewSong(state));
                    soPlayData.update((SongPlaylist) state.getSelectedPlaylist());
                    break;
                case 2:
                    System.out.println("Type the number of the song you want to remove");
                    System.out.println("\n");
                    int removedSong = askNumberInRange(1,size)-1;
                    state.getSelectedPlaylist().removeTrack(removedSong);
                    soPlayData.update((SongPlaylist) state.getSelectedPlaylist());
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
                    poPlayData.update((PodcastPlaylist) state.getSelectedPlaylist());

                    break;
                case 2:
                    System.out.println("Type the number of the song you want to remove");
                    System.out.println("\n");
                    int removedSong = askNumberInRange(1,size)-1;
                    state.getSelectedPlaylist().removeTrack(removedSong);
                    poPlayData.update((PodcastPlaylist) state.getSelectedPlaylist());
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
