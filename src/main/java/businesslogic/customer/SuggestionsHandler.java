package businesslogic.customer;

import businesslogic.utility.Handler;
import businesslogic.utility.State;

public class SuggestionsHandler extends Handler {


    private void renderChoices() {
        System.out.println("press the number of a song to select it or 0 to go back");
        System.out.println("\n");
    }

    @Override
    public State update(State state) {
        clearScreen();
        renderChoices();


        return state;
    }
}
