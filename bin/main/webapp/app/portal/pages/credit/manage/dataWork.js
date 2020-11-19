$(document).ready(function() {
	//var brGrpTypeId = "030199L";
	var colModel = [
	    { index:'IN_PGM_NM', name: 'IN_PGM_NM', label: '프로그램명', width: 300, align:'left'},
      	{ index:'IN_TGT_TB_NM', name: 'IN_TGT_TB_NM', label: '테이블명', width: 300, align:'left'},
      	{ index:'IN_TGT_TB_CONE', name: 'IN_TGT_TB_CONE', label: '엔티티명', width: 280, align:'left'},
      	{ index:'IN_EXE_SNO', name: 'IN_EXE_SNO', label: '실행차수', width: 100, align:'center'},
      	{ index:'IN_ETL_ERR_CONE', name: 'IN_ETL_ERR_CONE', label: '실행결과', width: 100, align:'center'},      	      
      	{ index:'IN_PRC_CNT', name: 'IN_PRC_CNT', label: '적재건수', width: 120, align:'right'},      	      
      	{ index:'IN_ETL_STT_DTM', name: 'IN_ETL_STT_DTM', label: '시작일시', width: 200, align:'center'},
      	{ index:'IN_ETL_END_DTM', name: 'IN_ETL_END_DTM', label: '종료일시', width: 200, align:'center'},
      	{ index:'IN_ETL_RQM_HUR_NM', name: 'IN_ETL_RQM_HUR_NM', label: '수행시간', width: 130, align:'center'}
    ];

	var opts = {
		"colModel":colModel, 
		pager:"#jqGridPager",
		autowidth:true,
		shrinkToFit:true,
		toppager: false,
		loadonce: true,
		scroll:-1,
		isPaging:false
	};
	
	var $grid = DE.jqgrid.render($("#jqGrid"), opts);
	DE.jqgrid.navGrid($("#jqGrid"), $("#jqGridPager"));
	
	$("button#btnSearch").on("click", function(e) {
		var postData = {
			"queryId":"credit_anatask.dataProcessingList",
			"startDt":DE.util.dateTimePicker.getValue("#startDt")
		};
		DE.jqgrid.reload($("#jqGrid"), postData);
		loadPage();
		loadGraph();
	});
	
	$(this).off("autoResize").on( "autoResize", function( event ) {
		setTimeout(function () {
	    	var heightMargin = 50;
	    	$("#jqGrid").setGridWidth($(".content-body .box-body").width());
	    	$("#jqGrid").setGridHeight($(".content-body").height() - ($(".content-body .box-body").offset().top-$(".content-body").offset().top)-heightMargin);
    	}, 500);
	}).trigger("autoResize");

	var init = function() {
		DE.util.dateTimePicker.picker($("#startDt"));
		$("#startDt").data("DateTimePicker").date(DE.util.dateFormat(new Date()));
        
        $("button#btnSearch").trigger("click");
	};
	init();
});

