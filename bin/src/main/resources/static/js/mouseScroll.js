const navigate = (event) => {
	event.preventDefault();
	if (event.deltaY < 0) {
		document.getElementById("back").click();
	}
	else {
		document.getElementById("forward").click();
	}
}

document.onwheel = navigate;

document.getElementById('log').innerHTML += '<p>hello</p>';

function showAlert() {
	alert("POOP");
}