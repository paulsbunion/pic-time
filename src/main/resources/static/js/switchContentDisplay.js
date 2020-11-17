$(document).ready(function() {
	var sel = $("#sel");
	sel.data("prev", sel.val());
	var allEventDivs = document.querySelectorAll('div[id^="eventId"]');
	
	for (var i = 0; i < allEventDivs.length; i++) {
		allEventDivs[i].style.display='none';
	}
	toggleDivDisplay(allEventDivs, 0, $("#sel").val());
	
	sel.change(function(data){
		var jqThis = $(this);
		
//		for (var i = 0; i < allEventDivs.length; i++) {
//			if (allEventDivs[i].id == $("#sel").val()) {
//				allEventDivs[i].style.display='block';
//			}
//			else {
//				allEventDivs[i].style.display='none';
//			}
//		}
//		document.getElementById("eventId" + jqThis.data("prev")).style.display='none';
//		document.getElementById("eventId" + $("#sel").val()).style.display='block';

		var matches = document.querySelectorAll('div[id^="eventId"]');
		toggleDivDisplay(matches, jqThis.data("prev"), $("#sel").val());
		jqThis.data("prev", jqThis.val());
//		var regexHide = new RegExp("eventId" + jqThis.data("prev") + ".*");
//		var regexShow = new RegExp("eventId" + $("#sel").val() + ".*");
////		var regexHide = new RegExp("eventId.+");
//		
//		var matches = document.querySelectorAll('div[id^="eventId"]');
//		
//		for (var j = 0; j < matches.length; j++) {
//			if (matches[j].id.match(regexHide)) {
////				alert("match Hide");
//				matches[j].style.display='none';
//			}
//			if (matches[j].id.match(regexShow)){
//				matches[j].style.display='block';
//			}
//		}
//		var matchesShow = document.querySelectorAll('div[id^="eventId"' + $("#sel").val() + ']');
//		alert("Old id:" + jqThis.data("prev") + " new id:" + $("#sel").val());
//		for (var i = 0; i < matchesHide.length; i++) {
//			matchesHide[i].style.display='none';
//		}
//		for (var i = 0; i < matchesShow.length; i++) {
//			matchesShow[i].style.display='block';
//		}


//		alert("Old id:" + jqThis.data("prev") + " new id:" + $("#sel").val());
//		jqThis.data("prev", jqThis.val());
	});
});

function toggleDivDisplay(matches, prev, selected) {
	
	var regexHide = new RegExp("eventId" + prev + ".*");
	var regexShow = new RegExp("eventId" + selected + ".*");
//		var regexHide = new RegExp("eventId.+");
	
//	var matches = document.querySelectorAll('div[id^="eventId"]');
	
	for (var j = 0; j < matches.length; j++) {
//		if (matches[j].id.match(regexHide)) {
////				alert("match Hide");
//			matches[j].style.display='none';
//		}

		if (matches[j].id.match(regexShow)){
			matches[j].style.display='block';
		}
		else {
//			alert("prev:" + prev + " selected:" + selected);
			matches[j].style.display='none';
		}
	}
}

//function toggleDisplay() {
//	var selectedEventId = document.getElementById('idSelector');
//	var
//}