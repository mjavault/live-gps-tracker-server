package tracking.web;

import tracking.domain.Track;
import tracking.domain.Tracker;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Michael
 * Date: 8/6/2015
 * Time: 5:54 PM
 */
public class PostServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Tracker instance = Tracker.getInstance();
            String key = request.getParameter("key");
            if (key != null) {
                Track track = instance.getTrack(key);
                if (track != null) {
                    double lat = Double.parseDouble(request.getParameter("lat"));
                    double lon = Double.parseDouble(request.getParameter("lon"));
                    double altitude = Double.parseDouble(request.getParameter("altitude"));
                    long timestamp = Long.parseLong(request.getParameter("timestamp"));
                    System.out.println("Post: [" + key + "] " + timestamp + ", " + lat + ", " + lon + ", " + altitude);
                    track.add(timestamp, lat, lon, altitude);
                } else {
                    System.err.println("Post: Invalid key (no track)");
                }
            } else {
                System.err.println("Post: Missing key");
            }
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
