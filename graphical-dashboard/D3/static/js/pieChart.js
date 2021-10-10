// look up more formats in https://github.com/d3/d3-format#formatLocale
var formatAsInteger = d3.format(",");

function d3PieChart(pieData, barData){

    var margin = {top: 30, right: 5, bottom: 20, left: 50};
    var width = 400 - margin.left - margin.right ,
        height = 400 - margin.top - margin.bottom,

        // radius should be half of the diameter which is equal to height and width ofc.
        outerRadius = Math.min(width, height) / 2,

        innerRadius = outerRadius * .999,

        // min rad for the SURVIVED PASSENGERS part.
        innerRadiusFinal = outerRadius * .5,
        // 0.1 closer for when hover happens
        innerRadiusFinal3 = outerRadius * .4,
        // color from the ones prebuilt in d3
        color = d3.scaleOrdinal(d3.schemeCategory10);

    var vis = d3.select("#pieChart")
         // add a svg to the pieChart div.
	    .append("svg:svg")
	    .data([pieData])
	    .attr("width", width)
	    .attr("height", height)
	    // :g to hold part together
	    .append("svg:g")
	    // center the circle
	    .attr("transform", "translate(" + outerRadius + "," + outerRadius + ")");

    var arc = d3.arc()
        .outerRadius(outerRadius).innerRadius(0);

    var arcFinal = d3.arc().innerRadius(innerRadiusFinal).outerRadius(outerRadius);
    var arcFinal3 = d3.arc().innerRadius(innerRadiusFinal3).outerRadius(outerRadius);

    var pie = d3.pie()
        .value(function(d) { return d.measure; });

    var arcs = vis.selectAll("g.slice")
        // the data is already selected in vis
        .data(pie)
        .enter()
        .append("svg:g")
        .attr("class", "slice")
        .on("mouseover", mouseover)
    	.on("mouseout", mouseout)
    	.on("click", up);

    arcs.append("svg:path")
        .attr("fill", function(d, i) { return color(i); } )
        .attr("d", arc)
		.append("svg:title")
        .text(function(d) { return d.data.category + ": " + formatAsInteger(d.data.measure)+"%"; });

    d3.selectAll("g.slice").selectAll("path").transition()
		.duration(750)
		.delay(10)
        .attr("d", arcFinal );


    arcs.filter(function(d) { return d.endAngle - d.startAngle > .2; })
    .append("svg:text")
    .attr("dy", ".35em")
    .attr("text-anchor", "middle")
    .attr("transform", function(d) { return "translate(" + arcFinal.centroid(d) + ")"; })
    .text(function(d) { return d.data.category; });

    vis.append("svg:text")
        .attr("dy", ".35em")
        .attr("text-anchor", "middle")
        .text("Survived passengers")
        .attr("class","title");

    function mouseover() {
        d3.select(this).select("path").transition()
        .duration(750)
            .attr("d", arcFinal3);
    }

    function mouseout() {
        d3.select(this).select("path").transition()
        .duration(750)
        .attr("d", arcFinal);
    }

    function up(d, i) {
        updateBarChart(d.data.category, color(i), barData);
     }
}