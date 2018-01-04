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
 * Time: 8:30 PM
 */

public class DiffServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int index = 0;
            HttpSession session = request.getSession();
            PrintWriter writer = response.getWriter();
            String key = request.getParameter("key");
            if (session != null && key != null) {
                Object indexObject = session.getAttribute("i-" + key);
                if (indexObject != null) {
                    index = (int) indexObject;
                }

                Tracker tracker = Tracker.getInstance();
                Track track = tracker.getTrack(key);
                if (track != null) {
                    List<Position> positions = track.getPositions();
                    List<Position> subList = positions.subList(index, positions.size());

                    //write the result out
                    writer.print("{\"diff\":[");
                    boolean first = true;
                    for (Position p : subList) {
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
                    System.err.println("Diff: Invalid key (no track)");
                }
            } else {
                System.err.println("Diff: Missing key");
            }
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }
}
