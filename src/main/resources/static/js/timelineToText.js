//$( document ).ready(HTMLToText);

$(document).on("click", "#HTML-to-text", function (event) {
	event.preventDefault();
	var text = HTMLToText();
	console.log("the container");
	console.log($("#HTMLasTextModal"));
	console.log($("#text-modal-body"));
	$("#text-modal-body").html(text);
	$('#HTMLasTextModal').modal('show');
});

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
	
	$timeline.children('div:not([style*="display: none"])').each(function() {
		var $timeslot = $(this);
		var $time = $timeslot.find(".timeslotTime span");
		var $title = $timeslot.find(".timeslotFlexTitleText");
		var $notes = $timeslot.find(".timeslotFlexNotesText");
		
		var timeString = $time.text();
		var titleString = $title.text();
		var notesString = $notes.text();
		
		if (!timeString) {
			
		}
		
		if (!titleString) {
			titleString = "<i>No Title assigned</i>";
		}
		
		if (!notesString) {
			
		}
		
		var data = [timeString, titleString, notesString];
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
	if (data[0]) {
		tempBuffer.push(data[0] + ": ");
//		tempBuffer.push("<br>");
	}
	if (data[1]) {
		tempBuffer.push("<b>");
		tempBuffer.push(data[1]);
		tempBuffer.push("</b>");
		tempBuffer.push("<br>");
	}
	if (data[2]) {
		tempBuffer.push(data[2]);
		tempBuffer.push("<br>");
	}
	tempBuffer.push("</p>");
	tempBuffer.push("<hr>");
	
	return tempBuffer.join("");
}


//$(document).on("click", ".open-EditModal", function (event) {
//	$('#editModal').modal('show');
//});