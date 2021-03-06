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
 * Time: 9:31 PM
 */
public class ClearServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Tracker instance = Tracker.getInstance();
            String key = request.getParameter("key");
            if (key != null) {
                Track track = instance.getTrack(key);
                if (track != null) {
                    track.clear();
                    System.out.println("Clear: Cleared track " + key);
                } else {
                    System.err.println("Clear: Invalid key (track does not exist)");
                }
            } else {
                System.err.println("Clear: Missing key");
            }
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
