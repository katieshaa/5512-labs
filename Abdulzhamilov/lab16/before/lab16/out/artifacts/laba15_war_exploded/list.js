function showList(showManager) {
    var dropdownList = showManager.parentNode;

    if(dropdownList === null) {
        return;
    }

    var showList = dropdownList.querySelector("ul.show-list");

    if(showList === null) {
        return;
    }

    if(showList.style.display === "none") {
        showManager.innerHTML = "[-]";
        showList.style.display = "block";
    }
    else {
        showManager.innerHTML = "[+]";
        showList.style.display = "none";
    }
}
function check() {
    var list = document.getElementById("level").value;
     var level = list;
    var list_name = document.getElementById("name").value;
    var name = list_name;
    if(level == 0)
    {
        var list = document.getElementsByTagName("div");
        console.log(list);
        for(var i = 0;i < list.length; i++)
        {
            if((list[i].innerHTML.charAt(0) == '<')&&(list[i].innerHTML.length - 9 == name.length))
            {
                var tmp = name ;
                for(var u = 4;u < list[i].innerHTML.length - 5;u++) {
                    tmp[u - 4] = list[i].innerHTML.charAt(u);
                }
                list[i].innerHTML = tmp;
            }
            if(list[i].innerHTML.length == name.length)
            {
                var count = 0;
                for(var j = 0;j < list[i].innerHTML.length; j++)
                {
                    var g =list[i].innerHTML.charAt(j)
                    var s = name.charAt(j)
                    if(s == g)
                        count++
                }
                if(count == list[i].innerHTML.length) {
                    var html = new XMLHttpRequest();
                    //html.setParameter("level", level);
                    //html.setParameter("name", name);
                    html.open('GET', "http://localhost:8080/ListServlet?level="+level+"&name="+name)
                    html.send();
                    setTimeout(function(){window.location.reload(true);},100);
                }
            }
        }
        return;
    }
    var list = document.getElementById(level);
    console.log(list);
    var downlist = list.getElementsByTagName("li");
    for(var i = 0;i < downlist.length; i++) {
        if (downlist[i].innerHTML.length == name.length) {
            var count = 0;
            for (var j = 0; j < downlist[i].innerHTML.length; j++) {
                var g = downlist[i].innerHTML.charAt(j)
                var s = name.charAt(j);
                if (s == g)
                    count++
            }
            if (count == downlist[i].innerHTML.length) {
                html = new XMLHttpRequest();
                html.open('GET', "http://localhost:8080/ListServlet?level=" + level + "&name=" + name)
                html.send();
                setTimeout(function(){window.location.reload(true);},100);
            }
        }
    }

    return;
}


var showLists = document.querySelectorAll("li.dropdown > ul.show-list");
var showManagers = document.querySelectorAll("li.dropdown > a.show-manager");

for(var i = 0; i < showLists.length; i++) {
    showLists[i].style.display = "none";
}

for(var i = 0; i < showManagers.length; i++) {
    showManagers[i].innerHTML = "[+]";
}
