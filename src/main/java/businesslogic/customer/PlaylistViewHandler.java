package businesslogic.customer;

import businesslogic.utility.Handler;
import businesslogic.utility.NavigationManager;
import businesslogic.utility.Session;
import dao.collections.PodcastPlaylistDAO;
import dao.collections.SongPlaylistDAO;
import domainmodel.entities.tracks.Track;

public class PlaylistViewHandler extends Handler {

    private final SongPlaylistDAO spDAO;
    private final PodcastPlaylistDAO ppDAO;

    public PlaylistViewHandler(SongPlaylistDAO spDAO, PodcastPlaylistDAO ppDAO) {
        this.spDAO = spDAO;
        this.ppDAO = ppDAO;
    }

    private void renderChoices() {

        System.out.println("1: play a song from the playlist");
        System.out.println("2: play entire playlist");
        System.out.println("3: modify the playlist");
        System.out.println("4: go back");
        System.out.println("5: close Swetify");
    }

    private Track chooseTrack(Session s) {
        System.out.println("Insert the number of the song you want to play");
        int size = s.getSelectedPlaylist().getTracks().size();
        return s.getSelectedPlaylist().getTracks().get(askNumberInRange(1,size)-1);
    }

    @Override
    public Session update(Session session) {
        clearScreen();
        renderChoices();
        int navigationOption = askNumberInRange(1, 6);

        switch (navigationOption) {
            case 1:
                session.addOnTop(chooseTrack(session));
                break;
            case 2:
                for (Track t : session.getSelectedPlaylist().getTracks().reversed()) {
                    session.getQueue().addTrackOnTop(t);
                }
                break;
            case 3:
                navigationManager.switchToController(NavigationManager.HandlerId.UPDATE_PLAYLIST);
                break;
            case 4:
                navigationManager.previousState();
                break;
            case 5:
                navigationManager.stop();
                break;

            default:
                printError("inserted option not valid");

        }

        return session;
    }
}
