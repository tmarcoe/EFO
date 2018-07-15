<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/charts.css" />
<script type="text/javascript" src="/script/Chart.js"></script>

<div class="barChart">
	<canvas class="reportCharts divshadow" id="myChart"></canvas>
</div>
<table class="buttonTable">
	<tr>
		<td><button class="fancy-button" type="button" onclick="window.history.back()">OK</button></td>
		<td><button class="fancy-button" type="button" onclick="renderCanvas()">View/Save Image</button></td>
	</tr>
</table>

<script>
	var ctx = document.getElementById("myChart");
	var frm = ${from};
	var to = ${to};
	var rpt = ${report};

	$(document).ready(
			function() {
				$.getJSON("/rest/" + rpt + "/from/" + frm + "/to/" + to, function(data) {
					var myChart = new Chart(ctx, data);
				}).fail(
						function(jqXHR, textStatus, errorThrown) {
							alert("error " + textStatus + "\n"
									+ "incoming Text " + jqXHR.responseText);
						});
			});

	function renderCanvas() {
		var canvas = document.getElementById("myChart");
		var img = canvas.toDataURL("image/png");

		document.write('<img src="'+img+'"/>');
	}
</script>

