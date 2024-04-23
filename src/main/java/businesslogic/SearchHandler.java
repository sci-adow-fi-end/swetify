package businesslogic;

import java.util.ArrayList;
import java.util.Optional;

import dao.Dao;
import domainmodel.Song;
import domainmodel.Track;

public class SearchHandler extends Handler {

    private String input = "";
    private ArrayList<Song> songs;
    private ArrayList<Song> podcasts ;
    private ArrayList<Song> artists;
    private Dao database;

    @Override
    protected void pullData() {
        try {
            songs = (ArrayList<Song>) database.get(input).orElseThrow();
        }catch (Exception e){
            System.out.println(ANSI_RED +"No matches found"+ ANSI_RESET);
        }
    }

    @Override
    protected void render() {
        System.out.println("enter a keyword to search or the number of a track to select it \n");
        int i = 0;
        for (Song song : songs){
            System.out.println(i+") "+ song.getTitle());
        }

    }

    @Override
    protected void handleInput() {
    }
}
