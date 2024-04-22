package businesslogic;

import java.util.ArrayList;
import java.util.Optional;

import dao.Dao;
import domainmodel.Song;

public class SearchHandler extends Handler {

    private String input = "";
    private Optional songs;
    private Dao database;

    @Override
    protected void pullData() {
        songs = database.get(input);
    }

    @Override
    protected void render() {

    }

    @Override
    protected void handleInput() {
    }
}
