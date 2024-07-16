package businesslogic.customer;

import businesslogic.utility.Handler;
import businesslogic.utility.State;
import dao.suggestions.SuggestionDAO;
import domainmodel.entities.tracks.Podcast;
import domainmodel.entities.tracks.Song;

import java.util.List;

public class SuggestionsHandler extends Handler {

    private final SuggestionDAO suggestionsDatabase;

    public SuggestionsHandler(SuggestionDAO suggestionsDatabase) {
        this.suggestionsDatabase = suggestionsDatabase;
    }

    private void renderChoices() {
        System.out.println("1: view suggested songs");
        System.out.println("2: view suggested podcasts");
        System.out.println("3: go back");
        System.out.println("4: close Swetify");
        System.out.println("\n");
    }

    @Override
    public State update(State state) {
        clearScreen();
        renderChoices();

        int option = askNumberInRange(1, 4);
        switch (option) {
            case 1:
                List<Song> suggestedSongs = suggestionsDatabase.getTopSongsBySimilarUsers(state.getLoggedUser());
                System.out.println("Swetify recommends you to listen the following tracks:");
                for (int i = 0; i < suggestedSongs.size(); i++) {
                    System.out.println(i + ") " + suggestedSongs.get(i).getTitle());
                }
                break;
            case 2:
                List<Podcast> suggestedPodcasts = suggestionsDatabase.getTopPodcastsBySimilarUsers(state.getLoggedUser());
                System.out.println("Swetify recommends you to listen the following podcasts:");
                for (int i = 0; i < suggestedPodcasts.size(); i++) {
                    System.out.println(i + ") " + suggestedPodcasts.get(i).getTitle());
                }
                break;
            case 3:
                navigationManager.previousState();
                break;
            case 4:
                navigationManager.stop();
                break;
        }

        return state;
    }
}
