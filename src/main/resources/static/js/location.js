//when client clicks(focus) on search box, select text. when selects from dropdown, fill textbox
$(function locationSearchFocusAndChoose() {
		$("#searchBox").autocomplete({
			source : searchLocations,
			minLength : 2,
			focus: function(event, ui) { // focus for items in search results
				event.preventDefault();
				
			},
			 select: function (event, ui) {
		            event.preventDefault();
		            $("#searchBox").val(ui.item.label); // replace string with selected value
		            $("#editLocation").val(ui.item.value); // set id for saving
			 }
		}).bind('focus', function() {
			$(this).select();
			$(this).autocomplete("search"); } );
// 		focus(function() { // focus for text input box
// 			$(this).select();
			
// 			searchLocations($(this).request, $(this).response);
// 		});
		
	});
	
	function clearLocation() {
		$("#searchBox").val("");
		$("#editLocation").val("");
	}
	
	function searchLocations(request, response) {
		$.ajax ({
			url : "/locations/search",
			dataType : "json",
			data : {
				q : request.term
			},
			success : function(data) {
				console.log(data);
//					response(data);
				var array = data.error ? [] : $.map(data, function(m){
					console.log("M");
					console.log(m.description);
					return {
						label: m.description + ': ' + m.street + ', ' + m.city + ', ' + m.state + ', ' + m.zipcode ,
						value: m.id
					};
				});
				
				response(array);
			}
		});
	}
	
//	function toggleCreateLocationDisplay() {
//		var addLocationDiv = $("#addNewLocation")[0];
//		console.log(addLocationDiv);
//		console.log(addLocationDiv.style.display);
//		if (addLocationDiv.style.display === "none") {
//			addLocationDiv.style.display = "block";
//		}
//		else {
//			addLocationDiv.style.display = "none";
//		}
//	}
	
	function toggleShowPhotographers() {
		toggleModalDivDisplay('showPhotographers');
		
		var div = $("#" + 'showPhotographers')[0];
		var displayStyle = div.style.display.toLowerCase();
		var button = $("#show-photographers-button")[0];
		console.log("button");
		console.log(button.value);
		if (displayStyle == "none") {
			button.innerHTML = "Show Photographers";
		}
		else {
			button.innerHTML = "Hide Photographers";
		}
	}
	
	function toggleModalDivDisplay(idName) {
		console.log(idName);
		var addDiv = $("#" + idName)[0];
		console.log(addDiv);
		console.log(addDiv.style.display);
		if (addDiv.style.display === "none") {
			addDiv.style.display = "block";
		}
		else {
			addDiv.style.display = "none";
		}
	}
	
	
	$(document).on("click", "#cancel-add-location", function (event) {
	event.preventDefault();
	clearErrors();
	toggleCreateLocationDisplay();
	
});

$(document).on("click", "#timeslot-new-location-form-submit", function ajaxCreateNewLocation(event) {
	event.preventDefault();
	
//		validateForm(this);
	
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	
	var _self = $('#timeslot-new-location-form');
	
	var locationData = {};
	locationData.id = null;
	locationData.description="";
	locationData.street="";
	locationData.city="";
	locationData.state ="";
	locationData.zipcode=0;
	
	locationData.description=$('#description').val();
	locationData.street=$('#street').val();
	locationData.city=$('#city').val();
	locationData.state =$('#state').val();
	locationData.zipcode=$('#zipcode').val();
	
	console.log("data here");
	console.log(locationData);
	console.log(JSON.stringify(locationData));

	clearErrors();
	
	const RELOAD_TIMER = 10;
	
	$.ajaxSetup({
        headers: {
            'X-CSRF-TOKEN': '<?= csrf_token() ?>'
        }
    });
	console.log("before ajaxNew");
	$.ajax({
		type: "POST",
		contentType: "application/json",
		url: "/locations/",
		data: JSON.stringify(locationData),

		dataTpye: 'JSON',
		
		beforeSend: function(xhr) {
			console.log("before req: " + token);
			xhr.setRequestHeader(header, token);
			console.log("token set");
		},
		
		success: function(response) {
			if (response !== true) {
				console.log("ERROR");
			}
// 			$('#rsp_start_time_new').text("");
			console.log("ajax1New");
			console.log(response);
			console.log(response.id);
			
			// set the hidden location id
            $("#editLocation").val(response.id); // set id for saving
			// set the location name string
			$("#searchBox").val(response.description + ": " + response.street + ", " + response.city + ", " + response.state + ", " + response.zipcode); // replace string with selected value
			// hide new location
			toggleCreateLocationDisplay();
			
// 			console.log(locationData.eventId);
// 			location.href = URL_add_parameter(location.href, "eventId", formData.eventId);
			
// 			 window.setTimeout(function(){window.location.reload()}, 3000);
			if (response.redirect) {
				//alert(location.href);
				//window.location.href = response.redirect;
			}
			
			//setTimeout(function() {
				//location.reload();
				//}, RELOAD_TIMER);
		},
		error: function (e) {
// 			$('#rsp_start_time_new').text(e.responseJSON.rsp_start_time);
			console.log("error from ajax request");
			console.log(e.responseJSON);
			var errorData = e.responseJSON;
// 			if (errorData.lenght = un)
			for (var i = 0; i < errorData.length; i++) {
				var err =  errorData[i].split(":");
				console.log(errorData[i]);
				var target = $("[id^=" + err[0] + "-error]");
				console.log($("[id^=" + err[0] + "-error]"));
				if(target.length > 0) {
					// if location error, change class css
					if (err[0] == "already-exists") {
						target.addClass('alert alert-danger');
					}
					if (target.text().length > 0) {
						target.text(target.text() +", " + err[1]);
					}
					else {
						target.text(err[1]);
					}
					
				}
// 				description-error
				console.log(errorData[i]);
			}
		}
	});

});

function clearErrors() {
	var errors = $("[id$='-error']");
	for (i = 0; i < errors.length; i++) {
		console.log(errors[i]);
		var target = $("#" + errors[i].id);
		target.empty();
// 		$("#" + errors[i].id).empty();
		// remove danger class
		$("#already-exists-error").removeClass('alert alert-danger');
// 		errors[i].text = '';
	}
}