//$( document ).ready(HTMLToText);
$(document).ready( function() {
	$("#navbar-print").html("<button type=\"button\" class=\"form-control btn-primary\" id=\"HTML-to-text\">Click to Print Timeline</button>");
});

$(document).on("click", "#HTML-to-text", function (event) {
	event.preventDefault();
	var text = HTMLToText();
	var eventTitle = $("#sel option:selected").text();
	var eventTimeRange = calcTimeRange();
	var date = getDate();
	console.log("the container");
	console.log($("#HTMLasTextModal"));
	console.log($("#text-modal-body"));
//	$("#timelineAsTextTitle").html("<b>" + eventTitle + ": " + date + " [" + eventTimeRange + "]" + "</b>");
	$("#timelineAsTextTitle").html("<b>" + date + " [" + eventTimeRange + "]" + "<br>" + eventTitle +  "</b>");
	$("#text-modal-body").html(text);
	
	$('#HTMLasTextModal').modal('show');
});


function calcTimeRange() {
	var $timeline = $(".day-calendar-content");
	var earliestStartTime = 99999;
	var latestEndTime = -1;
	
	$timeline.children('div:not([style*="display: none"])').each(function() {
		var $timeslot = $(this);
		var $time = $timeslot.find(".timeslotTime span");
		var timeString = $time.text();
		var timeData = timeString.split(":");
		if (timeData.length > 1) {
			var tempStartHr = timeData[0];
			var tempStartMin = timeData[1].substring(0,2);
			var tempStartMerid = timeData[1].split(" ")[1].substring(0,2);
			var tempStartTimeInMinutes = convertTextTimeToIntTime([tempStartHr, tempStartMin, tempStartMerid]);
			
			if (tempStartTimeInMinutes < earliestStartTime) {
				earliestStartTime = tempStartTimeInMinutes;
			}
			if (tempStartTimeInMinutes > latestEndTime) {
				latestEndTime = tempStartTimeInMinutes;
			}
			
			console.log("Start Time");
			console.log(tempStartTimeInMinutes);
			
			if (timeData.length > 2) {
				var tempEndtHr = timeData[1].split("-")[1];
				var tempEndMin = timeData[2].substring(0,2);
				var tempEndMerid = timeData[2].split(" ")[1];
				var tempEndTimeInMinutes = convertTextTimeToIntTime([tempEndtHr, tempEndMin, tempEndMerid]);
				
				if (tempEndTimeInMinutes > latestEndTime) {
					latestEndTime = tempEndTimeInMinutes;
				}
				
				if (latestEndTime == -1) {
					latestEndTime = "";
				}
				console.log("End Time");
				console.log(tempEndTimeInMinutes);
			}
		}
	});
	
	var startTime;
	var endTime;
	
	if (latestEndTime > 0) {
		endTime = convertIntTimeToString(latestEndTime);
	} else {
		endTime = "";
	}
	if (earliestStartTime < 1440) {
		startTime = convertIntTimeToString(earliestStartTime);
	}
	else {
//		startTime = "0:00 AM"
		startTime = "No Timeslots"
	}
	
	console.log("" + startTime + "-" + endTime);
	if (endTime.length > 0) {
		endTime = " - " + endTime;
	}
	return "" + startTime + endTime;
}

function convertIntTimeToString(time) {
	var hr = parseInt(time / 60, 10);
	var min = time % 60;
	var merid = "AM";
	if (hr > 11) {
		merid = "PM";
	}
	if (hr == 0) {
		hr = 12;
	}
	if (hr > 12) {
		hr -= 12;
	}
	
	if (min < 10) {
		min = "0" + min;
	}
	
	return "" + hr + ":" + min + " " + merid;
	
	return "";
}
function convertTextTimeToIntTime(time) {
	var hr = parseInt(time[0], 10);
	var min = parseInt(time[1], 10);
	if (hr == 12) {
		hr = 0;
	}
	if (time[2].toUpperCase() === 'PM') {
		hr += 12;
	}
	
	return hr * 60 + min;
	
}

function getDate() {
	return $("#DateAsText").html();
}
function HTMLToText(event) {
	var $timeline = $(".day-calendar-content");
	
//	var html = [];
//	html.push(
//	  "<html>",
//	  "<body>",
//	  "bla bla bla",
//	  "</body>",
//	  "</html>"
//	);
//	return html.join("");
	
	
	var stringBuilderArray = [];
//	stringBuilderArray.push
	
//	sel
	
	$timeline.children('div:not([style*="display: none"])').each(function() {
		var $timeslot = $(this);
		var $time = $timeslot.find(".timeslotTime span");
		var $title = $timeslot.find(".timeslotFlexTitleText");
		var $notes = $timeslot.find(".timeslotFlexNotesText");
		var location = $timeslot.data('location');
//		console.log("The location");
//		console.log($location);
		
		var timeString = $time.text();
		var titleString = $title.text();
		var notesString = $notes.text();
		
		if (!timeString) {
			
		}
		
		if (!titleString) {
			titleString = "<i>No Title assigned</i>";
		}
		
		if (!location) {
			location = "<i>No Location assigned</i>";
		}
		
		if (!notesString) {
			
		}
		
		var data = [timeString, titleString, location, notesString];
		stringBuilderArray.push(parseTimelineData(data));

		console.log(timeString);
		console.log(titleString);
		console.log(notesString);
			
	});
	
	var text = stringBuilderArray.join("")
	
	console.log("data");
	console.log(text);
	
	return text;
	
}

function printTextFromModal() {
	var text = document.getElementById("HTMLasTextModal");
	var $content = $("#HTMLasTextModal");
	var $hideData = $content.find(".hidden-print");
	var WinPrint = window.open('', '', 'left=0,top=0,width=800,height=900,toolbar=0,scrollbars=0,status=0');
	$hideData.hide();
	WinPrint.document.write(text.innerHTML);
	$hideData.show();
	WinPrint.document.close();
	WinPrint.focus();
	WinPrint.print();
	WinPrint.close();
	
}

function parseTimelineData(data) {
	var tempBuffer = [];
	tempBuffer.push("<p>");
	// timeString
	if (data[0]) {
		tempBuffer.push("<b>");
		tempBuffer.push(data[0] + ": ");
		tempBuffer.push("</b>");
//		tempBuffer.push("<hr>");
//		tempBuffer.push("<br>");
	}
	// titleString
	if (data[1]) {
		tempBuffer.push("<b>");
		tempBuffer.push(data[1]);
		tempBuffer.push("</b>");
//		tempBuffer.push("<br>");
	}
	// location
	if(data[2]) {
		tempBuffer.push("<b>");
		tempBuffer.push(" @ [<u>" + data[2] + "</u>]");
		tempBuffer.push("</b>");
		tempBuffer.push("<br>");
	}
	// notesString
	if (data[3]) {
		tempBuffer.push(data[3]);
		tempBuffer.push("<br>");
	}
	tempBuffer.push("</p>");
	tempBuffer.push("<hr>");
	
	return tempBuffer.join("");
}


//$(document).on("click", ".open-EditModal", function (event) {
//	$('#editModal').modal('show');
//});