import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.util.ArrayList;
/*
li - отдельный элемент списка
ul - маркированный список
ol - нумерованный список*/
public class ListServlet extends HttpServlet{
	public static final String WRAPPER_CLASS = "wrapper";
	public static final String DROPDOWN_CLASS = "dropdown";
	public static final String DROPDOWN_HEADER_CLASS = "title";
	public static final String DROPDOWN_MANAGER_CLASS = "show-manager";
	public static final String DROPDOWN_LIST_CLASS = "show-list";
	public static final String ONCLICK_DROPDOWN_LIST = "showDropdownList(this)";
	public static final String PROJECT = "/home/mozg/apache-tomcat-7.0.82/webapps/lab16/";
	public static final String LIST_FILE = "list.txt";
	public static final String HEAD_MAIN = "head_main.html";
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		RequestDispatcher headDispatcher = request.getRequestDispatcher(HEAD_MAIN);
		pw.println("<!DOCTYPE html>");
		pw.println("<html>");
		headDispatcher.include(request, response);
		pw.println("<body>");
		//pw.println("<button id='btn' onclick='showAll(this)' href='#' value='hide'></button>");
		pw.println("<input type='button' id='btn' value='show' onclick='showAll(this)'>");
		pw.println("<input type='button' value='delete' onclick='deleteAll(this)'>");
		pw.println("<div class='" + WRAPPER_CLASS + "'>");
		pw.println(getListHTML());
		pw.println("</div>");
		pw.println("</body>");
		pw.println("</html>");

	}
	private String getListHTML() {
		ListIO io = new ListIO(PROJECT + LIST_FILE);
		ArrayList<ArrayList<String>> list = io.read();
		if (list.size() == 0)
			return "Sorry, File don't exist";
		return arrayToHTML(list);
	}
	private String arrayToHTML(ArrayList<ArrayList<String>> list) {
		StringBuilder builder = new StringBuilder();
		ArrayList<String> sublist = null;
		builder.append("<ol>");
		for(int i = 0; i < list.size(); i++) {
			sublist = list.get(i);
			if(sublist.size() > 1) 
				builder.append(generateDropdownHTML(sublist));
			else if(sublist.size() == 1) 
				builder.append("<li id='elem'>").append(sublist.get(0)).append("</li>\n");
		}
		builder.append("</ol>\n");
		return builder.toString();
	}
	private String generateDropdownHTML(ArrayList<String> list) {
		StringBuilder builder = new StringBuilder();

		builder.append("<li id='elem' class='").append(DROPDOWN_CLASS).append("'>\n");

		builder.append("<div class='").append(DROPDOWN_HEADER_CLASS).append("'>");
		builder.append(list.get(0));
		builder.append("</div>\n");

		builder.append("<a class='").append(DROPDOWN_MANAGER_CLASS)
			   .append("' onclick='").append(ONCLICK_DROPDOWN_LIST)
			   .append("' href='#'>");
		builder.append("[+]");
		builder.append("</a>\n");
		builder.append("<ul class='").append(DROPDOWN_LIST_CLASS).append("'>");
		for(int i = 1; i < list.size(); i++){
			builder.append("<li>").append(list.get(i)).append("</li>\n");
		}
		builder.append("</ul>\n");
		builder.append("</li>\n");
		return builder.toString();
	}
}
