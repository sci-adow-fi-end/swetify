package businesslogic.utility;


import businesslogic.artist.AlbumLoadHandler;
import businesslogic.artist.ArtistHomeHandler;
import businesslogic.artist.PodcastLoadHandler;
import businesslogic.common.LoginHandler;
import businesslogic.common.RegistrationHandler;
import businesslogic.customer.*;
import dao.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class NavigationManager {


    public enum HandlerId {
        LOGIN,
        REGISTRATION,
        HOME,
        ARTIST_HOME,
        SEARCH,
        VIEW_PLAYLIST,
        UPDATE_PLAYLIST,
        VIEW_ARTIST,
        VIEW_TRACK,
        VIEW_SUGGESTIONS,
        PLAY_TRACK,
        VIEW_ALBUMS,
        LOAD_ALBUM,
        LOAD_PODCAST
    }

    public enum DaoId {
        USER,
        PODCAST,
        SONG,
        PODCAST_PLAYLIST,
        SONG_PLAYLIST,
        ARTIST,
        ALBUM
    }

    private final Map<HandlerId, Handler> handlers;
    private final Stack<Handler> states;
    private final Map<DaoId, BaseDao<?>> databases;
    private State currentState = new State();
    private int lastId = 0;

    public NavigationManager() {

        databases = new HashMap<>();
        databases.put(DaoId.USER, new UserDao());
        databases.put(DaoId.PODCAST, new PodcastDao());
        databases.put(DaoId.SONG, new SongDao());
        databases.put(DaoId.SONG_PLAYLIST, new SongPlaylistDao());
        databases.put(DaoId.PODCAST_PLAYLIST, new PodcastPlaylistDao());
        databases.put(DaoId.ARTIST, new ArtistDao());
        databases.put(DaoId.ALBUM, new AlbumDao());

        this.handlers = new HashMap<>();
        handlers.put(HandlerId.LOGIN, new LoginHandler((UserDao) databases.get(DaoId.USER),
                (ArtistDao) databases.get(DaoId.ARTIST)));
        handlers.put(HandlerId.REGISTRATION, new RegistrationHandler((UserDao) databases.get(DaoId.USER),
                (ArtistDao) databases.get(DaoId.ARTIST)));
        handlers.put(HandlerId.HOME, new HomeHandler());
        handlers.put(HandlerId.SEARCH, new SearchHandler((SongDao) databases.get(DaoId.SONG),
                (PodcastDao) databases.get(DaoId.PODCAST), (ArtistDao) databases.get(DaoId.ARTIST)));
        handlers.put(HandlerId.VIEW_PLAYLIST, new PlaylistViewHandler((SongPlaylistDao) databases.get(DaoId.SONG_PLAYLIST)
                ,(PodcastPlaylistDao) databases.get(DaoId.PODCAST_PLAYLIST)));
        handlers.put(HandlerId.VIEW_SUGGESTIONS, new SuggestionsHandler());
        handlers.put(HandlerId.PLAY_TRACK, new PlaybackHandler());
        handlers.put(HandlerId.VIEW_ARTIST, new ArtistInfoHandler());
        handlers.put(HandlerId.VIEW_ALBUMS, new AlbumsHandler());
        handlers.put(HandlerId.ARTIST_HOME, new ArtistHomeHandler());
        handlers.put(HandlerId.UPDATE_PLAYLIST, new PlaylistUpdateHandler((SongDao) databases.get(DaoId.SONG),(PodcastDao) databases.get(DaoId.PODCAST),
                (SongPlaylistDao) databases.get(DaoId.SONG_PLAYLIST),(PodcastPlaylistDao) databases.get(DaoId.PODCAST_PLAYLIST) ));
        handlers.put(HandlerId.LOAD_ALBUM, new AlbumLoadHandler((ArtistDao) databases.get(DaoId.ARTIST),
                (SongDao) databases.get(DaoId.SONG),(AlbumDao) databases.get(DaoId.ALBUM) ));
        handlers.put(HandlerId.LOAD_PODCAST, new PodcastLoadHandler((ArtistDao) databases.get(DaoId.ARTIST),
                (PodcastDao) databases.get(DaoId.PODCAST) ));
        //TODO stiò

        states = new Stack<>();

        for (Handler h : handlers.values()) {
            h.setNavigationManager(this);
        }
        if (!ConfigOptions.TEST_MODE) {
            start();
        }

    }

    public void start() {
        switchToController(HandlerId.LOGIN);
        run();
    }


    public void previousState() {
        states.pop();
    }

    public int getCurrentHandlerId() {
        for (Map.Entry<HandlerId, Handler> entry : handlers.entrySet()) {
            if (entry.getValue().equals(states.peek()))
                return entry.getKey().ordinal();
        }
        return -1;
    }

    public void switchToController(HandlerId id) {
        if (handlers.containsKey(id)) {
            states.push(handlers.get(id));
        } else {
            throw new IllegalArgumentException("gne gne il controller non c'è");
        }
    }

    public void pushHandler(HandlerId id) {
        if (handlers.containsKey(id)) {
            states.push(handlers.get(id));
        }
    }

    public void run() {
        while (!states.empty()) {
            currentState = states.peek().update(currentState);
        }
    }


    public void stop() {
        lastId = getCurrentHandlerId();
        while (!states.empty()) {
            states.pop();
        }

        System.out.println("Swetify closed");
    }

    public int getLastId() {
        return lastId;
    }

    public BaseDao<?> getDaoById(DaoId daoId) {
        return databases.get(daoId);
    }

    public Handler getHandlerById(HandlerId handlerId) {
        return handlers.get(handlerId);
    }

    public State getCurrentState() {
        return currentState;
    }

}
