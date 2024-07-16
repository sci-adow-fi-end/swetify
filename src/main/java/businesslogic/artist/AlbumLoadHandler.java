package businesslogic.artist;

import businesslogic.utility.ConfigOptions;
import businesslogic.utility.Handler;
import businesslogic.utility.State;
import dao.AlbumDao;
import dao.ArtistDao;
import dao.SongDao;
import domainmodel.entities.Album;
import domainmodel.entities.Artist;
import domainmodel.entities.track.Song;
import jakarta.persistence.NoResultException;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AlbumLoadHandler extends Handler {

    ArtistDao artistData;
    SongDao songData;

    public AlbumLoadHandler(ArtistDao artistData, SongDao songData, AlbumDao albumData) {
        this.artistData = artistData;
        this.songData = songData;
        this.albumData = albumData;
    }

    AlbumDao albumData;

    private void renderChoices() {
        System.out.println("1: Load an Album");
        System.out.println("2: exit");
        System.out.println("\n");
    }

    private String askSongName(){
        Scanner input = new Scanner(System.in);
        System.out.println("Insert song name");
        System.out.println("\n");
        String sn = input.nextLine();
        if (ConfigOptions.TEST_MODE) {
            String nextInput = getRestOfInput(input);
            System.setIn(new ByteArrayInputStream(nextInput.getBytes()));
        }
        return sn;
    }

    private String askSongLyrics(){
        Scanner input = new Scanner(System.in);
        System.out.println("Insert song lyrics");
        System.out.println("\n");
        String ly = input.nextLine();
        if (ConfigOptions.TEST_MODE) {
            String nextInput = getRestOfInput(input);
            System.setIn(new ByteArrayInputStream(nextInput.getBytes()));
        }
        return ly;
    }


    private List<Artist> askAuthors(State s){
        List<Artist> artists= new ArrayList<>();
        Scanner input = new Scanner(System.in);
        artists.add(s.getLoggedArtist());
        boolean insertionEnded = false;
        while(!insertionEnded){
            System.out.println("1: Add Collaboration");
            System.out.println("2: exit");
            insertionEnded = askNumberInRange(1,2)==2;
            if(!insertionEnded) {
                System.out.println("\n");
                System.out.println("Insert artist name");
                try {
                    artists.add(artistData.getByName(input.nextLine()));
                } catch (NoResultException e) {
                    printError("Artist not found");
                }
                if (ConfigOptions.TEST_MODE) {
                    String nextInput = getRestOfInput(input);
                    System.setIn(new ByteArrayInputStream(nextInput.getBytes()));
                }
            }
        }
        return artists;
    }

    private Song createSong(State s){

        String title = askSongName();
        String lyrics = askSongLyrics();
        System.out.println("Insert duration (minutes)");
        int minutes = askNumberInRange(0, Integer.MAX_VALUE);
        System.out.println("Insert duration (seconds)");
        int seconds = askNumberInRange(0, Integer.MAX_VALUE);
        List<Artist> authors = askAuthors(s);

        Song ns = new Song(title,lyrics,minutes,seconds,authors);
        songData.save(ns);
        return ns;
    }

    private Album createAlbum(State s){
        List<Song> songs= new ArrayList<>();
        String name;
        Scanner input = new Scanner(System.in);
        boolean insertionEnded = false;
        System.out.println("Insert album name");
        name = input.nextLine();

        if (ConfigOptions.TEST_MODE) {
            String nextInput = getRestOfInput(input);
            System.setIn(new ByteArrayInputStream(nextInput.getBytes()));
        }
        System.out.println("\n");
        Album na = new Album();
        na.getPlaylist().setTitle(name);
        albumData.save(na);
        while(!insertionEnded){
            System.out.println("1: Add Song");
            System.out.println("2: exit");
            insertionEnded = askNumberInRange(1,2)==2;
            songs.add(createSong(s));
        }
        na.getPlaylist().setTracks(songs);
        albumData.update(na);
        return na;
    }


    public State update(State s) {

        clearScreen();
        renderChoices();
        int navOpt = askNumberInRange(1,2);
        switch (navOpt){
            case 1:
                s.getLoggedArtist().addAlbum(createAlbum(s));
                artistData.update(s.getLoggedArtist());
                break;
            case 2:
                navigationManager.previousState();
                break;
            default:
                printError("option not valid");
        }
        return s;
    }
}
