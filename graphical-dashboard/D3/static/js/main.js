// d3 queue
queue()
    // convert to d3 json
	.defer(d3.json, piechartDataUrl)
    .defer(d3.json, barchartDataUrl)
    .await(ready);

// its a callback fn.
// the pieData and BarData come respectively from .defer function's piechartDataUrl and barchartDataUrl
// js, right?
function ready(error, pieData, barData) {
    // why does this guy need the two args?
    d3PieChart(pieData, barData);
    d3BarChart(barData);
}