package businesslogic;

import dao.AlbumDao;
import dao.ArtistDao;
import dao.SongDao;
import domainmodel.Album;
import domainmodel.Artist;
import domainmodel.Song;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AlbumLoadHandler extends Handler{

    ArtistDao artistData;
    SongDao songData;
    AlbumDao albumData;

    private void renderChoices() {
        System.out.println("1: Load a podcast");
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
            System.out.println("\n");
            System.out.println("Insert artist name");
            artists.add(artistData.getByName(input.nextLine()));
            if (ConfigOptions.TEST_MODE) {
                String nextInput = getRestOfInput(input);
                System.setIn(new ByteArrayInputStream(nextInput.getBytes()));
            }
        }
        return artists;
    }

    private Song createSong(State s){

        Song ns = new Song(askSongName(),askSongLyrics(),askNumberInRange(0, Integer.MAX_VALUE),
                askNumberInRange(0, Integer.MAX_VALUE),askAuthors(s));
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
        System.out.println("\n");
        while(!insertionEnded){
            System.out.println("1: Add Song");
            System.out.println("2: exit");
            insertionEnded = askNumberInRange(1,2)==2;
            System.out.println("\n");
            System.out.println("Insert artist name");
            songs.add(createSong(s));
        }
        Album na = new Album(name, songs);
        albumData.save(na);
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
