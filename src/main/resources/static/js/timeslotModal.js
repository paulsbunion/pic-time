$(document).on("click", "#newTimeslotModal", function (event) {
	   event.preventDefault();
	   
	var _self = $(this);
//	console.log(_self);
	
	var eventId = $("#sel").val();
	// if id null, call create event
	if (eventId == '' || eventId == null) {
		window.location.href = document.getElementById("newEventAnchor").href;
	}
	else {
		clearResponseErrorCodes("edit");
//		$('#rsp_start_time_new').text("");
//		console.log("value");
//		console.log($("#sel").val());
		
//		var eventId = _self.data('event-id');
		var timeslotId = null;

		var d = new Date();
		var hour = d.getHours();
		var min = roundToMultipleOfFive(d.getMinutes());
		var time = hour + ":" + min;
		var startTime = time;
		var endTime = null;
		var title = "";
		var notes = "";
		var location = null;
		var locationId = null;
		var data = $("#all_photogs_eventId_" + eventId).data("event-photographers");
		var availablePhotographers = [];
		var assignedPhotographers = createJSONfromPhotographerData(data).photographers;
		
		var modalData = {"eventId": eventId, "timeslotId": timeslotId, "startTime":startTime,
				"endTime":endTime, "title":title, "notes":notes, "location":location, "locationId":locationId,
				"availablePhotographers":availablePhotographers, "assignedPhotographers":assignedPhotographers};
		
		populateModalData(modalData);
		
		
		$("#editEventId").val($("#sel").val());
	     $('#editModal').modal('show');
  	}
	});


function roundToMultipleOfFive(time) {
	var temp = Math.floor(time / 5) * 5;
	temp = "" + temp;
	if (temp.length < 2) {
		temp = "0" + temp;
	}
	return temp;
}

function createJSONfromPhotographerData(data) {
	var staff = {};
	var photographers =[];
	staff.photographers = photographers;
	
	$.each(data, function(i, val) {
		var photographer = {"id":val.id, "firstName":val.firstName, "lastName":val.lastName};
		staff.photographers.push(photographer);
	});
	
	return staff;
}


	$(document).on("click", "#edit_event_button", function (event) {
		event.preventDefault();
		var eventId = $("#sel").val();
		window.location='/mvc/events/edit/' + eventId;
	});

   $(document).on("click", ".open-EditModal", function (event) {
	var _self = $(this);
	var eventId = _self.data('event-id');
	var timeslotId = _self.data('id');
	var startTime = _self.data('starttime');
	var endTime = _self.data('endtime');
	var title = _self.data('timeslottitle');
	var notes = _self.data('notes');
	var location = _self.data('location');
	var locationId = _self.data('location-id');
	

	var availablePhotographers = $("#avail_photogs_timeslot_" + timeslotId).data('avail_photogs');
	var assignedPhotographers = $("#assigned_photogs_timeslot_" + timeslotId).data('assigned_photogs');
	
	var modalData = {"eventId": eventId, "timeslotId": timeslotId, "startTime":startTime,
			"endTime":endTime, "title":title, "notes":notes, "location":location, "locationId":locationId,
			"availablePhotographers":availablePhotographers, "assignedPhotographers":assignedPhotographers};
	
	populateModalData(modalData);
	
     $('#editModal').modal('show');
    });
   
function populateModalData(modalData) {
	// clear
	$("#edit_avail_photogs").html('');
	
	appendPhotographers(modalData.assignedPhotographers, true);
	appendPhotographers(modalData.availablePhotographers, false);

	//$('#rsp_start_time').text("");
		clearResponseErrorCodes("edit");
	$("#editEventId").val(modalData.eventId);
	$("#editTimeslotId").val(modalData.timeslotId);
	
	$("#editTimeslotTitle").val(modalData.title);
	var startTimeSplit = modalData.startTime.split(":");
	var endTimeSplit = [null,null];
	if (modalData.endTime != undefined) {
		endTimeSplit = modalData.endTime.split(":");
	}
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
	if (endTimeSplit[0] == null) {
		$("#editEndTimeMeridian").val("");
	}
	
	$("#editStartTimeHr").val(startTimeSplit[0]);
	$("#editStartTimeMin").val(startTimeSplit[1]);
	
	$("#editEndTimeHr").val(endTimeSplit[0]);
	$("#editEndTimeMin").val(endTimeSplit[1]);
	
	$("#editNotes").val(modalData.notes);
	//	 var x = new Date(); 
	
	if (modalData.location != null) {
		$("#searchBox").val(modalData.location);
		$("#editLocation").val(modalData.locationId);
	}
	else {
		$("#searchBox").val("");
		$("#editLocation").val("");
	}
}


function appendPhotographers(photographers, assigned) {
	if (typeof photographers !== "undefined" && photographers != null && photographers.length > 0) {
		// populate modal data
		for (i = 0; i < photographers.length; i++) {
			p = photographers[i];
			appendPhotographer(p, assigned);
		}
	}
}
   
