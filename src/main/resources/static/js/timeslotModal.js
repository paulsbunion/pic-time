$(document).on("click", "#newTimeslotModal", function (event) {
	   event.preventDefault();
	   
	var _self = $(this);
	console.log(_self);
// 	var eventId = _self.data('event-id');
	
	var eventId = $("#sel").val()
	// if id null, call create event
	if (eventId == '' || eventId == null) {
		window.location.href = document.getElementById("newEventAnchor").href;
	}
	else {
	
		// 	var timeslotId = _self.data('id');
		// 	var startTime = 0;
		// 	var endTime = 0;
		// 	var title = _self.data('timeslottitle');
		// 	var notes = _self.data('notes');
		
		$('#rsp_start_time_new').text("");
		console.log("value");
		console.log($("#sel").val());
		$("#newEventId").val($("#sel").val());
		
		// $("#timeslotTitle").val(title);
		// $("#startTime").val(startTime);
		// $("#endTime").val(endTime);
		// $("#notes").val(notes);
		// 	 var x = new Date(); 
		
		// update timepicker
	
	     $('#newModal').modal('show');
  	}
	});

   
   
   
   
   
   
   
   
   $(document).on("click", ".open-EditModal", function (event) {
// 	   window.alert();
	var _self = $(this);
	console.log("self: " + _self.data('notes'));
	var eventId = _self.data('event-id');
	var timeslotId = _self.data('id');
	var startTime = _self.data('starttime');
	var endTime = _self.data('endtime');
	var title = _self.data('timeslottitle');
	var notes = _self.data('notes');
	var location = _self.data('location');
	var locationId = _self.data('location-id');

	console.log("zero error");
	console.log(startTime);
	
// 	var startTime = $(event.relatedTarget).data('val');
// 	window.alert(st);
// 	$(this).find(".modal-body").text(st);
$('#rsp_start_time').text("");
$("#editEventId").val(eventId);
$("#editTimeslotId").val(timeslotId);

$("#editTimeslotTitle").val(title);
// $("#startTime").val(startTime);
var startTimeSplit = startTime.split(":");
var endTimeSplit = endTime.split(":");
if (startTimeSplit[0] > 11) {
	$("#editStartTimeMeridian").val("PM");
	startTimeSplit[0]-= 12;
}
else {
	$("#editStartTimeMeridian").val("AM");
}
if (endTimeSplit[0] > 11) {
	$("#editEndTimeMeridian").val("PM");
	endTimeSplit[0]-= 12;
}
else {
	$("#editEndTimeMeridian").val("AM");
}

$("#editStartTimeHr").val(startTimeSplit[0]);
$("#editStartTimeMin").val(startTimeSplit[1]);
// $("#editStartTimeMeridian").val(startTimeSplit[2]);

$("#editEndTimeHr").val(endTimeSplit[0]);
$("#editEndTimeMin").val(endTimeSplit[1]);
// $("#editEndTimeMeridian").val(endTimeSplit[2]);

//$("#endTime").val(endTime);
$("#editNotes").val(notes);
	 var x = new Date(); 
//      $("#modal-body").html(myHeading + x);   

if (location != null) {
	$("#searchBox").val(location);
	$("#editLocation").val(locationId);
}
else {
	$("#searchBox").val("");
	$("#editLocation").val("");
}

// update timepicker

     $('#editModal').modal('show');
    });
   
   
