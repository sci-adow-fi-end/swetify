package businesslogic.customer;

import businesslogic.utility.ConfigOptions;
import businesslogic.utility.Handler;
import businesslogic.utility.Session;
import dao.collections.PodcastPlaylistDAO;
import dao.collections.SongPlaylistDAO;
import dao.tracks.PodcastDAO;
import dao.tracks.SongDAO;
import domainmodel.entities.collections.PodcastPlaylist;
import domainmodel.entities.collections.SongPlaylist;
import domainmodel.entities.tracks.Podcast;
import domainmodel.entities.tracks.Song;

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


    private Song askNewSong(Session s) {
        Scanner input = new Scanner(System.in);
        System.out.println("Insert song name");
        System.out.println("\n");
        List<Song> songs = songData.getByTitle(input.nextLine());
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

    private Podcast askNewPodcast(Session s) {
        Scanner input = new Scanner(System.in);
        System.out.println("Insert podcast name");
        System.out.println("\n");
        List<Podcast> podcasts = podcastData.getByTitle(input.nextLine());
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
    public Session update(Session session) {
        clearScreen();
        int size = session.getSelectedPlaylist().print();
        renderChoices();
        int option= askNumberInRange(1,4);


        if (session.getSelectedPlaylist() instanceof SongPlaylist) {

            switch (option) {
                case 1:
                    System.out.println("Type the name of the song you want to add");
                    System.out.println("\n");

                    ((SongPlaylist) (session.getSelectedPlaylist())).addSong(askNewSong(session));
                    soPlayData.update((SongPlaylist) session.getSelectedPlaylist());
                    break;
                case 2:
                    System.out.println("Type the number of the song you want to remove");
                    System.out.println("\n");
                    int removedSong = askNumberInRange(1,size)-1;
                    session.getSelectedPlaylist().removeTrack(removedSong);
                    soPlayData.update((SongPlaylist) session.getSelectedPlaylist());
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

                    ((PodcastPlaylist) (session.getSelectedPlaylist())).addPodcast(askNewPodcast(session));
                    poPlayData.update((PodcastPlaylist) session.getSelectedPlaylist());

                    break;
                case 2:
                    System.out.println("Type the number of the song you want to remove");
                    System.out.println("\n");
                    int removedSong = askNumberInRange(1,size)-1;
                    session.getSelectedPlaylist().removeTrack(removedSong);
                    poPlayData.update((PodcastPlaylist) session.getSelectedPlaylist());
                    break;
                case 3:
                    navigationManager.previousState();
                    break;
                case 4:
                    navigationManager.stop();
                    break;
            }

        }
        return session;
    }
}
