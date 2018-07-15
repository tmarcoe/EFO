<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/charts.css" />
<script type="text/javascript" src="/script/Chart.js"></script>

<div class="lineChart">
	<canvas class="reportCharts divshadow" id="myChart"></canvas>
</div>
<table class="buttonTable">
	<tr>
		<td><button class="fancy-button" type="button" onclick="window.history.back()">OK</button></td>
		<td><button class="fancy-button" type="button" onclick="renderCanvas()">View/Save Image</button></td>
	</tr>
</table>
<input id="fromValue" type="hidden" value="${from}"/>
<input id="toValue" type="hidden" value="${to}"/>
<input id="rpt" type="hidden" value="${report}" />

<script>

var ctx = document.getElementById("myChart");
var frm = $("#fromValue").val();
var to =  $("#toValue").val();
var rpt = $("#rpt").val();

$(document).ready(function () { $.getJSON("/rest/" + rpt + "?from=" + frm + "&to=" + to, function(data) {
		var myChart = new Chart(ctx, data);
		}).fail(function(jqXHR, textStatus, errorThrown) {
			alert("error " + textStatus + "\n" + "incoming Text " + jqXHR.responseText);
	    });
	});

function renderCanvas() {
	var canvas = document.getElementById("myChart");
	var img    = canvas.toDataURL("image/png");
	
	document.write('<img src="'+img+'"/>');
}

</script>

