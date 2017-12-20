import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by root on 06.12.17 with love.
 */
@WebServlet("/user")
public class Users extends HttpServlet{
    private HashMap<String, String> allUsers = new HashMap<>();

    public Users() {
        Reader reader = new Reader();
        allUsers = reader.read();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String newUsername = request.getParameter("newUsername");
            String newPassword = request.getParameter("newPassword");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            if (newUsername != null && newPassword != null) {
                if (allUsers.get(newUsername) == null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("username", newUsername);
                    allUsers.put(newUsername, newPassword);
                    Writer thread = new Writer(allUsers);
                    thread.start();
                    request.getRequestDispatcher("/board").forward(request, response);
                } else {
                    request.setAttribute("whatDo", "Username already used");
                    request.getRequestDispatcher("somethingWrong.jsp").forward(request, response);
                }
            } else {
                if (username != null && password != null) {
                    String checkPassword = allUsers.get(username);
                    if (checkPassword == null) {
                        request.setAttribute("whatDo", "Username or password wrong");
                        request.getRequestDispatcher("somethingWrong.jsp").forward(request, response);
                    } else {
                        if(checkPassword.equals(password)) {
                            HttpSession session = request.getSession();
                            session.setAttribute("username", username);
                            request.getRequestDispatcher("/board").forward(request, response);
                        } else {
                            request.setAttribute("whatDo", "Wrong password");
                            request.getRequestDispatcher("somethingWrong.jsp").forward(request, response);
                        }
                    }
                } else {
                    request.setAttribute("whatDo", "Something went wrong");
                    request.getRequestDispatcher("somethingWrong.jsp").forward(request, response);
                }
            }
        } catch (Exception e) {
            request.setAttribute("whatDo", e.toString());
            request.getRequestDispatcher("somethingWrong.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/board").forward(request, response);
    }

    public boolean addUsers(String username, String password) {
        if(allUsers.get(username) != null) {
            return false;
        } else {
            allUsers.put(username, password);
            return true;
        }
    }

    public boolean validateUser(String username, String password) {
        if(allUsers.get(username) != null) {
            if(allUsers.get(username).equals(password)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
