function showExtraList(buttonChangerView) {
	var changerView = buttonChangerView.parentNode;

	if(changerView === null) {
		return;
	}

	var list = changerView.parentNode;

	if(list === null) {
		return;
	}

	var extraList = list.querySelector("div.extra-list");

	if(extraList === null) {
		extraList = document.createElement("div");

		extraList.className = "extra-list";
		extraList.appendChild(createExtraList(list));

		list.appendChild(extraList);

		buttonChangerView.innerHTML = "Delete view";
	}
	else if(extraList.style.display === 'none') {
		extraList.style.display = "block";
		buttonChangerView.innerHTML = "Delete view";
	}
	else {
		extraList.style.display = "none";
		buttonChangerView.innerHTML = "Add view";
	}
}


function createExtraList(list) {
	var extraList = document.createElement("ol");
	var listElements = list.querySelectorAll(list.tagName + " > li");

	for(var i = 0; i < listElements.length; i++) {
		var extraListElement = document.createElement("li");

		if(listElements[i].className === "dropdown") {
			extraListElement.innerHTML = transformationDropdownList(listElements[i]);
		}
		else {
			extraListElement.innerHTML = listElements[i].innerHTML;
		}

		extraList.appendChild(extraListElement);
	}

	return extraList;
}


function transformationDropdownList(listElement) {
	var dropdownTitle = listElement.querySelector("div.title");
	var dropdownListElements = listElement.querySelectorAll("ul.show-list > li");

	if(dropdownTitle === null || dropdownListElements === null) {
		return "";
	}

	var lineList = dropdownTitle.innerHTML;
	var lengthDropdown = dropdownListElements.length;

	if(lengthDropdown == 0) {
		return;
	}

	lineList += " (";

	for(var i = 0; i < lengthDropdown - 1; i++) {
		lineList += (dropdownListElements[i].innerHTML + ", ");

	}

	lineList += dropdownListElements[lengthDropdown - 1].innerHTML;
	lineList += ")";

	return lineList;
}


var buttonsChangerView = document.querySelectorAll("div.changer-view > a");

for(var i = 0; i < buttonsChangerView.length; i++) {
	buttonsChangerView[i].innerHTML = "Add view";
}