import org.omg.CosNaming.NamingContextPackage.NotFound;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * Created by root on 14.11.17 with love.
 */
@WebServlet("/user")
public class User extends HttpServlet {
    private String name = "not set";
    private Notebook notebook;
    private boolean usernameExist = false;
    private HashMap<String, Integer> sortedOrNot = new HashMap<>();

    public User() {
        notebook = new Notebook();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tmp = request.getParameter("username");
        if (tmp != null) {
            name = tmp;
            sortedOrNot.putIfAbsent(name, 0);
            usernameExist = notebook.existName(name);
        }
        String newName = request.getParameter("newName");
        String newNumber = request.getParameter("newNumber");
        if (newName != null && newNumber != null && !newName.equals("") && !newNumber.equals("")) {
            try {
                notebook.addNumber(name, newName, newNumber);
            } catch (NotFound notFound) {
                notFound.printStackTrace();
            }
        } else {
            String newUsername = request.getParameter("newUsername");
            if (newUsername != null && !notebook.existName(newUsername)) {
                notebook.addUser(newUsername);
            }
        }
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("username", name);
        if (!usernameExist) {
            request.getRequestDispatcher("userNotFound.jsp").forward(request, response);
        } else {
            if (request.getParameter("sort") != null) {
                if (sortedOrNot.get(name) != null) {
                    if (sortedOrNot.get(name) != 1) {
                        request.setAttribute("table", notebook.getSortedMap(name));
                        sortedOrNot.put(name, 1);
                    } else {
                        request.setAttribute("table", notebook.getRecords(name));
                        sortedOrNot.put(name, 0);
                    }
                } else {
                    request.setAttribute("table", notebook.getSortedMap(name));
                    sortedOrNot.put(name, 0);
                }
            } else {
                if (sortedOrNot.get(name) != null) {
                    if (sortedOrNot.get(name) != 1) {
                        request.setAttribute("table", notebook.getRecords(name));
                    } else {
                        request.setAttribute("table", notebook.getSortedMap(name));
                    }
                } else {
                    request.setAttribute("table", notebook.getRecords(name));
                }
            }
            request.getRequestDispatcher("table.jsp").forward(request, response);
        }
    }

}
