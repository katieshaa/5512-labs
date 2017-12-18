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


var showLists = document.querySelectorAll("li.dropdown > ul.show-list");
var showManagers = document.querySelectorAll("li.dropdown > a.show-manager");

for(var i = 0; i < showLists.length; i++) {
    showLists[i].style.display = "none";
}

for(var i = 0; i < showManagers.length; i++) {
    showManagers[i].innerHTML = "[+]";
}
