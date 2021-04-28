//$( document ).ready(HTMLToText);
$(document).ready( function() {
	$("#navbar-print").html("<button type=\"button\" class=\"form-control btn-primary\" id=\"HTML-to-text\">Click to Print Timeline</button>");
});

$(document).on("click", "#HTML-to-text", function (event) {
	event.preventDefault();
	
	var eventId = $("#sel option:selected").val();
	var eventPhotographers = getEventPhotographers(eventId);
	console.log("Start");
	console.log(eventPhotographers);
	var text = HTMLToText(eventPhotographers.length);
	var eventTitle = $("#sel option:selected").text();
	var eventTimeRange = calcTimeRange();
	var date = getDate();
	
	var eventPhotographers = getEventPhotographers(eventId);
	var eventPhotographersString = "Photographer(s): " + printEventPhotographers(eventPhotographers);
	console.log("the container");
	console.log($("#HTMLasTextModal"));
	console.log($("#text-modal-body"));
//	$("#timelineAsTextTitle").html("<b>" + eventTitle + ": " + date + " [" + eventTimeRange + "]" + "</b>");
	var classStyle = 'class="center-self"';
	var titleString = "<div " + classStyle + "><b>" + date + " [" + eventTimeRange + "]" + "<br>" + eventTitle +  "</b><br><i>" + eventPhotographersString + "</i></div>";
	$("#timelineAsTextTitle").html(titleString);
//	$("#timelineAsTextTitle").html("<b>" + date + " [" + eventTimeRange + "]" + "<br>" + eventTitle +  "</b>");
	$("#printThis").html(titleString + "<hr>" + text);
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
	
//	return "";
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
function HTMLToText(eventPhotographersLength) {
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
		
		
		// get the aavailable timeslot photographers
		var timeslotId = $timeslot.attr('id').split("_")[1];
		console.log("the id is");
		console.log(timeslotId);
		var assignedTimeslotPhotogsId = "assigned_photogs_timeslot_" + timeslotId;
		var assignedTimeslotPhotographerData = $("#" + assignedTimeslotPhotogsId).data("assigned_photogs");
		var assignedTimeslotPhotographersString = "All";
		console.log($("#" + assignedTimeslotPhotogsId).data("assigned_photogs"));
		
		if (assignedTimeslotPhotographerData == null) {
			assignedTimeslotPhotographersString = "None";
		}
		else if (assignedTimeslotPhotographerData.length != eventPhotographersLength) {
			var assignedNames = processPhotographerData(assignedTimeslotPhotographerData);
			console.log("names");
			console.log(assignedTimeslotPhotographerData.length);
			console.log(eventPhotographersLength);
			console.log(printEventPhotographers(assignedNames));
			assignedTimeslotPhotographersString = printEventPhotographers(assignedNames);
			
			// add the names to the outputed string
//			getAssignedTimeslotPhotogs(sdfs)sdf;
		}
//		var eventPhotographers = "Photographer(s): " + getEventPhotographers(eventId);
//		console.log(eventPhotographers)
		
		
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
//		if (!assignedTimeslotPhotographersString) {
//			assignedTimeslotPhotographersString = "<i>(" + assignedTimeslotPhotographersString + ")</i>";
//		}
		
		if (!location) {
//			location = "<i>No Location assigned</i>";
			location = "";
		}
		
		if (!notesString) {
			
		}
		
		var data = [timeString, titleString, assignedTimeslotPhotographersString, location, notesString];
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

function getEventPhotographers (eventId) {
	var div = $("#all_photogs_eventId_" + eventId);
	var data = div.data("event-photographers");
	
//	var tempBuffer = [];
//	
//	$.each(data, function(i, val) {
//		tempBuffer.push(val.firstName + " " + val.lastName + ", ");
//	});
//	
//	return tempBuffer;
	
	return processPhotographerData(data);
}

function processPhotographerData(data) {
	var tempBuffer = [];
	
	$.each(data, function(i, val) {
		tempBuffer.push(val.firstName + " " + val.lastName + ", ");
	});
	
	return tempBuffer;
}

function printEventPhotographers(data) {
	var result = Array.prototype.join.call(data, "");
	
//	var result = data.join("");
	if (result.length > 0) {
		result = result.slice(0, -2);
	}
	else {
		result = "No Photographers Assigned";
	}
	return result;
}
function printTextFromModal() {
//	var text = document.getElementById("HTMLasTextModal");
	var $content = $("#HTMLasTextModal");
//	$content.html("<div class='always-print'> hello</div>");
	var printStyle = generateStyle();
	var $hideData = $content.find(".hidden-print");
//	var WinPrint = window.open('', '', 'left=0,top=0,width=800,height=900,toolbar=0,scrollbars=0,status=0');
	$hideData.hide();
//	console.log(text.innerHTML);
//	console.log(text);
//	WinPrint.document.write(text.innerHTML);
//	$content.css("tCext-align", "center");
	console.log($content.html());
	console.log($content);
	
//	$("#printThis").html($content);
//	WinPrint.document.write(printStyle);
//	WinPrint.document.write('<link rel="stylesheet" type="text/css" href="/css/main.css"/>');
//	WinPrint.document.write('<link rel="stylesheet" type="text/css" href="/css/timeslot-edit-modal.css"/>');
//	WinPrint.document.write($content.html());
	$hideData.show();
//	WinPrint.document.close();
//	WinPrint.focus();
//	WinPrint.print();
//	WinPrint.close();
	window.print();
	
}

function generateStyle() {
	var buffer = [];
	buffer.push('<style>');
	
	buffer.push(".center-self {" +
	"	text-align: center;" +
	"}");
	buffer.push('</style>');
	
	return buffer.join("");
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
	// assignedTimeslotPhotographersString
	if (data[2]) {
		console.log("data 2 found");
		console.log(data[2]);
//		tempBuffer.push("<b>");
		tempBuffer.push(" <i>(Photographers: " + data[2] + ")</i>");
//		tempBuffer.push("</b>");
	}
	// location
	if(data[3]) {
		tempBuffer.push("<b>");
		var address = data[3].split(": ")[1];
		address = address.replaceAll(" ", "+");
		address = address.replaceAll(",", "");
		var base = "www.google.com/maps/place/"
		
		tempBuffer.push(" @ [<a href='' onclick=window.open('//" + base + address +"')><u>" + data[2] + "</u></a>]");
		tempBuffer.push("</b>");
		tempBuffer.push("<br>");
	}
	// notesString
	if (data[4]) {
		tempBuffer.push(" " + data[4]);
		tempBuffer.push("<br>");
	}
	tempBuffer.push("</p>");
	tempBuffer.push("<hr>");
	
	return tempBuffer.join("");
}


//$(document).on("click", ".open-EditModal", function (event) {
//	$('#editModal').modal('show');
//});