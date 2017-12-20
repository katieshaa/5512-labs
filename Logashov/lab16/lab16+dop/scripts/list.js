function showDropdownList(showManager) {
	var dropdownList = showManager.parentNode;

	if(dropdownList === null) {
		return;
	}

	var showList = dropdownList.querySelector("ul.show-list");
	//только первый элемент show-list

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

function showAll(showManager)
{
	var showLists = document.querySelectorAll("li.dropdown > ul.show-list");
	var showManagers = document.querySelectorAll("li.dropdown > a.show-manager");
	for(var i = 0; i < showLists.length; i++) {
		showLists[i].style.display = "block";
	}

	for(var i = 0; i < showManagers.length; i++) {
		showManagers[i].innerHTML = "[-]";
	}
	var btn = document.getElementById('btn');
	btn.value = ('hide');
	btn.setAttribute('onclick', 'hideAll(this)');
}
function hideAll(showManager)
{
	var showLists = document.querySelectorAll("li.dropdown > ul.show-list");
	var showManagers = document.querySelectorAll("li.dropdown > a.show-manager");
	for(var i = 0; i < showLists.length; i++) {
		showLists[i].style.display = "none";
	}

	for(var i = 0; i < showManagers.length; i++) {
		showManagers[i].innerHTML = "[+]";
	}
	var btn = document.getElementById('btn');
	btn.value = ('show');
	btn.setAttribute('onclick', 'showAll(this)');
}

function deleteAll(showManager)
{
	var elem = document.getElementsByClassName('show-manager');
	//alert(elem.length);
	localStorage.setItem('but', '');
	for(var i = 0; i < elem.length; i++)
	{
		elem[i].innerHTML = "";
	}
var showLists = document.querySelectorAll("li.dropdown > ul.show-list")
for(var i = 0; i < showLists.length; i++) {
	showLists[i].style.display = "none";
}
}

var showLists = document.querySelectorAll("li.dropdown > ul.show-list");
//возвращает все элементы внутри documnet, удовлетворяющию li.dropdown > ul.show-list
var showManagers = document.querySelectorAll("li.dropdown > a.show-manager");

for(var i = 0; i < showLists.length; i++) {
	showLists[i].style.display = "none";
}

for(var i = 0; i < showManagers.length; i++) {
	showManagers[i].innerHTML = localStorage.getItem('but');
}
