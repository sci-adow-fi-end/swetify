package businesslogic;


import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import domainmodel.User;

public class NavigationManager {


    enum HandlerId{
        LOGIN,
        REGISTRATION,
        HOME,
        SEARCH,
        VIEW_PLAYLIST,
        VIEW_ARTIST,
        VIEW_TRACK,
        VIEW_SUGGESTIONS

    }
    private final Map<HandlerId, Handler> controllers;
    private final Stack<Handler> states;
    private State currentState;

    public NavigationManager() {
        this.controllers = new HashMap<>();
        controllers.put(HandlerId.LOGIN, new LoginHandler());
        controllers.put(HandlerId.REGISTRATION, new RegistrationHandler());
        controllers.put(HandlerId.HOME, new HomeHandler());
        controllers.put(HandlerId.SEARCH, new SearchHandler());
        controllers.put(HandlerId.VIEW_PLAYLIST, new PlaylistHandler());
        controllers.put(HandlerId.VIEW_SUGGESTIONS, new SuggestionsHandler());
        //TODO stiò


        states = new Stack<>();
        switchToController(HandlerId.LOGIN);
    }


    void previousState() {
        states.pop();
        states.peek().update();
    }


    void switchToController(HandlerId id) {
        if (controllers.containsKey(id)) {
            states.push(controllers.get(id));
            states.peek().update();
        } else {
            throw new IllegalArgumentException("gne gne il controller non c'è");
        }
    }

}
