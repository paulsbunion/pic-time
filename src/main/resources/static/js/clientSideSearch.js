function clientSideSearch(searchBarId, numColumns) {
	
	// remove extra white space and uppercase
	var searchBarString = $("#" + searchBarId).val().toUpperCase().replace(/\s\s+/g, ' ');

	// get row data to search
	var $rows = $("tbody tr"); 
	
	$rows.each(function(index, row) {
		td = row.getElementsByTagName("td");
		
		var match = false;
		for (i = 0; i < td.length && i < numColumns; i++) {
			tdText = td[i].innerText.replace(/\s\s+/g, ' ');
			
			if (tdText.toUpperCase().indexOf(searchBarString) > -1) {
				match = true;
				break;
			}
			else {
				match = false;
			}
		}
		
		if (match) {
			row.style.display = "";
		}
		else {
			row.style.display = "none";
		}
	});
}

function clearSearchBar(searchBarId, numColumns) {
	$("#" + searchBarId).val("");
	clientSideSearch(searchBarId, numColumns);
}