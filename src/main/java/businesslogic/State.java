package businesslogic;

import domainmodel.*;

public class State {

    private User loggedUser;
    private Playlist<? extends Track> selectedPlaylist;
    private Song selectedSong;
    private final PlaybackQueue queue = new PlaybackQueue();


    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public Song getSelectedSong() {
        return selectedSong;
    }

    public void setSelectedSong(Song selectedSong) {
        this.selectedSong = selectedSong;
    }

    public Playlist<? extends Track> getSelectedPlaylist() {
        return selectedPlaylist;
    }

    public void setSelectedPlaylist(Playlist<? extends Track> selectedPlaylist) {
        this.selectedPlaylist = selectedPlaylist;
    }

    public void addOnTop(Track t){
        queue.addTrack(t);
    }



}
