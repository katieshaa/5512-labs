package org.suai.handbook;


import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.IOException;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletConfig;



public class HandbookServlet extends HttpServlet {
	private static final String handbookPath = "C:\\Tomcat\\webapps\\handbook\\input.txt";
	private Handbook handbook = new Handbook();

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean lastCondition = true;
		boolean enterName = false;
		boolean enterPhone = false;
		PrintWriter pw = response.getWriter();
		pw.println("<html>");
		pw.println("<body>");
		pw.println("<center>");
		pw.println("<h1>Handbook</h1>");
		String action = request.getParameter("action");
		if(action != null) {
			String name = request.getParameter("name");
			String phone = request.getParameter("phone");
			if (action.equals("add")) {
				lastCondition = true;
				if (name.equals("")) {
					enterName = true;
				} else if (phone.equals("")) {
					enterPhone = true;
				} else {
					handbook.add(name, phone);
					write();
				}
			} else if (action.equals("delete")) {
				lastCondition = false;
				if (name.equals("")) {
					enterName = true;
				}
				else {
					if(phone.equals("")) {
						handbook.remove(name);
						write();
					}
					else {
						handbook.remove(name, phone);
						write();
					}
				}
			}
		}
		pw.println(handbook.toXmlTable());
		pw.println("<form method=\"GET\" action=\"/handbook/handbook/\">");
		pw.println("Name: <input type=\"text\" name=\"name\">");
		pw.println("Phone: <input type=\"text\" name=\"phone\">");
		pw.println("<br>");
		if(lastCondition) {
			pw.println("<input name=\"action\" value=\"delete\" type=\"radio\" value=\"add\">");
			pw.println("delete");
			pw.println("<input name=\"action\" value=\"add\" type=\"radio\" value=\"add\" checked>");
			pw.println("add");
		}
		else {
			pw.println("<input name=\"action\" value=\"delete\" type=\"radio\" value=\"add\" checked>");
			pw.println("delete");
			pw.println("<input name=\"action\" value=\"add\" type=\"radio\" value=\"add\">");
			pw.println("add");
		}
		pw.println("<input type=\"submit\" value=\"submit\">");
		pw.println("</form>");
		if(enterName) {
			pw.println("<br>");
			pw.println("Enter name.");
		}
		if(enterPhone) {
			pw.println("<br>");
			pw.println("Enter phone.");
		}
		pw.println("</body>");
		pw.println("</html>");

	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try {
			BufferedReader br = new BufferedReader(new FileReader(handbookPath));
			String record = br.readLine();
			int spaceInd;
			String name;
			String phone;
			while (record != null) {
				spaceInd = record.indexOf(' ');
				name = record.substring(0, spaceInd);
				phone = record.substring(spaceInd + 1);
				handbook.add(name, phone);
				record = br.readLine();
			}
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private void write() {
		try {
			PrintWriter printWriter = new PrintWriter(handbookPath);
			printWriter.print(handbook.toString());
			printWriter.close();
		}
		catch (IOException ex) {
			System.out.println("Error while writing.");
		}
	}
	
}