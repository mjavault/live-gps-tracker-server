package tracking.domain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Michael
 * Date: 8/6/2015
 * Time: 9:25 PM
 */
public class Track {
    private final List<Position> positions = new LinkedList<>();

    public void add(long timestamp, double lat, double lon, double altitude) {
        add(new Position(timestamp, lat, lon, altitude));
    }

    public void add(Position position) {
        positions.add(position);
    }

    public List<Position> getPositions() {
        return positions;
    }

    public static Track fromFile(String filename) {
        Track track = new Track();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = br.readLine();
            while (line != null) {
                String[] items = line.split(",");
                if (items.length == 4) {
                    long timestamp = Long.parseLong(items[0]);
                    double lat = Double.parseDouble(items[1]);
                    double lon = Double.parseDouble(items[2]);
                    double altitude = Double.parseDouble(items[3]);
                    track.add(new Position(timestamp, lat, lon, altitude));
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return track;
    }

    public void clear() {
        positions.clear();
    }
}
