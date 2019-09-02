<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/charts.css" />
<script type="text/javascript" src="/script/Chart.js"></script>

<div class="pieChart">
	<canvas class="reportCharts divshadow" id="myChart"></canvas>

	<table class="buttonTable">
		<tr>
			<td><button class="fancy-button" type="button" onclick="window.location.href='/#tabs-6'"><b>OK</b></button></td>
			<td><button class="fancy-button" type="button" onclick="renderCanvas()"><b>View/Save Image</b></button></td>
		</tr>
	</table>
</div>
<input id="rpt" type="hidden" value="${report}" />
<script>
	$(document).ready(
			function() {
				var rpt = $('#rpt').val();
				$.getJSON("/rest/" + rpt, function(data) {
					var ctx = $('#myChart');
					var myChart = new Chart(ctx, data);
				}).fail(
						function(jqXHR, textStatus, errorThrown) {
							alert("error " + textStatus + "\n"
									+ "incoming Text " + jqXHR.responseText);
						});

			});

	function renderCanvas() {
		var canvas = document.getElementById("myChart");
		var img = canvas.toDataURL("image/jpg");

		document.write('<img src="'+img+'"/>');
	}
</script>

