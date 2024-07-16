package businesslogic.customer;

import businesslogic.utility.Handler;
import businesslogic.utility.NavigationManager;
import businesslogic.utility.State;
import dao.PodcastPlaylistDao;
import dao.SongPlaylistDao;
import domainmodel.entities.playlist.PodcastPlaylist;
import domainmodel.entities.playlist.SongPlaylist;
import domainmodel.entities.track.Track;

import java.util.Scanner;

public class PlaylistViewHandler extends Handler {

    public PlaylistViewHandler(SongPlaylistDao spDAO, PodcastPlaylistDao ppDAO) {
        this.spDAO = spDAO;
        this.ppDAO = ppDAO;
    }

    SongPlaylistDao spDAO;
    PodcastPlaylistDao ppDAO;

    private void renderChoices() {

        System.out.println("1: play a song from the playlist");
        System.out.println("2: play entire playlist");
        System.out.println("3: modify the playlist");
        System.out.println("4: create a new playlist");
        System.out.println("5: go back");
        System.out.println("6: close Swetify");
    }

    private String askPlaylistName(){
        Scanner input = new Scanner(System.in);
        System.out.println("Insert playlist name");
        System.out.println("\n");
        return input.nextLine();
    }

    private SongPlaylist createSongPlaylist(){
        SongPlaylist sp = new SongPlaylist();
        sp.setTitle(askPlaylistName());
        spDAO.save(sp);
        return sp;
    }

    private PodcastPlaylist createPodcastPlaylist(){
        PodcastPlaylist pp = new PodcastPlaylist();
        pp.setTitle(askPlaylistName());
        ppDAO.save(pp);
        return pp;
    }

    private void savePlaylist(){
        System.out.println("1: create a playlist of songs");
        System.out.println("2: create a playlist of podcast");
        System.out.println("3: go back");
        System.out.println("\n");
        int choice = askNumberInRange(1,3);
        switch (choice) {
            case 1:
                createSongPlaylist();
                break;
            case 2:
                createPodcastPlaylist();
                break;
            case 3:
                navigationManager.switchToController(NavigationManager.HandlerId.VIEW_PLAYLIST);
                break;
            default:
                navigationManager.switchToController(NavigationManager.HandlerId.VIEW_PLAYLIST);
        }
    }

    private Track chooseTrack(State s){
        System.out.println("Insert the number of the song you want to play");
        int size = s.getSelectedPlaylist().getTracks().size();
        return s.getSelectedPlaylist().getTracks().get(askNumberInRange(1,size)-1);
    }

    @Override
    public State update(State state) {
        clearScreen();
        renderChoices();
        int navigationOption = askNumberInRange(1, 6);

        switch (navigationOption) {
            case 1:
                state.addOnTop(chooseTrack(state));
                break;
            case 2:
                for(Track t:state.getSelectedPlaylist().getTracks().reversed()){
                    state.getQueue().addTrackOnTop(t);
                }
                break;
            case 3:
                navigationManager.switchToController(NavigationManager.HandlerId.UPDATE_PLAYLIST);
                break;
            case 4:
                savePlaylist();
                break;
            case 5:
                navigationManager.previousState();
                break;
            case 6:
                navigationManager.stop();
                break;

            default:
                printError("inserted option not valid");

        }

        return state;
    }
}