// 	$(function() {
   	$(document).on("click", "#edit-modal-submit", function (event) {
		event.preventDefault();
		
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		
//		var _self = $('#myForm');
		
		var formData = {};
		formData = getFormData("edit");
		
		const RELOAD_TIMER = 10;
		
		$.ajaxSetup({
            headers: {
                'X-CSRF-TOKEN': '<?= csrf_token() ?>'
            }
        });

		$.ajax({
			type: "PUT",
			contentType: "application/json",
			url: "/events/" + $('#editEventId').val() + "/timeslots/" + $('#editTimeslotId').val(),
			data: JSON.stringify(formData),

			dataTpye: 'JSON',
			
			beforeSend: function(xhr) {
				xhr.setRequestHeader(header, token);
			},
			
			success: function(response) {
				$('#rsp_start_time').text("");
				location.href = URL_add_parameter(location.href, "eventId", formData.eventId);
				if (response.redirect) {
					window.location.href = response.redirect;
				}
				
				setTimeout(function() {
					location.reload();
					}, RELOAD_TIMER);
			},
			error: function (e) {
				$('#rsp_start_time').text(e.responseJSON.rsp_start_time);
			}
		});

	});
	
	
   	$(document).on("click", "#new-modal-submit", function (event) {
		event.preventDefault();
		
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		
//		var _self = $('#myFormNew');
		
		var formData = {};
		formData = getFormData("new");

		const RELOAD_TIMER = 10;
		
		$.ajaxSetup({
            headers: {
                'X-CSRF-TOKEN': '<?= csrf_token() ?>'
            }
        });
		$.ajax({
			type: "POST",
			contentType: "application/json",
			url: "/events/" + $('#newEventId').val() + "/timeslots/",
			data: JSON.stringify(formData),

			dataTpye: 'JSON',
			
			beforeSend: function(xhr) {
				xhr.setRequestHeader(header, token);
			},
			
			success: function(response) {
				$('#rsp_start_time_new').text("");
				location.href = URL_add_parameter(location.href, "eventId", formData.eventId);
				
				 window.setTimeout(function(){window.location.reload()}, 3000);
				if (response.redirect) {
					//alert(location.href);
					//window.location.href = response.redirect;
				}
			},
			error: function (e) {
				$('#rsp_start_time_new').text(e.responseJSON.rsp_start_time);
			}
		});

	});
	
	function getFormData(typeOfTimeslot) {
		
		var formData = {};
		formData.time={};
		formData.id = null;
		formData.time.startTime=0;
		formData.time.endTime=0;
		formData.title="";
		formData.notes=null;
		formData.client=null;
		formData.photographers=[];
		formData.locationId=null;
		formData.trackMileage=false;
		formData.eventId=0;
		
		let prefix = typeOfTimeslot.toLowerCase();
		if (prefix == "edit") {
			prefix = "edit";
			
			// check if need to clear location id
			if ($("#searchBox").val() == "") {
				$("#editLocation").val("");
			}
			
			// set existing id
			formData.id = $('#' + prefix + 'timeslotId').val();
		}
		
		var startTime = [$('#' + prefix + 'StartTimeHr').val(), $('#' + prefix + 'StartTimeMin').val(), $('#' + prefix + 'StartTimeMeridian').val()];
		var endTime = [$('#' + prefix + 'EndTimeHr').val(), $('#' + prefix + 'EndTimeMin').val(), $('#' + prefix + 'EndTimeMeridian').val()];
		formData.time.startTime = parseTime(startTime);
		formData.time.endTime =  parseTime(endTime);
		
		formData.client=null;
		formData.photographers=[];
		
		formData.locationId=$('#' + prefix + 'Location').val();
		
		formData.trackMileage=false;
		formData.eventId=$('#' + prefix + 'EventId').val();
		
		formData.title=$('#' + prefix + 'TimeslotTitle').val();
		if (formData.title == undefined || formData.title == null) {
			formData.title = "";
		}
		formData.notes=$('#' + prefix + 'Notes').val();
		
		console.log(formData);
//		alert("CHECK");
		
		return formData;
	}
	
	function parseData(self) {
// 		console.log(self);
		this.startTime = $('#editStartTime');
		startTime.val("700");
// 		this.startTime = 1200;

// 		$('#editStartTime').val() = 700;
		
// 		$("#startTime").val(1200);
// 		self.data('starttime').value = "1200";
// 		self.attr("starttime", "1200");
		console.log("in edit " + self.data('starttime'));
		console.log($('#editStartTime').val());
	}
	
	function parseTime(timeString) {
		console.log(timeString);
// 		var timeData = timeString.split(" ");
		
// 		var hr = parseInt(timeData[0]);
// 		var mn = timeData[2];
// 		var merid = timeData[3];

		var hr = parseInt(timeString[0]);
		var mn = parseInt(timeString[1]);
		var merid = timeString[2];
		
		if (merid.toUpperCase().normalize() === "PM" && hr != 12) {
			hr += 12;
		}
		if (merid.toUpperCase().normalize() === "AM" && hr == 12) {
			hr = "00";
		}
		else {
		}
		
// 		console.log(hr);
// 		console.log(mn);
// 		console.log(merid);

		console.log("checking hour length: " + hr.toString().length);
		if(hr.toString().length == 1) {
			console.log("length < 2");
			hr = "0" + hr;
		}
		if(mn.toString().length == 1) {
			console.log("length < 2");
			mn = "0" + mn;
		}
		var intTime = "" + hr + ":" + mn;
		console.log(intTime);
		return intTime;
		
	}
	
// 	function validateForm(obj) {
// 		if (timeToMinutes($('#editStartTime').val()) > timeToMinutes($('#editEndTime').val())) {
// 			console.log("error, start time cannot be after end time");
// 		}
// 	}
	
	function timeToMinutes(time) {
		var result = 0;
		
		var tempTime = parseTime(time);
		var data = tempTime.split(":");
		
		result = parseInt(data[0]) * 60;
		result += parseInt(data[1]);
		console.log("parsed time: " + result);
		return result;
	}
	
	function getDuration(timeStart, timeEnd) {
		var startTimeData = timeStart.split(" ");
		var endTimeData = timeEnd.split(" ");
		
		var startHr = parseInt(startTimeData[0]);
		var startMin = startTimeData[2];
		var startMerid = startTimeData[3];
		
		var endHr = parseInt(endTimeData[0]);
		var endMin = endTimeData[2];
		var endMerid = endTimeData[3];
		
		if(startMerid.toUpperCase().normalize() === "PM") {
			startHr += 12;
		}
		if(endMerid.toUpperCase().normalize() === "PM") {
			endHr += 12;
		}
		
		var totHrs = endHr - startHr;
		var totMins = endMin - startMin;
		if (endMin < startMin) {
			totMins = endMin + (60 - startMin);
		}
		
		var duration = totHrs * 100 + totMins;
		console.log(duration);
		return duration;
		
	}
	
// $(document).ajaxStop(function() {
//     setInterval(function() {
//         location.reload();
//     }, 300);
// });