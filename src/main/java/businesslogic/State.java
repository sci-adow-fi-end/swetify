package businesslogic;

import domainmodel.Playlist;
import domainmodel.Song;
import domainmodel.Track;
import domainmodel.User;

public class State {

    private User loggedUser;
    private Playlist<Track> nextPlaylist;
    private Song nextSong;


    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public Song getNextSong() {
        return nextSong;
    }

    public void setNextSong(Song nextSong) {
        this.nextSong = nextSong;
    }

    public Playlist<Track> getNextPlaylist() {
        return nextPlaylist;
    }

    public void setNextPlaylist(Playlist<Track> nextPlaylist) {
        this.nextPlaylist = nextPlaylist;
    }



}
