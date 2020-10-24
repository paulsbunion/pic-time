$(document).ready(function() {
	var sel = $("#sel");
	sel.data("prev", sel.val());
	
	var allEventDivs = document.querySelectorAll('div[id^="eventId"]');
	
	for (var i = 1; i < allEventDivs.length; i++) {
		allEventDivs[i].style.display='none';
	}
	
	sel.change(function(data){
		var jqThis = $(this);
		
		for (var i = 1; i < allEventDivs.length; i++) {
			if (allEventDivs[i].id == $("#sel").val()) {
				allEventDivs[i].style.display='block';
			}
			else {
				allEventDivs[i].style.display='none';
			}
		}
		document.getElementById("eventId" + jqThis.data("prev")).style.display='none';
		document.getElementById("eventId" + $("#sel").val()).style.display='block';
//		alert("Old id:" + jqThis.data("prev") + " new id:" + $("#sel").val());
//		jqThis.data("prev", jqThis.val());
	});
});

//function toggleDisplay() {
//	var selectedEventId = document.getElementById('idSelector');
//	var
//}