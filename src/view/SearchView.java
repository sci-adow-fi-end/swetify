package view;

import controller.NavigationController;
import model.Track;

import java.util.ArrayList;


public class SearchView extends View {

    private String inputBuffer;

    public enum Events{
        SEARCH_TRACK,
        LISTEN_TRACK,

        BACK
    }


    public SearchView(NavigationController controller) {
        super(controller);
    }


    @Override
    public void update() {
        //TODO pigghiati i searchresults
        //render(searchResults);
    }

    private void render(ArrayList<Track> tracks){
        System.out.println("cerca:");
        getUserInput();
        
    }
}