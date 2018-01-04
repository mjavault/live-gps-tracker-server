package tracking.domain;

/**
 * Created with IntelliJ IDEA.
 * User: Michael
 * Date: 8/6/2015
 * Time: 6:01 PM
 */
public class Position {
    private long timestamp;
    private double lat;
    private double lon;
    private double altitude;

    public Position(long timestamp, double lat, double lon, double altitude) {
        this.timestamp = timestamp;
        this.lat = lat;
        this.lon = lon;
        this.altitude = altitude;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public double getLatitude() {
        return lat;
    }

    public double getLongitude() {
        return lon;
    }

    public double getAltitude() {
        return altitude;
    }

    public String toCesiumString() {
        return "" + lon + "," + lat + "," + altitude;
    }
}
