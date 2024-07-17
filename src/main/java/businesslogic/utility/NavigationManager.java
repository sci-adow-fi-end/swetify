package businesslogic.utility;


import businesslogic.artist.AlbumLoadHandler;
import businesslogic.artist.ArtistHomeHandler;
import businesslogic.artist.PodcastLoadHandler;
import businesslogic.common.LoginHandler;
import businesslogic.common.RegistrationHandler;
import businesslogic.customer.*;
import dao.BaseDAO;
import dao.collections.AlbumDAO;
import dao.collections.PodcastPlaylistDAO;
import dao.collections.SongPlaylistDAO;
import dao.suggestions.SuggestionDAO;
import dao.tracks.PodcastDAO;
import dao.tracks.SongDAO;
import dao.users.ArtistDAO;
import dao.users.CustomerDAO;

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
        VIEW_ALL_PLAYLISTS,
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
        ALBUM,
        SUGGESTIONS
    }

    private final Map<HandlerId, Handler> handlers;
    private final Stack<Handler> states;
    private final Map<DaoId, BaseDAO<?>> databases;
    private State currentState = new State();
    private int lastId = 0;

    public NavigationManager() {

        databases = new HashMap<>();
        databases.put(DaoId.USER, new CustomerDAO());
        databases.put(DaoId.PODCAST, new PodcastDAO());
        databases.put(DaoId.SONG, new SongDAO());
        databases.put(DaoId.SONG_PLAYLIST, new SongPlaylistDAO());
        databases.put(DaoId.PODCAST_PLAYLIST, new PodcastPlaylistDAO());
        databases.put(DaoId.ARTIST, new ArtistDAO());
        databases.put(DaoId.ALBUM, new AlbumDAO());
        databases.put(DaoId.SUGGESTIONS, new SuggestionDAO());

        this.handlers = new HashMap<>();
        handlers.put(HandlerId.LOGIN, new LoginHandler((CustomerDAO) databases.get(DaoId.USER),
                (ArtistDAO) databases.get(DaoId.ARTIST)));
        handlers.put(HandlerId.REGISTRATION, new RegistrationHandler((CustomerDAO) databases.get(DaoId.USER),
                (ArtistDAO) databases.get(DaoId.ARTIST)));
        handlers.put(HandlerId.HOME, new HomeHandler());
        handlers.put(HandlerId.SEARCH, new SearchHandler((SongDAO) databases.get(DaoId.SONG),
                (PodcastDAO) databases.get(DaoId.PODCAST), (ArtistDAO) databases.get(DaoId.ARTIST)));
        handlers.put(HandlerId.VIEW_PLAYLIST, new PlaylistViewHandler((SongPlaylistDAO) databases.get(DaoId.SONG_PLAYLIST)
                , (PodcastPlaylistDAO) databases.get(DaoId.PODCAST_PLAYLIST)));
        handlers.put(HandlerId.VIEW_ALL_PLAYLISTS, new UserPlaylistsHandler((SongPlaylistDAO) databases.get(DaoId.SONG_PLAYLIST)
                , (PodcastPlaylistDAO) databases.get(DaoId.PODCAST_PLAYLIST)));
        handlers.put(HandlerId.VIEW_SUGGESTIONS, new SuggestionsHandler((SuggestionDAO) databases.get(DaoId.SUGGESTIONS)));
        handlers.put(HandlerId.PLAY_TRACK, new PlaybackHandler());
        handlers.put(HandlerId.VIEW_ARTIST, new ArtistInfoHandler((ArtistDAO) databases.get(DaoId.ARTIST),
                (CustomerDAO) databases.get(DaoId.USER)));
        handlers.put(HandlerId.VIEW_ALBUMS, new AlbumsHandler((AlbumDAO) databases.get(DaoId.ALBUM)));
        handlers.put(HandlerId.ARTIST_HOME, new ArtistHomeHandler());
        handlers.put(HandlerId.UPDATE_PLAYLIST, new PlaylistUpdateHandler((SongDAO) databases.get(DaoId.SONG), (PodcastDAO) databases.get(DaoId.PODCAST),
                (SongPlaylistDAO) databases.get(DaoId.SONG_PLAYLIST), (PodcastPlaylistDAO) databases.get(DaoId.PODCAST_PLAYLIST)));
        handlers.put(HandlerId.LOAD_ALBUM, new AlbumLoadHandler((ArtistDAO) databases.get(DaoId.ARTIST),
                (SongDAO) databases.get(DaoId.SONG), (AlbumDAO) databases.get(DaoId.ALBUM)));
        handlers.put(HandlerId.LOAD_PODCAST, new PodcastLoadHandler((ArtistDAO) databases.get(DaoId.ARTIST),
                (PodcastDAO) databases.get(DaoId.PODCAST)));

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

    public State getCurrentState() {
        return currentState;
    }

}
