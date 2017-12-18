package org.suai.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;


public class ListPhoneServlet extends HttpServlet {

    private final String PROJECT_PATH = "../webapps/list-phones/";
    private final String DATA_FILE = "users.txt";

    private static HashMap<String, ArrayList<String>> phoneBook = new HashMap<>();



    @Override
    public void init() {
        try (BufferedReader reader = new BufferedReader(new FileReader(PROJECT_PATH + DATA_FILE))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(":");
                String name = tokens[0];
                String[] nums = tokens[1].split(";");

                add(name, nums);
            }
        }
        catch(IOException exception) {
            System.out.println(exception);
        }
    }


    @Override
    public void destroy() {
        File data = new File(PROJECT_PATH + DATA_FILE);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(data))) {
            for (Map.Entry<String, ArrayList<String>> entry : phoneBook.entrySet()) {
                String name = entry.getKey();
                ArrayList<String> numbers = entry.getValue();

                writer.write(name + ":");

                for (String i : numbers) {
                    writer.write(i + ";");
                }

                writer.newLine();
            }
        }
        catch(IOException exception) {
            System.out.println(exception);
        }
    }


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();

        try (PrintWriter out = response.getWriter()) {
            out.println("<html>\n<body>\n");
            out.println("\n<br>\n");

            String name;
            String numbers;
            String[] nums;

            System.out.println(uri);

            switch (uri) {
                case "/phones/list-phones":
                    out.println(generateList());
                    break;

                case "/phones/list-phones/form":
                    out.println(generateForm());
                    break;

                case "/phones/list-phones/add":
                    name = request.getParameter("name");
                    numbers = request.getParameter("numbers");

                    if (numbers.contains(":")) {
                        nums = numbers.split(":");
                    }
                    else {
                        nums = new String[1];
                        nums[0] = numbers;
                    }

                    boolean allNums = true;

                    for (String i : nums) {
                        if (!isNum(i)) {
                            allNums = false;
                            break;
                        }
                    }

                    if (!allNums || name.equals("")) {
                        out.println("Error in input<br>");
                        out.println("<a href=\"/phones/list-phones\">Back to list</a>\n");
                    } else {
                        add(name, nums);

                        out.println(generateList());
                    }

                    break;

            out.println("</body>\n</html>");
        }
        catch (IOException exception) {
            System.out.println(exception);
        }
    }


    private String generateList() {
        StringBuilder list = new StringBuilder();

	list.append(generateFinder());
        list.append("<a href=\"/phones/list-phones/form\">Add</a>\n");

        for (Map.Entry<String, ArrayList<String>> entry : phoneBook.entrySet()) {
            String name = entry.getKey();
            ArrayList<String> numbers = entry.getValue();

            list.append("<p>").append(name).append("</p>");

            list.append("<ul style=\"list-style=type:circle\">");
            for (String i : numbers) {
                list.append("<li>").append(i).append("</li>\n");
            }
            list.append("</ul>\n");

            list.append("<hr>\n");
        }

        return list.toString();
    }

    private String generateForm() {
        return "<form method=\"GET\" action=\"/phones/list-phones/add\">\n" +
                    "Name: <input type=\"text\" name=\"name\">\n" +
                    "Number: <input type=\"text\" name=\"numbers\">\n" +
                    "<br>You can add multiple numbers by separating them with \':\' symbol" +
                    "<br>\n" +
                    "<input type=\"submit\" value=\"Submit\">" +
                "</form>\n" +
                "<a href=\"/phones/list-phones\">Go back to list</a>\n";
    }

    private synchronized void add(String name, String[] nums) {
        ArrayList<String> numbers;
        ArrayList<String> tmp = new ArrayList<>(Arrays.asList(nums));

        if (phoneBook.containsKey(name)) {
            numbers = phoneBook.get(name);
        }
        else {
            numbers = new ArrayList<>();
        }

        numbers.addAll(tmp);

        phoneBook.put(name, numbers);
    }


    private boolean isNum(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) < '0' || str.charAt(i) > '9') {
                return false;
            }
        }

        return true;
    }

}

