var $eventButton = null;
var loopTimer = 150;

$(document).ready(function () {
	var id = getParam('eventId');
	if (id != null) {
		$eventButton = $('#eventId_' + id);
		
		if ($eventButton == '' || $eventButton == null || typeof $eventButton == "undefined" || $eventButton.length == 0) {
			return;
		}
//		$eventButton.css("background-color", "green");
//		p.css("background-color", "red");
		switchColor();
	}
	
});

function getParam(name) {
	var urlParams = new URLSearchParams(window.location.search);
	if (urlParams.has(name)) {
		var id = urlParams.get(name);
		return id;
	}
	return null;
}

//var colors = ['yellow', 'orange', 'yellow', 'orange'];
var colors = ['blue', 'blue', 'blue', 'blue', 'mediumblue', 'darkblue', 'navy', 'midnightblue', 'navy', 'darkblue', 'mediumblue'];
var currentColor = 0;

function switchColor() {
if (currentColor >= colors.length) {
	currentColor = 0;
}	

$eventButton.css("background-color", colors[currentColor++]);
setTimeout(switchColor, loopTimer);
	
}
