package tracking.web;

import tracking.domain.Position;
import tracking.domain.Track;
import tracking.domain.Tracker;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Michael
 * Date: 8/6/2015
 * Time: 8:34 PM
 */
public class FullServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            PrintWriter writer = response.getWriter();
            String key = request.getParameter("key");
            if (session != null && key != null) {
                Tracker tracker = Tracker.getInstance();
                Track track = tracker.getTrack(key);
                if (track != null) {
                    List<Position> positions = track.getPositions();

                    //write the result out
                    boolean first = true;
                    writer.print("{\"values\":[");
                    for (Position p : positions) {
                        if (!first) {
                            writer.write(",");
                        }
                        writer.write(p.toCesiumString());
                        first = false;
                    }
                    writer.println("]}");

                    //save the last index
                    session.setAttribute("i-" + key, positions.size());
                } else {
                    System.err.println("Full: Invalid key (no track)");
                }
            } else {
                System.err.println("Full: Missing key");
            }
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }
}
