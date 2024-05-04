package businesslogic;
import java.util.Scanner;

import java.util.ArrayList;

import dao.Dao;
import domainmodel.Song;

public class SearchHandler extends Handler {

    private String input = "";
    private ArrayList<Song> songs;
    private ArrayList<Song> podcasts ;
    private ArrayList<Song> artists;
    private Dao songsDatabase;


    private void renderChoices() {
        clearScreen();
        System.out.println("enter a keyword to search or press 1 to go back\n");
    }
    //TODO remove casts when the dao class is ready

    @Override
    public void update() {

        Scanner scanner = new Scanner(System.in);
        input = scanner.nextLine();
        boolean songsFound = false;
        boolean podcastsFound = false;
        boolean artistsFound = false;

        try {
            songs = (ArrayList<Song>) songsDatabase.get(input).orElseThrow();
            songsFound = true;
        }catch (Exception e){
            System.out.println(ANSI_RED +"No matches found"+ ANSI_RESET);
        }

        if (songsFound){

            int i = 0;

            for (Song song : songs){
                System.out.println(i+") "+ song.getTitle());
            }

            //TODO add tracks and artists
        }

        return false;
    }
}
