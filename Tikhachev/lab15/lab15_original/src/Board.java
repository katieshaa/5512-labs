import jdk.internal.util.xml.impl.Pair;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by root on 06.12.17 with love.
 */

@WebServlet("/board")
public class Board extends HttpServlet {
    private ArrayList<HashMap<String, String>> adverts = new ArrayList<>();
    private ReaderWriterAdverts readerWriterAdverts = new ReaderWriterAdverts();

    public Board() {
        adverts = readerWriterAdverts.read();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute("adverts", adverts);
        request.getRequestDispatcher("board.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            if (request.getParameter("exit") != null) {
                session.invalidate();
                request.setAttribute("adverts", adverts);
            } else {
                String username = (String) session.getAttribute("username");
                String text = request.getParameter("advertText");
                if (username != null && text != null) {
                    HashMap<String, String> tmp = new HashMap<>();
                    tmp.put("User: " + username + "   Time" + new Date().toString(), text);
                    adverts.add(tmp);
                    readerWriterAdverts.write(adverts);
                }
                session.setAttribute("adverts", adverts);
            }
            request.getRequestDispatcher("board.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("whatDo", e.toString());
            request.getRequestDispatcher("somethingWrong.jsp").forward(request, response);
        }
    }
}