function appendPhotographer(p, assigned) {
	var checked = "";
	var divTargetId = "#edit_avail_photogs";
	if (assigned == true) {
		var checked = "checked";
	}
	var divStart = "<div class=\"form-group show-photographers\">";
	var content = "<span class=\"show-photographers-item\">" + p.firstName + " " + p.lastName + "</span>";
	var switchContent = "<label class=\"switch\">" +  
		"<input type=\"checkbox\" name=\"Auto Remind\" id=\"timeslot_photog_" + p.id + "\" " + checked + ">" +  
		"<span class=\"slider round\"></span>" + 
	"</label>";
	var divEnd ="</div>";
	
	$(divTargetId).append(divStart + switchContent + content + divEnd);
}
   

   	$(document).on("click", "#edit-modal-submit", function (event) {
		event.preventDefault();
		if (!validEndTime("edit")) {
			return false;
		}
		
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		
		var selectedPhotographers = $('[id^="timeslot_photog_"]:checked');
		
		// clear old photographers
		$("#photographers option").remove();
		
		if (selectedPhotographers.length == 0) {}
		else {
			var photogs = $("#photographers");
			// add new data
			for (i = 0; i < selectedPhotographers.length; i++) {
				var id = $(selectedPhotographers[i]).attr('id').split('timeslot_photog_')[1];
				photogs[0].options.add(new Option(id, id));
			}
			$("#photographers option").prop('selected', true);
		}

		var formData = {};
		formData = getFormData("edit");
		
		const RELOAD_TIMER = 10;
		
		$.ajaxSetup({
            headers: {
                'X-CSRF-TOKEN': '<?= csrf_token() ?>'
            }
        });

		if (formData.id == null || formData.id == undefined || formData.id == "") {
//			set to null for jackson mapping
			formData.id = null;
			$.ajax({
				type: "POST",
				contentType: "application/json",
				url: "/events/" + $('#editEventId').val() + "/timeslots/",
				data: JSON.stringify(formData),

				dataTpye: 'JSON',
				
				beforeSend: function(xhr) {
					xhr.setRequestHeader(header, token);
				},
				
				success: function(response) {
//					$('#rsp_start_time_new').text("");
					clearResponseErrorCodes("edit");
					location.href = URL_add_parameter(location.href, "eventId", formData.eventId);
					
					 window.setTimeout(function(){window.location.reload()}, 3000);
					if (response.redirect) {
						//alert(location.href);
						//window.location.href = response.redirect;
					}
				},
				error: function (e) {
					$('#rsp_start_time_edit').text(e.responseJSON.rsp_start_time);
					$('#rsp_end_time_edit').text(e.responseJSON.rsp_end_time);
				}
			});
		}
		else {
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
					clearResponseErrorCodes("edit");
	//				$('#rsp_start_time').text("");
					location.href = URL_add_parameter(location.href, "eventId", formData.eventId);
					if (response.redirect) {
						window.location.href = response.redirect;
					}
					
					// causes error reloading event selected page with no changes
					setTimeout(function() {
						location.reload();
						}, RELOAD_TIMER);
				},
				error: function (e) {
					$('#rsp_start_time_edit').text(e.responseJSON.rsp_start_time);
					$('#rsp_end_time_edit').text(e.responseJSON.rsp_end_time);
				}
			});
		}

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
//		if (prefix == "edit") {
			prefix = "edit";
			
			// check if need to clear location id
			if ($("#searchBox").val() == "") {
				$("#editLocation").val("");
			}
			
			var query = $("#" + prefix + "TimeslotId").val();
			// set existing id
			formData.id =  $("#" + prefix + "TimeslotId").val();
//		}
		var endTime = [null];
		var endTimeHr = $('#' + prefix + 'EndTimeHr').val();
		var endTimeMin = $('#' + prefix + 'EndTimeMin').val();
		
		if (endTimeHr != "" && endTimeMin != "") {
			var endTime = [$('#' + prefix + 'EndTimeHr').val(), $('#' + prefix + 'EndTimeMin').val(), 
				$('#' + prefix + 'EndTimeMeridian').val()];
		}
		
		var startTime = [$('#' + prefix + 'StartTimeHr').val(), $('#' + prefix + 'StartTimeMin').val(), 
			$('#' + prefix + 'StartTimeMeridian').val()];
