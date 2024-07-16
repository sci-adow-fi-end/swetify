package businesslogic.utility;

import domainmodel.PlaybackQueue;
import domainmodel.entities.Artist;
import domainmodel.entities.User;
import domainmodel.entities.playlist.Playlist;
import domainmodel.entities.track.Track;

public class State {

    private User loggedUser;


    private Artist loggedArtist;
    private Playlist<? extends Track> selectedPlaylist;
    private Track selectedTrack;

    private final PlaybackQueue queue = new PlaybackQueue();

    private Track playingTrack;
    private Artist viewingArtist;

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public Artist getLoggedArtist() {
        return loggedArtist;
    }

    public void setLoggedArtist(Artist loggedArtist) {
        this.loggedArtist = loggedArtist;
    }


    public Track getSelectedTrack() {
        return selectedTrack;
    }

    //public void setSelectedTrack(Song selectedTrack) {
        //this.selectedTrack = selectedTrack;
    //}
    //replaced Song with Track in setSelectedTrack() in order to be able to add podcasts to playback queue
    public void setSelectedTrack(Track selectedTrack) {this.selectedTrack = selectedTrack;}

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

    public void setViewingArtist(Artist artist) { this.viewingArtist = artist; }

    public Artist getViewingArtist() { return this.viewingArtist; }

}
