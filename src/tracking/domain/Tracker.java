package tracking.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Michael
 * Date: 8/6/2015
 * Time: 5:53 PM
 */
public class Tracker {
    private static Tracker instance;
    private final Map<String, Track> tracks = new HashMap<>();

    public static Tracker getInstance() {
        if (instance == null) {
            instance = new Tracker();
            //temporary fixtures
            //instance.add("flight-path", Track.fromFile("C:\\temp\\flight_path.csv"));
            instance.add("flight-path", Track.fromFile("/opt/flight_path.csv"));
            instance.add("track", new Track());
        }
        return instance;
    }

    public void add(String key, Track track) {
        tracks.put(key, track);
    }

    public Track getTrack(String key) {
        return tracks.get(key);
    }

    public void createTrack(String key) {
        instance.add("track", new Track());
    }
}
