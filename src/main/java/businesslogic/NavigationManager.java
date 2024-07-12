package businesslogic;


import dao.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class NavigationManager {


    public enum HandlerId{
        LOGIN,
        REGISTRATION,
        HOME,
	ARTIST_HOME,
        SEARCH,
        VIEW_PLAYLIST,
        VIEW_ARTIST,
        VIEW_TRACK,
        VIEW_SUGGESTIONS,
        PLAY_TRACK,
        VIEW_ALBUMS
    }
    public enum DaoId{
        USER,
        PODCAST,
        SONG,
        ARTIST
    }
    private final Map<HandlerId, Handler> controllers;
    private final Stack<Handler> states;
    private final Map<DaoId, Dao<?>> databases;
    private State currentState;
    private int lastId = 0;

    public NavigationManager() {

        databases = new HashMap<>();
        databases.put(DaoId.USER, new UserDao());
        databases.put(DaoId.PODCAST, new PodcastDao());
        databases.put(DaoId.SONG, new SongDao());
	    databases.put(DaoId.ARTIST, new ArtistDao());
	
        this.controllers = new HashMap<>();
        controllers.put(HandlerId.LOGIN, new LoginHandler((UserDao)databases.get(DaoId.USER),
                (ArtistDao)databases.get(DaoId.ARTIST)));
        controllers.put(HandlerId.REGISTRATION, new RegistrationHandler((UserDao)databases.get(DaoId.USER),
                (ArtistDao)databases.get(DaoId.ARTIST)));
        controllers.put(HandlerId.HOME, new HomeHandler());
        controllers.put(HandlerId.SEARCH, new SearchHandler((SongDao)databases.get(DaoId.SONG),
                (PodcastDao)databases.get(DaoId.PODCAST), (ArtistDao)databases.get(DaoId.ARTIST)));
        controllers.put(HandlerId.VIEW_PLAYLIST, new PlaylistHandler());
        controllers.put(HandlerId.VIEW_SUGGESTIONS, new SuggestionsHandler());
        controllers.put(HandlerId.PLAY_TRACK, new PlaybackHandler());
        controllers.put(HandlerId.VIEW_ARTIST, new ArtistInfoHandler());
        controllers.put(HandlerId.VIEW_ALBUMS, new AlbumsHandler());
        //TODO stiò

        states = new Stack<>();

        for (Handler h: controllers.values()){
            h.setNavigationManager(this);
        }
	if(!ConfigOptions.TEST_MODE){
	    start();
	}
	
    }

    public void start(){
        switchToController(HandlerId.LOGIN);
	run();
    }


    void previousState() {
        states.pop();
    }

    public int getCurrentHandlerId(){
        for (Map.Entry<HandlerId, Handler> entry : controllers.entrySet()){
            if (entry.getValue().equals(states.peek()))
                return entry.getKey().ordinal();
        }
        return -1;
    }

    void switchToController(HandlerId id) {
        if (controllers.containsKey(id)) {
            states.push(controllers.get(id));
        } else {
            throw new IllegalArgumentException("gne gne il controller non c'è");
        }
    }

    public void pushHandler(HandlerId id){
        if (controllers.containsKey(id)) {
            states.push(controllers.get(id));
        }
    }

    public void run(){
	while(!states.empty()){
	currentState = states.peek().update(currentState);
	}
    }
	

    public void stop(){
        lastId = getCurrentHandlerId();
        while (!states.empty()){
            states.pop();
        }

        System.out.println("Swetify closed");
    }

    public int getLastId(){
        return lastId;
    }

    public Dao<?> getDaoById(DaoId daoId){
        return databases.get(daoId);
    }

    public Handler getHandlerById(HandlerId handlerId){
        return controllers.get(handlerId);
    }

}