function loadPage(){
	var param = {"_search":false,"nd":1566802058509,"rows":10,"page":1,"sidx":"","sord":"asc","queryId":"credit_anatask.dataProcessingCntList","searchKey":"_all","searchValue":"","startDt":DE.util.dateTimePicker.getValue("#startDt")}
	$.ajax({
		url:'jqgrid/list', //request 보낼 서버의 경로
	    type:'post', // 메소드(get, post, put 등)
	    contentType: 'application/json; charset=utf-8',
	    dataType: 'json' ,  
	    data:JSON.stringify(param), //보낼 데이터
	    success: function(data) {
	    	var cardHtml = "";
	    	
	    	$.each(data["rows"], function (index, item) {
	    		var gubun = item["IN_MSJ_PVC_NM"];
	    		var gubunImg = "";
	    		if(gubun == 'ODS') gubunImg = "dataset_l0";
	    		if(gubun == 'DW') gubunImg = "dataset";
	    		if(gubun == 'DM') gubunImg = "dataset_l2";
	    		
	    		cardHtml = cardHtml + "<ul class=\"card_Area\">";
	    		cardHtml = cardHtml + "	<li class=\"li_area01\">";
	    		cardHtml = cardHtml + "		<div class=\"cardList_IMG effect\"><img src=\"../assets/images/"+gubunImg+".png\" width=\"60\" height=\"70\"></div>";
	    		cardHtml = cardHtml + "		<div class=\"cardList_Contents_Area\">";
	    		cardHtml = cardHtml + "			<div class=\"cardList_impor\">테이블 : "+item["IN_MSJ_PVC_NM_CNT"]+"본</div>";
	    		cardHtml = cardHtml + "			<div class=\"cardList_impor\">처리시간 : "+item["IN_ETL_STT_DTM"]+" ~ "+item["IN_ETL_END_DTM"]+"</div>";
	    		cardHtml = cardHtml + "		</div>";
	    		cardHtml = cardHtml + "	</li>";
	    		cardHtml = cardHtml + "	<li class=\"li_area02\" id=\"oneChart"+index+"\"></li>";
	    		cardHtml = cardHtml + "</ul>";
	    		
	    		//if(index == 0){
	    		setTimeout(function() {
	    			var w = 100, h = 100;
		    	    var dataStr = "["+item["SUCCESS_CNT"]+", "+item["FAIL_CNT"]+", "+item["STANDBY_CNT"]+", "+item["ING_CNT"]+"]";
			    	var graphData = JSON.parse(dataStr);
			    	var colorData = ["#5a99d3", "#d9722d", "#ffbf00", "#a3a3a3"];
			    	var pie = d3.pie();
			    	var arc = d3.arc().innerRadius(15).outerRadius(30);
			    	 
			    	var svg = d3.select("#oneChart"+index)
			    	    .append("svg")
			    	    .attr("width", w)
			    	    .attr("height", h)
			    	    .attr("id", "graphWrap");
			    	 
			    	var g = svg.selectAll(".pie")
			    	    .data(pie(graphData))
			    	    .enter()
			    	    .append("g")
			    	    .attr("class", "pie")
			    	    //.attr("stroke", "black")
			    	    .attr("transform","translate("+w/2+","+h/2+")");
			    	 
			    	g.append("path")
			    	    .style("fill", function(d, i) {
			    	        return colorData[i];
			    	    }) 
			    	    .transition()
			    	    .duration(400)
			    	    .delay(function(d, i) { 
			    	        return i * 400;
			    	    })
			    	    .attrTween("d", function(d, i) { 
			    	        var interpolate = d3.interpolate(
			    	            {startAngle : d.startAngle, endAngle : d.startAngle}, 
			    	            {startAngle : d.startAngle, endAngle : d.endAngle} 
			    	        );
			    	        return function(t){
			    	            return arc(interpolate(t)); 
			    	        }
			    	    });
			    	 
			    	g.append("text")
			    	.attr("transform", function(d) {
			        var _d = arc.centroid(d);
			        _d[0] *= 1.5;	//multiply by a constant factor
			        _d[1] *= 1.5;	//multiply by a constant factor
			        return "translate(" + _d + ")";
			      })
			      .attr("dy", ".50em")
			      .style("text-anchor", "middle")
			      .text(function(d, i) {
			    	  if(graphData[i] == 0) {
			              return '';
			            }
			        return graphData[i] + '%';
			      });
			    	
			    	var tooltip = d3.select("#oneChart"+index).append("div").attr("class","tooltip2");
			    	tooltip.append("div").attr("class","name");
			    	
			    	g.on("mouseover",function(d, i){
			    		var str = "";
			    		if(i==0) str="성공 : " + graphData[i] + "%";
			    		if(i==1) str="실패 : " + graphData[i] + "%";
			    		if(i==2) str="대기중 : " + graphData[i] + "%";
			    		if(i==3) str="진행중 : " + graphData[i] + "%";
			    		tooltip.select(".name").html(str);                
			    		tooltip.style("display", "block");
			    		
			    	});
			    	g.on("mouseout",function(d){            
			    		tooltip.style("display", "none");
			    	});
			    	
			    	/*svg.append("circle").attr("cx",130).attr("cy",120).attr("r", 6).style("fill", "#5a99d3")
			    	svg.append("circle").attr("cx",130).attr("cy",140).attr("r", 6).style("fill", "#d9722d")
			    	svg.append("circle").attr("cx",130).attr("cy",160).attr("r", 6).style("fill", "#a3a3a3")
			    	svg.append("circle").attr("cx",130).attr("cy",180).attr("r", 6).style("fill", "#ffbf00")
			    	svg.append("text").attr("x", 140).attr("y", 120).text("성공").style("font-size", "13px").style("fill", "#5a99d3").attr("alignment-baseline","middle")
			    	svg.append("text").attr("x", 140).attr("y", 140).text("실패").style("font-size", "13px").style("fill", "#d9722d").attr("alignment-baseline","middle")
			    	svg.append("text").attr("x", 140).attr("y", 160).text("진행중").style("font-size", "13px").style("fill", "#a3a3a3").attr("alignment-baseline","middle")
			    	svg.append("text").attr("x", 140).attr("y", 180).text("대기중").style("font-size", "13px").style("fill", "#ffbf00").attr("alignment-baseline","middle")
			    
			    	svg.append("g")
			    	.attr("transform", "translate(100, 20)")
			    	.append("text")
			    	.text("실행상태 현황").style("text-align", "center")
			    	.attr("class", "title");*/
	    		}, 100);
	    		//}
	    	})
	    	$("#cardList").html(cardHtml);
	    },
	    error: function(err) {
	        alert("error");
	    }
	});
}

