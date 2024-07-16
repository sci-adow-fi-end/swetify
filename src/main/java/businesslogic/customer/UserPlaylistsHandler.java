package businesslogic.customer;

import businesslogic.utility.Handler;
import businesslogic.utility.State;
import domainmodel.entities.collections.Playlist;
import domainmodel.entities.tracks.Podcast;
import domainmodel.entities.tracks.Song;

import java.util.ArrayList;
import java.util.Scanner;

public class UserPlaylistsHandler extends Handler {

    private ArrayList<Playlist<Song>> songPlaylists;
    private ArrayList<Playlist<Podcast>> podcastsPlaylists;

    //TODO: pigliare le playlist dal dao
    //TODO: decide how to implement PlaylistDao to handle generics

    private void renderChoices() {
        System.out.println("press the number of a playlist to select it or 0 to go back");
        System.out.println("\n");
    }

    @Override
    public State update(State state) {
        clearScreen();
        int index = 1;
        System.out.println("Song playlists:");
        for (Playlist<Song> sp : songPlaylists) {
            System.out.println(index+") "+sp.getTitle());
            index++;
        }
        System.out.println("\n");

        System.out.println("Podcast playlists:");
        for (Playlist<Podcast> pp : podcastsPlaylists) {
            System.out.println(index+") "+pp.getTitle());
            index++;
        }
        System.out.println("\n");

        renderChoices();

        boolean validNavigationChoice=false;
        int navigationChoice=-1;
        Scanner scanner = new Scanner(System.in);

        while(!validNavigationChoice) {
            validNavigationChoice = true;
            try {
                navigationChoice = scanner.nextInt();
            } catch (NumberFormatException e) {
                printError("Inserted value is not a number");
                validNavigationChoice = false;
                continue;
            }

            if (navigationChoice==0){
                navigationManager.previousState();
            }
            else if (navigationChoice <= songPlaylists.size()){
                state.setSelectedPlaylist(songPlaylists.get(index-1));
            }
            else if (navigationChoice <= songPlaylists.size()+podcastsPlaylists.size()){
                state.setSelectedPlaylist(podcastsPlaylists.get(index-1-songPlaylists.size()));
            }
            else{
                validNavigationChoice = false;
            }
        }

        return state;
    }

}
