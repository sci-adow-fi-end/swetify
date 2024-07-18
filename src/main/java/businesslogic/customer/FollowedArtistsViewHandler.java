package businesslogic.customer;

import businesslogic.utility.Handler;
import businesslogic.utility.NavigationManager;
import businesslogic.utility.Session;
import dao.tracks.PodcastDAO;
import dao.tracks.SongDAO;
import domainmodel.entities.tracks.Podcast;
import domainmodel.entities.tracks.Song;
import domainmodel.entities.tracks.Track;
import domainmodel.entities.users.Artist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FollowedArtistsViewHandler extends Handler {

    SongDAO songsDAO;
    PodcastDAO podcastsDAO;

    public FollowedArtistsViewHandler(SongDAO sd, PodcastDAO pd) {
        songsDAO = sd;
        podcastsDAO = pd;
    }

    private void renderChoices() {
        System.out.println("1: View followed artists releases");
        System.out.println("2: Exit");
        System.out.println("\n");
    }

    boolean printReleases(Session s) {
        List<Podcast> podcasts;
        List<Song> songs;
        HashMap<Integer, Track> bigMap = new HashMap<>();


        int i = 1;

        for (Artist artist : s.getLoggedUser().getFollowedArtists()) {
            podcasts = podcastsDAO.getByArtist(artist);
            songs = songsDAO.getByArtist(artist);
            System.out.println(artist.getStageName() + ":");
            System.out.println();

            System.out.println("Songs:");

            for (Song song : songs) {
                System.out.println(i + ") " + song);
                bigMap.put(i, song);
                i++;
            }
            System.out.println();

            System.out.println("Songs:");
            for (Podcast podcast : podcasts) {
                System.out.println(i + ") " + podcast);
                bigMap.put(i, podcast);
                i++;
            }
            System.out.println("\n");

        }

        if (i > 1) {
            System.out.println("insert the number of a track to play it or the number of an artist to view his info");
            int choice = askNumberInRange(1, i);
            if (bigMap.containsKey(choice)) {
                s.setSelectedTrack(bigMap.get(choice));
                return true;
            } else {
                printError("value out of range");
                return false;
            }

        } else {
            printError("no results found");
            return false;
        }
    }


    public Session update(Session s) {

        clearScreen();
        renderChoices();

        int navigationChoice = askNumberInRange(1, 2);
        switch (navigationChoice) {
            case 1:
                boolean isATrackSelected = printReleases(s);
                if (isATrackSelected) {
                    navigationManager.switchToController(NavigationManager.HandlerId.PLAY_TRACK);
                }
                break;
            case 2:
                navigationManager.previousState();
                break;
        }


        return s;
    }

}
