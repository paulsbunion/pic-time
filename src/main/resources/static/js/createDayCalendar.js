for (var i = 0; i < 24; i++) {
	var hourDiv = document.createElement("div");
	hourDiv.className = "hour";
	hourDiv.style.gridArea = i + 1 + (i * 3) + " / 1 / span 4 / span 1";

	var hourDisplay = i;
	var ampm = "";
	if (hourDisplay == 0) {
		hourDisplay = 12;
	}
	if (i > 12) {
		hourDisplay -= 12;
	}
	if (i < 12) {
		ampm += "AM";
	}
	else {
		ampm += "PM";
	}

	hourDiv.innerHTML = hourDisplay + ampm;

	var objTo = document.getElementById("cal");
	objTo.appendChild(hourDiv);

	// create event slots with lines

	for (var j = 1; j <= 4; j++) {
		var eventDiv = document.createElement("div");
		eventDiv.className = "event-detail";

		eventDiv.id = hourDisplay + ":" + getMinute(j) + ampm;

		eventDiv.style.gridArea = j + (i * 4) + " / 2 / span 1 / span 1";

//		eventDiv.innerHTML = i + j;

		if (j % 2 == 0) {
			eventDiv.style.borderBottom = "1px solid";
		}

		var objTo = document.getElementById("cal");
		objTo.appendChild(eventDiv);
	}

	//	th:style="${(j % 2 == 0)} ? 'border-bottom: 1px solid;' : 'background: yellow;'">
}

function getMinute(quarter) {
	var minute;
	switch (quarter) {
		case 2:
			minute = "15";
			break;
		case 3:
			minute = "30";
			break;
		case 4:
			minute = "45";
			break;
		default:
			minute = "00";
	}
	return minute;
}