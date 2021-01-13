$(document).ready(function() {
	var sel = $("#sel");
	sel.data("prev", sel.val());
	var allEventDivs = document.querySelectorAll('div[id^="eventId"]');
	
	for (var i = 0; i < allEventDivs.length; i++) {
		allEventDivs[i].style.display='none';
	}
//	get URL parameter
	const queryString = window.location.search;
	const urlParams = new URLSearchParams(queryString);
	
	var eventId =urlParams.get('eventId');
	console.log("event id is :  " + eventId);
	if (isNaN(eventId) || eventId < 0) {
		console.log("bad data");
//		if no URL parameter, set to selected event id
		eventId = $("#sel").val();
	}
	else {
		// select event from dropdown
		document.getElementById("sel").value = eventId;
	}
	toggleDivDisplay(allEventDivs, 0, eventId);
	
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
			matches[j].style.display='inline-block';
		}
		else {
//			alert("prev:" + prev + " selected:" + selected);
			matches[j].style.display='none';
		}
	}
	
	// check overlap
	adjustForOverlap(matches);
}

function adjustForOverlap(matches) {
	box1 = null;
	box2 = null;
	
	for (var index = 0; index < matches.length; index++) {
		if (matches[index].style.display == 'inline-block') {
			
			box1 = matches[index].getBoundingClientRect();
			
			var pointer = index + 1;
			var widthOffset = 20;
			var topOffset = 5;
			var collisions = 0;
			var border = 2;
			
			while (pointer < matches.length) {
				if (matches[pointer].style.display == 'inline-block') {
					box2 = matches[pointer].getBoundingClientRect();
					
					// check collision
					if (collision(box1, box2, border)) {
						// update collisions
						collisions++;
						
//						alert(getComputedStyle(matches[index]).borderTopWidth);
						// adjsut box2 offset
						matches[pointer].style['margin-left'] = collisions * widthOffset + 20 + "px";
						
						// add offset if same start time
						if (box1.top == box2.top) {
							matches[pointer].style['margin-top'] = collisions * topOffset + "px";
						}
//						alert(matches[pointer].style['left']);
//						alert(matches[index].children[0].innerText);
//						alert(matches[pointer].children[0].innerText);
						// update pointers
						index = pointer;
					}
				}
				pointer++;
			}
		}
	}
}

function collision(box1, box2, border) {
	if (box2.top >= box1.top && box2.top < box1.bottom - border) {
		return true;
	}
	else {
		return false;
	}
}

//function toggleDisplay() {
//	var selectedEventId = document.getElementById('idSelector');
//	var
//}