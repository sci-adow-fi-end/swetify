package businesslogic.customer;

import businesslogic.utility.Handler;
import businesslogic.utility.NavigationManager;
import businesslogic.utility.Session;
import dao.collections.PodcastPlaylistDAO;
import dao.collections.SongPlaylistDAO;
import domainmodel.entities.collections.Playlist;
import domainmodel.entities.collections.PodcastPlaylist;
import domainmodel.entities.collections.SongPlaylist;
import domainmodel.entities.tracks.Podcast;
import domainmodel.entities.tracks.Song;

import java.util.List;
import java.util.Scanner;

public class UserPlaylistsHandler extends Handler {

    private List<SongPlaylist> songPlaylists;
    private List<PodcastPlaylist> podcastsPlaylists;
    private final SongPlaylistDAO spDAO;
    private final PodcastPlaylistDAO ppDAO;

    public UserPlaylistsHandler(SongPlaylistDAO spDAO, PodcastPlaylistDAO ppDAO) {
        this.spDAO = spDAO;
        this.ppDAO = ppDAO;
    }

    private void renderChoices() {
        System.out.println("1: view your playlists");
        System.out.println("2: create a new playlist");
        System.out.println("3: go back");
        System.out.println("4: close Swetify");
        System.out.println("\n");
    }

    private int renderSavedPlaylists(Session session) {
        clearScreen();

        songPlaylists = spDAO.getByCustomer(session.getLoggedUser());
        podcastsPlaylists = ppDAO.getByCustomer(session.getLoggedUser());

        int index = 1;
        System.out.println("Song playlists:");
        for (Playlist<Song> sp : songPlaylists) {
            System.out.println(index + ") " + sp.getTitle());
            index++;
        }
        System.out.println("\n");

        System.out.println("Podcast playlists:");
        for (Playlist<Podcast> pp : podcastsPlaylists) {
            System.out.println(index + ") " + pp.getTitle());
            index++;
        }
        System.out.println("\n");
        System.out.println("Type the number of the playlist you want to view");

        return index;
    }

    private String askPlaylistName() {
        Scanner input = new Scanner(System.in);
        System.out.println("Insert playlist name");
        System.out.println("\n");
        return input.nextLine();
    }

    private SongPlaylist createSongPlaylist() {
        SongPlaylist sp = new SongPlaylist();
        sp.setTitle(askPlaylistName());
        spDAO.save(sp);
        return sp;
    }

    private PodcastPlaylist createPodcastPlaylist() {
        PodcastPlaylist pp = new PodcastPlaylist();
        pp.setTitle(askPlaylistName());
        ppDAO.save(pp);
        return pp;
    }

    private void savePlaylist() {
        System.out.println("1: create a playlist of songs");
        System.out.println("2: create a playlist of podcast");
        System.out.println("\n");
        int choice = askNumberInRange(1, 3);
        switch (choice) {
            case 1:
                createSongPlaylist();
                break;
            case 2:
                createPodcastPlaylist();
                break;
            default:
                printError("Inserted option not valid");
        }
    }

    @Override
    public Session update(Session session) {
        clearScreen();
        renderChoices();
        int choice = askNumberInRange(1, 4);

        switch (choice) {
            case 1:
                int index = renderSavedPlaylists(session);
                int navigationChoice = askNumberInRange(1, index - 1);

                if (navigationChoice <= songPlaylists.size()) {
                    session.setSelectedPlaylist(songPlaylists.get(index - 1));
                    navigationManager.switchToController(NavigationManager.HandlerId.VIEW_PLAYLIST);
                } else if (navigationChoice <= songPlaylists.size() + podcastsPlaylists.size()) {
                    session.setSelectedPlaylist(podcastsPlaylists.get(index - 1 - songPlaylists.size()));
                    navigationManager.switchToController(NavigationManager.HandlerId.VIEW_PLAYLIST);
                }
                break;
            case 2:
                savePlaylist();
                break;
            case 3:
                navigationManager.previousState();
                break;
            case 4:
                navigationManager.stop();
                break;
            default:
                printError("Choice is not valid");
        }
        return session;
    }

}
