import dao.suggestions.SuggestionDAO;
import dao.tracks.SongDAO;
import dao.users.CustomerDAO;
import domainmodel.entities.suggestions.SongPlaysCount;
import domainmodel.entities.tracks.Song;
import domainmodel.entities.users.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SuggestionDAOTest extends BaseTest {

    SuggestionDAO sud = new SuggestionDAO();
    Customer cus = new Customer();
    List<SongPlaysCount> countList = new ArrayList<>();


    class IntTuple{
        int left;
        int right;
        public IntTuple(int left, int right){
            this.left=left;
            this.right=right;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            IntTuple intTuple = (IntTuple) o;
            return left == intTuple.left && right == intTuple.right;
        }

        @Override
        public int hashCode() {
            return Objects.hash(left, right);
        }
    }

    @BeforeEach
    public void setUp(){
        super.setUp();

        SongDAO sod = new SongDAO();
        CustomerDAO ud = new CustomerDAO();
        List<Song> songList = new ArrayList<>();
        List<Customer> customerList = new ArrayList<>();


        for (int i=0; i<100; i++){


            String title = "song"+i;
            Song song = new Song();
            songList.add(song);
            song.setTitle(title);
            song.setDuration(Duration.ofSeconds(i));
            sod.save(song);

            String username = "user"+i;
            Customer customer = new Customer();
            customerList.add(customer);
            customer.setUsername(username);
            ud.save(customer);

        }
        cus = customerList.getFirst();
        Random random = new Random("carciofo".hashCode());
        HashSet<IntTuple> hm = new HashSet<>();

        for (int i=0; i<200; i++){


            boolean vabbene = false;

            while (!vabbene) {
                int songIndex = random.nextInt(100);
                int userIndex = random.nextInt(100);
                IntTuple pair = new IntTuple(userIndex,songIndex);
                if (!hm.contains(pair)){
                    vabbene = true;
                    hm.add(pair);
                    SongPlaysCount spc = new SongPlaysCount(customerList.get(userIndex),songList.get(songIndex),random.nextInt(20000));
                    sud.save(spc);
                    countList.add(spc);
                }
            }

        }
    }


    class SongPlaysCountComparator implements Comparator<SongPlaysCount>{

        @Override
        public int compare(SongPlaysCount songPlaysCount, SongPlaysCount t1) {
            return t1.getPlays()-songPlaysCount.getPlays();
        }
    }


    private List<Song> getUserTopTen(List<SongPlaysCount> spcl, Customer cus){

        List<SongPlaysCount> spcl2 = new ArrayList<>(spcl);
        List<Song> songs = new ArrayList<>();
        spcl2.removeIf(gianni -> gianni.getCustomer()!= cus);
        spcl2.sort(new SongPlaysCountComparator());
        if(spcl2.size()>10) {
            spcl2.subList(0, 10);
        }
        spcl2.forEach(peppe -> songs.add((Song) peppe.getTrack()));
        return songs;
    }

    public List<Customer> getCustomersWhoListenedTopTenSongs(List<Song> topTenSongs, List<SongPlaysCount> spcl) {
        List<Customer> customers = new ArrayList<>();

        // Trova tutti i customer che hanno ascoltato le canzoni nella lista topTenSongs
        for (SongPlaysCount spc : spcl) {
            if (topTenSongs.contains(spc.getTrack()) && !customers.contains(spc.getCustomer())) {
                customers.add(spc.getCustomer());
            }
        }

        return customers;
    }

    public List<Song> getTopTenSongsByTopTenListeners(List<Customer> customers, List<SongPlaysCount> spcl) {
        Map<Song, Integer> songPlayCountMap = new HashMap<>();

        // Somma le riproduzioni di ogni canzone ascoltata dai customer restituiti
        for (SongPlaysCount spc : spcl) {
            if (customers.contains(spc.getCustomer())) {
                Song song = (Song) spc.getTrack();
                songPlayCountMap.put(song, songPlayCountMap.getOrDefault(song, 0) + spc.getPlays());
            }
        }

        // Ordina le canzoni per numero di ascolti in ordine decrescente
        List<Song> sortedSongs = songPlayCountMap.entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .map(Map.Entry::getKey)
                .toList();

        // Limita la lunghezza della lista a 10 canzoni e restituisce le prime 10
        return sortedSongs.stream().limit(10).collect(Collectors.toList());
    }



    @Test
    void testSuggestionsRandom(){
            assertEquals(getTopTenSongsByTopTenListeners(getCustomersWhoListenedTopTenSongs(getUserTopTen(countList, cus),
                    countList), countList), sud.getTopSongsBySimilarUsers(cus));

    }


}
