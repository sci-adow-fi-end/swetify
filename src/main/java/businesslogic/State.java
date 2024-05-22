package businesslogic;

import domainmodel.*;

public class State {

    private User loggedUser;
    private Playlist<? extends Track> selectedPlaylist;
    private Track selectedTrack;


    private final PlaybackQueue queue = new PlaybackQueue();


    private Track playingTrack;


    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public Track getSelectedTrack() {
        return selectedTrack;
    }

    public void setSelectedTrack(Song selectedTrack) {
        this.selectedTrack = selectedTrack;
    }

    public Playlist<? extends Track> getSelectedPlaylist() {
        return selectedPlaylist;
    }

    public void setSelectedPlaylist(Playlist<? extends Track> selectedPlaylist) {
        this.selectedPlaylist = selectedPlaylist;
    }

    public void addOnTop(Track t){
        queue.addTrackOnTop(t);
    }


    public Track getPlayingTrack() {
        return playingTrack;
    }

    public void setPlayingTrack(Track playingTrack) {
        this.playingTrack = playingTrack;
    }

    public PlaybackQueue getQueue() {
        return queue;
    }

}