function loadGraph(){
	var param = {"_search":false,"nd":1566802058510,"rows":10,"page":1,"sidx":"","sord":"asc","queryId":"credit_anatask.dataProcessingGraphList","searchKey":"_all","searchValue":"","startDt":DE.util.dateTimePicker.getValue("#startDt")}
	$.ajax({
		url:'jqgrid/list', //request 보낼 서버의 경로
	    type:'post', // 메소드(get, post, put 등)
	    contentType: 'application/json; charset=utf-8',
	    dataType: 'json' ,  
	    data:JSON.stringify(param), //보낼 데이터
	    success: function(data) {
	    	$('#cardGraphDiv').html('');
	    	if(data["rows"].length > 0){
		    	var dataStr = "[{";
		    	$.each(data["rows"], function (index, item) {
		    		if(index > 0){
		    			dataStr += ", \""+item["IN_ETL_STT_DTM"] +"\":"+item["SS_CNT"];
		    		}else{
		    			dataStr += "\""+item["IN_ETL_STT_DTM"] +"\":"+item["SS_CNT"];
		    		}
		    	});
		    	dataStr += "}]"; 
		    	
		    	var dataset = JSON.parse(dataStr);
		    	var keys = d3.keys(dataset[0]);
	    	    var data = [];
	    	 
	    	    dataset.forEach(function(d, i) {
    	           data[i] = keys.map(function(key) { 
    	        	   return {x: key, y: d[key]}; 
    	           })
	    	    });
	    	 
	    	    var margin = {left: 20, top: 30, right: 10, bottom: 40};
	    	    var svg = d3.select("#cardGraphDiv").append("svg");
	    	    var width  = parseInt(svg.style("width"), 10) - margin.left - margin.right;
	    	    var height = parseInt(svg.style("height"), 10)- margin.top  - margin.bottom;
	    	 
	    	    var svgG = svg.append("g").attr("transform", "translate(" + margin.left + "," + margin.top + ")");
	    	 
	    	    var xScale = d3.scalePoint()//scaleBand() scaleOrdinal
	    	        .domain(keys).range([0, width]);
	    	 
	    	    var yScale = d3.scaleLinear()
	    	        .domain([0, d3.max(dataset, function(d) { return d3.max(keys, function(key) { return d[key];});})])
	    	        .nice()
	    	        .range([height, 0]);
	    	 
	    	    var colors = d3.scaleOrdinal().range(["RoyalBlue"]);  
	    	    
	    	    svgG.append("g")
	    	        .attr("class", "grid")
	    	        .attr("transform", "translate(0," + height + ")")
	    	        .call(d3.axisBottom(xScale)
	    	            .tickSize(-height)
	    	        ).selectAll("text")  
		    	      .style("text-anchor", "end")
		    	      .attr("dx", "-.8em")
		    	      .attr("dy", ".15em")
		    	      .attr("transform", "rotate(-65)");
	    	 
	    	    svgG.append("g")
	    	        .attr("class", "grid")
	    	        .call(d3.axisLeft(yScale)
	    	            .ticks(10)
	    	            .tickSize(-width)
	    	        );
	    	    
	    	    var line = d3.line()
	    	    	.curve(d3.curveMonotoneX)
	    	        .x(function(d) { return xScale(d.x); })
	    	        .y(function(d) { return yScale(d.y); });
	    	 
	    	    var lineG = svgG.append("g")
	    	        .selectAll("g")
    	        	.data(data)
	    	        .enter().append("g");
	    	 
	    	    lineG.append("path")
	    	        .attr("class", "lineChart")
	    	        .style("stroke", function(d, i) { })
	    	        .attr("d", line);
	    	 
	    	    lineG.selectAll("dot")
	            	.data(function(d) {return d })
	            	.enter().append("circle")
	                .attr("r", 1)
	                .attr('fill', colors)
	                .attr("cx", function(d) { return xScale(d.x) })
	                .attr("cy", function(d) { return yScale(d.y);})
	                .on("mouseover", function() { tooltip.style("display", null); })
	                .on("mouseout",  function() { tooltip.style("display", "none"); })
	                .on("mousemove", function(d) {
	                    tooltip.style("left", (d3.event.pageX+10)+"px");
	                    tooltip.style("top",  (d3.event.pageY-10)+"px");
	                    tooltip.html("time : " + d.x + "<br/>" + "count : " + d.y);
	                });

	    	    var tooltip = d3.select("body")
	            	.append("div")
	            	.attr("class", "toolTip")
	            	.style("display", "none");
	    	    
	    	    svg.append("g")
		    		.attr("transform", "translate("+width/2+", 20)")
		    		.append("text")
		    		.text("시간대별 데이터 처리 현황").style("text-align", "center")
		    		.attr("class", "title");
	    	    
	    	    //svg.call(d3.zoom().on("zoom", function () {
	    	    //	svg.attr("transform", d3.event.transform)
	    	    //}));
	    	}
	    },
	    error: function(err) {
	        alert("error");
	    }
	});
}

function goView(st){
	$('.box-body').hide();
	$('.box-body').eq(st).show();
	$('#cateDiv > span').removeClass('active');
	$('#cateDiv > span').eq(st).addClass('active');
}