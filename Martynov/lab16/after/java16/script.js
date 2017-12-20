function onClick(node) {
	var parent = node.parentNode;

	var innerList = parent.getElementsByTagName("ol");

	if (innerList[0].style.display != "none") {
		node.innerHTML = "[+]";
		innerList[0].style.display = "none"
	} else {
		node.innerHTML = "[-]";
		innerList[0].style.display = "block"
	}

}

function deleteClick(node) {
	var parent = node.parentNode;
	parent.remove();
}
