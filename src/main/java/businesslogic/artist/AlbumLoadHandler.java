package businesslogic.artist;

import businesslogic.utility.ConfigOptions;
import businesslogic.utility.Handler;
import businesslogic.utility.Session;
import dao.collections.AlbumDAO;
import dao.tracks.SongDAO;
import dao.users.ArtistDAO;
import domainmodel.entities.collections.Album;
import domainmodel.entities.tracks.Song;
import domainmodel.entities.users.Artist;
import jakarta.persistence.NoResultException;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AlbumLoadHandler extends Handler {

    private final ArtistDAO artistData;
    private final SongDAO songData;
    private final AlbumDAO albumData;

    public AlbumLoadHandler(ArtistDAO artistData, SongDAO songData, AlbumDAO albumData) {
        this.artistData = artistData;
        this.songData = songData;
        this.albumData = albumData;
    }

    private void renderChoices() {
        System.out.println("1: Load an Album");
        System.out.println("2: Go back");
        System.out.println("3: Close Swetify");
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


    private List<Artist> askAuthors(Session s) {
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
                    artists.addAll(artistData.getByStageName(input.nextLine()));
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

    private Song createSong(Session s) {

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

    private void createAlbum(Session s) {
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
        while(!insertionEnded){
            System.out.println("1: Add Song");
            System.out.println("2: Exit");
            insertionEnded = askNumberInRange(1,2)==2;
            if(!insertionEnded){
            songs.add(createSong(s));
            }
        }
        Album na = new Album(name, songs, s.getLoggedArtist());
        albumData.save(na);
    }


    public Session update(Session s) {

        clearScreen();
        renderChoices();
        int navOpt = askNumberInRange(1,3);
        switch (navOpt){
            case 1:
                createAlbum(s);
                break;
            case 2:
                navigationManager.previousState();
                break;
            case 3:
                navigationManager.stop();
                break;
            default:
                printError("option not valid");
        }
        return s;
    }
}
