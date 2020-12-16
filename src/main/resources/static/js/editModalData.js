<script type ="text/javascript">
	$(document).on("click", ".open-modal", function() {
		var x = new Date();
		var myHeading = "<p>I am added Date</p>";
		$("#modal-body").html(myHeading + x);
		$('#modal').modal('show');
	});
</script>