//		var endTime = [$('#' + prefix + 'EndTimeHr').val(), $('#' + prefix + 'EndTimeMin').val(), 
//			$('#' + prefix + 'EndTimeMeridian').val()];
		formData.time.startTime = parseTime(startTime);
		formData.time.endTime =  parseTime(endTime);
		
		formData.client=null;
		formData.photographers=[];
		var photogs = $("#photographers :selected");
		if (typeof photogs !== undefined && photogs != null) {
			photogs.each(function() {
				formData.photographers.push(parseInt(this.value));
			});
		}
		
		formData.locationId=$('#' + prefix + 'Location').val();
		
		formData.trackMileage=false;
		formData.eventId=$('#' + prefix + 'EventId').val();
		
		formData.title=$('#' + prefix + 'TimeslotTitle').val();
		if (formData.title == undefined || formData.title == null) {
			formData.title = "";
		}
		formData.notes=$('#' + prefix + 'Notes').val();
		
		return formData;
	}
	
	function parseTime(timeString) {
		if(timeString[0] == null) {
			return null;
		}
		var hr = parseInt(timeString[0]);
		var mn = parseInt(timeString[1]);
		var merid = timeString[2];
		
		if (merid.toUpperCase().normalize() === "PM" && hr != 12) {
			hr += 12;
		}
		if (merid.toUpperCase().normalize() === "AM" && hr == 12) {
			hr = "00";
		}
		else {}
		
		if(hr.toString().length == 1) {
			hr = "0" + hr;
		}
		if(mn.toString().length == 1) {
			mn = "0" + mn;
		}
		var intTime = "" + hr + ":" + mn;
		return intTime;
		
	}
	
	function timeToMinutes(time) {
		var result = 0;
		
		var tempTime = parseTime(time);
		var data = tempTime.split(":");
		
		result = parseInt(data[0]) * 60;
		result += parseInt(data[1]);
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
		return duration;
		
	}
	
	function validEndTime(modalType) {
		clearResponseErrorCodes(modalType);
		var endTime = [null];
		var endTimeHr = $('#' + modalType + 'EndTimeHr').val();
		var endTimeMin = $('#' + modalType + 'EndTimeMin').val();
		var merid = $('#' + modalType + 'EndTimeMeridian').val();
		
		var endTimeHrSet = !(endTimeHr == "" || endTimeHr == null);
		var endTimeMinSet = !(endTimeMin == "" || endTimeMin == null);
		var meridSet = !(merid == "" || merid == null);
		var allSet = endTimeHrSet && endTimeMinSet && meridSet;
		var allClear = !endTimeHrSet && !endTimeMinSet && !meridSet;
				
		if (allSet || allClear) {
			return true;
		}
		else {
			$('#rsp_end_time_' + modalType).text(" invalid, set or clear all values");
			return false;
		}
	}
	
	function clearResponseErrorCodes(modalType) {
		$('#rsp_start_time_' + modalType).text("");
		$('#rsp_end_time_' + modalType).text("");
	}
	
	$(document).on("click", "#clear_endtime_edit", function(event) {
		event.preventDefault();
		clearEndTime("edit");
	});
	
	function clearEndTime(modalType) {
		$("#" + modalType + "EndTimeHr").val('');
		$("#" + modalType + "EndTimeMin").val('');
		$("#" + modalType + "EndTimeMeridian").val('');
	}
	
	$(function() {
		var $baseCost = $("#baseCost");
//		var $amount = $("#amount");
//		$amount.val($baseCost.val());
		
		addCommas();
		
		var $form = $("#form");
		
		$baseCost.on( "keyup", function( event ) {
			
			
			// When user select text in the document, also abort.
			var selection = window.getSelection().toString();
			if ( selection !== '' ) {
				return;
			}
			
			// When the arrow keys are pressed, abort.
			if ( $.inArray( event.keyCode, [38,40,37,39] ) !== -1 ) {
				return;
			}
			
			
			var $this = $( this );
			
			// Get the value.
			var input = $this.val();
			
			input = input.replace(/[\D\s\._\-]+/g, "");
					input = input ? parseInt( input, 10 ) : 0;

					$this.val( function() {
						return ( input === 0 ) ? "" : input.toLocaleString( "en-US" );
					} );
		} );
		
		
		$form.on( "submit", function( event ) {
			
			var amountWithCommas = $baseCost.val();
			amountWithCommas = amountWithCommas.replace(/[($)\s\._,\-]+/g, ''); // Sanitize the values.
			$baseCost.val(amountWithCommas);
			
			
////			event.preventDefault();
//			console.log("submit");
//			var $this = $( this );
//			var arr = $this.serializeArray();
//		
//			for (var i = 0; i < arr.length; i++) {
//					arr[i].value = arr[i].value.replace(/[($)\s\._,\-]+/g, ''); // Sanitize the values.
//			};
//			
//			console.log( arr );
			
//			event.preventDefault();
		});
	});
	
	function addCommas() {
		var $amount = $("#baseCost");
		var amount = $amount.val();
		
		amount = amount.replace(/[\D\s\._\-]+/g, "");
		amount = amount ? parseInt( amount, 10 ) : 0;
		console.log(amount.toLocaleString( "en-US" ));

		$amount.val( function() {
					return ( amount === 0 ) ? "" : amount.toLocaleString( "en-US" );
				} );
	}