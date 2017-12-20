<%@ page import="java.util.Map" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.sun.org.apache.xerces.internal.xs.StringList" %><%--
  Created by IntelliJ IDEA.
  User: root
  Date: 15.12.17
  Time: 20:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">
    function openList(eve) {
        var a = document.getElementById(eve);
        if(a.style.display === 'none') {
            a.style.display = 'block';
            eve = eve + 'plus';
            document.getElementById(eve).innerHTML = "[-]";
        } else {
            if (a.style.display === 'block') {
                a.style.display = 'none';
                eve = eve + 'plus';
                document.getElementById(eve).innerHTML = "[+]"
            }
        }
    };

    function checkSymbol() {
        var fill = document.getElementById("textForAdd");

        if(fill.value.length > 10) {
            alert("Length > 10")
        } else {
            var req = new XMLHttpRequest();
            var number = document.getElementById("numberForAdd");
            req.open("GET", "/list?newString=" + fill.value + "&newNumber=" + number.value);
            req.send();
            setTimeout(function () {window.location.reload(true);}, 100);
        }
    }
</script>
<html>
<head>
    <title>List</title>
</head>
<body>
<div class="parent">
    <div class="table">
        <div class="plusWidth"></div>

            <div>
                <input name="number" id="numberForAdd" placeholder="Number Of Element..." class="inputUsername" required>
            </div>
            <div>
                <input name="text" id="textForAdd" type="password" placeholder="Text..." class="inputUsername" required>
            </div>
            <button class="button" onclick="checkSymbol()">Add</button>

    <div class="list">
        <div class="elementList">
            <%if(request.getAttribute("list") != null) {
                out.println("<ol>");
                HashMap<String, ArrayList<String>> map = (HashMap<String, ArrayList<String>>) request.getAttribute("list");
                for(Map.Entry<String, ArrayList<String>> entry : map.entrySet()) {
                    String key = entry.getKey();
                    ArrayList<String> elements = entry.getValue();
                    out.println("<li>" + key + " <a id=\"" + key + "plus" + "\" onclick=\"openList('" + key + "')\">[+]</a>\n" +
                            "                    <ul id=\"" + key + "\" style=\"display: none\">\n");
                    for(int i = 0; i < elements.size(); i++) {
                        out.println("<li>" + elements.get(i) + "</li>");
                    }
                    out.println("                    </ul>\n" +
                            "                </li>");
                }
                out.println("</ol>");
            }%>
        </div>
    </div>
    </div>
</div>
</body>
<link rel="stylesheet" type="text/css" href="list.css">
</html>
