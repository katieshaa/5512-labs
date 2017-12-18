import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 15.12.17 with love.
 */

@WebServlet("/list")
public class List extends HttpServlet {
    private HashMap<String, ArrayList<String>> mapOfAnimals = new HashMap<>();
    private ReaderWriterAnimals readerWriterAnimals = new ReaderWriterAnimals();

    public List() {
        mapOfAnimals = readerWriterAnimals.read();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String num = request.getParameter("newNumber");
        if(num != null) {
            int number = Integer.parseInt(num);
            String text = request.getParameter("newString");
            int counter = 1;
            if(number == 0) {
                HashMap<String, ArrayList<String>> map = new HashMap<>();
                for (Map.Entry<String, ArrayList<String>> entry : mapOfAnimals.entrySet()) {
                    map.put(entry.getKey(), entry.getValue());
                }
                map.put(text, new ArrayList<>());
                mapOfAnimals = map;
            } else {
                for (Map.Entry<String, ArrayList<String>> entry : mapOfAnimals.entrySet()) {
                    if (counter == number) {
                        entry.getValue().add(text);
                    }
                    counter++;
                }
            }
        }

        request.setAttribute("list", mapOfAnimals);
        request.getRequestDispatcher("list.jsp").forward(request, response);
    }
}
