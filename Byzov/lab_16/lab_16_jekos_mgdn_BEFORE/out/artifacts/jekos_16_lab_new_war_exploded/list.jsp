<%@ page import="java.util.Map" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.sun.org.apache.xerces.internal.xs.StringList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">
    function openList(jeks) {
        var a = document.getElementById(jeks);
        if(a.style.display === 'none') {
            a.style.display = 'block';
            jeks = jeks + 'plus';
            document.getElementById(jeks).innerHTML = "[-]";
        } else {
            if (a.style.display === 'block') {
                a.style.display = 'none';
                jeks = jeks + 'plus';
                document.getElementById(jeks).innerHTML = "[+]"
            }
        }
    };

    function proverka() {
        var fill = document.getElementById("textForAdd");
            var req = new XMLHttpRequest();
            var number = document.getElementById("numberForAdd");
            req.open("GET", "/list?newString=" + fill.value + "&newNumber=" + number.value);
            req.send();
            setTimeout(function () {window.location.reload(true);}, 100);

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
                <input name="number" id="numberForAdd" placeholder="level" class="inputUsername" required>
            </div>
            <div>
                <input name="text" id="textForAdd" type="password" placeholder="new text" class="inputUsername" required>
            </div>
            <button class="button" onclick="proverka()">Add</button>

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
