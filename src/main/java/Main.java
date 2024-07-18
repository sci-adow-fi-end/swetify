import businesslogic.utility.ConfigOptions;
import businesslogic.utility.NavigationManager;
import dao.tracks.SongDAO;
import dao.users.ArtistDAO;
import dao.users.CustomerDAO;
import domainmodel.entities.tracks.Song;
import domainmodel.entities.users.Artist;
import domainmodel.entities.users.Customer;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        SongDAO songDao = new SongDAO();
        ArtistDAO artistDao = new ArtistDAO();
        CustomerDAO customerDao = new CustomerDAO();
        Customer customer = new Customer("stio", "stio");
        customerDao.save(customer);
        Artist artist = new Artist("Stio", "Stio", "StioNelCHill", "no biography for you, asshole");
        artistDao.save(artist);
        List<Artist> authors = new ArrayList<>();
        authors.add(artist);
        Song song = new Song("Stio", "KPop", 1, 10, authors);
        songDao.save(song);

        ConfigOptions.TEST_MODE = false;
        NavigationManager nm = new NavigationManager();
    }
}
